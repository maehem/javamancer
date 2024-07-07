/*
 * MIT License
 *
 * Copyright (c) 2024 Mark J. Koch ( @maehem on GitHub )
 *
 * Portions of this software are Copyright (c) 2018 Henadzi Matuts and are
 * derived from their project: https://github.com/HenadziMatuts/Reuromancer
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package com.maehem.javamancer.resource.file;

import com.maehem.javamancer.logging.Logging;
import static com.maehem.javamancer.logging.Logging.LOGGER;
import java.util.HexFormat;
import java.util.logging.Level;

/**
 *
 * @author Mark J Koch ( @maehem on GitHub )
 */
public class Util {

    /*
    uint8_t DosPal[1024] = {
    0x00, 0x00, 0x00, 0x00, // black
    0x80, 0x00, 0x00, 0x00, // blue
    0x00, 0x80, 0x00, 0x00, // green
    0x80, 0x80, 0x00, 0x00, // cyan
    0x00, 0x00, 0x80, 0x00, // red
    0x80, 0x00, 0x80, 0x00, // magnetta
    0x00, 0x80, 0x80, 0x00, // brown
    0xC0, 0xC0, 0xC0, 0x00, // light gray
    0x80, 0x80, 0x80, 0x00, // dark gray
    0xFF, 0x00, 0x00, 0x00, // light blue
    0x00, 0xFF, 0x00, 0x00, // light green
    0xFF, 0xFF, 0x00, 0x00, // light cyan
    0x00, 0x00, 0xFF, 0x00, // light red
    0xFF, 0x00, 0xFF, 0x00, // light magnetta
    0x00, 0xFF, 0xFF, 0x00, // yellow
    0xFF, 0xFF, 0xFF, 0x00, // white
    0x00,
};
     */
    /**
     * Color Palette
     *
     * Stored as web color string red[23..16] grn:[15..0] blu:[7..0] For JavaFX
     * color creation with: Color.web(palette[index], double alpha)
     */
    public static final String[] palette = {
        "0x000000", //black
        "0x000080", //blue
        "0x008000", //green
        "0x008080", //cyan
        "0x800000", //red
        "0x800080", //magenta
        "0x808000", //brown
        "0xC0C0C0", //light gray
        "0x808080", //dark gray
        "0x0000FF", //light blue
        "0x00FF00", //light green
        "0x00FFFF", //light cyan
        "0xFF0000", //light red
        "0xFF00FF", //light magenta
        "0xFFFF00", //yellow
        "0xFFFFFF" // white
    };

