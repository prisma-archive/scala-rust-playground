use std::os::raw::c_char;

mod ffi_utils;

#[no_mangle]
pub extern "C" fn simple_test(msg: *const c_char) {
    let m = ffi_utils::to_str(msg);
    println!("{}", m);
}