package com.sleek.util;

public class Base64 {

    public static byte[] decode(byte[] in) {
        return decode(in, in.length);
    }

    public static byte[] decode(byte[] in, int len) {
        int length = len / 4 * 3;

        if (length == 0) {
            return new byte[0];
        }

        byte[] out = new byte[length];

        int pad = 0;
        byte chr;

        for (;;len--) {
            chr = in[len-1];

            if ((chr == '\n') || (chr == '\r') ||
                    (chr == ' ') || (chr == '\t')) {
                continue;
            }
            if (chr == '=') {
                pad++;
            } else {
                break;
            }
        }

        int out_index = 0;
        int in_index = 0;
        int bits = 0;
        int quantum = 0;
        for (int i=0; i<len; i++) {
            chr = in[i];
            if (chr == '=') {
                break;
            }

            if ((chr == '\n') || (chr == '\r') ||
                    (chr == ' ') || (chr == '\t')) {
                continue;
            }
            if ((chr >= 'A') && (chr <= 'Z')) {
                bits = chr - 65;
            } else if ((chr >= 'a') && (chr <= 'z')) {
                bits = chr - 71;
            } else if ((chr >= '0') && (chr <= '9')) {
                bits = chr + 4;
            } else if (chr == '+') {
                bits = 62;
            } else if (chr == '/') {
                bits = 63;
            } else {
                return null;
            }

            quantum = (quantum << 6) | (byte) bits;
            if (in_index%4 == 3) {
                out[out_index++] = (byte) ((quantum & 0x00FF0000) >> 16);
                out[out_index++] = (byte) ((quantum & 0x0000FF00) >> 8);
                out[out_index++] = (byte) (quantum & 0x000000FF);
            }
            in_index++;
        }
        if (pad > 0) {
            quantum = quantum << (6*pad);
            out[out_index++] = (byte) ((quantum & 0x00FF0000) >> 16);
            if (pad == 1) {
                out[out_index++] = (byte) ((quantum & 0x0000FF00) >> 8);
            }
        }

        byte[] result = new byte[out_index];
        System.arraycopy(out, 0, result, 0, out_index);
        return result;
    }

    private static final byte[] map = new byte[]
            {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N',
                    'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', 'a', 'b',
                    'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p',
                    'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', '0', '1', '2', '3',
                    '4', '5', '6', '7', '8', '9', '+', '/'};

    public static String encode(byte[] in) {
        int length = in.length * 4 / 3;
        length += length / 76 + 3; // for crlr
        byte[] out = new byte[length];
        int index = 0, i, crlr = 0, end = in.length - in.length%3;
        for (i=0; i<end; i+=3) {
            out[index++] = map[(in[i] & 0xff) >> 2];
            out[index++] = map[((in[i] & 0x03) << 4)
                    | ((in[i+1] & 0xff) >> 4)];
            out[index++] = map[((in[i+1] & 0x0f) << 2)
                    | ((in[i+2] & 0xff) >> 6)];
            out[index++] = map[(in[i+2] & 0x3f)];
            if (((index - crlr)%76 == 0) && (index != 0)) {
                out[index++] = '\n';
                crlr++;
            }
        }
        switch (in.length % 3) {
            case 1:
                out[index++] = map[(in[end] & 0xff) >> 2];
                out[index++] = map[(in[end] & 0x03) << 4];
                out[index++] = '=';
                out[index++] = '=';
                break;
            case 2:
                out[index++] = map[(in[end] & 0xff) >> 2];
                out[index++] = map[((in[end] & 0x03) << 4)
                        | ((in[end+1] & 0xff) >> 4)];
                out[index++] = map[((in[end+1] & 0x0f) << 2)];
                out[index++] = '=';
                break;
        }
        return new String(out, 0, index);
    }

    public static String encodeNoNewline(byte[] in) {
        int length = in.length * 4 / 3;

        length += length / 76 + 3;

        byte[] out = new byte[length];
        int index = 0, i, end = in.length - in.length%3;

        for (i=0; i<end; i+=3) {
            out[index++] = map[(in[i] & 0xff) >> 2];
            out[index++] = map[((in[i] & 0x03) << 4)
                    | ((in[i+1] & 0xff) >> 4)];
            out[index++] = map[((in[i+1] & 0x0f) << 2)
                    | ((in[i+2] & 0xff) >> 6)];
            out[index++] = map[(in[i+2] & 0x3f)];
        }
        switch (in.length % 3) {
            case 1:
                out[index++] = map[(in[end] & 0xff) >> 2];
                out[index++] = map[(in[end] & 0x03) << 4];
                out[index++] = '=';
                out[index++] = '=';
                break;
            case 2:
                out[index++] = map[(in[end] & 0xff) >> 2];
                out[index++] = map[((in[end] & 0x03) << 4)
                        | ((in[end+1] & 0xff) >> 4)];
                out[index++] = map[((in[end+1] & 0x0f) << 2)];
                out[index++] = '=';
                break;
        }
        return new String(out, 0, index);
    }
}