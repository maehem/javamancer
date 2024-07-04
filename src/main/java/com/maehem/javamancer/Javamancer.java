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
package com.maehem.javamancer;

import com.maehem.javamancer.logging.Logging;
import com.maehem.javamancer.resource.BrowserPane;
import com.maehem.javamancer.resource.Ingest;
import com.maehem.javamancer.resource.model.DAT;
import com.maehem.javamancer.root.AboutPane;
import com.maehem.javamancer.root.AboutPaneListener;
import com.maehem.javamancer.root.BrowserPaneListener;
import com.maehem.javamancer.root.RootButtonListener;
import com.maehem.javamancer.root.RootPane;
import com.maehem.javamancer.root.settings.SettingsPane;
import com.maehem.javamancer.root.settings.SettingsPaneListener;
import java.io.FileNotFoundException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 *
 * @author Mark J Koch ( @maehem on GitHub )
 */
public class Javamancer extends Application implements RootButtonListener, AboutPaneListener, SettingsPaneListener, BrowserPaneListener {

    // TODO: create a static logger in logging package.
    public static final Logger LOGGER = Logging.LOGGER;
    private Scene scene;

    private DAT dat = null;

    public enum Mode {
        ROOT, GAME, BROWSER, SETTINGS, ABOUT
    }

    public static final String WINDOW_POS_X_PROP_KEY = "Window.X";
    public static final String WINDOW_POS_Y_PROP_KEY = "Window.Y";

    private final AppProperties appProperties;
    private final JavamancerPane rootPane;
    private JavamancerPane gamePane = null;
    private JavamancerPane browserPane = null;
    private JavamancerPane settingsPane = null;
    private JavamancerPane aboutPane = null;
    private final ResourceBundle MSG;
    private Mode mode = Mode.ROOT;

    public Javamancer() {
        super();
        Logging.configureLogging();

        //Locale.setDefault(Locale.GERMANY);  // uncomment for i18n debug
        MSG = ResourceBundle.getBundle("i18n/App"); // Must be done after super() called.
        LOGGER.setLevel(Level.FINEST);
        LOGGER.log(Level.CONFIG, "Log Start");

        appProperties = AppProperties.getInstance();

        rootPane = new RootPane(this);
    }

    @Override
    public void start(Stage stage) throws Exception {
        // Set the title of the Stage
        stage.setTitle(MSG.getString("TITLE"));

        // Host Services allows opening of browser links inside app.
        appProperties.setHostServices(getHostServices());

        // Add icon for the app
        Image appIcon = new Image(getClass().getResourceAsStream("/icons/icon-circuit-heart.png"));
        stage.getIcons().add(appIcon);

        scene = new Scene(rootPane);  // Create the Scene
        scene.getStylesheets().add(this.getClass().getResource("/style/dark.css").toExternalForm());
        stage.setScene(scene); // Add the scene to the Stage

        rootPane.pullProperties(appProperties); // Load settings from prop file.
        // Always keep the aspect ratio for any window size.
        final double aspectRatio = 4.0 / 3.0; // 4:3 old style aspect ratio
        stage.minWidthProperty().bind(scene.heightProperty().multiply(aspectRatio));
        stage.minHeightProperty().bind(scene.widthProperty().divide(aspectRatio));

        // Configure saved properties.
        pullProperties(appProperties, stage);

        stage.show(); // Display the Stage

        stage.setOnCloseRequest((t) -> {
            pushProperties(appProperties, stage);
            rootPane.pushProperties(appProperties);
            appProperties.save();

            Platform.exit();
        });
    }

    public static void main(String[] args) {
        launch(args);
    }

    /**
     *
     * @param button
     */
    @Override
    public void rootButtonPressed(Mode button) {
        switch (button) {
            case GAME -> {
                LOGGER.log(Level.SEVERE, "Game Button Pressed");
            }
            case BROWSER -> {
                LOGGER.log(Level.SEVERE, "Browser Button Pressed");
                if (dat == null) {
                    try {
                        dat = Ingest.ingestDAT(appProperties.getDatFiles());
                    } catch (FileNotFoundException ex) {
                        Logger.getLogger(Javamancer.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                if (browserPane == null) {
                    browserPane = new BrowserPane(this);
                }
                scene.setRoot(browserPane);
                mode = Mode.BROWSER;
            }
            case SETTINGS -> {
                LOGGER.log(Level.SEVERE, "Settings Button Pressed");
                if (settingsPane == null) {
                    settingsPane = new SettingsPane(this);
                }
                scene.setRoot(settingsPane);
                mode = Mode.SETTINGS;
            }
            case ABOUT -> {
                LOGGER.log(Level.SEVERE, "About Button Pressed");
                if (aboutPane == null) {
                    aboutPane = new AboutPane(this);
                }
                scene.setRoot(aboutPane);
                mode = Mode.ABOUT;
            }
        }
    }

    public void pushProperties(AppProperties appProperties, Stage stage) {
        appProperties.setProperty(WINDOW_POS_X_PROP_KEY, String.valueOf(stage.getX()));
        appProperties.setProperty(WINDOW_POS_Y_PROP_KEY, String.valueOf(stage.getY()));
    }

    public void pullProperties(AppProperties appProperties, Stage stage) {
        String posX = appProperties.getProperty(WINDOW_POS_X_PROP_KEY, "50");
        String posY = appProperties.getProperty(WINDOW_POS_Y_PROP_KEY, "20");
        stage.setX(Double.parseDouble(posX));
        stage.setY(Double.parseDouble(posY));
    }

    @Override
    public void aboutActionPerformed(AboutPane.Action action) {
        scene.setRoot(rootPane);
        mode = Mode.ROOT;
    }

    @Override
    public void settingsActionPerformed(SettingsPane.Action action) {
        scene.setRoot(rootPane);
        mode = Mode.ROOT;
    }

    @Override
    public void broswerActionPerformed(BrowserPane.Action action) {
        scene.setRoot(rootPane);
        mode = Mode.ROOT;
    }

}
