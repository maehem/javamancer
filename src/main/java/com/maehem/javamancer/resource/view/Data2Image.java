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
package com.maehem.javamancer.resource.view;

import com.maehem.javamancer.resource.file.Util;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

/**
 *
 * @author Mark J Koch ( @maehem on GitHub )
 */
public class Data2Image extends WritableImage {

    /**
     * Used for IMH files. W/H is stored at beginning of data block.
     *
     * @param data
     * @param dataIndex
     */
    public Data2Image(byte[] data, int dataIndex) {
        super(probeWidth(data, dataIndex), probeHeight(data, dataIndex));
        if (getWidth() == 0 || getHeight() == 0) {
            return;
        }

        dataIndex += 8; // X/Y W/H header is 8 bytes.
        PixelWriter pw = getPixelWriter();
        for (int row = 0; row < getHeight(); row++) {
            for (int col = 0; col < getWidth(); col += 2) {
                byte pixels = data[dataIndex];
                dataIndex++;

                // pixels contains two 4-bit indexed color nibbles.
                pw.setColor(col, row, Color.web(Util.palette[(pixels & 0xF0) >> 4], 1.0));
                pw.setColor(col + 1, row, Color.web(Util.palette[(pixels & 0xF)], 1.0));
            }
        }
    }

    /**
     * Used for PIC files. No W/H in data, supplied by caller.
     *
     * @param data
     * @param width
     * @param height
     * @param dataIndex
     */
    public Data2Image(byte[] data, int width, int height, int dataIndex) {
        super(width * 2, height);
        if (getWidth() == 0 || getHeight() == 0) {
            return;
        }

        //dataIndex += 8; // X/Y W/H header is 8 bytes.
        PixelWriter pw = getPixelWriter();
        for (int row = 0; row < getHeight(); row++) {
            for (int col = 0; col < getWidth(); col += 2) {
                byte pixels = data[dataIndex];
                dataIndex++;

                // pixels contains two 4-bit indexed color nibbles.
                pw.setColor(col, row, Color.web(Util.palette[(pixels & 0xF0) >> 4], 1.0));
                pw.setColor(col + 1, row, Color.web(Util.palette[(pixels & 0xF)], 1.0));
            }
        }
    }


    private static int probeWidth(byte[] src, int srcIdx) {
        return ((src[srcIdx + 5] & 0xFF) << 8) + ((src[srcIdx + 4] & 0xFF) * 2);
    }

    private static int probeHeight(byte[] src, int srcIdx) {
        return ((src[srcIdx + 7] & 0xFF) << 8) + ((src[srcIdx + 6] & 0xFF));
    }

}
