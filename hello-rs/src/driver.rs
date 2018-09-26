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
    transaction: RefCell<Option<Transaction<'a>>>,
}

pub fn connect<'a>(url: String) -> PsqlConnection<'a> {
    let conn = Connection::connect(url, TlsMode::None).unwrap();
    return PsqlConnection {
        connection: conn,
        transaction: RefCell::new(None),
    };
}

impl<'a> Drop for PsqlConnection<'a> {
    fn drop(&mut self) {
        println!("[Rust] Dropping psql connection");
    }
}

impl<'a> PsqlConnection<'a> {
    pub fn query(&self, query: String, params: Vec<&GcValue>) -> String {
        println!("Query received the params: {:?}", params);
        let mutRef = self.transaction.borrow_mut();
        let rows = match *mutRef {
            Some(ref t) => t.query(&*query, &gcValuesToToSql(params)).unwrap(),
            None => self
                .connection
                .query(&*query, &gcValuesToToSql(params))
                .unwrap(),
        };

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

    pub fn execute(&self, query: String, params: Vec<&GcValue>) {
        println!("Execute received the params: {:?}", params);

        let mutRef = self.transaction.borrow_mut();
        match *mutRef {
            Some(ref t) => {
                println!("[Rust] Have transaction");
                t.execute(&*query, &gcValuesToToSql(params)).unwrap()
            }
            None => self
                .connection
                .execute(&*query, &gcValuesToToSql(params))
                .unwrap(),
        };

        println!("EXEC DONE")
    }

    pub fn close(self) {
        // self.connection.finish().unwrap();
    }

    pub fn startTransaction(&'a mut self) {
        let ta = self.connection.transaction().unwrap();
        self.transaction.replace(Some(ta));
    }

    pub fn commitTransaction(&self) {
        // let ta = self.transaction.unwrap();
        // ta.set_commit();
        // ta.finish().unwrap();
        // self.transaction = None;

        let taOpt = self.transaction.replace(None);
        match taOpt {
            Some(ta) => {
                println!("[Rust] Have transaction");
                ta.commit().unwrap();
            }
            None => (),
        }
    }

    pub fn rollbackTransaction(&self) {
        let taOpt = self.transaction.replace(None);
        match taOpt {
            Some(ta) => {
                println!("[Rust] Have transaction");
                ta.set_rollback();
                //ta.finish().unwrap();
            }

            None => (),
        }
    }
}
