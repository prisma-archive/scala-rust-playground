#![crate_type = "dylib"]

use std::ffi::{CStr,CString};
use std::mem;
use std::str;
use std::os::raw::c_char;

#[no_mangle]
#[allow(non_snake_case)]
pub extern fn printHello() {
    println!("Hello! (This was printed in Rust)");
}


#[no_mangle]
#[allow(non_snake_case)]
pub extern fn hello() -> *const c_char {
    return to_ptr("Hello from Rust!".to_string())
}

#[no_mangle]
#[allow(non_snake_case)]
pub extern fn formatHello(str: *const c_char) -> *const c_char {
    let argAsString = to_string(str);
    return to_ptr(format!("Hello {}, Rust is saying hello to you!", argAsString))
}


#[no_mangle]
#[allow(non_snake_case)]
pub extern fn newCounterByReference() -> Box<Counter> {
    let c = Counter {
        count: 0
    };
//    mem::forget(&c);
    return Box::new(c);
}

#[no_mangle]
#[allow(non_snake_case)]
pub extern fn newCounterByValue() -> Counter {
    let c = Counter {
        count: 0
    };
//    mem::forget(&c);
    return c;
}

#[no_mangle]
#[repr(C)]
#[allow(non_snake_case)]
pub struct Counter {
    count: u64
}

#[no_mangle]
#[allow(non_snake_case)]
pub fn increment(arg: &mut Counter){
    arg.count = arg.count + 1
        //println!(format!("count is {} now!", self.count))
}

/// Convert a native string to a Rust string
fn to_string(pointer: *const c_char) -> String {
    let slice = unsafe { CStr::from_ptr(pointer).to_bytes() };
    str::from_utf8(slice).unwrap().to_string()
}

/// Convert a Rust string to a native string
fn to_ptr(string: String) -> *const c_char {
    let cs = CString::new(string.as_bytes()).unwrap();
    let ptr = cs.as_ptr();
    // Tell Rust not to clean up the string while we still have a pointer to it.
    // Otherwise, we'll get a segfault.

    mem::forget(cs);
    ptr
}
