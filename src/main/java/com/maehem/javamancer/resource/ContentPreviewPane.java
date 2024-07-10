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
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;

/**
 *
 * @author Mark J Koch ( @maehem on GitHub )
 */
public class ContentPreviewPane extends StackPane implements ChangeListener<File> {

    public static final Logger LOGGER = Logging.LOGGER;

    public ContentPreviewPane() {
    }

    @Override
    public void changed(ObservableValue<? extends File> ov, File oldFile, File clickedFile) {
        if (clickedFile == null) {
            LOGGER.log(Level.FINER, "Clear clicked.");
            getChildren().clear();
        } else {
            LOGGER.log(Level.FINER, "User clicked: {0}", clickedFile.getName());
            getChildren().clear();
            int width = 100;
            String parent = clickedFile.getParentFile().getName();
            if (parent.equals("imh")) {
                width = 300;
            } else if (parent.equals("pic")) {
                width = 608;
            } else if (parent.equals("anh")) {
                width = 200;
            }
            try {
                Image img = new Image(new FileInputStream(clickedFile), width, 224, true, true);
                ImageView iv = new ImageView(img);
                getChildren().add(iv);

            } catch (FileNotFoundException ex) {
                LOGGER.log(Level.SEVERE, null, ex);
            }
        }
    }

}
