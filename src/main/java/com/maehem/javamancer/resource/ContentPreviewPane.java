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

import com.maehem.javamancer.AppProperties;
import com.maehem.javamancer.ViewUtils;
import com.maehem.javamancer.logging.Logging;
import com.maehem.javamancer.resource.view.AnimationSequence;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Stream;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Group;
import javafx.scene.control.TextArea;
import javafx.scene.control.TreeItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.util.Duration;

/**
 *
 * @author Mark J Koch ( @maehem on GitHub )
 */
public class ContentPreviewPane extends StackPane implements ChangeListener<Object> {

    public static final Logger LOGGER = Logging.LOGGER;
    Timeline timeline = null;

    public ContentPreviewPane() {
    }

    @Override
    public void changed(ObservableValue<? extends Object> ov, Object oldFile, Object clickedObject) {
        if (timeline != null) {
            timeline.stop();
            timeline = null;
        }

        switch (clickedObject) {
            case File clickedFile -> {
                if (clickedFile == null) {
                    LOGGER.log(Level.FINEST, "Clear clicked.");
                    getChildren().clear();
                } else {
                    LOGGER.log(Level.FINE, "User clicked: {0}", clickedFile.getName());
                    getChildren().clear();
                    String parent = clickedFile.getParentFile().getName();
                    switch (parent) {
                        case "imh" -> {
                            getChildren().add(doImage(clickedFile, 300));
                        }
                        case "pic" -> {
                            int width = (int) ViewUtils.PIC_PREF_WIDTH;
                            getChildren().add(doImage(clickedFile, width));
                        }
                        case "anh" -> {  // Not used.
                            getChildren().add(doImage(clickedFile, 200));
                        }
                        case "bih" -> {
                            getChildren().add(bihPreview(clickedFile));
                        }
                        default -> {
                        }
                    }
                }
            }
            case TreeItem tv -> {
                if (tv.getValue() instanceof File file) {
                    if (file.getName().startsWith("anim")) {
                        LOGGER.log(Level.FINEST, "User Clicked in Anim Item.");
                        getChildren().clear();
                        Group compGroup = new Group();
                        getChildren().add(compGroup);

                        {  // Add background room reference image.
                            AppProperties app = AppProperties.getInstance();
                            // Get Room name.
                            File roomFolder = file.getParentFile().getParentFile();
                            LOGGER.log(Level.FINER, "Room File: {0}", roomFolder.getName());
                            File picFolder = new File(app.getCacheFolder(), "pic");
                            File roomPngFile = new File(picFolder, roomFolder.getName() + ".png");
                            try { // Draw room pic
                                LOGGER.log(Level.FINEST, "Add PIC.");
                                ImageView iv = new ImageView(new Image(
                                        new FileInputStream(roomPngFile),
                                        ViewUtils.PIC_PREF_WIDTH, 0, true, true
                                ));
                                compGroup.getChildren().add(iv);
                            } catch (FileNotFoundException ex) {
                                LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
                            }
                        }

                        AnimationSequence animSequence = new AnimationSequence();
                        compGroup.getChildren().add(Util.compGroup(file, animSequence));

                        Util.configTimeline(timeline, animSequence);
                        
                    }
                }
            }
            default -> {
            }
        }
    }


    private static ImageView doImage(File clickedFile, int width) {
        try {
            Image img = new Image(new FileInputStream(clickedFile), width, 0, true, true);
            ImageView iv = new ImageView(img);
            return iv;
        } catch (FileNotFoundException ex) {
            LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
        }

        return null;
    }

    private static VBox bihPreview(File roomFile) {
        LOGGER.log(Level.FINE, "Do BIH Preview: {0}", roomFile.getAbsolutePath());
        VBox box = new VBox();
        box.setMaxHeight(Double.MAX_VALUE);

        try {
            Path path = Paths.get(roomFile.toURI());
            // Some text has a 'return' character (0x0D) that is not displayable.
            // We replace it with space+tilde+newline (" ~\n") here to show that
            // it is a text scroll stop-point in the game engine.
            TextArea textArea = new TextArea(Files.readString(path).replace("\r", " ~\n"));
            textArea.setWrapText(true);
            InputStream fontStream = ContentPreviewPane.class.getResourceAsStream("/fonts/OxygenMono-Regular.ttf");
            textArea.setFont(Font.loadFont(fontStream, 12));
            VBox.setVgrow(textArea, Priority.ALWAYS);
            box.getChildren().add(textArea);
        } catch (IOException ex) {
            LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
        }

        return box;
    }
}
