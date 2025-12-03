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
import com.maehem.javamancer.resource.file.PicResource;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Mark J Koch ( @maehem on GitHub )
 */
public class PICThing {
    public static final Logger LOGGER = Logging.LOGGER;

    public final String name;
    //public final byte data[];
    public final ArrayList<byte[]> dataBlock = new ArrayList<>();

    public PICThing(PicResource pic, byte[] data, int len) {
        this.name = pic.getName();

        //this.data = new byte[64000];
        //System.arraycopy(data, 0, this.data, 0, len);

        int i = 0;
        while (i < len) {
            //int width = ((data[i + 5] & 0xFF) << 8) + ((data[i + 4] & 0xFF));
            //int height = ((data[i + 7] & 0xFF) << 8) + ((data[i + 6] & 0xFF));
            int width = 152;
            int height = 112;
            int size = width * height;
            byte blob[] = new byte[size];
            System.arraycopy(data, i, blob, 0, size);
            i += size;
            LOGGER.log(Level.FINE, "PICThing Added a blob: {0}x{1}", new Object[]{width, height});
            dataBlock.add(blob);
        }

        LOGGER.log(Level.FINE, "{0}:: PIC Blob count = {1}", new Object[]{name, dataBlock.size()});
    }

}
