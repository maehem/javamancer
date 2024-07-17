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
package com.maehem.javamancer.neuro.view.ui;

import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.stage.StageStyle;
import javafx.stage.Window;

/**
 *
 * @author Mark J Koch ( @maehem on GitHub )
 */
public class LoadSaveDialog extends Dialog<Integer> {

    public enum Type {
        LOAD, SAVE
    };

    private static final Integer[] OPTIONS
            = new Integer[]{1, 2, 3, 4};
    private Integer selectedOption = null;
    Button applyButton;

    public LoadSaveDialog(Type type, Window window) {
        super();
        initStyle(StageStyle.UNDECORATED);
        initOwner(window); // Causes dialog to center on game window.
        DialogPane dialogPane = getDialogPane();
        dialogPane.getStylesheets().add(
                getClass().getResource("/style/neuro.css").toExternalForm());
        dialogPane.getStyleClass().add("neuroDialog");
        setGraphic(null);
        setHeaderText("Load Game");

        HBox box = new HBox();
        box.setSpacing(40);
        box.getChildren().add(new Region());
        for (Integer option : OPTIONS) {
            Button optionButton = new Button(String.valueOf(option));
            optionButton.setId("neuro-button-no-border");
            optionButton.setOnAction((event) -> {
                selectedOption = option;
                applyButton.fire();
            });
            box.getChildren().add(optionButton);
        }
        box.getChildren().add(new Region());
        getDialogPane().setContent(box);
        ButtonType buttonType = new ButtonType("APPLY", ButtonBar.ButtonData.LEFT);
        getDialogPane().getButtonTypes().add(buttonType);
        applyButton = (Button) getDialogPane().lookupButton(buttonType);
        applyButton.setId("neuro-button-no-border");
        applyButton.setText("exit");

        setResultConverter((dialogButton) -> {
            return selectedOption;
        });
    }

}
