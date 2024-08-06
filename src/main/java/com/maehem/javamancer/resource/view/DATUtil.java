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
import static com.maehem.javamancer.logging.Logging.LOGGER;
import com.maehem.javamancer.resource.model.ANHAnima;
import com.maehem.javamancer.resource.model.ANHEntry;
import com.maehem.javamancer.resource.model.ANHFrame;
import com.maehem.javamancer.resource.model.DAT;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.HexFormat;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.image.Image;

/**
 *
 * @author Mark J Koch ( @maehem on GitHub )
 */
public class DATUtil {

    private static final Logger LOG = Logging.LOGGER;

    public static void createCache(DAT dat, File cacheFolder) {

        File anhDir = new File(cacheFolder, "anh");
        if (!anhDir.isDirectory()) {
            if (anhDir.exists()) {
                LOG.log(Level.CONFIG, "Cache 'anh' folder is not a folder. Deleting.");
                anhDir.delete();
            }
            LOG.log(Level.CONFIG, "Cache 'anh' folder created.");
            anhDir.mkdir();

            populateANH(dat, anhDir);
        } else {
            LOG.log(Level.CONFIG, "Cache 'anh' folder found.");
        }

        File bihDir = new File(cacheFolder, "bih");
        if (!bihDir.isDirectory()) {
            if (bihDir.exists()) {
                LOG.log(Level.CONFIG, "Cache 'bih' folder is not a folder. Deleting.");
                bihDir.delete();
            }
            LOG.log(Level.CONFIG, "Cache 'bih' folder created.");
            bihDir.mkdir();

            populateBIH(dat, bihDir);
        } else {
            LOG.log(Level.CONFIG, "Cache 'bih' folder found.");
        }

        File imhDir = new File(cacheFolder, "imh");
        if (!imhDir.isDirectory()) {
            if (imhDir.exists()) {
                LOG.log(Level.CONFIG, "Cache 'imh' folder is not a folder. Deleting.");
                imhDir.delete();
            }
            LOG.log(Level.CONFIG, "Cache 'imh' folder created.");
            imhDir.mkdir();

            populateIMH(dat, imhDir);
        } else {
            LOG.log(Level.CONFIG, "Cache 'imh' folder found.");
        }

        File picDir = new File(cacheFolder, "pic");
        if (!picDir.isDirectory()) {
            if (picDir.exists()) {
                LOG.log(Level.CONFIG, "Cache 'pic' folder is not a folder. Deleting.");
                picDir.delete();
            }
            LOG.log(Level.CONFIG, "Cache 'pic' folder created.");
            picDir.mkdir();

            populatePIC(dat, picDir);
        } else {
            LOG.log(Level.CONFIG, "Cache 'pic' folder found.");
        }

        File txhDir = new File(cacheFolder, "txh");
        if (!txhDir.isDirectory()) {
            if (txhDir.exists()) {
                LOG.log(Level.CONFIG, "Cache 'txh' folder is not a folder. Deleting.");
                txhDir.delete();
            }
            LOG.log(Level.CONFIG, "Cache 'txh' folder created.");
            txhDir.mkdir();

            populateTXH(dat, txhDir);
        } else {
            LOG.log(Level.CONFIG, "Cache 'txh' folder found.");
        }

//        File ftuFile = new File(cacheFolder, "ftUser.txt");
//        if (!ftuFile.isFile()) {
//            if (ftuFile.exists()) {
//                LOG.log(Level.CONFIG, "Cache ''{0}'' delete.", ftuFile.getName());
//                ftuFile.delete();
//            }
//        } else {
//            LOG.log(Level.CONFIG, "Cache ''{0}'' file found.", ftuFile.getName());
//        }
//        populateFTU(dat, ftuFile);
        File gSaveFile = new File(cacheFolder, "gamesave.bin");
        if (!gSaveFile.isFile()) {
            if (gSaveFile.exists()) {
                LOG.log(Level.CONFIG, "Cache ''{0}'' delete.", gSaveFile.getName());
                gSaveFile.delete();
            }
        } else {
            LOG.log(Level.CONFIG, "Cache ''{0}'' file found.", gSaveFile.getName());
        }
        populateGameSave(dat, gSaveFile);

    }

