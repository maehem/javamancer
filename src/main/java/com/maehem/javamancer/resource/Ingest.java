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
package com.maehem.javamancer.resource;

import com.maehem.javamancer.logging.Logging;
import com.maehem.javamancer.resource.file.*;
import com.maehem.javamancer.resource.model.DAT;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.logging.Level;

/**
 *
 * @author Mark J Koch ( @maehem on GitHub )
 */
public class Ingest {

    public static DAT ingestDAT(File[] datFile) throws FileNotFoundException {
        DAT dat = new DAT();
        RandomAccessFile raf[] = {
            new RandomAccessFile(datFile[0], "r"),
            new RandomAccessFile(datFile[1], "r")
        };

        // Ingest IMH
        for (IMH imh : IMH.values()) {
            byte[] dest = new byte[64000];

            decompressResource(raf[imh.fileNum], imh, dest);

            int a = 0; // debug breakpoint
        }
        // Ingest PIC
        // Ingest ANH
        // Ingest BIH


        return dat;
    }

    private static void decompressResource(RandomAccessFile raf, Resource resource, byte[] dest) {
        /*<code>
    uint8_t compd[64000];

    if (strstr(src->name, ".PIC") || strstr(src->name, ".IMH")) {
        fseek(f, src->offset + 32, SEEK_SET);
    }else{
        fseek(f, src->offset, SEEK_SET);
    }
    fread(compd, 1, src->size, f);

    if (strstr(src->name, ".IMH")) {
        return decompress_imh(compd, dst);
    } else if (strstr(src->name, ".PIC")) {
        return decompress_pic(compd, dst);
    } else if (strstr(src->name, ".BIH")) {
        return decompress_bih(compd, dst);
    } else {
        return decompress_anh(compd, dst);
    }
    </code> */

        try {
            byte[] compressedData = new byte[64000];
            if (resource instanceof PIC || resource instanceof IMH) {
                // fseek(f, src->offset + 32, SEEK_SET);
                raf.seek(resource.getOffset() + 32);
            } else {
                // fseek(f, src->offset, SEEK_SET);
                raf.seek(resource.getOffset());
            }

            // fread(compd, 1, src->size, f);
            raf.read(compressedData, 0, resource.getSize());
            resource.decompress(compressedData, dest);
        } catch (IOException ex) {
            Logging.LOGGER.log(Level.SEVERE, null, ex);
        }

    }

}
