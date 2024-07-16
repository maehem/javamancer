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
package com.maehem.javamancer.resource;

import com.maehem.javamancer.logging.Logging;
import static com.maehem.javamancer.logging.Logging.LOGGER;
import java.util.HexFormat;
import java.util.logging.Level;

/**
 *
 * @author Mark J Koch ( @maehem on GitHub )
 */
public class Util {

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

//        HexFormat hexFormat = HexFormat.of();
//        LOGGER.log(Level.SEVERE, "RLE Decode => ");
//        int columns = 16;
//        for (int ii = dstIdxOrig; ii < dstIdx; ii += columns) {
//            StringBuilder sb = new StringBuilder(String.format("%04X", ii & 0xFFFF) + ": ");
//            try {
//                for (int i = 0; i < columns; i++) {
//                    byte b = dst[ii + i];
//                    sb.append(hexFormat.toHexDigits(b)).append(" ");
//                }
//            } catch (IndexOutOfBoundsException ex) {
//
//            }
//            Logging.LOGGER.log(Level.SEVERE, sb.toString());
//        }

        return processed;
    }

    public static void xorRows(byte[] inout, int inoutIdx, int w, int h) {
        for (int i = 0; i < h - 1; i++) {
            for (int j = 0; j < w; j++) {
                inout[inoutIdx + ((i + 1) * w) + j] ^= inout[inoutIdx + (i * w) + j];
            }
        }
        LOGGER.log(Level.FINER, "XOR Result ===>");
        HexFormat hexFormat = HexFormat.of();
        for (int ii = inoutIdx; ii < inoutIdx + w * h; ii += 16) {
            Logging.LOGGER.log(Level.FINER,
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
}
