/*
 * MIT License
 *
 * Copyright (c) 2026 Mark J. Koch ( @maehem on GitHub )
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
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.logging.Level;
import javafx.animation.Animation;
import javafx.application.Platform;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

/**
 * Entries have a list of AnimationSequence which have PNG fragment as a List of
 * ImageView. Each AnimationSequence has it's own frame rate. PNG fragments are
 * small and are placed in the Pane using data from the 'anim[1-9][1-9]' i.e.
 * anim00 directory's 'meta.txt' file. Animations are played in sequence unless
 * a RoomExtra dictates some other trigger for playback.
 *
 * An AnimationEntry loops on playback until stop() is called.
 *
 * @author mark
 */
public class AnimationEntry extends Pane {

    private final ArrayList<AnimationSequence> anim = new ArrayList<>();
    int currentSequence = 0;

    public AnimationEntry(File entryDir) {
        File[] listFiles = entryDir.listFiles((dir, name) -> {
            return dir.isDirectory() && name.startsWith("anim");
        });

        Arrays.sort(listFiles);
        boolean first = true;
        for (File dir : listFiles) {
            AnimationSequence sequence = new AnimationSequence(dir);
            anim.add(sequence);
            
            // Experiment:
            // Hold first frame of first sequence as base.
            if ( first ) {
                first = false;
                ImageView iv = new ImageView(sequence.images.get(0));
                iv.setLayoutX(sequence.locations.get(0).getX());
                iv.setLayoutY(sequence.locations.get(0).getY());
                getChildren().add(iv);
            }
            LOGGER.log(Level.FINER, () -> "    Add sequence for: " + dir.getName());
            getChildren().add(sequence);

            sequence.timeline.setOnFinished((t) -> {
                LOGGER.log(Level.FINEST, () -> "Sequence # " + currentSequence + " for " + dir.getPath() + " timeline finished.");
                anim.get(currentSequence).setVisible(false);
                currentSequence = (currentSequence + 1) % anim.size(); // Loop through anims.
                LOGGER.log(Level.FINER, () -> dir.getName() + " timeline finished. Start sequence #: " + currentSequence);
                Platform.runLater(() -> {
                    anim.get(currentSequence).start();
                });
            });
        }
        setVisible(false);
    }

    public void start() {
        setVisible(true);
        currentSequence = 0;
        anim.get(currentSequence).start();
    }

    public void stop() {
        anim.get(currentSequence).timeline.stop();
    }

    public boolean isRunning() {
        return anim.get(currentSequence).timeline.getStatus() == Animation.Status.RUNNING;
    }
    
    public void cleanUp() {
    }

}
