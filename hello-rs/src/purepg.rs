extern crate postgres;
extern crate serde_json;

use self::postgres::{Connection, TlsMode};
use self::postgres::rows::Row;
use std::env;
use std::collections::BTreeMap;

pub fn get_posts() -> String {
    let conn = Connection::connect(database_url(), TlsMode::None).unwrap();
    let rows = &conn.query("SELECT * FROM posts;", &[]).unwrap();
    println!("The result set has {} columns", rows.columns().len());
    for column in rows.columns() {
        println!("column {} of type {}", column.name(), column.type_());
    }
    let mut vec = Vec::new();
    for row in rows {
        let json = serializeToJson(row);
        vec.push(json);
    }
    return serde_json::to_string(&vec).unwrap();
}

fn serializeToJson(row: Row) -> serde_json::Value {
    let mut map = serde_json::Map::new();
    for (i, column)  in row.columns().iter().enumerate() {
        let json_value: serde_json::Value = match column.type_() {
            &postgres::types::BOOL => serde_json::Value::Bool(row.get(i)),
            &postgres::types::INT4 => {
                let value:i32 = row.get(i);
                let number = serde_json::Number::from_f64(value as f64
                ).unwrap();
                serde_json::Value::Number(number)
            }
            &postgres::types::VARCHAR => serde_json::Value::String(row.get(i)),
            &postgres::types::TEXT => serde_json::Value::String(row.get(i)),
            x => panic!("type {} is not supported", x),
        };
        map.insert(String::from(column.name()), json_value);
    }

    return serde_json::Value::Object(map);
}

fn database_url() -> String {
    return env::var("DATABASE_URL").unwrap_or(String::from("postgres://postgres:prisma@localhost/"));
}