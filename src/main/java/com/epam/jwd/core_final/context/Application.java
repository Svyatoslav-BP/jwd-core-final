package com.epam.jwd.core_final.context;

import com.epam.jwd.core_final.context.impl.NassaContext;
import com.epam.jwd.core_final.exception.InvalidStateException;

import java.util.function.Supplier;

public interface Application {

    final NassaContext nassaContext = new NassaContext();
    static ApplicationMenu start() throws InvalidStateException {
        final Supplier<ApplicationContext> applicationContextSupplier =() -> nassaContext; // todo
        nassaContext.init();
        return applicationContextSupplier::get;
    }
}
