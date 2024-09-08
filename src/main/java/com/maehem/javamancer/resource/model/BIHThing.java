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
    public final ArrayList<String> passwords = new ArrayList<>(); // DB Obly
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
                    cbOffset = (data[1] & 0xff) << 8 + (data[0] & 0xff);
                    cbSegment = (data[3] & 0xff) << 8 + (data[2] & 0xff);
                    ctrlStructAddr = (data[5] & 0xff) << 8 + (data[4] & 0xff);
                    int txtOffset = ((data[7] & 0xff) << 8) + (data[6] & 0xff);
                    byteCodeArrayOffset[0] = ((data[9] & 0xff) << 8) + (data[8] & 0xff);
                    byteCodeArrayOffset[1] = ((data[11] & 0xff) << 8) + (data[10] & 0xff); // Passwords (1 or 2)
                    byteCodeArrayOffset[2] = ((data[13] & 0xff) << 8) + (data[12] & 0xff);
                    unknown = new byte[txtOffset];
                    System.arraycopy(data, 0, unknown, 0, txtOffset);
                    initPasswords(byteCodeArrayOffset[1], byteCodeArrayOffset[2]);
                    initText(txtOffset);
                } else if (name.startsWith("COPEN")
                        || name.startsWith("PSYCHO")
                        || name.startsWith("SEA2")
                        || name.startsWith("IRS1")) {  // Plain text files.
                    cbOffset = 0;
                    cbSegment = 0;
                    ctrlStructAddr = 0;
                    unknown = new byte[0];
                    initText(0);
                } else if (name.startsWith("IRS0")
                        || name.startsWith("SEA0")
                        || name.startsWith("SEA1")
                        || name.startsWith("POLICE")) {  // Name/BAMA list
                    cbOffset = 0;
                    cbSegment = 0;
                    ctrlStructAddr = 0;
                    unknown = new byte[0];
                    bamaList();
                } else if (!name.startsWith("R")) { // Entirely Unknown type.
                    LOGGER.log(Level.FINER, "Special DB BIH \"{0}\" found.", name);
                    cbOffset = 0;
                    cbSegment = 0;
                    ctrlStructAddr = 0;
                    unknown = new byte[data.length];
                    System.arraycopy(data, 0, unknown, 0, data.length);
                } else {  // Should only be ROOM type here.
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

    private void initPasswords(int index, int endIndex) {
        String textBlob = new String(data, index, endIndex - index).trim();

        for (int i = 0; i < textBlob.length();) {
            int start = i;

            // 0x00 break up text elements so look for one.
            i = textBlob.indexOf('\0', start);
            if (i < 0) {
                // None found so go to end.
                i = textBlob.length();
            }

            // grab the start index up to the found '00' index.
            passwords.add(textBlob.substring(start, i));

            if (i < textBlob.length()) {
                // if not at the end, increment next start point.
                ++i;
            }
        }
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

    /**
     * <pre>
       Example:
        Hex View  00 01 02 03 04 05 06 07  08 09 0A 0B 0C 0D 0E 0F

        00000000  01 00 20 20 20 20 20 20  20 20 20 20 20 20 20 20  ..
        00000010  20 20 20 30 35 36 33 30  36 31 31 38 00 63 01 00     056306118.c..
        00000020  46 49 4E 44 4C 45 59 20  4D 41 54 54 48 45 57 20  FINDLEY MATTHEW
        00000030  20 20 00 30 30 31 31 33  31 39 36 38 00 FF 00 00    .001131968....
        00000040  43 48 55 4E 47 20 4C 4F  20 44 55 43 20 20 20 20  CHUNG LO DUC
        00000050  20 20 00 34 37 31 32 39  34 38 31 39 00 FF 00 00    .471294819....
        00000060  4E 41 4B 41 53 4F 4E 45  20 53 41 4E 44 52 41 20  NAKASONE SANDRA
        00000070  20 20 00 32 35 35 38 38  35 36 39 37 00 FF 00 00    .255885697....
        00000080  4D 41 52 54 49 4E 45 5A  20 52 41 55 4C 20 20 20  MARTINEZ RAUL
        00000090  20 20 00 35 34 39 38 38  37 31 31 30 00 FF 00 00    .549887110....
        000000A0  00
     * </pre>.
     */
    private void bamaList() {
        // Break into 32 byte chunks. Each chunk is a list item.
        for (int i = 0; i < data.length; i += 32) {

            if (data[i] == 0) {
                break;
            }

            // Chunk  ==  <19 byte name> | <9 byte number> [00] < 2 bytes> [00]
            String nameStr = new String(data, i, 19);
            String bamaStr = new String(data, i + 19, 9);
            // Leave out bytes for now until we know what they are for.

            text.add(nameStr + "\t" + bamaStr);
        }
    }
}
