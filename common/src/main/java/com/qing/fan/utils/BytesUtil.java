package com.qing.fan.utils;

import java.nio.charset.Charset;

/**
 * @author QingFan 2020-05-27
 * @version 1.0.0
 */
public class BytesUtil {

    private static final String UTF8_ENCODING = "UTF-8";

    private static final Charset UTF8_CHARSET = Charset.forName(UTF8_ENCODING);

    private static final byte[] EMPTY_BYTE_ARRAY = new byte[0];

    /**
     * Size of boolean in bytes
     */
    public static final int SIZEOF_BOOLEAN = Byte.SIZE / Byte.SIZE;

    /**
     * Size of byte in bytes
     */
    public static final int SIZEOF_BYTE = SIZEOF_BOOLEAN;

    /**
     * Size of char in bytes
     */
    public static final int SIZEOF_CHAR = Character.SIZE / Byte.SIZE;

    /**
     * Size of double in bytes
     */
    public static final int SIZEOF_DOUBLE = Double.SIZE / Byte.SIZE;

    /**
     * Size of float in bytes
     */
    public static final int SIZEOF_FLOAT = Float.SIZE / Byte.SIZE;

    /**
     * Size of int in bytes
     */
    public static final int SIZEOF_INT = Integer.SIZE / Byte.SIZE;

    /**
     * Size of long in bytes
     */
    public static final int SIZEOF_LONG = Long.SIZE / Byte.SIZE;

    /**
     * Size of short in bytes
     */
    public static final int SIZEOF_SHORT = Short.SIZE / Byte.SIZE;

    /**
     * Mask to apply to a long to reveal the lower int only. Use like this:
     * int i = (int)(0xFFFFFFFF00000000l ^ some_long_value);
     */
    public static final long MASK_FOR_LOWER_INT_IN_LONG = 0xFFFFFFFF00000000l;

    /**
     * Estimate of size cost to pay beyond payload in jvm for instance of byte [].
     * Estimate based on study of jhat and jprofiler numbers.
     */
    // JHat says BU is 56 bytes.
    // SizeOf which uses java.lang.instrument says 24 bytes. (3 longs?)
    public static final int ESTIMATED_HEAP_TAX = 16;

    private static final boolean UNSAFE_UNALIGNED = UnsafeAvailChecker.unaligned();

    /**
     * Returns length of the byte array, returning 0 if the array is null.
     * Useful for calculating sizes.
     *
     * @param b byte array, which can be null
     * @return 0 if b is null, otherwise returns length
     */
    final public static int len(byte[] b) {
        return b == null ? 0 : b.length;
    }

    public static byte[][] toByteArrays(final byte[] column) {
        byte[][] result = new byte[1][];
        result[0] = column;
        return result;
    }

    public static byte[] toBytes(String s) {
        return s.getBytes(UTF8_CHARSET);
    }

    public static byte[] toBytes(long val) {
        byte[] b = new byte[8];
        for (int i = 7; i > 0; i--) {
            b[i] = (byte) val;
            val >>>= 8;
        }
        b[0] = (byte) val;
        return b;
    }

    public static byte[] longToBytes(long res) {
        byte[] buffer = new byte[8];
        for (int i = 0; i < 8; i++) {
            int offset = 64 - (i + 1) * 8;
            buffer[i] = (byte) ((res >> offset) & 0xff);
        }
        return buffer;
    }

    public static long bytesToLong(byte[] b) {
        long values = 0;
        for (int i = 0; i < 8; i++) {
            values <<= 8; values|= (b[i] & 0xff);
        }
        return values;
    }
}
