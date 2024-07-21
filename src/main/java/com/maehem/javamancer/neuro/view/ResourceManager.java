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
package com.maehem.javamancer.neuro.view;

import com.maehem.javamancer.logging.Logging;
import com.maehem.javamancer.neuro.model.Room;
import com.maehem.javamancer.neuro.model.TextResource;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.image.Image;

/**
 *
 * @author Mark J Koch ( @maehem on GitHub )
 */
public class ResourceManager {

    private static final Logger LOGGER = Logging.LOGGER;

    public enum Type {
        IMH, PIC, BIH, ANH
    }

    public static final boolean IMG_SMOOTH = true;
    public static final double IMG_SCALE = 2.0;
    //public static final int PIC_W = 304;
    //public static final int PIC_H = 112;

    private final File resourceFolder;
    private final File imhFolder;
    private final File picFolder;
    private final File anhFolder;
    private final File bihFolder;

    public ResourceManager(File resourceFolder) {

        this.resourceFolder = resourceFolder;
        this.anhFolder = new File(resourceFolder, "anh");
        this.picFolder = new File(resourceFolder, "pic");
        this.imhFolder = new File(resourceFolder, "imh");
        this.bihFolder = new File(resourceFolder, "bih");
    }

    public Image getSprite(String name) {
        return getSprite(name, 1.0);
    }

    public Image getSprite(String name, double scaleMore) {
        try {
            File imgFile = new File(imhFolder, name + ".png");
            Image protoImg = new Image(new FileInputStream(imgFile));

            return new Image(new FileInputStream(imgFile),
                    protoImg.getWidth() * IMG_SCALE * scaleMore, protoImg.getHeight() * IMG_SCALE * scaleMore,
                    true, IMG_SMOOTH
            );
        } catch (FileNotFoundException ex) {
            LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
        }

        LOGGER.log(Level.SEVERE, "Image not found in IMH folder! ===> {0}.png", name);
        return null;
    }

    public Image getBackdrop(Room room) {
        try {
            File imgFile = new File(picFolder, room.name() + ".png");
            Image protoImg = new Image(new FileInputStream(imgFile));

            return new Image(new FileInputStream(imgFile),
                    protoImg.getWidth() * IMG_SCALE, protoImg.getHeight() * IMG_SCALE,
                    true, IMG_SMOOTH
            );
        } catch (FileNotFoundException ex) {
            LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
        }

        return null;
    }

    public TextResource getText(Room room) {
        BufferedReader in = null;
        TextResource tr = new TextResource();
        try {
            File txtFile = new File(bihFolder, room.name() + "_meta.txt");
            in = new BufferedReader(new FileReader(txtFile), 16 * 1024);
            try (Scanner read = new Scanner(in)) {
                read.useDelimiter("\n");
                boolean foundText = false;

                while (read.hasNext()) {
                    String txt = read.next();
                    if (foundText) {
                        tr.add(txt);
                    } else if (txt.startsWith("// END Text Elements")) {
                        break;
                    } else if (txt.startsWith("// Text Elements:")) {
                        foundText = true;
                    }
                }
            }
        } catch (FileNotFoundException ex) {
            LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
        } finally {
            try {
                in.close();
            } catch (IOException ex) {
                LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
            }
        }

        return tr;
    }

    public String getFirstTimeText() {
        try {
            File txtFile = new File(resourceFolder, "ftUser.txt");
            return Files.readString(txtFile.toPath());
        } catch (FileNotFoundException ex) {
            LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
        } catch (IOException ex) {
            LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
        }
        return "";
    }

    // TODO:
//    public Animation getAnimation( String name ) {
//
//    }
}
