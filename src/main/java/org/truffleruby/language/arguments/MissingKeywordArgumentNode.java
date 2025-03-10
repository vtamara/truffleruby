/*
 * Copyright (c) 2014, 2021 Oracle and/or its affiliates. All rights reserved. This
 * code is released under a tri EPL/GPL/LGPL license. You can use it,
 * redistribute it and/or modify it under the terms of the:
 *
 * Eclipse Public License version 2.0, or
 * GNU General Public License version 2, or
 * GNU Lesser General Public License version 2.1.
 */
package org.truffleruby.language.arguments;

import org.truffleruby.RubyLanguage;
import org.truffleruby.core.exception.RubyException;
import org.truffleruby.core.hash.RubyHash;
import org.truffleruby.core.hash.library.HashStoreLibrary;
import org.truffleruby.core.symbol.RubySymbol;
import org.truffleruby.language.RubyContextSourceNode;
import org.truffleruby.language.control.RaiseException;

import com.oracle.truffle.api.CompilerDirectives.CompilationFinal;
import com.oracle.truffle.api.CompilerDirectives.TruffleBoundary;
import com.oracle.truffle.api.frame.VirtualFrame;
import org.truffleruby.language.methods.Arity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class MissingKeywordArgumentNode extends RubyContextSourceNode {
    @CompilationFinal(dimensions = 1) private final RubySymbol[] requiredKeywords;
    @Child private ReadUserKeywordsHashNode readUserKeywordsHashNode;
    @Child private HashStoreLibrary hashes;

    public MissingKeywordArgumentNode(RubyLanguage language, Arity arity) {
        requiredKeywords = requiredKeywordsAsSymbols(language, arity);
        readUserKeywordsHashNode = new ReadUserKeywordsHashNode();
        hashes = insert(HashStoreLibrary.createDispatched());
    }

    @Override
    public Object execute(VirtualFrame frame) {
        RubyHash actualKeywords = readUserKeywordsHashNode.execute(frame);
        throw new RaiseException(getContext(), missingKeywordsError(actualKeywords));
    }

    private RubySymbol[] requiredKeywordsAsSymbols(RubyLanguage language, Arity arity) {
        final String[] requiredKeywords = arity.getRequiredKeywordArguments();
        final RubySymbol[] symbols = new RubySymbol[requiredKeywords.length];

        for (int i = 0; i < requiredKeywords.length; i++) {
            symbols[i] = language.getSymbol(requiredKeywords[i]);
        }

        return symbols;
    }

    @TruffleBoundary
    private RubyException missingKeywordsError(RubyHash actualKeywords) {
        final Object[] missingKeywords = findMissingKeywordArguments(actualKeywords);
        return coreExceptions().argumentErrorMissingKeywords(missingKeywords, this);
    }

    @TruffleBoundary
    @SuppressWarnings("unchecked")
    private Object[] findMissingKeywordArguments(RubyHash actualKeywords) {
        if (actualKeywords == null) {
            return requiredKeywords;
        }

        final ArrayList<Object> actualKeywordsAsList = new ArrayList<>();
        hashes.eachEntry(
                actualKeywords.store,
                actualKeywords,
                (index, key, value, state) -> ((ArrayList<Object>) state).add(key),
                actualKeywordsAsList);

        final List<RubySymbol> requiredKeywordsAsList = Arrays.asList(requiredKeywords);
        final List<RubySymbol> missingKeywords = requiredKeywordsAsList.stream()
                .filter(k -> !actualKeywordsAsList.contains(k))
                .collect(Collectors.toList());

        return missingKeywords.toArray();
    }
}
