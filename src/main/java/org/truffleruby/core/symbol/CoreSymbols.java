/*
 * Copyright (c) 2020 Oracle and/or its affiliates. All rights reserved. This
 * code is released under a tri EPL/GPL/LGPL license. You can use it,
 * redistribute it and/or modify it under the terms of the:
 *
 * Eclipse Public License version 2.0, or
 * GNU General Public License version 2, or
 * GNU Lesser General Public License version 2.1.
 */
package org.truffleruby.core.symbol;

import java.util.ArrayList;
import java.util.List;

import org.jcodings.specific.USASCIIEncoding;
import org.truffleruby.core.rope.Rope;
import org.truffleruby.core.rope.RopeConstants;
import org.truffleruby.core.rope.RopeOperations;

// GENERATED BY tool/generate-core-symbols.rb
// This file is automatically generated from tool/id.def with 'jt build core-symbols'

// @formatter:off
public class CoreSymbols {

    public static long STATIC_SYMBOL_ID = 0x1;
    private static long GLOBAL_SYMBOL_ID = (0x03 << 1);

    public static final List<RubySymbol> CORE_SYMBOLS = new ArrayList<>();
    public static final RubySymbol[] STATIC_SYMBOLS = new RubySymbol[216];

    public static final RubySymbol CLASS = createRubySymbol("class");
    public static final RubySymbol DIVMOD = createRubySymbol("divmod");
    public static final RubySymbol IMMEDIATE = createRubySymbol("immediate");
    public static final RubySymbol LINE = createRubySymbol("line");
    public static final RubySymbol NEVER = createRubySymbol("never");
    public static final RubySymbol ON_BLOCKING = createRubySymbol("on_blocking");


    public static final RubySymbol BANG = CoreSymbols.createRubySymbol("!", 33);
    public static final RubySymbol DOUBLE_QUOTE = CoreSymbols.createRubySymbol("\"", 34);
    public static final RubySymbol POUND = CoreSymbols.createRubySymbol("#", 35);
    public static final RubySymbol DOLLAR = CoreSymbols.createRubySymbol("$", 36);
    public static final RubySymbol MODULO = CoreSymbols.createRubySymbol("%", 37);
    public static final RubySymbol AMPERSAND = CoreSymbols.createRubySymbol("&", 38);
    public static final RubySymbol SINGLE_QUOTE = CoreSymbols.createRubySymbol("'", 39);
    public static final RubySymbol LPAREN = CoreSymbols.createRubySymbol("(", 40);
    public static final RubySymbol RPAREN = CoreSymbols.createRubySymbol(")", 41);
    public static final RubySymbol MULTIPLY = CoreSymbols.createRubySymbol("*", 42);
    public static final RubySymbol PLUS = CoreSymbols.createRubySymbol("+", 43);
    public static final RubySymbol COMMA = CoreSymbols.createRubySymbol(",", 44);
    public static final RubySymbol MINUS = CoreSymbols.createRubySymbol("-", 45);
    public static final RubySymbol PERIOD = CoreSymbols.createRubySymbol(".", 46);
    public static final RubySymbol DIVIDE = CoreSymbols.createRubySymbol("/", 47);
    public static final RubySymbol COLON = CoreSymbols.createRubySymbol(":", 58);
    public static final RubySymbol SEMICOLON = CoreSymbols.createRubySymbol(";", 59);
    public static final RubySymbol LESS_THAN = CoreSymbols.createRubySymbol("<", 60);
    public static final RubySymbol EQUAL = CoreSymbols.createRubySymbol("=", 61);
    public static final RubySymbol GREATER_THAN = CoreSymbols.createRubySymbol(">", 62);
    public static final RubySymbol QUESTION_MARK = CoreSymbols.createRubySymbol("?", 63);
    public static final RubySymbol AT_SYMBOL = CoreSymbols.createRubySymbol("@", 64);
    public static final RubySymbol LEFT_BRACKET = CoreSymbols.createRubySymbol("[", 91);
    public static final RubySymbol BACK_SLASH = CoreSymbols.createRubySymbol("\\", 92);
    public static final RubySymbol RIGHT_BRACKET = CoreSymbols.createRubySymbol("]", 93);
    public static final RubySymbol CIRCUMFLEX = CoreSymbols.createRubySymbol("^", 94);
    public static final RubySymbol BACK_TICK = CoreSymbols.createRubySymbol("`", 96);
    public static final RubySymbol LEFT_BRACE = CoreSymbols.createRubySymbol("{", 123);
    public static final RubySymbol PIPE = CoreSymbols.createRubySymbol("|", 124);
    public static final RubySymbol RIGHT_BRACE = CoreSymbols.createRubySymbol("}", 125);
    public static final RubySymbol TILDE = CoreSymbols.createRubySymbol("~", 126);


