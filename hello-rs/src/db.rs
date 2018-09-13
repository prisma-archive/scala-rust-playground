extern crate diesel;

use diesel::prelude::*;
use diesel::pg::PgConnection;
use diesel::dsl::sql_query;
use std::env;

pub fn establish_connection() -> PgConnection {
    return PgConnection::establish(&database_url())
        .expect(&format!("Error connecting to {}", database_url()));

}

pub fn get_posts_diesel(connection: PgConnection) -> String {
    connection.execute("Select 1 as foo;");
    let result = connection.execute("Select * from posts;");
    match result {
        Ok(x) => println!("got {} posts", x),
        Err(e) => println!("error accessing db: {:?}", e),
    }
//    let posts = sql_query("SELECT * FROM posts ORDER BY id").load::<Post>(&connection);
//    match posts {
//        Ok(posts) => println!("got {} posts", posts.len()),
//        Err(e) => println!("error accessing db: {:?}", e),
//    }
    return String::from("Hello, world!");
}

fn database_url() -> String {
    return env::var("DATABASE_URL").unwrap_or(String::from("postgres://postgres:prisma@localhost/"));
}

//#[derive(QueryableByName)]
struct Post {
    title: String
}