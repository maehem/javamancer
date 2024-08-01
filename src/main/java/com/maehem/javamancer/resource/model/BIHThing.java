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
package com.maehem.javamancer.resource.model;

import com.maehem.javamancer.logging.Logging;
import com.maehem.javamancer.resource.file.BihResource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HexFormat;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * <pre>
 *typedef struct bih_hdr_t {
 *      uint16_t cb_offt;                 // a8e8
 *      uint16_t cb_segt;                 // a8ea
 *      uint16_t ctrl_struct_addr;        // a8ec
 *      uint16_t text_offset;             // a8ee
 *      uint16_t bytecode_array_offt[3];  // a8f0
 *      uint16_t init_obj_code_offt[3];   // a8f6
 *      uint16_t unknown[10];             // a8fc
 *      // the rest of bih file
 * } bih_hdr_t;
 * </pre>
 *
 *
 * @author Mark J Koch ( @maehem on GitHub )
 */
public class BIHThing {

    public static final Logger LOGGER = Logging.LOGGER;

    public final byte[] data;

    public final String name;
    public final int cbOffset;
    public final int cbSegment;
    public final int ctrlStructAddr;
    public final ArrayList<String> text = new ArrayList<>();
    public final int[] byteCodeArrayOffset = new int[3];
    public final int[] iocOff = new int[3];
    public final byte[] unknown;

    public final byte[][] byteCode = new byte[3][];
    public final byte[][] objectCode = new byte[3][];

