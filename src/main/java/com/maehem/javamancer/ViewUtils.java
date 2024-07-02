/*
    Licensed to the Apache Software Foundation (ASF) under one or more
    contributor license agreements.  See the NOTICE file distributed with this
    work for additional information regarding copyright ownership.  The ASF
    licenses this file to you under the Apache License, Version 2.0
    (the "License"); you may not use this file except in compliance with the
    License.  You may obtain a copy of the License at

      http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
    WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  See the
    License for the specific language governing permissions and limitations
    under the License.
 */
package com.maehem.javamancer;

import com.maehem.javamancer.logging.Logging;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBase;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.Tooltip;
import javafx.scene.effect.Blend;
import javafx.scene.effect.BlendMode;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.effect.ColorInput;
import javafx.scene.effect.Effect;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

/**
 *
 * @author Mark J Koch ( @maehem on GitHub )
 */
public class ViewUtils {

    private static final Logger LOGGER = Logging.LOGGER;

    public static final int ICON_IMG_SIZE = 24;
    public static final int HDR_IMG_SIZE = 16;
    public static final double DIALOG_GRAPHIC_SIZE = 48;
    public static final String CSS_FILE = "/style/dark.css";
    public static final String FONT_PATH = "/fonts/mango-classic.ttf";
    //public static final double FONT_SCALE = 1.055; // Font height can vary depending on Family.
    public static final double FONT_SCALE = 1.000; // Mango Custom. Font height can vary depending on Family.
    public static double FONT_SIZE_MULT = 1.666; // JavaFX pixel units to font size ratio. Found experimentally.

    public static Button createIconButton(String name, Image img) {
        return (Button) createIconButton(name, img, ICON_IMG_SIZE, false);
    }

    public static ToggleButton createIconToggleButton(String name, Image img) {
        return (ToggleButton) createIconButton(name, img, ICON_IMG_SIZE, true);
    }

    public static ButtonBase createIconButton(String name, Image img, int size, boolean asToggle) {
        ImageView icon = createIcon(img, size);

        ButtonBase b;
        if (asToggle) {
            b = new ToggleButton();
        } else {
            b = new Button();
        }
        b.setUserData(name);
        b.setId("button-icon");
        b.setGraphic(icon);
        b.setTooltip(new Tooltip(name));
        b.setMaxSize(ICON_IMG_SIZE, ICON_IMG_SIZE);

        return b;
    }

    public static Button createHeaderButton(String name, Image img, String tooltip) {
        ImageView icon = createIcon(img, HDR_IMG_SIZE);

        Button b;
        b = new Button();
        b.setUserData(name);
        b.setId("button-icon");
        b.setGraphic(icon);
        b.setTooltip(new Tooltip(tooltip));
        b.setMaxSize(HDR_IMG_SIZE, HDR_IMG_SIZE);

        return b;
    }

    public static final Effect getColorIconEffect(ImageView imgView, Color c, double w, double h) {
        ImageView clip = new ImageView(imgView.getImage());
        clip.setFitHeight(h);
        clip.setPreserveRatio(true);
        imgView.setClip(clip);

        ColorAdjust monochrome = new ColorAdjust();
        monochrome.setSaturation(-1.0);

        Blend blush = new Blend(
                BlendMode.SCREEN,
                monochrome,
                new ColorInput(0, 0, w, h, c)
        );

        return blush;
    }

    public static final ImageView createIcon(Image img, double size) {
        ImageView graphic = new ImageView(img);
        graphic.setFitHeight(size);
        graphic.setPreserveRatio(true);
        graphic.setEffect(getColorIconEffect(graphic, Color.LIGHTGRAY, size, size));
        graphic.setSmooth(size < 24);

        return graphic;
    }

    public static final void applyAppStylesheet(ObservableList<String> sylesheetList) {
        sylesheetList.add(ViewUtils.class.getResource(CSS_FILE).toExternalForm());
    }

    public static final Image getImage(String jarPath) {
        InputStream stream = ViewUtils.class.getResourceAsStream(jarPath);
        if (stream == null) {
            LOGGER.log(Level.SEVERE, "Could not locate resource: {0}", jarPath);
        }
        return new Image(stream);
    }

    public static final Font getDefaultFont(double size) {
        Font font = Font.loadFont(
                ViewUtils.class.getResourceAsStream(FONT_PATH),
                size);

        return font;
    }
}