    public static final RubySymbol DOT2 = createRubySymbol("..", 128);
    public static final RubySymbol DOT3 = createRubySymbol("...", 129);
    public static final RubySymbol UPLUS = createRubySymbol("+@", 130);
    public static final RubySymbol UMINUS = createRubySymbol("-@", 131);
    public static final RubySymbol POW = createRubySymbol("**", 132);
    public static final RubySymbol CMP = createRubySymbol("<=>", 133);
    public static final RubySymbol LSHFT = createRubySymbol("<<", 134);
    public static final RubySymbol RSHFT = createRubySymbol(">>", 135);
    public static final RubySymbol LEQ = createRubySymbol("<=", 136);
    public static final RubySymbol GEQ = createRubySymbol(">=", 137);
    public static final RubySymbol EQ = createRubySymbol("==", 138);
    public static final RubySymbol EQQ = createRubySymbol("===", 139);
    public static final RubySymbol NEQ = createRubySymbol("!=", 140);
    public static final RubySymbol MATCH = createRubySymbol("=~", 141);
    public static final RubySymbol NMATCH = createRubySymbol("!~", 142);
    public static final RubySymbol AREF = createRubySymbol("[]", 143);
    public static final RubySymbol ASET = createRubySymbol("[]=", 144);
    public static final RubySymbol COLON2 = createRubySymbol("::", 145);
    public static final RubySymbol ANDOP = createRubySymbol("&&", 146);
    public static final RubySymbol OROP = createRubySymbol("||", 147);
    public static final RubySymbol ANDDOT = createRubySymbol("&.", 148);

    public static final RubySymbol NULL = createRubySymbol("", 149);
    public static final RubySymbol EMPTYP = createRubySymbol("empty?", 150);
    public static final RubySymbol EQLP = createRubySymbol("eql?", 151);
    public static final RubySymbol RESPOND_TO = createRubySymbol("respond_to?", 152);
    public static final RubySymbol RESPOND_TO_MISSING = createRubySymbol("respond_to_missing?", 153);
    public static final RubySymbol IFUNC = createRubySymbol("<IFUNC>", 154);
    public static final RubySymbol CFUNC = createRubySymbol("<CFUNC>", 155);
    public static final RubySymbol CORE_SET_METHOD_ALIAS = createRubySymbol("core#set_method_alias", 156);
    public static final RubySymbol CORE_SET_VARIABLE_ALIAS = createRubySymbol("core#set_variable_alias", 157);
    public static final RubySymbol CORE_UNDEF_METHOD = createRubySymbol("core#undef_method", 158);
    public static final RubySymbol CORE_DEFINE_METHOD = createRubySymbol("core#define_method", 159);
    public static final RubySymbol CORE_DEFINE_SINGLETON_METHOD = createRubySymbol("core#define_singleton_method", 160);
    public static final RubySymbol CORE_SET_POSTEXE = createRubySymbol("core#set_postexe", 161);
    public static final RubySymbol CORE_HASH_MERGE_PTR = createRubySymbol("core#hash_merge_ptr", 162);
    public static final RubySymbol CORE_HASH_MERGE_KWD = createRubySymbol("core#hash_merge_kwd", 163);
    // Skipped preserved token: `_debug_created_info`
    public static final int LAST_OP_ID = 164;

