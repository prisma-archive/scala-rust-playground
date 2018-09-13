mod db;

extern crate diesel;

fn main() {
    let connection = db::establish_connection();
    let posts = db::get_posts(connection);
    println!("done")
}
