mod db;
mod purepg;

extern crate diesel;

fn main() {
//    let connection = db::establish_connection();
//    let posts = db::get_posts_diesel(connection);
//    println!("done")
    {
        let json = r###"{"discriminator": "String", "value": "my string"}"###;
        let result = purepg::toGcValue(String::from(json)).unwrap();
        println!("{:?}", result);
        let expected = purepg::GcValue::String(String::from("my string"));
        assert_eq!(result, expected);
    }
    {
        let json = r###"[{"discriminator": "String", "value": "my string"}, {"discriminator": "Boolean", "value": true}]"###;
        let result = purepg::toGcValues(&String::from(json)).unwrap();
        println!("{:?}", result);
        let expected = vec!(purepg::GcValue::String(String::from("my string")), purepg::GcValue::Boolean(true));
        assert_eq!(result, expected);
    }

}
