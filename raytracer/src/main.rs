use nalgebra::Vector3;
use std::f64::consts::PI;
use rand::{Rng, random};

type Vec3 = Vector3<f64>;
type Color = Vector3<f32>;


fn random_f64() -> f64 {
    let mut rng = rand::thread_rng();
    rng.gen_range(0.0, 1.0)
}

fn random_in_unit_sphere() -> Vec3 {
    let mut rng = rand::thread_rng();
    loop {
        let v: Vec3 = Vec3::new(
            rng.gen_range(0.0, 1.0),
            rng.gen_range(0.0, 1.0),
            rng.gen_range(0.0, 1.0),
        );
        if v.magnitude_squared() < 1.0 {
            break v
        }
    }
}

fn random_on_unit_sphere() -> Vec3 {
    let mut rng = rand::thread_rng();
    let a: f64 = rng.gen_range(0.0, 2.0 * PI);
    let z: f64 = rng.gen_range(-1.0, 1.0);
    let r: f64 = (1.0 - z * z).sqrt();
    let x: f64 = r * a.cos();
    let y: f64 = r * a.sin();
    Vec3::new(x, y, z)
}


///
/// RAY
///
#[derive(Copy, Clone, Debug)]
pub struct Ray {
    pub origin: Vec3,
    pub direction: Vec3
}

impl Ray {
    pub fn at(&self, t: f64) -> Vec3 {
        &self.origin + t * &self.direction
    }
}

#[derive(Copy, Clone, Debug)]
pub struct HitRecord {
    pub point: Vec3,
    pub outside: bool,
    pub normal: Vec3,
    pub t: f64,
}



///
/// SHAPE
///

#[derive(Copy, Clone, Debug)]
pub struct Square {
    pub center: Vec3,
    pub radius: f64
}


impl Square {
    pub fn hit(&self, ray: &Ray, t_min: f64, t_max: f64) -> Option<HitRecord> {
        let amc: Vec3 = &ray.origin - &self.center;
        let a = ray.direction.magnitude_squared();
        let b = ray.direction.dot(&amc);
        let c = amc.magnitude_squared() - self.radius * self.radius;
        let discriminant = b*b - a*c;
        if discriminant > 0.0 {
            let d_sqrt = discriminant.sqrt();
            let t = (-b - d_sqrt) / a;
            let t = if t > t_min && t < t_max {
                Some(t)
            } else {
                let t = (-b + d_sqrt) / a;
                if t > t_min && t < t_max {
                    Some(t)
                } else {
                    Option::None
                }
            };
            t.map(|t| {
                let point = ray.at(t);
                let mut normal: Vec3 = (&point - &self.center) / self.radius;
                let outside = normal.dot(&ray.direction) <= 0.0;
                if !outside { normal = -normal; }
                HitRecord { point, normal, outside, t }
            })
        } else {
            Option::None
        }
    }
}

#[derive(Copy, Clone, Debug)]
enum Shape {
    Square(Square)
}
impl Shape {
    pub fn hit(&self, ray: &Ray, t_min: f64, t_max: f64) -> Option<HitRecord> {
        match self {
            Shape::Square(s) => s.hit(ray, t_min, t_max),
        }
    }
}





///
/// MATERIAL
///

#[derive(Copy, Clone, Debug)]
enum Material {
    Lambertian { color: Color },
    Metal { color: Color, fuzz: f64 },
    Dielectric { refraction_index: f64 }
}

#[derive(Copy, Clone, Debug)]
struct Scatter {
    attenuation: Color,
    ray: Ray
}

#[inline]
fn refract(uv: &Vec3, n: &Vec3, eta_over_etat: f64) -> Vec3 {
    // not understanding, just copying
    let cos_theta = (-uv).dot(n);
    let r_out_parallel: Vec3 = eta_over_etat * (uv + cos_theta * n);
    let r_out_perp : Vec3 = -(1.0 - r_out_parallel.magnitude_squared()).sqrt() * n;
    r_out_parallel + r_out_perp
}

#[inline]
fn reflect(v: &Vec3, n: &Vec3) -> Vec3 {
    v - 2.0 * v.dot(n) * n
}

fn schlick(cosine: f64, ref_idx: f64) -> f64 {
    let r0 = (1.0-ref_idx) / (1.0+ref_idx);
    let r0 = r0*r0;
    r0 + (1.0-r0)*(1.0 - cosine).powi(5)
}


impl Material {
    fn scatter(&self, ray: &Ray, hit: &HitRecord) -> Scatter {
        match self {
            Material::Lambertian { color } => {
                Scatter {
                    attenuation: *color,
                    ray: Ray {
                        origin: hit.point,
                        direction: hit.normal + random_on_unit_sphere()
                    }
                }
            },
            Material::Metal { color, fuzz } => {
                let n = hit.normal;
                let v: Vec3 = ray.direction.normalize();
                let reflected = reflect(&v, &n);
                Scatter {
                    attenuation: *color,
                    ray: Ray {
                        origin: hit.point,
                        direction: reflected + random_in_unit_sphere() * *fuzz
                    }
                }
            }
            Material::Dielectric { refraction_index } => {
                let refraction_index = *refraction_index;
                let eta_over_etat: f64 = if hit.outside {
                    1.0 / refraction_index
                } else {
                    refraction_index
                };
                let v: Vec3 = ray.direction.normalize();
                // something I don't bother to understand
                let cos_theta = (-v).dot(&hit.normal).min(1.0);
                let sin_theta = (1.0 - cos_theta * cos_theta).sqrt();
                let is_reflect = eta_over_etat * sin_theta > 1.0
                    || random_f64() < schlick(cos_theta, eta_over_etat);
                Scatter {
                    attenuation: Color::new(1.0, 1.0, 1.0),
                    ray: Ray {
                        origin: hit.point,
                        direction: if is_reflect {
                                reflect(&v, &hit.normal)
                            } else {
                                refract(&v, &hit.normal, eta_over_etat)
                            }
                    }
                }
            }
        }
    }
}


