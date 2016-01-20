package org.paradise;

import org.apache.camel.spring.boot.FatJarRouter;
import org.apache.camel.spring.boot.FatWarInitializer;

/**
 * Created by miaot on 20/01/2016.
 */
public class MainSpringBootCamelRouterWarInitializer extends FatWarInitializer {

    @Override protected Class<? extends FatJarRouter> routerClass() {

        return MainSpringBootCamelRouter.class;
    }

}
