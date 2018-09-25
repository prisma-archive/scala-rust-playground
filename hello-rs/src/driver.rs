extern crate postgres;
extern crate serde_json;

use self::postgres::{Connection, TlsMode};
use purepg::{gcValuesToToSql, serializeToJson, GcValue};

#[repr(C)]
#[no_mangle]
#[allow(non_snake_case)]
pub struct PsqlConnection {
    connection: Box<Connection>,
    // pub x: int64,
}

pub fn connect(url: String) -> Box<PsqlConnection> {
    let conn = Connection::connect(url, TlsMode::None).unwrap();
    return Box::new(PsqlConnection {
        connection: Box::new(conn),
    });
}

impl PsqlConnection {
    pub fn query(&self, query: String, params: Vec<&GcValue>) -> String {
        println!("Query received the params: {:?}", params);

        let rows = self
            .connection
            .query(&*query, &gcValuesToToSql(params))
            .unwrap();

        println!("The result set has {} columns", rows.columns().len());
        for column in rows.columns() {
            println!("column {} of type {}", column.name(), column.type_());
        }

        let mut vec = Vec::new();
        for row in rows.iter() {
            let json = serializeToJson(row);
            vec.push(json);
        }
        return serde_json::to_string(&vec).unwrap();
    }
}
