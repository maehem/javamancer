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
package com.maehem.javamancer.neuro;

import com.maehem.javamancer.logging.Logging;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;

/**
 *
 * @author Mark J Koch ( @maehem on GitHub )
 */
public abstract class NeuroModePane extends Pane {

    public static final Logger LOGGER = Logging.LOGGER;
    private final double SCALE = NeuroGamePane.RESOURCE_SCALE;
    public static final double BUTTON_FONT_SIZE = 24;

    private final File resourceFolder;
    private final File imhFolder;
    private final File picFolder;
    private final File anhFolder;
    private final File bihFolder;

    private final NeuroModePaneListener listener;

    public NeuroModePane(NeuroModePaneListener listener, File resourceFolder) {
        this.listener = listener;
        this.resourceFolder = resourceFolder;
        this.anhFolder = new File(resourceFolder, "anh");
        this.picFolder = new File(resourceFolder, "pic");
        this.imhFolder = new File(resourceFolder, "imh");
        this.bihFolder = new File(resourceFolder, "bih");
    }

    public Image getImhImage(String name) {
        try {
            // Bring resources in at 2X by scaling up it when loaded into Image().
            // This causes the images to look as expected. Bringing small bitmap images in
            // at their tiny native size causes JavaFX to smooth or blur them.
            // So, This 2X technique has the effect of "over sampling".
            Image protoImg = new Image(new FileInputStream(new File(imhFolder, name + ".png")));
            return new Image(new FileInputStream(new File(imhFolder, name + ".png")),
                    protoImg.getWidth() * SCALE, protoImg.getHeight() * SCALE,
                    true, true
            );
        } catch (FileNotFoundException ex) {
            LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
        }

        return null;  // TODO: Return ERROR/BROKEN_IMAGE icon.
    }

    public Image getPicImage(String name) {
        try {
            // Bring resources in at 2X by scaling up it when loaded into Image().
            // This causes the images to look as expected. Bringing small bitmap images in
            // at their tiny native size causes JavaFX to smooth or blur them.
            // So, This 2X technique has the effect of "over sampling".
            Image protoImg = new Image(new FileInputStream(new File(picFolder, name + ".png")));
            return new Image(new FileInputStream(new File(picFolder, name + ".png")),
                    protoImg.getWidth() * SCALE, protoImg.getHeight() * SCALE,
                    true, true
            );
        } catch (FileNotFoundException ex) {
            LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
        }

        return null;  // TODO: Return ERROR/BROKEN_IMAGE icon.
    }

    public Image getAnhImage(String name) {
        try {
            Image protoImg = new Image(new FileInputStream(new File(anhFolder, name)));
            return new Image(new FileInputStream(new File(anhFolder, name)),
                    protoImg.getWidth() * SCALE, protoImg.getHeight() * SCALE,
                    true, true
            );
        } catch (FileNotFoundException ex) {
            LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
        }

        return null;  // TODO: Return ERROR/BROKEN_IMAGE icon.
    }

    public File getBihFile(String name) {
        return new File(bihFolder, name + "_meta.txt");
    }

}
