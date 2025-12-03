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
package com.maehem.javamancer.root.settings.item;

import com.maehem.javamancer.AppProperties;
import com.maehem.javamancer.ViewUtils;
import com.maehem.javamancer.logging.Logging;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.HostServices;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;

/**
 *
 * @author Mark J Koch ( @maehem on GitHub )
 */
public class DatFilesStatusItem extends HBox {

    public static final Logger LOGGER = Logging.LOGGER;
    private final ResourceBundle MSG;

    private static final Image infoImage = ViewUtils.getImage("/icons/information.png");
    private static final Image folderImage = ViewUtils.getImage("/icons/folder.png");

    private final Label label = new Label();
    private final Button checkIcon = new Button();
    private final Button crossIcon = new Button();
    private final Button infoButton;
    private final Button folderButton;

    public DatFilesStatusItem() {
        MSG = ResourceBundle.getBundle("i18n/Settings");

        setSpacing(8);

        label.setText(MSG.getString("STATUS_DAT_LABEL") + ":");
        checkIcon.getStyleClass().add("check-icon");
        crossIcon.getStyleClass().add("cross-icon");

        infoButton = ViewUtils.createIconButton(MSG.getString("STATUS_DAT_HOWTO_TOOLTIP"), infoImage);
        folderButton = ViewUtils.createIconButton(MSG.getString("STATUS_DAT_FOLDER_TOOLTIP"), folderImage);

        AppProperties appProps = AppProperties.getInstance();
        HostServices hostServices = appProps.getHostServices();
        folderButton.setOnAction((t) -> {
            LOGGER.log(Level.FINE, "Open DAT Folder...");
            hostServices.showDocument(appProps.getDatFolder().getAbsolutePath());
        });
        infoButton.setOnAction((t) -> {
            LOGGER.log(Level.FINE, "Open DAT How To...");
            doInfoPopup();
        });

        Platform.runLater(() -> {
            updateStatus();
        });
    }

    public Label getLabel() {
        return label;
    }

    public final void updateStatus() {
        AppProperties appProps = AppProperties.getInstance();
        getChildren().clear();
        if (appProps.datFilesPresent()) {
            getChildren().add(checkIcon);
        } else {
            getChildren().add(crossIcon);
        }

        // Info Icon
        getChildren().add(infoButton);
        // Folder launch icon.
        getChildren().add(folderButton);

    }

    private void doInfoPopup() {
        Alert alert = new Alert(AlertType.INFORMATION);
        ViewUtils.applyAppStylesheet(alert.getDialogPane().getStylesheets());
        alert.getDialogPane().setId("root-opaque");
        alert.setTitle(MSG.getString("STATUS_DAT_HOWTO_TITLE"));
        alert.setHeaderText(MSG.getString("STATUS_DAT_HOWTO_HEADER"));
        alert.setContentText(MSG.getString("STATUS_DAT_HOWTO_INSTRUCTIONS"));
        alert.showAndWait();
    }
}
