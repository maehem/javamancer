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

import com.maehem.javamancer.ViewUtils;
import com.maehem.javamancer.logging.Logging;
import static com.maehem.javamancer.logging.Logging.LOGGER;
import com.maehem.javamancer.resource.view.AnimationSequence;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HexFormat;
import java.util.logging.Level;
import java.util.stream.Stream;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

/**
 *
 * @author Mark J Koch ( @maehem on GitHub )
 */
public class Util {

    /**
     *
     * <pre>
     * int decode_rle(uint8_t *_src, uint32_t len, uint8_t *_dst) {
     *      uint8_t *src = _src, *dst = _dst, *p = _dst;
     *      uint32_t processed = 0;
     *
     *      while (len) {
     *          if (*src > 0x7F) {
     *              int i = 0x100 - *src++;
     *              processed++;
     *
     *              while (i--) {
     *                  dst++ = *src++;
     *                  len--;
     *                  processed++;
     *              }
     *          } else {
     *              int num = *src++, val = *src++;
     *              processed += 2;
     *
     *              memset(dst, val, (size_t)++num);
     *              dst += num;
     *              len -= num;
     *          }
     *      }
     *
     *      return processed;
     * }
     * </pre>
     *
     *
     *
     *
     * @param src
     * @param len
     * @param dst
     * @return
     */
    public static int decodeRLE(byte[] src, int srcIdx, int len, byte[] dst, int dstIdx) {
        //int decode_rle(uint8_t *_src, uint32_t len, uint8_t *_dst) {

//	uint8_t *src = _src, *dst = _dst, *p = _dst;
//	uint32_t processed = 0;
        int srcIdxOrig = srcIdx;
        int dstIdxOrig = dstIdx;

        int processed = 0;

        while (len > 0) {
            //   if (*src > 0x7F) {
            if ((src[srcIdx] & 0xFF) > 0x7F) { // Copy value 0x100 - val times
                /*
                 *              int i = 0x100 - *src++;
                 *              processed++;
                 *
                 *              while (i--) {
                 *                  dst++ = *src++;
                 *                  len--;
                 *                  processed++;
                 *              }
                 */
                int i = 0x100 - (src[srcIdx] & 0xFF);
                srcIdx++;
                processed++;

                while (i > 0) {
                    i--;
                    //*dst++ = *src++;
                    dst[dstIdx] = src[srcIdx];
                    dstIdx++;
                    srcIdx++;
                    len--;
                    processed++;
                }
            } else {  // Duplicate val  num times.
                /*
                 *              int num = *src++, val = *src++;
                 *              processed += 2;
                 *
                 *              memset(dst, val, (size_t)++num);
                 *              dst += num;
                 *              len -= num;
                 */
                int num = src[srcIdx] & 0xFF;
                srcIdx++;
                int val = src[srcIdx] & 0xFF;
                srcIdx++;

                processed += 2;

                num++;
                //memset(dst, val, (size_t)++num );
                for (int ii = 0; ii < num; ii++) {
                    dst[dstIdx + ii] = (byte) (val & 0xFF);
                }

                dstIdx += num;
                len -= num;
            }
        }

//        HexFormat hexFormat = HexFormat.of();
//        LOGGER.log(Level.FINE, "RLE Decode => ");
//        int columns = 16;
//        for (int ii = dstIdxOrig; ii < dstIdx; ii += columns) {
//            StringBuilder sb = new StringBuilder(String.format("%04X", ii & 0xFFFF) + ": ");
//            try {
//                for (int i = 0; i < columns; i++) {
//                    byte b = dst[ii + i];
//                    sb.append(hexFormat.toHexDigits(b)).append(" ");
//                }
//            } catch (IndexOutOfBoundsException ex) {
//
//            }
//            Logging.LOGGER.log(Level.FINE, sb.toString());
//        }

        return processed;
    }

    public static void xorRows(byte[] inout, int inoutIdx, int w, int h) {
        for (int i = 0; i < h - 1; i++) {
            for (int j = 0; j < w; j++) {
                inout[inoutIdx + ((i + 1) * w) + j] ^= inout[inoutIdx + (i * w) + j];
            }
        }
        LOGGER.log(Level.FINER, "XOR Result ===>");
        HexFormat hexFormat = HexFormat.of();
        for (int ii = inoutIdx; ii < inoutIdx + w * h; ii += 16) {
            Logging.LOGGER.log(Level.FINER,
                    String.format("%04X", ii & 0xFFFF) + ": "
                    + hexFormat.toHexDigits(inout[ii]) + " " + hexFormat.toHexDigits(inout[ii + 1]) + " "
                    + hexFormat.toHexDigits(inout[ii + 2]) + " " + hexFormat.toHexDigits(inout[ii + 3]) + " "
                    + hexFormat.toHexDigits(inout[ii + 4]) + " " + hexFormat.toHexDigits(inout[ii + 5]) + " "
                    + hexFormat.toHexDigits(inout[ii + 6]) + " " + hexFormat.toHexDigits(inout[ii + 7]) + " "
                    + hexFormat.toHexDigits(inout[ii + 8]) + " " + hexFormat.toHexDigits(inout[ii + 9]) + " "
                    + hexFormat.toHexDigits(inout[ii + 10]) + " " + hexFormat.toHexDigits(inout[ii + 11]) + " "
                    + hexFormat.toHexDigits(inout[ii + 12]) + " " + hexFormat.toHexDigits(inout[ii + 13]) + " "
                    + hexFormat.toHexDigits(inout[ii + 14]) + " " + hexFormat.toHexDigits(inout[ii + 15]) + " "
            );
        }
    }
    
