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

    @CFunction
    static native CCharPointer processJson(CCharPointer str);

    @CFunction
    static native CCharPointer readFromDb(CCharPointer query);

    @CFunction
    static native CIntegration.Counter newCounterByReference();

    @CFunction
    static native CIntegration.Counter newCounterByValue();

    @CFunction
    static native void increment(CIntegration.Counter counter);

    @CFunction
    static native CIntegration.RustConnection newConnection(CCharPointer url);

    @CFunction
    static native void startTransaction(CIntegration.RustConnection connection);

    @CFunction
    static native void commitTransaction(CIntegration.RustConnection connection);

    @CFunction
    static native void rollbackTransaction(CIntegration.RustConnection connection);

    @CFunction
    static native void closeConnection(CIntegration.RustConnection connection);

    @CFunction
    static native void sqlExecute(CIntegration.RustConnection connection, CCharPointer query, CCharPointer params);

    @CFunction
    static native CCharPointer sqlQuery(CIntegration.RustConnection connection, CCharPointer query, CCharPointer params);
}
