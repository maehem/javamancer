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
package com.maehem.javamancer.neuro.view.cyberspace;

import com.maehem.javamancer.logging.Logging;
import com.maehem.javamancer.neuro.model.GameState;
import com.maehem.javamancer.neuro.view.ResourceManager;
import java.util.logging.Logger;
import javafx.animation.AnimationTimer;
import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

/**
 *
 * @author Mark J Koch ( @maehem on GitHub )
 */
public abstract class GridPane extends Pane {

    protected static final Logger LOGGER = Logging.LOGGER;
    protected static final int HORIZON = 100;
    protected static final int GRID = VisualPane.GRID_SIZE;

    protected final GameState gameState;
    protected final ResourceManager resourceManager;

    public enum Direction {
        LEFT, RIGHT, FORWARD, BACKWARD
    }

    public GridPane(GameState gs) {
        this.gameState = gs;
        this.resourceManager = gs.resourceManager;

    }

    protected class FrameSequence extends AnimationTimer {

        int index = 0;
        long last = 0;
        ImageView frames[];
        private final boolean reverse;
        private final boolean repeat;

        public FrameSequence(ImageView[] frames, boolean reverse, boolean repeat) {
            this.frames = frames;
            this.reverse = reverse;
            this.repeat = repeat;
            for (ImageView frame : frames) {
                frame.setVisible(false);
            }
        }

        @Override
        public void handle(long now) {
            if (now - 100000000 > last) { // Fire - 100000000nS == 100mS
                last = now;
                for (ImageView frame : frames) {
                    frame.setVisible(false);
                }
                if (index < frames.length) {
                    frames[reverse ? frames.length - 1 - index : index].setVisible(true);
                    index++;
                    if (repeat && index >= frames.length) {
                        index = 0;
                    }
                } else { // Blank everything and stop on one-shot.
                    index = 0;
                    if (!repeat) {
                        stop();
                    }
                }
                indexChanged(index);
            }
        }

        public boolean isReverse() {
            return reverse;
        }

        public void indexChanged(int index) {

        }
    }

    protected class ImageStack extends StackPane {

        private int current = 0;

        public ImageStack(double x, double y, Node... nodes) {
            super(nodes);

            setLayoutX(x);
            setLayoutY(y);
            show(current);
        }

        public final void show(int i) {
            current = i % getChildren().size();
            for (Node n : getChildren()) {
                n.setVisible(false);
            }
            if (i >= 0) {
                getChildren().get(current).setVisible(true);
            }
        }

        public int next() {
            show(current + 1);
            return current;
        }

    }

}
