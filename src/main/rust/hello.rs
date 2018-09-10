fn main() {
    let s = hello();
    println!("{}", s)
}

fn hello() -> String {
    return "Hello, world!".to_string();
}
