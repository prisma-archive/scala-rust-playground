package example;

import org.graalvm.nativeimage.c.function.CFunction;
import org.graalvm.nativeimage.c.function.CLibrary;
import org.graalvm.nativeimage.c.type.CCharPointer;

@CLibrary("hello")
public class RustInterfaceGraal {
    @CFunction
    static native void printHello();

    @CFunction
    static native CCharPointer hello();

    @CFunction
    static native CCharPointer formatHello(CCharPointer str);
}
