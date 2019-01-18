package io.prisma.playground;

import org.graalvm.nativeimage.c.function.CFunction;
import org.graalvm.nativeimage.c.function.CLibrary;
import org.graalvm.nativeimage.c.type.CCharPointer;

@CLibrary("playground")
public class RustInterfaceGraal {
    @CFunction
    static native void simple_test(CCharPointer params);
}
