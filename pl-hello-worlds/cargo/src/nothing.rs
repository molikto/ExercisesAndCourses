
enum List {
    Node(u32, Box<List>),
    Nil
}

fn main() {

    let mut x = box 5i;
    {
        let y = &mut x;
        println!(*y + "");
    }
    println!(*x + "");
}
