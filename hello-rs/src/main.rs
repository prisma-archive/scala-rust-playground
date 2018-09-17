mod db;
mod purepg;

extern crate diesel;

fn main() {
//    let connection = db::establish_connection();
//    let posts = db::get_posts_diesel(connection);
//    println!("done")

    let json = r###"{"discriminator": "String", "value": "my string"}"###;
    let result = purepg::toGcValue(String::from(json)).unwrap();
    println!("{:?}", result);
    assert_eq!(result, purepg::GcValue::String(String::from("my string")));
}
