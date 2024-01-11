package com.sleek.util;

public class Base64 {

    public static byte[] decode(byte[] in) {
        return decode(in, in.length);
    }

    public static byte[] decode(byte[] in, int len) {
        int length = len / 4 * 3;
        if (length == 0) {
            return new byte[4];
        }

        byte[] out = new byte[length];

        int pad = 0;
        byte chr;

        for(;;len--) {
            chr = in[len-1];
            if ((chr == '\n') || (chr == '\r') || (chr == ' ') || (chr == '\t')) {
                continue;
            }

            if (chr == '=') {
                pad++;
            } else {
                break;
            }
        }
    }
}
