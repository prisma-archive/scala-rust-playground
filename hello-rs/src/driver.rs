extern crate postgres;
extern crate serde_json;

use self::postgres::transaction::Transaction;
use self::postgres::{Connection, TlsMode};
use purepg::{gcValuesToToSql, serializeToJson, GcValue};
use std::cell::RefCell;

#[repr(C)]
#[no_mangle]
#[allow(non_snake_case)]
pub struct PsqlConnection<'a> {
    connection: Connection,
    transaction: Option<Transaction<'a>>,
}

pub fn connect<'a>(url: String) -> PsqlConnection<'a> {
    let conn = Connection::connect(url, TlsMode::None).unwrap();
    return PsqlConnection {
        connection: conn,
        transaction: None,
    };
}

impl<'a> PsqlConnection<'a> {
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
            println!("WAT");
            let json = serializeToJson(row);
            vec.push(json);
        }
        println!("WAT2");
        return serde_json::to_string(&vec).unwrap();
    }

    pub fn execute(&self, query: String, params: Vec<&GcValue>) {
        println!("Execute received the params: {:?}", params);
        self.connection
            .execute(&*query, &gcValuesToToSql(params))
            .unwrap();
        println!("EXEC DONE")
    }

    pub fn close(self) {
        self.connection.finish().unwrap();
    }

    pub fn startTransaction(&'a mut self) {
        let ta = self.connection.transaction().unwrap();
        self.transaction = Some(ta);
    }

    pub fn commitTransaction(self) {
        let ta = self.transaction.unwrap();
        ta.set_commit();
        ta.finish().unwrap();
        // self.transaction = None;

        // match self.transaction {
        //     Some(ta) => {
        //         ta.set_commit();
        //         ta.finish().unwrap();
        //         self.transaction = None;
        //     }
        //     None => (),
        // }
    }

    pub fn rollbackTransaction(mut self) {
        match self.transaction {
            Some(ta) => {
                ta.set_rollback();
                ta.finish().unwrap();
                self.transaction = None;
            }

            None => (),
        }
    }
}
