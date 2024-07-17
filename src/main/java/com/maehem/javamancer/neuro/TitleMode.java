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

import com.maehem.javamancer.neuro.ui.LoadSaveDialog;
import java.util.Optional;
import java.util.logging.Level;
import javafx.scene.control.Button;
import javafx.scene.control.DialogPane;
import javafx.scene.control.TextInputDialog;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.StageStyle;

/**
 *
 * @author Mark J Koch ( @maehem on GitHub )
 */
public class TitleMode extends NeuroModePane {

    private final Font vtFont = Font.loadFont(TitleMode.class.getResourceAsStream("/fonts/VT323-Regular.ttf"), BUTTON_FONT_SIZE);

    public TitleMode(NeuroModePaneListener listener, ResourceManager resourceManager) {
        super(listener, resourceManager);

        //ImageView titleView = new ImageView(getImhImage("TITLE_1"));
        ImageView titleView = new ImageView(getResourceManager().getSprite("TITLE_1"));
        ImageView snowBackground = makeSnowBackground(
                titleView.getImage().getWidth(),
                titleView.getImage().getHeight(),
                8);

        getChildren().addAll(snowBackground, titleView);

        Button newButton = new Button("New");
        newButton.setId("neuro-button-no-border");
        newButton.setFont(vtFont);
        //newButton.setLayoutX(80);
        //newButton.setLayoutY(310);
        newButton.setOnAction((t) -> {
            //listener.neuroModeActionPerformed(NeuroModePaneListener.Action.LOAD_SAVE);
            // Check for Save Games
            // If so, present options.
            // else New Game -> present Name chooser.
            doNewplayerDialog();
        });
        Button loadButton = new Button("/Load");
        loadButton.setId("neuro-button-no-border");
        loadButton.setFont(vtFont);
        loadButton.setOnAction((t) -> {
            //listener.neuroModeActionPerformed(NeuroModePaneListener.Action.LOAD_SAVE);
            // Check for Save Games
            // If so, present options.
            // else New Game -> present Name chooser.
            doLoadDialog();
        });
        HBox newLoadBox = new HBox(newButton, loadButton);
        newLoadBox.setId("neuro-button");
        newLoadBox.setLayoutX(100);
        newLoadBox.setLayoutY(310);

        Button quit = new Button("Quit");
        quit.setId("neuro-button");
        quit.setFont(vtFont);
        quit.setLayoutX(486);
        quit.setLayoutY(310);

        quit.setOnAction((t) -> {
            getListener().neuroModeActionPerformed(NeuroModePaneListener.Action.QUIT);
        });

        getChildren().addAll(newLoadBox, quit);

    }

    private void doNewplayerDialog() {
        TextInputDialog dialog = new TextInputDialog();

        dialog.initOwner(getScene().getWindow());
        dialog.initStyle(StageStyle.UNDECORATED);
        //dialog.getEditor().setCursor(Cursor.DEFAULT);
        DialogPane dialogPane = dialog.getDialogPane();
        dialogPane.getStylesheets().add(
                getClass().getResource("/style/neuro.css").toExternalForm());
        dialogPane.getStyleClass().add("neuroDialog");
        dialog.setGraphic(null);
        dialog.setHeaderText("YOUR NAME?");
        dialog.showAndWait();
    }

    private void doLoadDialog() {
        LoadSaveDialog dialog = new LoadSaveDialog(LoadSaveDialog.Type.LOAD, getScene().getWindow());
        Optional<Integer> result = dialog.showAndWait();
        Integer selected = null;
        if (result.isPresent()) {
            selected = result.get();
            LOGGER.log(Level.SEVERE, "User wants to load {0}.", selected);
        } else if (selected == null) {
            // Nothing happes.
            LOGGER.log(Level.SEVERE, "User aborted Load dialog.");
        }
    }

    private ImageView makeSnowBackground(double w, double h, int intensity) {

        WritableImage img = new WritableImage((int) w, (int) h);
        PixelWriter pw = img.getPixelWriter();
        for (int y = 0; y < h; y += 2) {
            for (int x = 0; x < w; x += 2) {
                Color randomGrey = randomGrey(intensity);
                pw.setColor(x, y, randomGrey);
                pw.setColor(x + 1, y, randomGrey);
                pw.setColor(x, y + 1, randomGrey);
                pw.setColor(x + 1, y + 1, randomGrey);
            }
        }

        return new ImageView(img);
    }

    private Color randomGrey(int intensity) {
        if (intensity > 64) {
            intensity = 64;
        }
        if (intensity < 0) {
            intensity = 0;
        }
        double random = Math.random();

        double grey = random * random * intensity / 64;
        return new Color(grey, grey, grey, 1.0);
    }
}
