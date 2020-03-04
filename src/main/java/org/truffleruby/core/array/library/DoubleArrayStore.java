/*
 * Copyright (c) 2020 Oracle and/or its affiliates. All rights reserved. This
 * code is released under a tri EPL/GPL/LGPL license. You can use it,
 * redistribute it and/or modify it under the terms of the:
 *
 * Eclipse Public License version 2.0, or
 * GNU General Public License version 2, or
 * GNU Lesser General Public License version 2.1.
 */

package org.truffleruby.core.array.library;

import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;

import com.oracle.truffle.api.CompilerDirectives.TruffleBoundary;
import com.oracle.truffle.api.dsl.Fallback;
import com.oracle.truffle.api.dsl.GenerateUncached;
import com.oracle.truffle.api.dsl.ImportStatic;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.library.CachedLibrary;
import com.oracle.truffle.api.library.ExportLibrary;
import com.oracle.truffle.api.library.ExportMessage;

import org.truffleruby.core.array.ArrayGuards;
import org.truffleruby.core.array.ArrayUtils;
import org.truffleruby.core.array.library.ArrayStoreLibrary.ArrayAllocator;

@ExportLibrary(value = ArrayStoreLibrary.class, receiverType = double[].class)
@GenerateUncached
public class DoubleArrayStore {

    @ExportMessage
    public static double read(double[] store, int index) {
        return store[index];
    }

    @ExportMessage
    public static boolean acceptsValue(double[] store, Object value) {
        return value instanceof Double;
    }

    @ExportMessage
    static class AcceptsAllValues {

        @Specialization
        protected static boolean acceptsZeroValues(double[] store, ZeroLengthArrayStore otherStore) {
            return true;
        }

        @Specialization
        protected static boolean acceptsDoubleValues(double[] store, double[] otherStore) {
            return true;
        }

        @Specialization
        protected static boolean acceptsDelegateValues(double[] store, DelegatedArrayStorage otherStore) {
            return otherStore.storage instanceof double[];
        }

        @Fallback
        protected static boolean acceptsOtherValues(double[] store, Object otherStore) {
            return false;
        }
    }

    @ExportMessage
    public static boolean isMutable(double[] store) {
        return true;
    }

    @ExportMessage
    public static boolean isPrimitive(double[] store) {
        return true;
    }

    @ExportMessage
    public static String toString(double[] store) {
        return "double[]";
    }

    @ExportMessage
    public static void write(double[] store, int index, Object value) {
        store[index] = (double) value;
    }

    @ExportMessage
    public static int capacity(double[] store) {
        return store.length;
    }

    @ExportMessage
    public static double[] expand(double[] store, int newCapacity) {
        return ArrayUtils.grow(store, newCapacity);
    }

    @ExportMessage
    @ImportStatic(ArrayGuards.class)
    static class CopyContents {

        @Specialization
        protected static void copyContents(double[] srcStore, int srcStart, double[] destStore, int destStart,
                int length) {
            System.arraycopy(srcStore, srcStart, destStore, destStart, length);
        }

        @Specialization(guards = "!isDoubleStore(destStore)", limit = "STORAGE_STRATEGIES")
        protected static void copyContents(double[] srcStore, int srcStart, Object destStore, int destStart, int length,
                @CachedLibrary("destStore") ArrayStoreLibrary destStores) {
            for (int i = srcStart; i < length; i++) {
                destStores.write(destStore, destStart + i, srcStore[(srcStart + i)]);
            }
        }

        protected static boolean isDoubleStore(Object store) {
            return store instanceof double[];
        }
    }

    @ExportMessage
    public static double[] toJavaArrayCopy(double[] store, int length) {
        return ArrayUtils.extractRange(store, 0, length);
    }

    @ExportMessage
    @TruffleBoundary
    public static void sort(double[] store, int size) {
        Arrays.sort(store, 0, size);
    }

    @ExportMessage
    public static Iterable<Object> getIterable(double[] store, int from, int length) {
        return () -> new Iterator<Object>() {

            private int n = from;

            @Override
            public boolean hasNext() {
                return n < from + length;
            }

            @Override
            public Object next() throws NoSuchElementException {
                if (n >= from + length) {
                    throw new NoSuchElementException();
                }

                final Object object = store[n];
                n++;
                return object;
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException("remove");
            }

        };
    }

    @ExportMessage
    static class GeneralizeForValue {

        @Specialization
        protected static ArrayAllocator generalize(double[] store, double newValue) {
            return DOUBLE_ARRAY_ALLOCATOR;
        }

        @Fallback
        protected static ArrayAllocator generalize(double[] store, Object newValue) {
            return ObjectArrayStore.OBJECT_ARRAY_ALLOCATOR;
        }
    }

    @ExportMessage
    @ImportStatic(ArrayGuards.class)
    static class GeneralizeForStore {

        @Specialization
        protected static ArrayAllocator generalize(double[] store, int[] newStore) {
            return ObjectArrayStore.OBJECT_ARRAY_ALLOCATOR;
        }

        @Specialization
        protected static ArrayAllocator generalize(double[] store, long[] newStore) {
            return ObjectArrayStore.OBJECT_ARRAY_ALLOCATOR;
        }

        @Specialization
        protected static ArrayAllocator generalize(double[] store, double[] newStore) {
            return DOUBLE_ARRAY_ALLOCATOR;
        }

        @Specialization
        protected static ArrayAllocator generalize(double[] store, Object[] newStore) {
            return ObjectArrayStore.OBJECT_ARRAY_ALLOCATOR;
        }

        @Specialization(limit = "STORAGE_STRATEGIES")
        protected static ArrayAllocator generalize(double[] store, Object newStore,
                @CachedLibrary("newStore") ArrayStoreLibrary newStores) {
            return newStores.generalizeForStore(newStore, store);
        }
    }

    @ExportMessage
    public static ArrayAllocator allocator(double[] store) {
        return DOUBLE_ARRAY_ALLOCATOR;
    }

    public static final ArrayAllocator DOUBLE_ARRAY_ALLOCATOR = new DoubleArrayAllocator();

    private static class DoubleArrayAllocator extends ArrayAllocator {

        @Override
        public double[] allocate(int capacity) {
            return new double[capacity];
        }

        @Override
        public boolean accepts(Object value) {
            return value instanceof Double;
        }

        @Override
        public boolean specializesFor(Object value) {
            return value instanceof Double;
        }

        @Override
        public boolean isDefaultValue(Object value) {
            return (double) value == 0.0;
        }
    }
}
