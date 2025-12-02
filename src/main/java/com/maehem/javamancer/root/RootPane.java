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
package com.maehem.javamancer.root;

import com.maehem.javamancer.AppProperties;
import com.maehem.javamancer.Javamancer.Mode;
import com.maehem.javamancer.JavamancerPane;
import java.util.ResourceBundle;
import java.util.logging.Level;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

/**
 *
 * @author Mark J Koch ( @maehem on GitHub )
 */
public class RootPane extends JavamancerPane {

    private final RootButtonListener listener;

    public static final String PANE_SIZE_W_PROP_KEY = "RootPane.W";
    public static final String PANE_SIZE_H_PROP_KEY = "RootPane.H";

    private final Button gameButton = new Button("Game");
    private final Button browserButton = new Button("Resource Browser");
    private final Button settingsButton = new Button("Settings");
    private final Button aboutButton = new Button("About");
    private final ResourceBundle MSG;

    public RootPane(RootButtonListener parent) {
        super();
        MSG = ResourceBundle.getBundle("i18n/Root"); // Must be done after super() called.
        LOGGER.log(Level.CONFIG, "Create Root Pane.");
        this.listener = parent;

        //setId("root-clear");
        BackgroundSize backgroundSize = new BackgroundSize(1, 1, true, true, false, false);
        BackgroundImage bgImage = new BackgroundImage(new Image(getClass().getResourceAsStream("/backdrops/root-backdrop.png")),
                BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER, backgroundSize);

        setBackground(new Background(bgImage));

        VBox buttonBar = new VBox(gameButton, browserButton, settingsButton, aboutButton);

        // Apply CSS style ID
        //setId("transparent-background");
        buttonBar.setId("root-button-bar");
        gameButton.setId("root-button");
        browserButton.setId("root-button");
        settingsButton.setId("root-button");
        aboutButton.setId("root-button");

        setLeft(buttonBar);

        Pane topPane = new Pane();
        topPane.minHeightProperty().bind(heightProperty().multiply(0.25)); // 25% of window height.

        setTop(topPane);

        Text copyrightText = new Text("Copyright ©2024 by  Mark J. Koch");
        Text licenseText = new Text("Released under MIT License.");
        licenseText.setFont(Font.font(Font.getDefault().getSize() * 0.8));
        VBox bottomBox = new VBox(copyrightText, licenseText);
        bottomBox.setAlignment(Pos.BASELINE_RIGHT);
        setBottom(bottomBox);

        initListeners();

        refresh();
    }

    private void initListeners() {
        gameButton.setOnAction((t) -> {
            listener.rootButtonPressed(Mode.GAME);
        });
        browserButton.setOnAction((t) -> {
            listener.rootButtonPressed(Mode.BROWSER);
        });
        settingsButton.setOnAction((t) -> {
            listener.rootButtonPressed(Mode.SETTINGS);
        });
        aboutButton.setOnAction((t) -> {
            listener.rootButtonPressed(Mode.ABOUT);
        });
    }

    @Override
    public void pushProperties(AppProperties appProperties) {
        appProperties.setProperty(PANE_SIZE_W_PROP_KEY, String.valueOf(getWidth()));
        appProperties.setProperty(PANE_SIZE_H_PROP_KEY, String.valueOf(getHeight()));
    }

    @Override
    public void pullProperties(AppProperties appProperties) {
        String sizeW = appProperties.getProperty(PANE_SIZE_W_PROP_KEY, "640");
        String sizeH = appProperties.getProperty(PANE_SIZE_H_PROP_KEY, "480");
        setPrefSize(Double.parseDouble(sizeW), Double.parseDouble(sizeH));
    }

    @Override
    public final void refresh() {
        boolean datFilesPresent = AppProperties.getInstance().datFilesPresent();
        boolean cacheFilesPresent = AppProperties.getInstance().cacheFilesPresent();
        gameButton.setDisable(!datFilesPresent || !cacheFilesPresent);
        browserButton.setDisable(!datFilesPresent);
    }

}