    private static void populateIMH(DAT dat, File folder) {
        dat.imh.forEach((imh) -> {
            LOG.log(Level.CONFIG, "Process IMH: {0}", imh.name);
            PNGWriter pngWriter = new PNGWriter();

            imh.dataBlock.forEach((blob) -> {
                // Create Image
                Image img;
                if (imh.name.startsWith("NEURO")) {
                    img = new Data2Image(blob, 0, Data2Image.Alpha.BLACK_IS_BLACK);
                } else {
                    img = new Data2Image(blob, 0, Data2Image.Alpha.BLACK_IS_CLEAR);
                }

                int indexOf = imh.dataBlock.indexOf(blob);
                LOG.log(Level.FINE, "    Found sub-image: {0}x{1}", new Object[]{img.getWidth(), img.getHeight()});

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
            LOG.log(Level.CONFIG, "Process PIC: {0}", pic.name);
            PNGWriter pngWriter = new PNGWriter();

            pic.dataBlock.forEach((blob) -> {
                // Create Image
                Image img = new Data2Image(blob, 152, 112, 0);
                int indexOf = pic.dataBlock.indexOf(blob);
                LOG.log(Level.FINE, "    Found sub-image: {0}x{1}", new Object[]{img.getWidth(), img.getHeight()});

                try {
                    // Save to PNG
                    if (indexOf > 0) { // There should only be one PIC per room.
                        pngWriter.write(new File(folder, pic.name + "_" + (indexOf + 1) + ".png"), img);
                    } else {
                        pngWriter.write(new File(folder, pic.name + ".png"), img);
                    }
                } catch (IOException ex) {
                    LOG.log(Level.SEVERE, ex.toString(), ex);
                }
            });

        });
    }

    private static void populateANH(DAT dat, File folder) {
        dat.anh.forEach((anh) -> {
            LOG.log(Level.CONFIG, "Process ANH: {0} with {1} entries.", new Object[]{anh.name, anh.anhEntry.size()});

            File subDir = new File(folder, anh.name);
            subDir.mkdir();
            LOG.log(Level.FINE, "Create ANH Sub Dir: {0}", subDir.getAbsolutePath());

            for (int entryNum = 0; entryNum < anh.anhEntry.size(); entryNum++) {
                ANHEntry entry = anh.anhEntry.get(entryNum);
                File entryDir = new File(subDir, "entry" + (entryNum + 1));
                entryDir.mkdir();
                LOG.log(Level.FINE, "Create Entry Dir: {0}", entryDir.getAbsolutePath());

                for (int animNum = 0; animNum < entry.frames.size(); animNum++) {
                    ANHAnima anim = entry.frames.get(animNum);
                    File animDir = new File(entryDir, "anim" + (animNum < 10 ? "0" : "") + animNum);
                    animDir.mkdir();
                    LOG.log(Level.FINE, "Create Anim Dir: {0}", animDir.getAbsolutePath());

                    try {
                        File sleepFile = new File(animDir, "meta.txt");
                        LOG.log(Level.CONFIG, "Create Meta File: {0}", sleepFile.getAbsolutePath());
                        try (RandomAccessFile writer = new RandomAccessFile(sleepFile, "rw")) {
                            writer.getChannel().truncate(0L);
                            writer.writeBytes("// This is a comment.\n");
                            writer.writeBytes("sleep:" + String.valueOf(anim.sleep) + "\n");

                            // Store frame coords in meta file and store image as PNG.
                            for (int frameNum = 0; frameNum < anim.frames.size(); frameNum++) {
                                ANHFrame frame = anim.frames.get(frameNum);
                                writer.writeBytes(frame.xOffset + "," + frame.yOffset + "\n");

                                File frameFile = new File(animDir, (frameNum < 10 ? "0" : "") + frameNum + ".png");
                                makeImageFile(frameFile, frame.getXorData(), frame.w, frame.h, 0);
                                LOG.log(Level.FINE, "Create image: {0}", frameFile.getAbsolutePath());
                            }
                        }
                    } catch (FileNotFoundException ex) {
                        LOG.log(Level.SEVERE, ex.toString(), ex);
                    } catch (IOException ex) {
                        LOG.log(Level.SEVERE, ex.toString(), ex);
                    }
                }
            }

        });
    }

    private static void populateBIH(DAT dat, File folder) {
        // TODO:  Inject Credits for original game and also inject credits for
        // this Java port of the game.

        dat.bih.forEach((bihThing) -> {
            LOG.log(Level.CONFIG, "Process BIH: {0}.", new Object[]{bihThing.name});

            // Meta.txt
            // Text.txt
            File metaFile = new File(folder, bihThing.name + "_meta.txt");
            LOG.log(Level.CONFIG, "Create Meta File: {0}", metaFile.getAbsolutePath());
            try (RandomAccessFile writer = new RandomAccessFile(metaFile, "rw")) {
                writer.getChannel().truncate(0L);
                writer.writeBytes("// Meta for BIH: " + bihThing.name + "\n");
                if (bihThing.cbOffset != 0 || bihThing.cbSegment != 0 || bihThing.ctrlStructAddr != 0 || bihThing.unknown.length != 0) {
                    writer.writeBytes("name:" + String.valueOf(bihThing.name) + "\n");
                    writer.writeBytes("\n");

                    // Write addr of unkown and bytes in hex string. :  ab12: 00 00 00 00 00 ...
                    writer.writeBytes("unknown:\n");
                    LOGGER.log(Level.FINER, "Unknown Bytes @ :");
                    if (bihThing.name.equals("ROOMPOS")) {
                        writer.writeBytes(hexBlob(20, bihThing.unknown, 20)); // Data are 20 bytes each
                    } else {
                        writer.writeBytes(hexBlob(20, bihThing.unknown, 24));
                    }

                    writer.writeBytes("\n");
                    LOGGER.log(Level.FINER, "Byte Codes:");
                    for (int i = 0; i < 3; i++) {
                        writer.writeBytes("ByteCodes [" + i + "] :::");
                        if (bihThing.byteCode[i] != null && bihThing.byteCode[i].length > 0) {
                            writer.writeBytes("\n");
                            writer.writeBytes(hexBlob(bihThing.byteCodeArrayOffset[i], bihThing.byteCode[i], 16));
                        } else {
                            writer.writeBytes("    NONE\n");
                        }
                        writer.writeBytes("\n");
                    }

                    writer.writeBytes("\n");
                    LOGGER.log(Level.FINER, "Object Codes:");
                    for (int i = 0; i < 3; i++) {
                        writer.writeBytes("ObjectCodes [" + i + "] :::");
                        if (bihThing.objectCode[i] != null && bihThing.objectCode[i].length > 0) {
                            writer.writeBytes("\n");
                            writer.writeBytes(hexBlob(bihThing.iocOff[i], bihThing.objectCode[i], 16));
                        } else {
                            writer.writeBytes("    NONE\n");
                        }
                        writer.writeBytes("\n");
                    }

                    writer.writeBytes("\n");
                }
                writer.writeBytes("// Text Elements:");
                if (!bihThing.text.isEmpty()) {
                    writer.writeBytes("\n");
                } else {
                    writer.writeBytes("  NONE\n");
                }
                for (String text : bihThing.text) {
                    // TODO: Replace any byte == 01 with "<player_name>"
                    // TODO: Maybe remove line breaks?
                    writer.writeBytes(text);
                    writer.writeBytes("\n");
                }
                writer.writeBytes("// END Text Elements");

                writer.writeBytes("\n\n// Ancillary header fields: (no known use, always zero)\n");
                writer.writeBytes("cbOffset:" + String.valueOf(bihThing.cbOffset) + "\n");
                writer.writeBytes("cbSegment:" + String.valueOf(bihThing.cbSegment) + "\n");
                writer.writeBytes("ctrlStructAddr:" + String.valueOf(bihThing.ctrlStructAddr) + "\n");

            } catch (FileNotFoundException ex) {
                LOG.log(Level.SEVERE, ex.toString(), ex);
            } catch (IOException ex) {
                LOG.log(Level.SEVERE, ex.toString(), ex);
            }

            // Save raw BIH as a binary file.
            File binaryFile = new File(folder, bihThing.name + ".bih");
            LOG.log(Level.CONFIG, "Create Binary File: {0}", binaryFile.getAbsolutePath());
            try (RandomAccessFile binWriter = new RandomAccessFile(binaryFile, "rw")) {
                binWriter.getChannel().truncate(0L);
                binWriter.write(bihThing.data);
                binWriter.close();
            } catch (IOException ex) {
                LOG.log(Level.SEVERE, ex.toString(), ex);
            }

        });
    }

    private static String hexBlob(int addr, byte[] blob, int columns) {
        StringBuilder sb = new StringBuilder();
        HexFormat hexFormat = HexFormat.of();
        for (int ii = 0; ii < blob.length; ii += columns) {
            sb.append(String.format("%04X", (ii + addr) & 0xFFFF)).append(": ");
            try {
                for (int i = 0; i < columns; i++) {
                    byte b = blob[ii + i];
                    sb.append(hexFormat.toHexDigits(b)).append(" ");
                }
            } catch (IndexOutOfBoundsException ex) {

            }
            sb.append("\n");
        }

        return sb.toString();
    }

    private static boolean makeImageFile(File file, byte blob[], int w, int h, int offset) {
        PNGWriter pngWriter = new PNGWriter();

        // Create Image
        Image img = new Data2Image(blob, w, h, offset);
        LOG.log(Level.CONFIG, "    Found sub-image: {0}x{1}", new Object[]{img.getWidth(), img.getHeight()});

        try {
            pngWriter.write(file, img); // Save to PNG

        } catch (IOException ex) {
            LOG.log(Level.SEVERE, ex.toString(), ex);
            return false;
        }

        return true;
    }

    private static void populateTXH(DAT dat, File folder) {
        dat.txh.forEach((txhThing) -> {
            LOG.log(Level.CONFIG, "Process TXH: {0}.", new Object[]{txhThing.name});

            File metaFile = new File(folder, txhThing.name + ".txt");
            LOG.log(Level.CONFIG, "Create TXT File: {0}", metaFile.getAbsolutePath());
            try (RandomAccessFile writer = new RandomAccessFile(metaFile, "rw")) {
                writer.getChannel().truncate(0L);
                writer.writeBytes("// Text for: " + txhThing.name + "\n");
                writer.write(txhThing.data);
                writer.close();
            } catch (FileNotFoundException ex) {
                LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
            } catch (IOException ex) {
                LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
            }
        });

    }

    private static void populateGameSave(DAT dat, File gSaveFile) {
        try (RandomAccessFile writer = new RandomAccessFile(gSaveFile, "rw")) {
            writer.getChannel().truncate(0L);
            //writer.writeBytes("// GameSave Raw Data: " + dat.gamesave.name + "\n");
            writer.write(dat.gamesave.data);
            writer.close();
        } catch (FileNotFoundException ex) {
            LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
        } catch (IOException ex) {
            LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
        }
    }

}