    public BIHThing(BihResource bih, byte[] data, int len) {
        this.name = bih.getName();

        this.data = new byte[len];
        System.arraycopy(data, 0, this.data, 0, len);

        switch (name) {
            case "NEWS", "PAXBBS" -> {
                LOGGER.log(Level.FINER, "Special BIH \"{0}\" found.", name);
                cbOffset = 0;
                cbSegment = 0;
                ctrlStructAddr = 0;
                initText(0);
                unknown = new byte[0];
            }
            case "CORNERS" -> {
                LOGGER.log(Level.FINER, "Special BIH \"{0}\" found.", name);
                cbOffset = 0;
                cbSegment = 0;
                ctrlStructAddr = 0;
                unknown = new byte[data.length];
                System.arraycopy(data, 0, unknown, 0, data.length);
            }
            case "ROOMPOS" -> {
                LOGGER.log(Level.FINER, "Special BIH \"{0}\" found.", name);
                cbOffset = 0;
                cbSegment = 0;
                ctrlStructAddr = 0;
                unknown = new byte[data.length];
                System.arraycopy(data, 0, unknown, 0, data.length);
            }
            default -> {
                if (name.startsWith("DB")) {
                    int txtOffset = ((data[7] & 0xff) << 8) + (data[6] & 0xff);
                    cbOffset = (data[1] & 0xff) << 8 + (data[0] & 0xff);
                    cbSegment = (data[3] & 0xff) << 8 + (data[2] & 0xff);
                    ctrlStructAddr = (data[5] & 0xff) << 8 + (data[4] & 0xff);
                    unknown = new byte[txtOffset];
                    System.arraycopy(data, 0, unknown, 0, txtOffset);
                    initText(txtOffset);
                } else if (!name.startsWith("R")) {
                    LOGGER.log(Level.FINER, "Special DB BIH \"{0}\" found.", name);
                    cbOffset = 0;
                    cbSegment = 0;
                    ctrlStructAddr = 0;
                    unknown = new byte[data.length];
                    System.arraycopy(data, 0, unknown, 0, data.length);
                } else {
                    cbOffset = (data[1] & 0xff) << 8 + (data[0] & 0xff);
                    cbSegment = (data[3] & 0xff) << 8 + (data[2] & 0xff);
                    ctrlStructAddr = (data[5] & 0xff) << 8 + (data[4] & 0xff);
                    int txtOffset = ((data[7] & 0xff) << 8) + (data[6] & 0xff);
                    initText(txtOffset);
                    byteCodeArrayOffset[0] = ((data[9] & 0xff) << 8) + (data[8] & 0xff);
                    byteCodeArrayOffset[1] = ((data[11] & 0xff) << 8) + (data[10] & 0xff);
                    byteCodeArrayOffset[2] = ((data[13] & 0xff) << 8) + (data[12] & 0xff);
                    iocOff[0] = ((data[15] & 0xff) << 8) + (data[14] & 0xff);
                    iocOff[1] = ((data[17] & 0xff) << 8) + (data[16] & 0xff);
                    iocOff[2] = ((data[19] & 0xff) << 8) + (data[18] & 0xff);
                    unknown = new byte[20];
                    System.arraycopy(data, 20, unknown, 0, 20);
                    // Process byte array
                    int bcEnd[] = new int[]{0, 0, 0};
                    if (byteCodeArrayOffset[0] != 0) { // Something there.
                        bcEnd[0] = txtOffset;
                        // Whittle it down.
                        if (iocOff[2] != 0 && iocOff[2] > iocOff[1]) {
                            bcEnd[0] = iocOff[2];
                        }
                        if (iocOff[1] != 0 && iocOff[1] > iocOff[0]) {
                            bcEnd[0] = iocOff[1];
                        }
                        if (iocOff[0] != 0) {
                            bcEnd[0] = iocOff[0];
                        }
                        if (byteCodeArrayOffset[2] != 0) {
                            bcEnd[0] = byteCodeArrayOffset[2];
                        }
                        if (byteCodeArrayOffset[1] != 0) {
                            bcEnd[0] = byteCodeArrayOffset[1];
                        }
                    }
                    if (byteCodeArrayOffset[1] != 0) { // Something there.
                        bcEnd[1] = txtOffset;
                        // Whittle it down.
                        if (iocOff[2] != 0 && iocOff[2] > iocOff[1]) {
                            bcEnd[1] = iocOff[2];
                        }
                        if (iocOff[1] != 0 && iocOff[1] > iocOff[0]) {
                            bcEnd[1] = iocOff[1];
                        }
                        if (iocOff[0] != 0) {
                            bcEnd[1] = iocOff[0];
                        }
                        if (byteCodeArrayOffset[2] != 0) {
                            bcEnd[1] = byteCodeArrayOffset[2];
                        }
                    }
                    if (byteCodeArrayOffset[2] != 0) { // Something there.
                        bcEnd[2] = txtOffset;
                        // Whittle it down.
                        if (iocOff[2] != 0 && iocOff[2] > iocOff[1]) {
                            bcEnd[2] = iocOff[2];
                        } else if (iocOff[1] != 0 && iocOff[1] > iocOff[0]) {
                            bcEnd[2] = iocOff[1];
                        } else if (iocOff[0] != 0) {
                            bcEnd[2] = iocOff[0];
                        }
                    }
                    int iocEnd[] = new int[]{
                        smallestOf(iocOff[0],
                        iocOff[1],
                        iocOff[2],
                        txtOffset
                        ),
                        smallestOf(iocOff[1],
                        iocOff[0],
                        iocOff[2],
                        txtOffset
                        ),
                        smallestOf(iocOff[2],
                        iocOff[0],
                        iocOff[1],
                        txtOffset
                        )
                    };  // Assemble Byte Codes and Object Codes
                    for (int i = 0; i < 3; i++) {
                        byteCode[i] = new byte[bcEnd[i] - byteCodeArrayOffset[i]];
                        System.arraycopy(data, byteCodeArrayOffset[i], byteCode[i], 0, byteCode[i].length);

                        objectCode[i] = new byte[iocEnd[i] - iocOff[i]];
                        System.arraycopy(data, iocOff[i], objectCode[i], 0, objectCode[i].length);
                    }
                    HexFormat hex = HexFormat.of();
                    LOGGER.log(Level.FINEST, "BIH: {0}", name);
                    LOGGER.log(Level.FINEST, "        textOffset: {0}", hex.toHexDigits(txtOffset));
                    LOGGER.log(Level.FINEST, "    Byte Code:\n    0:   beg: {0}  last:{1}\n    1:   beg: {2}  last:{3}\n    2:   beg: {4}  last:{5}",
                            new Object[]{
                                hex.toHexDigits(byteCodeArrayOffset[0]), hex.toHexDigits(bcEnd[0]),
                                hex.toHexDigits(byteCodeArrayOffset[1]), hex.toHexDigits(bcEnd[1]),
                                hex.toHexDigits(byteCodeArrayOffset[2]), hex.toHexDigits(bcEnd[2])
                            });
                    LOGGER.log(Level.FINEST, "     Obj Code:\n    0:   beg: {0}  last:{1}\n    1:   beg: {2}  last:{3}\n    2:   beg: {4}  last:{5}",
                            new Object[]{
                                hex.toHexDigits(iocOff[0]), hex.toHexDigits(iocEnd[0]),
                                hex.toHexDigits(iocOff[1]), hex.toHexDigits(iocEnd[1]),
                                hex.toHexDigits(iocOff[2]), hex.toHexDigits(iocEnd[2])
                            });
                }
            }
        }
    }

    private int smallestOf(int a, int b, int c, int d) {
        LOGGER.log(Level.SEVERE,
                "smallestOf:  a:{0} ::: b:{1}  c:{2}  d:{3}",
                new Object[]{a, b, c, d}
        );
        if (a != 0) {
            // Gather three other numbers
            ArrayList<Integer> idxList = new ArrayList<>();
            if (b != 0 && b > a) {
                idxList.add(b);
            }
            if (c != 0 && c > a) {
                idxList.add(c);
            }
            if (d != 0 && d > a) {
                idxList.add(d);
            }
            Integer[] vals = idxList.toArray(Integer[]::new);
            Arrays.sort(vals);

            if (vals.length > 0) {
                LOGGER.log(Level.FINEST, "    return: {0}", vals[0]);
                return vals[0];
            }
        }

        LOGGER.log(Level.FINEST, "    return: {0}", 0);

        return 0;
    }

    private void initText(int index) {
        String textBlob = new String(data, index, data.length - index).trim();

        for (int i = 0; i < textBlob.length();) {
            int start = i;

            // 0x00 break up text elements so look for one.
            i = textBlob.indexOf('\0', start);
            if (i < 0) {
                // None found so go to end.
                i = textBlob.length();
            }

            // grab the start index up to the found '00' index.
            text.add(textBlob.substring(start, i));

            if (i < textBlob.length()) {
                // if not at the end, increment next start point.
                ++i;
            }
        }
    }
}
