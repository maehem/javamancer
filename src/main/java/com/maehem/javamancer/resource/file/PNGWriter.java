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
package com.maehem.javamancer.resource.file;

import com.maehem.javamancer.logging.Logging;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.zip.CRC32;
import java.util.zip.Deflater;
import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;

/**
 *
 * @author Mark J Koch ( @maehem on GitHub )
 */
public class PNGWriter {

    private static final Logger LOGGER = Logging.LOGGER;

    //   "137 80 78 71 13 10 26 10"
    //         ==> 0x89, PNG, DosLineEnding(13,10), DOS New Line(26), Unix New Line(10)
    public static final long SIGNATURE = 0x89504E470D0A1A0Al;

    public void write(File file, Image img) throws FileNotFoundException, IOException {

        RandomAccessFile raf = new RandomAccessFile(file, "rw");
        raf.getChannel().truncate(0);

        // Write file magic number
        LOGGER.log(Level.FINEST, "    - Write Signature");
        raf.writeLong(SIGNATURE);

        // Write chunks
        LOGGER.log(Level.FINEST, "    - IHDR Chunk");
        writeIHDRChunk(raf, img);
        LOGGER.log(Level.FINEST, "    - SBIT Chunk");
        writeSBITChunk(raf);
        LOGGER.log(Level.FINEST, "    - PHYS Chunk");
        writePHYSChunk(raf);
        LOGGER.log(Level.FINEST, "    - TEXT Chunk");
        writeTEXTChunk(raf, "Software\0Javamancer");

        int imgSize = (int) ((img.getWidth() + 1) * img.getHeight() * 4);
        LOGGER.log(Level.FINEST, "    :: Image Size {0} bytes -- uncompressed", imgSize);
        byte[] imgBuf = new byte[imgSize + 4 + 1024];
        PixelReader pr = img.getPixelReader();
        int i = 0;
        for ( int y=0; y<img.getHeight(); y++ ) {
            for (int x = 0; x < img.getWidth(); x++) {
                int argb = pr.getArgb(x, y);
                imgBuf[i + 0] = (byte) (((argb & 0xFF000000) >> 24) & 0xFF);
                imgBuf[i + 1] = (byte) (((argb & 0x00FF0000) >> 16) & 0xFF);
                imgBuf[i + 2] = (byte) (((argb & 0x0000FF00) >> 8) & 0xFF);
                imgBuf[i + 3] = (byte) ((argb & 0x000000FF) & 0xFF);
                i += 4;
            }
            i += 1;
        }
        LOGGER.log(Level.FINEST, "     * Compress");
        ByteBuffer bb = ByteBuffer.allocate(imgSize + 5);
        Deflater deflater = new Deflater(Deflater.DEFAULT_COMPRESSION);
        deflater.setInput(imgBuf);
        deflater.finish();
        deflater.deflate(bb);
        int size = deflater.getTotalOut() + 5;
        deflater.end();

        //List<Integer> compressed = compress(new String(imgBuf));
        LOGGER.log(Level.FINEST, "    :: Image Size {0} bytes -- compressed", size);
        //final int size = compressed.size();
        int len = size;
        while (len > 0) {
            byte chunk[];
            if (len >= 8192) {
                chunk = new byte[8192 + 4];
                chunk[0] = 'I';
                chunk[1] = 'D';
                chunk[2] = 'A';
                chunk[3] = 'T';
                for (int ii = 4; ii < chunk.length; ii++) {
                    chunk[ii] = bb.get(size - len + ii - 4);
                }
                len -= 8192;
            } else {
                chunk = new byte[len + 4];
                chunk[0] = 'I';
                chunk[1] = 'D';
                chunk[2] = 'A';
                chunk[3] = 'T';
                for (int ii = 4; ii < chunk.length; ii++) {
                    //LOGGER.log(Level.SEVERE, "Chunk Index: {0}", size - len + ii - 4);
                    chunk[ii] = bb.get(size - len + ii - 4);
                }
                len = 0;
            }
            LOGGER.log(Level.FINEST, "    - DAT Chunk");
            // write chunks in IDAT header plus 8192 bytes.
            writeIDATChunk(raf, chunk);
        }
        LOGGER.log(Level.FINEST, "    - END Chunk");
        // write end Chunk
        writeIENDChunk(raf);

        raf.close();
        LOGGER.log(Level.FINEST, "Finished Writing: {0}", file.getAbsolutePath());
    }

