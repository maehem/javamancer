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
import com.maehem.javamancer.resource.file.AnhResource;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * <pre>
 * typedef struct anh_hdr_t {
 *      uint16_t anh_entries;
 *      // first entry hdr
 *  } anh_hdr_t;
 * </pre>
 *
 *
 * @author Mark J Koch ( @maehem on
 *
 * GitHub )
 */
public class ANHThing {

    public static final Logger LOGGER = Logging.LOGGER;

    private final byte[] data;

    public final String name;
    public final ArrayList<ANHEntry> anhEntry = new ArrayList<>();

    public ANHThing(AnhResource anh, byte[] data, int len, byte roomData[]) {
        this.name = anh.getName();

        this.data = new byte[len];
        System.arraycopy(data, 0, this.data, 0, len);

        int nEntries = ((data[1] & 0xFF) << 8) + (data[0] & 0xFF);
        int offset = 2;
        LOGGER.log(Level.FINE, "ANHThing: {0} items found.", nEntries);
        for (int i = 0; i < nEntries; i++) {
            int entLen = ((data[offset + 1] & 0xFF) << 8) + (data[offset] & 0xFF);
            offset += 2;
            byte entData[] = new byte[entLen];
            System.arraycopy(this.data, offset, entData, 0, entLen);
            ANHEntry entry = new ANHEntry(entData, roomData);

            anhEntry.add(entry);
            offset += entLen;
        }

        LOGGER.log(Level.FINE, () -> "ANHThing Ingested " + nEntries + " entries.");
    }

}
