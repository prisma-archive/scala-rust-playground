package io.prisma.playground;

import org.graalvm.nativeimage.c.function.CFunction;
import org.graalvm.nativeimage.c.function.CLibrary;
import org.graalvm.nativeimage.c.type.CCharPointer;
import org.graalvm.nativeimage.c.type.CLongPointer;

@CLibrary("playground")
public class RustInterfaceGraal {
    @CFunction
    static native CIntegration.ProtoBuf pb_output();
}
