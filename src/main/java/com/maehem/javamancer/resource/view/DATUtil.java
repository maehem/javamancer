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

import com.maehem.javamancer.logging.Logging;
import com.maehem.javamancer.resource.file.PNGWriter;
import com.maehem.javamancer.resource.model.DAT;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.image.Image;

/**
 *
 * @author Mark J Koch ( @maehem on GitHub )
 */
public class DATUtil {

    private static Logger LOG = Logging.LOGGER;

    public static void createCache(DAT dat, File cacheFolder) {

        File anhDir = new File(cacheFolder, "anh");
        if (anhDir.isDirectory()) {

        } else {
            LOG.log(Level.SEVERE, "Cache 'anh' folder is not a folder!");
        }

        File bihDir = new File(cacheFolder, "bih");
        if (bihDir.isDirectory()) {

        } else {
            LOG.log(Level.SEVERE, "Cache 'bih' folder is not a folder!");
        }

        File imhDir = new File(cacheFolder, "imh");
        if (!imhDir.isDirectory()) {
            if (imhDir.exists()) {
                LOG.log(Level.SEVERE, "Cache 'imh' folder is not a folder. Deleting.");
                imhDir.delete();
            }
            LOG.log(Level.SEVERE, "Cache 'imh' folder created.");
            imhDir.mkdir();

            populateIMH(dat, imhDir);
        } else {
            LOG.log(Level.SEVERE, "Cache 'imh' folder found.");
        }

        File picDir = new File(cacheFolder, "pic");
        if (!picDir.isDirectory()) {
            if (picDir.exists()) {
                LOG.log(Level.SEVERE, "Cache 'pic' folder is not a folder. Deleting.");
                picDir.delete();
            }
            LOG.log(Level.SEVERE, "Cache 'pic' folder created.");
            picDir.mkdir();

            populatePIC(dat, picDir);
        } else {
            LOG.log(Level.SEVERE, "Cache 'pic' folder found.");
        }
    }

    private static void populateIMH(DAT dat, File folder) {
        dat.imh.forEach((imh) -> {
            LOG.log(Level.SEVERE, "Process IMH: " + imh.name);
            PNGWriter pngWriter = new PNGWriter();

            imh.dataBlock.forEach((blob) -> {
                // Create Image
                Image img = new Data2Image(blob, 0);
                int indexOf = imh.dataBlock.indexOf(blob);
                LOG.log(Level.SEVERE, "    Found sub-image: {0}x{1}", new Object[]{img.getWidth(), img.getHeight()});

                try {
                    // Save to PNG
                    pngWriter.write(new File(folder, imh.name + "_" + (indexOf + 1) + ".png"), img);
                } catch (IOException ex) {
                    LOG.log(Level.SEVERE, null, ex);
                }
            });

        });
    }

    private static void populatePIC(DAT dat, File folder) {
        dat.pic.forEach((pic) -> {
            LOG.log(Level.SEVERE, "Process PIC: " + pic.name);
            PNGWriter pngWriter = new PNGWriter();

            pic.dataBlock.forEach((blob) -> {
                // Create Image
                Image img = new Data2Image(blob, 152, 112, 0);
                int indexOf = pic.dataBlock.indexOf(blob);
                LOG.log(Level.SEVERE, "    Found sub-image: {0}x{1}", new Object[]{img.getWidth(), img.getHeight()});

                try {
                    // Save to PNG
                    pngWriter.write(new File(folder, pic.name + "_" + (indexOf + 1) + ".png"), img);
                } catch (IOException ex) {
                    LOG.log(Level.SEVERE, null, ex);
                }
            });

        });
    }
}
