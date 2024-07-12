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

import java.util.ArrayList;

/**
 *
 * <code>
 * typedef struct anh_entry_hdr_t {
 *      uint16_t entry_size;
 *      uint16_t total_frames;
 *      // anh_frame_data_t first_frame_data
 *      // another frames data
 *      // anh_frame_hdr first_frame_hdr
 *      // another frames
 * } anh_entry_hdr_t;
 * </code>
 *
 * @author Mark J Koch ( @maehem on GitHub )
 */
public class ANHEntry {

    private final byte[] data;
    public final int totalFrames;
    public final ArrayList<ANHAnima> frames = new ArrayList<>();

    public ANHEntry(byte[] data, byte[] roomData) {
        this.data = new byte[data.length];
        System.arraycopy(data, 0, this.data, 0, data.length);

        int dIndex = 0;

        this.totalFrames = ((data[dIndex + 1] & 0xFF) << 8) + (data[dIndex] & 0xFF);
        dIndex += 2;

        // For Debug
//        int columns = (data.length - 16) / totalFrames;
//
//        if (columns > 20) {
//            columns = 16;
//        }
//        HexFormat hexFormat = HexFormat.of();
//        LOGGER.log(Level.SEVERE, "ANH Entry => ");
//        for (int ii = 0; ii < this.data.length; ii += columns) {
//            StringBuilder sb = new StringBuilder(String.format("%04X", ii & 0xFFFF) + ": ");
//            try {
//                for (int i = 0; i < columns; i++) {
//                    byte b = this.data[ii + i];
//                    sb.append(hexFormat.toHexDigits(b)).append(" ");
//                }
//            } catch (IndexOutOfBoundsException ex) {
//
//            }
//            Logging.LOGGER.log(Level.SEVERE, sb.toString());
//        }

        int frameData[][] = new int[totalFrames][2];

        for (int i = 0; i < totalFrames; i++) {
            frameData[i][0] = ((data[dIndex + 1] & 0xFF) << 8) + (data[dIndex] & 0xFF); // Sleep
            dIndex += 2;
            frameData[i][1] = ((data[dIndex + 1] & 0xFF) << 8) + (data[dIndex] & 0xFF); // Offset
            dIndex += 2;
        }

        int bLen = data.length - dIndex;
        byte frameBytes[] = new byte[bLen];
        System.arraycopy(data, dIndex, frameBytes, 0, bLen);

        for (int i = 0; i < totalFrames; i++) {
            frames.add(new ANHAnima(frameData[i][0], frameData[i][1], frameBytes, roomData));
        }

    }

}