    public static void fillAnimSequence(File pngDir,
            AnimationSequence animSequence,
            ArrayList<String> locList) throws FileNotFoundException {

        // Load frames.
        File[] pngFiles = pngDir.listFiles((dir, name) -> {
            return name.matches("[0-9][0-9].png"); // ex.  00.png, 01.png, etc.
        });
        Arrays.sort(pngFiles);
        int listIndex = 0;
        for (File pngFile : pngFiles) {
            Image img0 = new Image(new FileInputStream(pngFile));
            double w = img0.getWidth() * ViewUtils.PIC_PREVIEW_SCALE;
            Image img = new Image(new FileInputStream(pngFile), w, 0, true, true);

            ImageView iv = new ImageView(img);
            animSequence.images.add(iv);

            String[] split = locList.get(listIndex).split(",");
            iv.setLayoutX((Integer.parseInt(split[0]) - 4) * ViewUtils.PIC_PREVIEW_SCALE * 2.0);
            iv.setLayoutY((Integer.parseInt(split[1]) - 8) * ViewUtils.PIC_PREVIEW_SCALE);
            LOGGER.log(Level.FINEST, "Add anim frame.");

            //compGroup.getChildren().add(iv);
            iv.setVisible(listIndex == 0);
            listIndex++;
        }

    }
    
    public static void configTimeline(Timeline timeline, AnimationSequence animSequence) {
                        timeline = new Timeline(new KeyFrame(
                                Duration.millis(animSequence.getSleep() * 50),
                                ae -> {
                                    //LOGGER.log(Level.FINE, "Anim Frame Event.");
                                    ArrayList<ImageView> images = animSequence.images;
                                    int next = 0;
                                    for (int i = 0; i < images.size(); i++) {
                                        if (images.get(i).isVisible()) {
                                            images.get(i).setVisible(false);
                                            next = i + 1;
                                        }
                                    }
                                    next %= images.size();
                                    //LOGGER.log(Level.FINE, "SetVisible: " + next);
                                    images.get(next).setVisible(true);
                                }
                        ));
                        timeline.setCycleCount(Animation.INDEFINITE);
                        LOGGER.log(Level.FINER, "Start Play Timeline.");
                        timeline.play();
        
    }
    
    public static Group compGroup( File animDir, AnimationSequence animSequence) {
                            Group compGroup = new Group();
                        try {
                            //getChildren().add(compGroup);

                            ArrayList<String> locList = new ArrayList<>();

                            // Get metadata.  Sleep, and locations.
                            File meta = new File(animDir, "meta.txt");
                            if (meta.exists()) {
                                LOGGER.log(Level.FINEST, "Found meta.txt");
                                try (Stream<String> stream = Files.lines(Paths.get(meta.toURI()))) {
                                    stream.forEach((line) -> {
                                        if (line.startsWith("sleep:")) {
                                            String[] split = line.split(":");
                                            animSequence.setSleep(Integer.parseInt(split[1]));
                                            LOGGER.log(Level.FINER, "Set Sleep to: {0}", animSequence.getSleep());
                                        } else if (line.startsWith("//")) {
                                            // Ignore comment
                                            LOGGER.log(Level.FINER, line);
                                        } else if (line.contains(",")) {
                                            locList.add(line);
                                        }
                                    });
                                } catch (IOException ex) {
                                    LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
                                }
                            }
//                            // Draw room pic
//                            {
//                                LOGGER.log(Level.FINEST, "Add PIC.");
//                                ImageView iv = new ImageView(new Image(
//                                        new FileInputStream(roomPngFile),
//                                        ViewUtils.PIC_PREF_WIDTH, 0, true, true
//                                ));
//                                compGroup.getChildren().add(iv);
//                            }

                            Util.fillAnimSequence(animDir, animSequence, locList);
                            animSequence.images.forEach((img) -> {
                                compGroup.getChildren().add(img);
                            });
                        } catch (FileNotFoundException ex) {
                            LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
                        }
                        
                        return compGroup;
    }
}
