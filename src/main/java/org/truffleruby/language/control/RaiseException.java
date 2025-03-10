/*
 * Copyright (c) 2013, 2021 Oracle and/or its affiliates. All rights reserved. This
 * code is released under a tri EPL/GPL/LGPL license. You can use it,
 * redistribute it and/or modify it under the terms of the:
 *
 * Eclipse Public License version 2.0, or
 * GNU General Public License version 2, or
 * GNU Lesser General Public License version 2.1.
 */
package org.truffleruby.language.control;

import com.oracle.truffle.api.exception.AbstractTruffleException;
import com.oracle.truffle.api.interop.InteropLibrary;
import com.oracle.truffle.api.library.ExportLibrary;
import org.truffleruby.RubyContext;
import org.truffleruby.core.exception.ExceptionOperations;
import org.truffleruby.core.exception.RubyException;
import org.truffleruby.core.module.ModuleFields;
import org.truffleruby.language.backtrace.Backtrace;

import com.oracle.truffle.api.CompilerDirectives.TruffleBoundary;

/** A ControlFlowException holding a Ruby exception. */
@SuppressWarnings("serial")
@ExportLibrary(value = InteropLibrary.class, delegateTo = "exception")
public final class RaiseException extends AbstractTruffleException {

    protected final RubyException exception;

    public RaiseException(RubyContext context, RubyException exception) {
        this(context, exception, null);
    }

    public RaiseException(RubyContext context, RubyException exception, Throwable cause) {
        super(null, cause, UNLIMITED_STACK_TRACE, exception.getLocation());
        this.exception = exception;

        final Backtrace backtrace = exception.backtrace;
        if (backtrace != null) { // The backtrace could be null if for example a user backtrace was passed to Kernel#raise
            backtrace.setRaiseException(this);
        }

        if (context.getOptions().BACKTRACE_ON_RAISE) {
            context.getDefaultBacktraceFormatter().printRubyExceptionOnEnvStderr("raise: ", this);
        }
    }

    public RaiseException(RaiseException copy, RubyException exception) {
        super(copy);
        this.exception = exception;
    }

    public RubyException getException() {
        return exception;
    }

    @Override
    @TruffleBoundary
    public String getMessage() {
        final ModuleFields exceptionClass = exception.getLogicalClass().fields;
        final String message = ExceptionOperations.messageToString(exception);
        return String.format("%s (%s)", message, exceptionClass.getName());
    }

}
