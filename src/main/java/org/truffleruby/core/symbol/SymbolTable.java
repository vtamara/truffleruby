/*
 * Copyright (c) 2013, 2021 Oracle and/or its affiliates. All rights reserved. This
 * code is released under a tri EPL/GPL/LGPL license. You can use it,
 * redistribute it and/or modify it under the terms of the:
 *
 * Eclipse Public License version 2.0, or
 * GNU General Public License version 2, or
 * GNU Lesser General Public License version 2.1.
 */
package org.truffleruby.core.symbol;

import java.util.Collection;

import com.oracle.truffle.api.strings.AbstractTruffleString;
import com.oracle.truffle.api.strings.TruffleString;
import org.truffleruby.collections.WeakValueCache;
import org.truffleruby.core.encoding.Encodings;
import org.truffleruby.core.encoding.RubyEncoding;
import org.truffleruby.core.encoding.TStringUtils;
import org.truffleruby.core.rope.LeafRope;
import org.truffleruby.core.rope.RopeCache;
import org.truffleruby.core.rope.RopeOperations;
import org.truffleruby.core.rope.TStringCache;
import org.truffleruby.core.rope.TStringWithEncoding;
import org.truffleruby.core.string.StringOperations;

import com.oracle.truffle.api.CompilerDirectives.TruffleBoundary;

public class SymbolTable {

    private final TStringCache tstringCache;
    private final RopeCache ropeCache;

    // A cache for j.l.String to Symbols. Entries are kept as long as the Symbol is alive.
    // However, this doesn't matter as the cache entries will be re-created when used.
    private final WeakValueCache<String, RubySymbol> stringToSymbolCache = new WeakValueCache<>();

    // Weak map of TStringWithEncoding to Symbol to keep Symbols unique.
    // As long as the Symbol is referenced, the entry will stay in the symbolMap.
    private final WeakValueCache<TStringWithEncoding, RubySymbol> symbolMap = new WeakValueCache<>();

    public SymbolTable(TStringCache tstringCache, RopeCache ropeCache, CoreSymbols coreSymbols) {
        this.tstringCache = tstringCache;
        this.ropeCache = ropeCache;
        addCoreSymbols(coreSymbols);
    }

    private void addCoreSymbols(CoreSymbols coreSymbols) {
        for (RubySymbol symbol : coreSymbols.CORE_SYMBOLS) {
            var rope = symbol.tstring;
            var lookup = normalizeForLookup(rope, symbol.encoding);
            assert rope == lookup.tstring;
            assert rope == tstringCache.getTString(symbol.tstring, symbol.encoding);

            final RubySymbol existing = symbolMap.put(lookup, symbol);
            if (existing != null) {
                throw new AssertionError("Duplicate Symbol in SymbolTable: " + existing);
            }

            final RubySymbol old = stringToSymbolCache.put(symbol.getString(), symbol);
            if (old != null) {
                throw new AssertionError("Duplicate Symbol in SymbolTable: " + old);
            }
        }
    }

    @TruffleBoundary
    public RubySymbol getSymbol(String string) {
        RubySymbol symbol = stringToSymbolCache.get(string);
        if (symbol != null) {
            return symbol;
        }

        final TruffleString str;
        final RubyEncoding encoding;
        if (StringOperations.isAsciiOnly(string)) {
            str = TStringUtils.usAsciiString(string);
            encoding = Encodings.US_ASCII;
        } else {
            str = TStringUtils.utf8TString(string);
            encoding = Encodings.UTF_8;
        }
        symbol = getSymbol(str, encoding);

        // Add it to the direct java.lang.String to Symbol cache
        stringToSymbolCache.addInCacheIfAbsent(string, symbol);

        return symbol;
    }

    @TruffleBoundary
    public RubySymbol getSymbol(AbstractTruffleString tstring, RubyEncoding originalEncoding) {
        var key = normalizeForLookup(tstring, originalEncoding);
        final RubySymbol symbol = symbolMap.get(key);
        if (symbol != null) {
            return symbol;
        }

        final RubyEncoding symbolEncoding = key.encoding;
        var cachedTString = tstringCache.getTString(key.tstring, symbolEncoding);
        final LeafRope cachedRope = ropeCache.getRope(key.toRope());
        final RubySymbol newSymbol = createSymbol(cachedRope, cachedTString, symbolEncoding);
        // Use a TStringWithEncoding with the cached TString in symbolMap, since the Symbol refers to it and so we
        // do not keep the other TString alive unnecessarily.
        return symbolMap.addInCacheIfAbsent(new TStringWithEncoding(cachedTString, symbolEncoding), newSymbol);
    }

    @TruffleBoundary
    public RubySymbol getSymbolIfExists(AbstractTruffleString tstring, RubyEncoding encoding) {
        var key = normalizeForLookup(tstring, encoding);
        return symbolMap.get(key);
    }

    private TStringWithEncoding normalizeForLookup(AbstractTruffleString rope, RubyEncoding encoding) {
        TruffleString string = rope.asManagedTruffleStringUncached(encoding.tencoding);
        var strEnc = new TStringWithEncoding(string, encoding);

        if (strEnc.isAsciiOnly() && encoding != Encodings.US_ASCII) {
            strEnc = strEnc.forceEncoding(Encodings.US_ASCII);
        }

        return strEnc;
    }

    private RubySymbol createSymbol(LeafRope cachedRope, TruffleString truffleString, RubyEncoding encoding) {
        final String string = RopeOperations.decodeOrEscapeBinaryRope(cachedRope);
        return new RubySymbol(string, cachedRope, truffleString, encoding);
    }

    @TruffleBoundary
    public Collection<RubySymbol> allSymbols() {
        // allSymbols is a private concrete collection not a view
        return symbolMap.values();
    }

}
