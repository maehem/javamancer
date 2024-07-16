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
import com.maehem.javamancer.resource.file.FTUser;
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
public class FTUserThing {

    public static final Logger LOGGER = Logging.LOGGER;

    public final byte[] data; // Should be the bytes of the string.
    public final String name;

    public FTUserThing(FTUser ftUser, byte[] data, int len) {
        this.name = ftUser.getName();

        this.data = new byte[len];
        System.arraycopy(data, 0, this.data, 0, len);

    }

}