    /**
     *
     * <pre>
     * int decode_rle(uint8_t *_src, uint32_t len, uint8_t *_dst) {
     *      uint8_t *src = _src, *dst = _dst, *p = _dst;
     *      uint32_t processed = 0;
     *
     *      while (len) {
     *          if (*src > 0x7F) {
     *              int i = 0x100 - *src++;
     *              processed++;
     *
     *              while (i--) {
     *                  dst++ = *src++;
     *                  len--;
     *                  processed++;
     *              }
     *          } else {
     *              int num = *src++, val = *src++;
     *              processed += 2;
     *
     *              memset(dst, val, (size_t)++num);
     *              dst += num;
     *              len -= num;
     *          }
     *      }
     *
     *      return processed;
     * }
     * </pre>
     *
     *
     *
     *
     * @param src
     * @param len
     * @param dst
     * @return
     */
    public static int decodeRLE(byte[] src, int srcIdx, int len, byte[] dst, int dstIdx) {
        //int decode_rle(uint8_t *_src, uint32_t len, uint8_t *_dst) {

//	uint8_t *src = _src, *dst = _dst, *p = _dst;
//	uint32_t processed = 0;
        int srcIdxOrig = srcIdx;
        int dstIdxOrig = dstIdx;

        int processed = 0;

        while (len > 0) {
            //   if (*src > 0x7F) {
            if ((src[srcIdx] & 0xFF) > 0x7F) { // Copy value 0x100 - val times
                /*
                 *              int i = 0x100 - *src++;
                 *              processed++;
                 *
                 *              while (i--) {
                 *                  dst++ = *src++;
                 *                  len--;
                 *                  processed++;
                 *              }
                 */
                int i = 0x100 - (src[srcIdx] & 0xFF);
                srcIdx++;
                processed++;

                while (i > 0) {
                    i--;
                    //*dst++ = *src++;
                    dst[dstIdx] = src[srcIdx];
                    dstIdx++;
                    srcIdx++;
                    len--;
                    processed++;
                }
            } else {  // Duplicate val  num times.
                /*
                 *              int num = *src++, val = *src++;
                 *              processed += 2;
                 *
                 *              memset(dst, val, (size_t)++num);
                 *              dst += num;
                 *              len -= num;
                 */
                int num = src[srcIdx] & 0xFF;
                srcIdx++;
                int val = src[srcIdx] & 0xFF;
                srcIdx++;

                processed += 2;

                num++;
                //memset(dst, val, (size_t)++num );
                for (int ii = 0; ii < num; ii++) {
                    dst[dstIdx + ii] = (byte) (val & 0xFF);
                }

                dstIdx += num;
                len -= num;
            }
        }

        HexFormat hexFormat = HexFormat.of();
        LOGGER.log(Level.SEVERE, "RLE Decode => ");
        for (int ii = dstIdxOrig; ii < dstIdx; ii += 16) {
            Logging.LOGGER.log(Level.SEVERE,
                    String.format("%04X", ii & 0xFFFF) + ": "
                    + hexFormat.toHexDigits(dst[ii]) + " " + hexFormat.toHexDigits(dst[ii + 1]) + " "
                    + hexFormat.toHexDigits(dst[ii + 2]) + " " + hexFormat.toHexDigits(dst[ii + 3]) + " "
                    + hexFormat.toHexDigits(dst[ii + 4]) + " " + hexFormat.toHexDigits(dst[ii + 5]) + " "
                    + hexFormat.toHexDigits(dst[ii + 6]) + " " + hexFormat.toHexDigits(dst[ii + 7]) + " "
                    + hexFormat.toHexDigits(dst[ii + 8]) + " " + hexFormat.toHexDigits(dst[ii + 9]) + " "
                    + hexFormat.toHexDigits(dst[ii + 10]) + " " + hexFormat.toHexDigits(dst[ii + 11]) + " "
                    + hexFormat.toHexDigits(dst[ii + 12]) + " " + hexFormat.toHexDigits(dst[ii + 13]) + " "
                    + hexFormat.toHexDigits(dst[ii + 14]) + " " + hexFormat.toHexDigits(dst[ii + 15]) + " "
            );
        }

        return processed;
    }

    public static void xorRows(byte[] inout, int inoutIdx, int w, int h) {
        for (int i = 0; i < h - 1; i++) {
            for (int j = 0; j < w; j++) {
                inout[inoutIdx + ((i + 1) * w) + j] ^= inout[inoutIdx + (i * w) + j];
            }
        }
        LOGGER.log(Level.SEVERE, "XOR Result ===>");
        HexFormat hexFormat = HexFormat.of();
        for (int ii = inoutIdx; ii < inoutIdx + w * h; ii += 16) {
            Logging.LOGGER.log(Level.SEVERE,
                    String.format("%04X", ii & 0xFFFF) + ": "
                    + hexFormat.toHexDigits(inout[ii]) + " " + hexFormat.toHexDigits(inout[ii + 1]) + " "
                    + hexFormat.toHexDigits(inout[ii + 2]) + " " + hexFormat.toHexDigits(inout[ii + 3]) + " "
                    + hexFormat.toHexDigits(inout[ii + 4]) + " " + hexFormat.toHexDigits(inout[ii + 5]) + " "
                    + hexFormat.toHexDigits(inout[ii + 6]) + " " + hexFormat.toHexDigits(inout[ii + 7]) + " "
                    + hexFormat.toHexDigits(inout[ii + 8]) + " " + hexFormat.toHexDigits(inout[ii + 9]) + " "
                    + hexFormat.toHexDigits(inout[ii + 10]) + " " + hexFormat.toHexDigits(inout[ii + 11]) + " "
                    + hexFormat.toHexDigits(inout[ii + 12]) + " " + hexFormat.toHexDigits(inout[ii + 13]) + " "
                    + hexFormat.toHexDigits(inout[ii + 14]) + " " + hexFormat.toHexDigits(inout[ii + 15]) + " "
            );
        }
    }
//    static void xor_rows(uint8_t  *inout, uint32_t w, uint32_t h) {
//	uint8_t *p = inout;
//
//        for (uint32_t i = 0; i < h - 1; i++) {
//            for (uint32_t j = 0; j < w; j++) {
//                p[((i + 1) * w) + j] ^= p[(i * w) + j];
//            }
//        }
//    }
}
