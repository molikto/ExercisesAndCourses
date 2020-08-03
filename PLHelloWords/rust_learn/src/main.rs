use std::io;
use rand::Rng;
use std::cmp::Ordering;
use std::net::TcpListener;
use std::collections::{HashMap, BTreeSet, HashSet};
use std::io::empty;


pub mod schedule {
    use std::collections::{HashMap, BTreeSet, HashSet};

    pub type Time = u32;
    pub type TimePair = (Time, Time);
    pub type Str = &'static str;
    pub type Date = Str;

    #[derive(PartialEq, Clone, Debug)]
    pub enum TodoType {
        Category, Product, Project, Item
    }

    #[derive(PartialEq, Clone, Debug)]
    pub struct Todo {
        pub name: Str,
        pub tp: TodoType,
        pub suspended: bool,
        pub important: bool,
        pub children: Vec<Todo>
    }

    impl Todo {
        fn names(&self) -> Vec<Vec<&str>> {
            let name: &str = &self.name;
            [vec![vec![name]], self.children.names().iter().map(|n| {
                [vec![name], n.clone()].concat()
            }).collect()].concat()
        }
    }

    trait Vec_Todo_ {
        fn names(&self) -> Vec<Vec<&str>>;
    }

    impl Vec_Todo_ for Vec<Todo> {
        fn names(&self) -> Vec<Vec<&str>> {
            self.iter().flat_map(|n| n.names()).collect()
        }
    }

    #[derive(PartialEq, Clone, Debug)]
    pub struct Log {
        pub start: Time,
        pub todo: Vec<Str>,
        pub note: Option<Str>,
        pub items: Vec<Str>,
        pub times: Vec<TimePair>
    }

    #[derive(PartialEq, Clone, Debug)]
    pub struct Day {
        pub date: Date,
        pub logs: Vec<Log>
    }

    #[derive(PartialEq, Clone, Debug)]
    pub struct Schedule {
        pub todos: Vec<Todo>,
        pub days: Vec<Day>
    }

    pub mod report {
        use crate::schedule;
        use schedule::Time;
        use std::collections::HashMap;

        #[derive(PartialEq, Clone, Debug)]
        pub struct Day<'a> {
            pub day: &'a schedule::Day,
            //pub categories: HashMap<&'a str, Time>
            pub logs: Vec<Log<'a>>
        }

        #[derive(PartialEq, Clone, Debug)]
        pub struct Log<'a> {
            pub log: &'a schedule::Log,
            pub resolved: Vec<&'a str>
        }

        #[derive(PartialEq, Clone, Debug)]
        pub struct Schedule<'a> {
            pub schedule: &'a schedule::Schedule,
            pub days: Vec<Day<'a>>,
            pub unique_tails: HashMap<Vec<&'a str>, Vec<&'a str>>
        }
    }

    impl Schedule {
        pub fn report(&self) -> report::Schedule {
            let unique_tails = {
                let mut tails : HashMap<Vec<&str>, Vec<&str>> = HashMap::new();
                let mut duplicated: HashSet<Vec<&str>> = HashSet::new();
                let names = self.todos.names();
                for name  in names {
                    for i in 1.. name.len() {
                        let slice = name[name.len() - i ..].to_vec();
                        if tails.contains_key(&slice) {
                            duplicated.insert(slice);
                        } else {
                            tails.insert(slice, name.clone());
                        }
                    }
                }
                for dup in duplicated {
                    tails.remove(&dup);
                }
                tails
            };
            report::Schedule {
                schedule: self,
                days: self.days.iter().map(|d| {
                    report::Day {
                        day: d,
                        logs: d.logs.iter().map(|l| {
                            report::Log {
                                log: l,
                                resolved: unique_tails.get::<Vec<&str>>(&l.todo.iter().map(|s| s as &str).collect()).expect("").clone()
                            }
                        }).collect()
                        //categories
                    }
                }).collect(),
                unique_tails
            }
        }
    }
}