    private void writeIHDRChunk(RandomAccessFile raf, Image img) throws IOException {
        ByteBuffer bb = ByteBuffer.allocate(21);
        bb.putInt(13); // Length after IHDR
        bb.put((byte) 'I');
        bb.put((byte) 'H');
        bb.put((byte) 'D');
        bb.put((byte) 'R');
        bb.putInt((int) img.getWidth());
        bb.putInt((int) img.getHeight());
        bb.put((byte) 8); // Bit Depth
        bb.put((byte) 6); // Color Type:  RGBA = 06
        bb.put((byte) 0); // Comression Method: NONE (00)
        bb.put((byte) 0); // Filter Method:  NONE (00)
        bb.put((byte) 0); // Interlacing:  NONE (00)

        CRC32 crc = new CRC32();
        byte[] array = bb.array();
        crc.update(array, 4, array.length - 4);

        //long c = crc(bb.array(), 4, 17);
        //bb.putInt((int) (c & 0xFFFFFFFF));

        raf.write(bb.array());
        raf.writeInt((int) crc.getValue());
    }

    private void writeSBITChunk(RandomAccessFile raf) throws IOException {
        ByteBuffer bb = ByteBuffer.allocate(12);
        bb.putInt(4); // Length after SBIT
        bb.put((byte) 's'); // Ancillary
        bb.put((byte) 'B'); // Public
        bb.put((byte) 'I'); // Always Upper
        bb.put((byte) 'T'); // Unsafe to copy
        bb.put((byte) 8); // Bits per color R
        bb.put((byte) 8); // Bits per color G
        bb.put((byte) 8); // Bits per color B
        bb.put((byte) 8); // Bits per color A

        CRC32 crc = new CRC32();
        byte[] array = bb.array();
        crc.update(array, 4, array.length - 4);

        raf.write(bb.array());
        raf.writeInt((int) crc.getValue());
    }

    private void writePHYSChunk(RandomAccessFile raf) throws IOException {
        ByteBuffer bb = ByteBuffer.allocate(17);
        bb.putInt(9); // Length after PHYS
        bb.put((byte) 'p'); // Ancillary
        bb.put((byte) 'H'); // Public
        bb.put((byte) 'Y'); // Always Upper
        bb.put((byte) 's'); // Safe to copy
        bb.putInt(2834); // Pixels per unit X
        bb.putInt(2834); // Pixels per unit Y
        bb.put((byte) 1); //  Unit :: Meter

        raf.write(bb.array());

        CRC32 crc = new CRC32();
        byte[] array = bb.array();
        crc.update(array, 4, array.length - 4);
        raf.writeInt((int) crc.getValue());
    }

    private void writeTEXTChunk(RandomAccessFile raf, String text) throws IOException {
        ByteBuffer bb = ByteBuffer.allocate(text.length() + 8);
        bb.putInt(text.length()); // Length after TEXT
        bb.put((byte) 't'); // Ancillary
        bb.put((byte) 'E'); // Public
        bb.put((byte) 'X'); // Always Upper
        bb.put((byte) 't'); // Safe to copy
        //byte[] bytes = text.getBytes();
        bb.put(text.getBytes());

        raf.write(bb.array());

        CRC32 crc = new CRC32();
        byte[] array = bb.array();
        crc.update(array, 4, array.length - 4);
        raf.writeInt((int) crc.getValue());
    }

    private void writeIDATChunk(RandomAccessFile raf, byte[] chunk) throws IOException {

        //ByteBuffer bb = ByteBuffer.allocate(chunk.length);
        //bb.putInt(4); // Length after IDAT
        //bb.putChar('I'); // Required
        //bb.putChar('D'); // Public
        //bb.putChar('A'); // Always Upper
        //bb.putChar('T'); // Unsafe to copy

        raf.writeInt(chunk.length - 4);
        raf.write(chunk);

        CRC32 crc = new CRC32();
        crc.update(chunk, 4, chunk.length - 4);
        raf.writeInt((int) crc.getValue());

    }

    private void writeIENDChunk(RandomAccessFile raf) throws IOException {
        ByteBuffer bb = ByteBuffer.allocate(8);
        bb.putInt(0); // Length after IEND
        bb.put((byte) 'I'); // Required
        bb.put((byte) 'E'); // Public
        bb.put((byte) 'N'); // Always Upper
        bb.put((byte) 'D'); // Unsafe to copy

        byte[] array = bb.array();
        raf.write(bb.array());

        CRC32 crc = new CRC32();
        crc.update(array, 4, array.length - 4);
        raf.writeInt((int) crc.getValue());
    }

}
