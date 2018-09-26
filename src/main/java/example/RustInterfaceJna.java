package example;

import com.sun.jna.Library;
import com.sun.jna.Pointer;
import com.sun.jna.Structure;

public interface RustInterfaceJna extends Library {
//    public static class RustConnection extends Structure {
//        public static class ByReference extends RustConnection implements Structure.ByReference {
//
//        }
//    }

    Pointer newConnection(String url);


}