fn my_schedule() {
    use schedule::*;
    let s = Schedule {
        todos: vec![Todo {
            name: "work",
            tp: TodoType::Category,
            suspended: false,
            important: false,
            children: vec![Todo {
                name: "programming",
                tp: TodoType::Category,
                suspended: false,
                important: false,
                children: vec![Todo {
                    name: "practice",
                    tp: TodoType::Category,
                    suspended: false,
                    important: false,
                    children: vec![]
                }]
            }, Todo {
                name: "music",
                tp: TodoType::Category,
                suspended: false,
                important: false,
                children: vec![Todo {
                    name: "practice",
                    tp: TodoType::Category,
                    suspended: false,
                    important: false,
                    children: vec![]
                }]
            }]
        }],
        days: vec![Day {
            date: "test",
            logs: vec![Log {
                start: 0,
                todo: vec!["practice"],
                note: None,
                items: vec![],
                times: vec![]
            }]
        }]
    };
    let r= s.report();
    println!("{:?}", r);
}



struct User {
    username: String
}



pub trait Test1 {
    fn test1(&self) -> String;
}

fn largest<T : PartialOrd>(list: &[T]) -> &T {
    let mut largest = &list[0];
    for item in list {
        if *item > *largest {
            largest = item;
        }
    }
    largest
}
fn unique_in_order<T>(sequence: T) -> Vec<T::Item>
    where
        T: std::iter::IntoIterator,
        T::Item: std::cmp::PartialEq + std::fmt::Debug,
{
    let mut seq: Vec<T::Item> = sequence.into_iter().collect();
    seq.dedup();
    seq
}

fn dna_strand(dna: &str) -> String {
    dna.chars().map(|c| {
        match c {
            'A' => 'T',
            s => s
        }
    }).collect()
}

fn tribonacci(signature: &[f64; 3], n: usize) -> Vec<f64> {
    let mut res: Vec<f64> = Vec::with_capacity(n);
    res.resize(n, 0.);
    for i in 0.. signature.len() {
        if n > i {
            res[i] = signature[i];
        }
    }
    for i in signature.len() .. n {
        res[i] = res[i - 1] + res[i - 2] + res[i - 3];
    }
    res
}
fn is_prime(p: u64) -> bool {
    p >= 2 &&
        (2..)
            .take_while(|q| q * q <= p)
            .all(|q| p % q != 0)
}

fn step(g: i32, m: u64, n: u64) -> Option<(u64, u64)> {
    (m..n)
        .map(|p| (p, p + g as u64))
        .filter(|&(p0, p1)| is_prime(p0) && is_prime(p1))
        .nth(0)
}

fn movie(card: i32, ticket: i32, perc: f64) -> i32 {
    let mut i = 1;
    let mut first: f64 = 0.;
    let mut second: f64 = card as f64;
    let mut price_perc = ticket as f64;
    loop {
        price_perc *= perc;
        first += ticket as f64;
        second += price_perc;
        if first.ceil() > second.ceil() {
            return i;
        }
        i += 1;
    }
}


fn min_value(mut digits: Vec<i32>) -> i32 {
    digits.sort();
    digits.dedup();
    let mut res = 0;
    for d in digits {
        res = res * 10 + d;
    }
    res
}

fn tcp() {
    let listener = TcpListener::bind("127.0.0.1:7878").unwrap();

    for stream in listener.incoming() {
        let stream = stream.unwrap();

        println!("Connection established!");
    }
}

fn main() {
    my_schedule();
}

fn old_test() {
    let months = ["January", "February", "March", "April", "May", "June", "July",
        "August", "September", "October", "November", "December"];
    for n in (1.. 4).rev() {
        println!("{}", n)
    }
}

fn guess_game() {
    println!("Guess a number!");
    let secret_number: i32 =  rand::thread_rng().gen_range(1, 101);
    loop {
        println!("Please input your guess.");
        let mut guess = String::new();
        io::stdin().read_line(&mut guess).expect("Failed to read line");
        println!("You guessed: {}", guess);
        let guess: i32 = guess.trim().parse().expect("Please type a number");
        match guess.cmp(&secret_number) {
            Ordering::Less => println!("Too small"),
            Ordering::Greater => println!("Too big!"),
            Ordering::Equal => {
                println!("Equal!");
                break;
            }
        }
    }
}