///
/// WORLD
///

struct Object {
    shape: Shape,
    material: Material
}

struct World {
    pub shapes: Vec<Object>
}


impl World {
    pub fn hit(&self, ray: &Ray, t_min: f64, t_max: f64) -> Option<(HitRecord, Material)> {
        let mut res: Option<(HitRecord, Material)> = Option::None;
        for obj in &self.shapes {
            let max = res.as_ref().map(|n| n.0.t).unwrap_or(t_max);
            for r in obj.shape.hit(ray, t_min, max) {
                res = Some((r, obj.material))
            }
        }
        res
    }
}


///
/// CAMERA
///

struct Camera {
    origin: Vec3,
    bottom_left_from_origin: Vec3,
    horizontal: Vec3,
    vertical: Vec3
}

impl Camera {
    pub fn new(
        look_from: Vec3,
        look_at: Vec3,
        vup: Vec3,
        vertical_fov: f64,
        aspect_ratio: f64,
    ) -> Camera {
        let h = (vertical_fov / 2.0).tan();
        let viewport_height: f64 = 2.0 * h;
        let viewport_width = aspect_ratio * viewport_height;

        let w: Vec3 = (look_from - look_at).normalize();
        let u: Vec3 = vup.cross(&w).normalize();
        let v = w.cross(&u);
        let origin = look_from;
        let horizontal: Vec3 = viewport_width * u;
        let vertical: Vec3 = viewport_height * v;
        let bottom_left_from_origin = - horizontal / 2.0 - vertical / 2.0 - w;
        Camera {
            origin, bottom_left_from_origin, horizontal, vertical
        }
    }

    pub fn ray(&self, i: f64, j: f64) -> Ray {
        Ray {
            origin: self.origin,
            direction: &self.bottom_left_from_origin + j * &self.vertical + i * &self.horizontal
        }
    }
}


///
/// RENDERER
///

fn ray_color(world: &World, ray: &Ray, depth: i32) -> Color {
    if depth <= 0 {
        Color::new(0.0, 0.0, 0.0)
    } else {
        let hit_point = world.hit(ray, 0.001 /* fix shadow acne */, f64::INFINITY);
        match hit_point {
            Some((hit_point, material)) => {
                let scatter = material.scatter(ray, &hit_point);
                scatter.attenuation.component_mul(&ray_color(world, &scatter.ray, depth - 1))
                // let normal: Vec3 = hit_point.normal.normalize();
                // 0.5 * Color::new(
                //     normal.x + 1.0,
                //     normal.y + 1.0,
                //     normal.z + 1.0
                // )
            },
            None => {
                let unit = ray.direction.normalize();
                let t = (0.5 * (unit.y + 1.0)) as f32;
                (1.0 - t) * Color::new(1.0, 1.0, 1.0) + t * Color::new(0.5, 0.7, 1.0)
            },
        }
    }
}

fn main() {
    let image_width = 384;
    let image_height = image_width * 9 / 16;
    println!("P3\n{} {}\n255", image_width, image_height);
    let viewport_height = 2.0;
    let camera = Camera::new(
        Vec3::new(-2.0, 2.0, 1.0),
        Vec3::new(0.0, 0.0, -1.0),
        Vec3::new(0.0, 1.0, 0.0),
        20.0 * PI / 180.0,
        (image_width as f64) / (image_height as f64));
    let world = World {
        shapes: vec![
            Object {
                shape: Shape::Square(Square {
                    center: Vec3::new(0.0, 0.0, -1.0),
                    radius: 0.5
                }),
                //material: Material::Dielectric {refraction_index: 2.4 }
                 material: Material::Lambertian { color: Color::new(0.7, 0.3, 0.3) }
            },
            Object {
                shape: Shape::Square(Square {
                    center: Vec3::new(1.0, 0.0, -1.0),
                    radius: 0.5
                }),
                material: Material::Metal { color: Color::new(0.8, 0.8, 0.8), fuzz: 1.0 }
            },
            Object {
                shape: Shape::Square(Square {
                    center: Vec3::new(-1.0, 0.0, -1.0),
                    radius: 0.5
                }),
                material: Material::Dielectric {refraction_index: 1.5 }
                //material: Material::Metal{  color: Color::new(0.8,0.6,0.2) , fuzz: 0.3 }
            },
            Object {
                shape: Shape::Square(Square {
                    center: Vec3::new(0.0, -100.5, -1.0),
                    radius: 100.0
                }),
                material: Material::Lambertian { color: Color::new(0.8, 0.8, 0.0) }
            },
        ]
    };
    let sample_size = 200;
    let ray_depth = 50;
    let mut rng = rand::thread_rng();
    for j in 0 .. image_height {
        for i in 0 .. image_width {
            eprint!("line {}, {}\r", j, i);
            let mut color: Color = Color::new(0.0, 0.0, 0.0);
            for _ in 0 .. sample_size {
                let j = ((image_height - j - 1) as f64 + rng.gen_range(0.0, 1.0)) / image_height as f64;
                let i = (i as f64 + rng.gen_range(0.0, 1.0)) / image_width as f64;
                let ray = camera.ray(i, j);
                color += ray_color(&world, &ray, ray_depth);
            }
            color /= sample_size as f32;
            let color = palette::GammaSrgb::new(color.x, color.y, color.z).into_linear();
            // TODO why 255.99??
            println!("{} {} {}",
                     (color.red * 255.99) as i32,
                     (color.green * 255.99) as i32,
                     (color.blue * 255.99) as i32);
        }
    }
}
