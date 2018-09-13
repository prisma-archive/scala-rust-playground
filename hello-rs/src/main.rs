mod db;

extern crate diesel;

fn main() {
    let connection = db::establish_connection();
    let posts = db::get_posts_diesel(connection);
    println!("done")
}
