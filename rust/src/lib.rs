#[macro_use]
extern crate prost_derive;

mod ffi_utils;
mod protobuf;

use protobuf::{
    rpc::{Header, User},
    ProtoBuf,
};

use std::slice;
use prost::Message;

#[no_mangle]
pub extern "C" fn pb_output() -> *mut ProtoBuf {
    let user = User {
        header: Header {
            type_name: String::from("User")
        },
        name: String::from("Musti"),
    };

    let mut payload = Vec::new();
    user.encode(&mut payload).unwrap();

    ProtoBuf::from(payload).into_boxed_ptr()
}

#[no_mangle]
pub extern "C" fn pb_input(data: *mut u8, len: usize) {
    let payload = unsafe { slice::from_raw_parts_mut(data, len) };
    let user = User::decode(payload).unwrap();
    
    println!("Rust got a type {} from Scala with name {}", user.header.type_name, user.name);
}

#[cfg(test)]
mod tests {
    use prost::Message;

    use crate::protobuf::{
        rpc::{Header, Rpc, User},
    };

    #[test]
    fn test_example_rpc_functionality() {
        let user = User {
            header: Header {
                type_name: String::from("User")
            },
            name: String::from("Musti"),
        };

        let mut payload = Vec::new();
        user.encode(&mut payload).unwrap();

        let rpc_data = Rpc::decode(&payload).unwrap();

        assert_eq!(rpc_data.header.type_name, "User");
    }
}