    public static final RubySymbol MAX = createRubySymbol("max", toLocal(165));
    public static final RubySymbol MIN = createRubySymbol("min", toLocal(166));
    public static final RubySymbol FREEZE = createRubySymbol("freeze", toLocal(167));
    public static final RubySymbol INSPECT = createRubySymbol("inspect", toLocal(168));
    public static final RubySymbol INTERN = createRubySymbol("intern", toLocal(169));
    public static final RubySymbol OBJECT_ID = createRubySymbol("object_id", toLocal(170));
    public static final RubySymbol CONST_MISSING = createRubySymbol("const_missing", toLocal(171));
    public static final RubySymbol METHODMISSING = createRubySymbol("method_missing", toLocal(172));
    public static final RubySymbol METHOD_ADDED = createRubySymbol("method_added", toLocal(173));
    public static final RubySymbol SINGLETON_METHOD_ADDED = createRubySymbol("singleton_method_added", toLocal(174));
    public static final RubySymbol METHOD_REMOVED = createRubySymbol("method_removed", toLocal(175));
    public static final RubySymbol SINGLETON_METHOD_REMOVED = createRubySymbol("singleton_method_removed", toLocal(176));
    public static final RubySymbol METHOD_UNDEFINED = createRubySymbol("method_undefined", toLocal(177));
    public static final RubySymbol SINGLETON_METHOD_UNDEFINED = createRubySymbol("singleton_method_undefined", toLocal(178));
    public static final RubySymbol LENGTH = createRubySymbol("length", toLocal(179));
    public static final RubySymbol SIZE = createRubySymbol("size", toLocal(180));
    public static final RubySymbol GETS = createRubySymbol("gets", toLocal(181));
    public static final RubySymbol SUCC = createRubySymbol("succ", toLocal(182));
    public static final RubySymbol EACH = createRubySymbol("each", toLocal(183));
    public static final RubySymbol PROC = createRubySymbol("proc", toLocal(184));
    public static final RubySymbol LAMBDA = createRubySymbol("lambda", toLocal(185));
    public static final RubySymbol SEND = createRubySymbol("send", toLocal(186));
    public static final RubySymbol __SEND__ = createRubySymbol("__send__", toLocal(187));
    public static final RubySymbol __ATTACHED__ = createRubySymbol("__attached__", toLocal(188));
    public static final RubySymbol INITIALIZE = createRubySymbol("initialize", toLocal(189));
    public static final RubySymbol INITIALIZE_COPY = createRubySymbol("initialize_copy", toLocal(190));
    public static final RubySymbol INITIALIZE_CLONE = createRubySymbol("initialize_clone", toLocal(191));
    public static final RubySymbol INITIALIZE_DUP = createRubySymbol("initialize_dup", toLocal(192));
    public static final RubySymbol TO_INT = createRubySymbol("to_int", toLocal(193));
    public static final RubySymbol TO_ARY = createRubySymbol("to_ary", toLocal(194));
    public static final RubySymbol TO_STR = createRubySymbol("to_str", toLocal(195));
    public static final RubySymbol TO_SYM = createRubySymbol("to_sym", toLocal(196));
    public static final RubySymbol TO_HASH = createRubySymbol("to_hash", toLocal(197));
    public static final RubySymbol TO_PROC = createRubySymbol("to_proc", toLocal(198));
    public static final RubySymbol TO_IO = createRubySymbol("to_io", toLocal(199));
    public static final RubySymbol TO_A = createRubySymbol("to_a", toLocal(200));
    public static final RubySymbol TO_S = createRubySymbol("to_s", toLocal(201));
    public static final RubySymbol TO_I = createRubySymbol("to_i", toLocal(202));
    public static final RubySymbol TO_F = createRubySymbol("to_f", toLocal(203));
    public static final RubySymbol TO_R = createRubySymbol("to_r", toLocal(204));
    public static final RubySymbol BT = createRubySymbol("bt", toLocal(205));
    public static final RubySymbol BT_LOCATIONS = createRubySymbol("bt_locations", toLocal(206));
    public static final RubySymbol CALL = createRubySymbol("call", toLocal(207));
    public static final RubySymbol MESG = createRubySymbol("mesg", toLocal(208));
    public static final RubySymbol EXCEPTION = createRubySymbol("exception", toLocal(209));
    public static final RubySymbol NOT = createRubySymbol("not", toLocal(210));
    public static final RubySymbol AND = createRubySymbol("and", toLocal(211));
    public static final RubySymbol OR = createRubySymbol("or", toLocal(212));
    public static final RubySymbol USCORE = createRubySymbol("_", toLocal(213));
    public static final RubySymbol LASTLINE = createRubySymbol("$_", toGlobal(214));
    public static final RubySymbol BACKREF = createRubySymbol("$~", toGlobal(215));

    public static final int STATIC_SYMBOLS_SIZE = 216;
    static {
        assert STATIC_SYMBOLS_SIZE == STATIC_SYMBOLS.length;
    }

    public static RubySymbol createRubySymbol(String string, long id) {
        Rope rope = RopeConstants.lookupUSASCII(string);
        if (rope == null) {
            rope = RopeOperations.encodeAscii(string, USASCIIEncoding.INSTANCE);
        }

        final RubySymbol symbol = new RubySymbol(string, rope, id);
        CORE_SYMBOLS.add(symbol);

        if (id != RubySymbol.UNASSIGNED_ID) {
            final int index = idToIndex(id);
            STATIC_SYMBOLS[index] = symbol;
        }
        return symbol;
    }

    public static RubySymbol createRubySymbol(String string) {
        return createRubySymbol(string, RubySymbol.UNASSIGNED_ID);
    }

    public static int idToIndex(long id) {
      final int index;
      if (id > LAST_OP_ID) {
        index = (int) id >> 4;
      } else {
        index = (int) id;
      }
      assert index < STATIC_SYMBOLS_SIZE;
      return index;
    }

    private static long toLocal(long id) {
        return id << 4 | STATIC_SYMBOL_ID;
    }

    private static long toGlobal(long id) {
        return id << 4 | STATIC_SYMBOL_ID | GLOBAL_SYMBOL_ID;
    }

    public static boolean isDynamicSymbol(long value) {
        return (value & STATIC_SYMBOL_ID) == 0 && value > LAST_OP_ID;
    }

}
// @formatter:on
