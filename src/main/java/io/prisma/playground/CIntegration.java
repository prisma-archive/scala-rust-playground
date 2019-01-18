package io.prisma.playground;

import org.graalvm.nativeimage.c.CContext;
import java.util.Collections;
import java.util.List;


@CContext(CIntegration.CIntegrationDirectives.class)
public class CIntegration {
    static class CIntegrationDirectives implements CContext.Directives {

        @Override
        public List<String> getHeaderFiles() {
            /*
             * The header file with the C declarations that are imported. We use a helper class that
             * locates the file in our project structure.
             */
            return Collections.singletonList("\"" + System.getProperty("user.dir") + "/playground.h\"");
        }
    }
}
