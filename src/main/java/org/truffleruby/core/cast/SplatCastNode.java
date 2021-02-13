/*
 * Copyright (c) 2013, 2020 Oracle and/or its affiliates. All rights reserved. This
 * code is released under a tri EPL/GPL/LGPL license. You can use it,
 * redistribute it and/or modify it under the terms of the:
 *
 * Eclipse Public License version 2.0, or
 * GNU General Public License version 2, or
 * GNU Lesser General Public License version 2.1.
 */
package org.truffleruby.core.cast;

import org.truffleruby.RubyLanguage;
import org.truffleruby.core.array.ArrayDupNode;
import org.truffleruby.core.array.ArrayDupNodeGen;
import org.truffleruby.core.array.RubyArray;
import org.truffleruby.core.symbol.RubySymbol;
import org.truffleruby.language.Nil;
import org.truffleruby.language.RubyContextSourceNode;
import org.truffleruby.language.RubyNode;
import org.truffleruby.language.dispatch.DispatchNode;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.CompilerDirectives.CompilationFinal;
import com.oracle.truffle.api.dsl.Cached;
import com.oracle.truffle.api.dsl.NodeChild;
import com.oracle.truffle.api.dsl.Specialization;

import static org.truffleruby.language.dispatch.DispatchConfiguration.PRIVATE_RETURN_MISSING;

/** Splat as used to cast a value to an array if it isn't already, as in {@code *value}. */
@NodeChild(value = "child", type = RubyNode.class)
public abstract class SplatCastNode extends RubyContextSourceNode {

    public enum NilBehavior {
        EMPTY_ARRAY,
        ARRAY_WITH_NIL,
        NIL,
        CONVERT
    }

    private final NilBehavior nilBehavior;
    private final RubySymbol conversionMethod;
    @CompilationFinal private boolean copy = true;

    @Child private ArrayDupNode dup;
    @Child private DispatchNode toA;

    public SplatCastNode(RubyLanguage language, NilBehavior nilBehavior, boolean useToAry) {
        this.nilBehavior = nilBehavior;
        // Calling private #to_a is allowed for the *splat operator.
        conversionMethod = useToAry ? language.coreSymbols.TO_ARY : language.coreSymbols.TO_A;
    }

    public void doNotCopy() {
        copy = false;
    }

    public abstract RubyNode getChild();

    @Specialization
    protected Object splatNil(Nil nil) {
        switch (nilBehavior) {
            case EMPTY_ARRAY:
                return createEmptyArray();

            case ARRAY_WITH_NIL:
                return createArray(new Object[]{ nil });

            case CONVERT:
                return callToA(nil);

            case NIL:
                return nil;

            default:
                throw CompilerDirectives.shouldNotReachHere();
        }
    }

    @Specialization
    protected RubyArray splat(RubyArray array) {
        // TODO(cs): is it necessary to dup here in all cases?
        // It is needed at least for [*ary] (parsed as just a SplatParseNode) and b = *ary.
        if (copy) {
            return executeDup(array);
        } else {
            return array;
        }
    }

    @Specialization(guards = { "!isNil(object)", "!isRubyArray(object)" })
    protected RubyArray splat(Object object,
            @Cached DispatchNode toArrayNode) {
        final Object array = toArrayNode.call(
                coreLibrary().truffleTypeModule,
                "rb_check_convert_type",
                object,
                coreLibrary().arrayClass,
                conversionMethod);
        if (array == nil) {
            return createArray(new Object[]{ object });
        } else {
            if (copy) {
                return executeDup((RubyArray) array);
            } else {
                return (RubyArray) array;
            }
        }
    }

    private Object callToA(Object nil) {
        if (toA == null) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            toA = insert(DispatchNode.create(PRIVATE_RETURN_MISSING));
        }
        return toA.call(nil, "to_a");
    }

    private RubyArray executeDup(RubyArray array) {
        if (dup == null) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            dup = insert(ArrayDupNodeGen.create());
        }
        return dup.executeDup(array);
    }

}
