/*
 * Copyright (c) 2013, 2016 Oracle and/or its affiliates. All rights reserved. This
 * code is released under a tri EPL/GPL/LGPL license. You can use it,
 * redistribute it and/or modify it under the terms of the:
 *
 * Eclipse Public License version 1.0
 * GNU General Public License version 2
 * GNU Lesser General Public License version 2.1
 */
package org.jruby.truffle.language.objects;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.object.DynamicObject;
import com.oracle.truffle.api.source.SourceSection;
import org.jcodings.specific.UTF8Encoding;
import org.jruby.truffle.RubyContext;
import org.jruby.truffle.core.module.ModuleOperations;
import org.jruby.truffle.language.LexicalScope;
import org.jruby.truffle.language.RubyNode;

public class WriteClassVariableNode extends RubyNode {

    private final String name;
    private final LexicalScope lexicalScope;
    @Child private RubyNode rhs;

    public WriteClassVariableNode(RubyContext context, SourceSection sourceSection, String name, LexicalScope lexicalScope, RubyNode rhs) {
        super(context, sourceSection);
        this.name = name;
        this.lexicalScope = lexicalScope;
        this.rhs = rhs;
    }

    @Override
    public Object execute(VirtualFrame frame) {
        CompilerDirectives.transferToInterpreter();

        final Object rhsValue = rhs.execute(frame);

        final DynamicObject module = lexicalScope.resolveTargetModule();

        ModuleOperations.setClassVariable(getContext(), module, name, rhsValue, this);

        return rhsValue;
    }

    @Override
    public Object isDefined(VirtualFrame frame) {
        return create7BitString("assignment", UTF8Encoding.INSTANCE);
    }

}
