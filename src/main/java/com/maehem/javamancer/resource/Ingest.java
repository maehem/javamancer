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
import static com.maehem.javamancer.logging.Logging.LOGGER;
import com.maehem.javamancer.resource.file.*;
import com.maehem.javamancer.resource.model.ANHThing;
import com.maehem.javamancer.resource.model.BIHThing;
import com.maehem.javamancer.resource.model.DAT;
import com.maehem.javamancer.resource.model.FTUserThing;
import com.maehem.javamancer.resource.model.GameSaveThing;
import com.maehem.javamancer.resource.model.IMHThing;
import com.maehem.javamancer.resource.model.PICThing;
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

            int len = decompressResource(raf[imh.fileNum], imh, dest);
            IMHThing thing = new IMHThing(imh, dest, len);

            dat.imh.add(thing);
        }
        // Ingest PIC
        for (PIC pic : PIC.values()) {
            byte[] dest = new byte[64000];

            int len = decompressResource(raf[pic.fileNum], pic, dest);
            PICThing thing = new PICThing(pic, dest, len);

            dat.pic.add(thing);
        }
        // Ingest ANH
        for (ANH anh : ANH.values()) {
            byte[] dest = new byte[64000];

            int len = decompressResource(raf[anh.fileNum], anh, dest);

            byte roomData[] = {};

            for (PICThing picThing : dat.pic) {
                if (picThing.name.equals(anh.getName())) {
                    roomData = picThing.dataBlock.get(0);
                    break;
                }
            }

            if (roomData.length == 0) {
                LOGGER.log(Level.SEVERE, "PIC data missing! Room: " + anh.getName());
            }

            ANHThing thing = new ANHThing(anh, dest, len, roomData);

            dat.anh.add(thing);
        }
        // Ingest BIH
        for (BIH bih : BIH.values()) {
            byte[] dest = new byte[64000];

            int len = decompressResource(raf[bih.fileNum], bih, dest);
            BIHThing thing = new BIHThing(bih, dest, len);

            dat.bih.add(thing);
        }

        // Ingest FTUSER
        byte[] dest = new byte[64000];

        int len = decompressResource(raf[FTUser.fileNum], FTUser.TXT, dest);
        FTUserThing thing = new FTUserThing(FTUser.TXT, dest, len);
        dat.ftuser = thing;

        // Ingest Game Save default data.
        dest = new byte[SaveGame.size];

        len = decompressResource(raf[SaveGame.fileNum], SaveGame.DATA, dest);
        GameSaveThing gsThing = new GameSaveThing(SaveGame.DATA, dest, len);
        dat.gamesave = gsThing;

        return dat;
    }

    private static int decompressResource(RandomAccessFile raf, Resource resource, byte[] dest) {
        Logging.LOGGER.log(Level.FINE, "Decompress: " + resource.getName());

        try {
            byte[] compressedData = new byte[64000];
            if (resource instanceof PIC || resource instanceof IMH) {
                raf.seek(resource.getOffset() + 32); // TODO Get and review the 32 byte header.
            } else {
                raf.seek(resource.getOffset());
            }

            raf.read(compressedData, 0, resource.getSize());
            return resource.decompress(compressedData, dest);
        } catch (IOException ex) {
            Logging.LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
        }

        return 0;
    }

}
