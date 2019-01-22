package io.prisma.playground;

import org.graalvm.nativeimage.c.function.CFunction;
import org.graalvm.nativeimage.c.function.CLibrary;
import org.graalvm.nativeimage.c.type.CCharPointer;
import org.graalvm.nativeimage.c.type.CLongPointer;
import org.graalvm.nativeimage.c.type.CTypeConversion;
import rpc.rpc.User;
import java.nio.ByteBuffer;

@CLibrary("playground")
public class RustInterfaceGraal {
    @CFunction
    static native CIntegration.ProtoBuf pb_output();

    @CFunction
    static native void pb_input(CCharPointer data, long len);
}
