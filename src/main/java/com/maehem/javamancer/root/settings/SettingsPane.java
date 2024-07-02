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
package com.maehem.javamancer.root.settings;

import com.maehem.javamancer.AppProperties;
import com.maehem.javamancer.JavamancerPane;
import java.util.ResourceBundle;
import java.util.logging.Level;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Separator;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

/**
 *
 * @author Mark J Koch ( @maehem on GitHub )
 */
public class SettingsPane extends JavamancerPane {

    public enum Action {
        DONE
    };

    private final SettingsPaneListener listener;

    private final Button doneButton; //= new Button("Done");
    private final ResourceBundle MSG;

    public SettingsPane(SettingsPaneListener parent) {
        super();
        MSG = ResourceBundle.getBundle("i18n/Settings"); // Must be done after super() called.
        LOGGER.log(Level.CONFIG, MSG.getString("LOG_CREATE"));
        this.listener = parent;

        // Apply CSS style ID
        setId("root-opaque");

        doneButton = new Button(MSG.getString("DONE_BUTTON"));
        doneButton.setId("settings-done-button"); // CSS ID

        Text titleText = new Text(MSG.getString("TITLE"));
        titleText.setId("settings-title");
        titleText.setTextAlignment(TextAlignment.CENTER);
        VBox topBox = new VBox(titleText, new Separator());
        topBox.setAlignment(Pos.CENTER);
        setTop(topBox);

        SettingsContentPane contentBox = new SettingsContentPane();
        HBox contentExpandBox = new HBox(contentBox);
        HBox.setHgrow(contentBox, Priority.ALWAYS);
        setCenter(contentExpandBox);

        VBox bottomBox = new VBox(doneButton);
        bottomBox.setAlignment(Pos.CENTER);
        setBottom(bottomBox);

        initListeners();
    }

    private void initListeners() {
        doneButton.setOnAction((t) -> {
            listener.settingsActionPerformed(Action.DONE);
        });
    }

    @Override
    public void pushProperties(AppProperties appProperties) {
    }

    @Override
    public void pullProperties(AppProperties appProperties) {
    }

}
