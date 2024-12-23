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

import com.maehem.javamancer.ViewUtils;
import static com.maehem.javamancer.logging.Logging.LOGGER;
import com.maehem.javamancer.resource.Util;
import java.util.ArrayList;
import java.util.HexFormat;
import java.util.logging.Level;

/**
 *
 * @author Mark J Koch ( @maehem on GitHub )
 */
public class ANHAnima {

    public final int sleep;
    public final int offset;
    public final byte data[];
    public final ArrayList<ANHFrame> frames = new ArrayList<>();

    public ANHAnima(int sleep, int offset, byte[] data, byte[] roomPic) {
        this.sleep = sleep;
        this.offset = offset; // TODO: Don't keep
        this.data = data;    // TODO: Don't keep

        int p = offset;
        while (p < data.length - 1) {
            int xOff = data[p++] & 0xFF;
            int yOff = data[p++] & 0xFF;
            int w = data[p++] & 0xFF;
            int h = data[p++] & 0xFF;
            byte rleBlob[] = new byte[w * h];
            LOGGER.log(Level.CONFIG, "Frame RLE settings: x:{0} y:{1} {2}x{3}  len:{4}", new Object[]{xOff, yOff, w, h, w * h});
            p += Util.decodeRLE(data, p, w * h, rleBlob, 0);
            //System.arraycopy(data, p, rleBlob, 0, rleBlob.length);

            HexFormat hexFormat = HexFormat.of();
            StringBuilder sb = new StringBuilder("Frame Info: " + xOff + "," + yOff + " " + w + "x" + h + " :: ");
            for (int i = 0; i < rleBlob.length; i++) {
                if (i % w == 0) {
                    sb.append("\n");
                }
                sb.append(hexFormat.toHexDigits(rleBlob[i])).append(" ");
            }
            LOGGER.log(Level.FINE, sb.toString());

            frames.add(new ANHFrame(xOff, yOff, w, h, rleBlob));
        }
        LOGGER.log(Level.SEVERE, "Animation has " + frames.size() + " frames.");

        // Apply XOR frames to room PIC
        // Copy Room data. Original might be used elsewhere.
        //byte roomCopy[] = new byte[roomPic.length];
        //System.arraycopy(roomPic, 0, roomCopy, 0, roomPic.length);

        final int PIC_W = ViewUtils.PIC_DATA_WIDTH;

        frames.forEach((frame) -> {
            //System.arraycopy(roomData, 0, roomCopy, 0, roomData.length);
            byte xorFrame[] = new byte[frame.data.length];
            // XOR apply frame data.
            for (int y = 0; y < frame.h; y++) {
                for (int x = 0; x < frame.w; x++) {
                    int index = ((frame.yOffset - 8) * PIC_W) + (frame.xOffset - 4) + (y * PIC_W) + x;
                    try {
                        roomPic[index] ^= frame.data[y * frame.w + x]; // XOR, for next frame
                        xorFrame[y * frame.w + x] = roomPic[index]; // Our snapshot.
                    } catch (ArrayIndexOutOfBoundsException ex) {
                        int a = 0; // debug break
                        // Sometimes writes past win height. Ignore.
                    }
                }
            }

            // Store clipped frame.
            frame.setXorData(xorFrame);
        });
    }

}
