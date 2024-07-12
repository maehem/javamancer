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
import com.maehem.javamancer.resource.file.BIH;
import java.util.ArrayList;
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
    public final int[] initObjCodeOffset = new int[3];
    public final byte[] unknown = new byte[20];

    public BIHThing(BIH bih, byte[] data, int len) {
        this.name = bih.getName();

        this.data = new byte[len];
        System.arraycopy(data, 0, this.data, 0, len);

        if (name.equals("NEWS") || name.equals("PAXBBS")) {
            LOGGER.log(Level.SEVERE, "Special BIH \"" + name + "\" found.");
            cbOffset = 0;
            cbSegment = 0;
            ctrlStructAddr = 0;
            initText(0);
        } else {
            cbOffset = (data[1] & 0xff << 8) + data[0] & 0xff;
            cbSegment = (data[3] & 0xff << 8) + data[2] & 0xff;
            ctrlStructAddr = (data[5] & 0xff << 8) + data[4] & 0xff;

            initText((data[7] & 0xff << 8) + data[6] & 0xff);

            byteCodeArrayOffset[0] = (data[9] & 0xff << 8) + data[8] & 0xff;
            byteCodeArrayOffset[1] = (data[11] & 0xff << 8) + data[10] & 0xff;
            byteCodeArrayOffset[2] = (data[13] & 0xff << 8) + data[12] & 0xff;

            initObjCodeOffset[0] = (data[15] & 0xff << 8) + data[14] & 0xff;
            initObjCodeOffset[1] = (data[17] & 0xff << 8) + data[16] & 0xff;
            initObjCodeOffset[2] = (data[19] & 0xff << 8) + data[18] & 0xff;

            System.arraycopy(data, 18, unknown, 0, 20);
        }
    }

    private void initText(int index) {
        String textBlob = new String(data, index, data.length - index).trim();

        for (int i = 0; i < textBlob.length();) {
            int start = i;
            i = textBlob.indexOf('\0', start);
            if (i < 0) {
                i = textBlob.length();
            }

            text.add(textBlob.substring(start, i));

            if (i < textBlob.length()) {
                ++i;
            }
        }
    }
}
