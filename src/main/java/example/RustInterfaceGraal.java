package example;

import org.graalvm.nativeimage.c.function.CFunction;
import org.graalvm.nativeimage.c.function.CLibrary;

@CLibrary("hello")
public class RustInterfaceGraal {
    @CFunction
    static native void printGreeting();
}
