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

import static com.maehem.javamancer.Javamancer.LOGGER;
import com.maehem.javamancer.ViewUtils;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.stream.Stream;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Point2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

/**
 *
 * AnimationSequence has PNG fragments as a List of ImageView. Each
 * AnimationSequence has it's own frame rate. PNG fragments are small and are
 * placed in the Pane using data from the 'anim[1-9][1-9]' i.e. anim00
 * directory's 'meta.txt' file. Animations are played in sequence unless a
 * RoomExtra dictates some other trigger for playback.
 *
 * @author Mark J Koch ( @maehem on GitHub )
 */
public class AnimationSequence extends ImageView {

    public final ArrayList<Image> images = new ArrayList<>();
    public final ArrayList<Point2D> locations = new ArrayList<>();
    public final Timeline timeline = new Timeline();

    private int sleep;
    private int imageIndex = 0;

    public AnimationSequence(File animDir) {
        setVisible(true);
        // Scan the meta.txt for coordinates and sleep time.
        buildLocList(animDir);

        File[] pngFiles = animDir.listFiles((dir, name) -> {
            return name.matches("[0-9][0-9].png");
        });
        Arrays.sort(pngFiles);

        try {
            int listIndex = 0;

            for (File pngFile : pngFiles) {
                // To reduce the blurry artifact of small images, we have to
                // first know the size of the raw Image so that we can scale it
                // upon creating the final Image.
                Image img0 = new Image(new FileInputStream(pngFile));
                double w = img0.getWidth() * ViewUtils.PIC_PREVIEW_SCALE;
                Image img = new Image(new FileInputStream(pngFile), w, 0, true, true);

                images.add(img);
                if (listIndex == 0) {
                    setCurrentImage(0);
                }

                listIndex++;
            }
            LOGGER.log(Level.FINER, () -> "        created AnimationSequence with " + images.size() + " images.");

            configTimeline();
            setVisible(false);

        } catch (FileNotFoundException ex) {
            LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
        }
    }

    /**
     * @return the sleep
     */
    public double getSleep() {
        return sleep;
    }

    /**
     * @param sleep the sleep to set
     */
    private void setSleep(int sleep) {
        this.sleep = sleep;
    }

    public void start() {
        LOGGER.log(Level.FINER, "Sequence start() called.");
        setVisible(true);
        imageIndex = 0;
        timeline.playFromStart();
    }

    private void buildLocList(File animDir) {

        // Get metadata.  Sleep, and locations.
        File meta = new File(animDir, "meta.txt");
        if (meta.exists()) {
            LOGGER.log(Level.FINEST, "Found meta.txt");
            try (Stream<String> stream = Files.lines(Paths.get(meta.toURI()))) {
                int i = 0;
                stream.forEach((line) -> {
                    if (line.startsWith("sleep:")) {
                        String[] split = line.split(":");
                        setSleep(Integer.parseInt(split[1]));
                        LOGGER.log(Level.FINE, "Set Sleep to: {0}", getSleep());
                    } else if (line.startsWith("//")) {
                        // Ignore commented lines
                        LOGGER.log(Level.FINEST, line);
                    } else if (line.contains(",")) {
                        //locList.add(line);
                        String[] split = line.split(",");
                        locations.add(new Point2D(
                                // X val is * 2.0 because original game used 
                                // nibbles for each pixel and thus had two pixel
                                // values per byte.
                                (Integer.parseInt(split[0]) - 4) * ViewUtils.PIC_PREVIEW_SCALE * 2.0,
                                (Integer.parseInt(split[1]) - 8) * ViewUtils.PIC_PREVIEW_SCALE));
                        LOGGER.log(Level.FINER, ()
                                -> "        Add location at: " + i
                                + " x:" + locations.getLast().getX()
                                + " y:" + locations.getLast().getY()
                        );
                    }
                });
            } catch (IOException ex) {
                LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
            }
        }
    }

    private void configTimeline() {
        timeline.getKeyFrames().add(new KeyFrame(
                Duration.millis((getSleep() + 1) * 100), // TODO: Set back to 50
                ae -> {
                    LOGGER.log(Level.FINEST, () -> "Anim Frame Event  index:" + imageIndex);
                    setCurrentImage(imageIndex);
                    imageIndex = (imageIndex + 1) % images.size(); // Cycle through images
                }
        ));
        timeline.setCycleCount(images.size());
    }

    private void setCurrentImage(int index) {
        setImage(images.get(index));
        setLayoutX(locations.get(index).getX());
        setLayoutY(locations.get(index).getY());
    }

}
