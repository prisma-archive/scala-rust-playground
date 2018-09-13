use diesel::prelude::*;
use diesel::pg::PgConnection;
use diesel::dsl::sql_query;
use std::env;

pub fn establish_connection() -> PgConnection {
    let database_url = env::var("DATABASE_URL").expect("DATABASE_URL must be set");
    return PgConnection::establish(&database_url)
        .expect(&format!("Error connecting to {}", database_url));

}

pub fn get_posts(connection: PgConnection) -> String {
    connection.execute("Select 1 as foo;");
    let result = connection.execute("Select * from posts;");
    match result {
        Ok(x) => println!("got {} posts", x),
        Err(e) => println!("error accessing db: {:?}", e),
    }
//    let posts = sql_query("SELECT * FROM posts ORDER BY id").load(&connection);
//    match posts {
//        Ok(posts) => println!("got {} posts", posts.len()),
//        Err(e) => println!("error accessing db: {:?}", e),
//    }
    return String::from("Hello, world!");
}

//#[derive(QueryableByName)]
struct Post {
    title: String
}