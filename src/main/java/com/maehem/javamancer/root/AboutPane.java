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
import com.maehem.javamancer.JavamancerPane;
import java.util.ResourceBundle;
import java.util.logging.Level;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
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
import javafx.scene.text.TextAlignment;
import javafx.scene.text.TextFlow;

/**
 *
 * @author Mark J Koch ( @maehem on GitHub )
 */
public class AboutPane extends JavamancerPane {

    public enum Action {
        DONE
    };

    private final AboutPaneListener listener;

    private final Button doneButton; //= new Button("Done");
    private final ResourceBundle MSG;

    public AboutPane(AboutPaneListener parent) {
        super();
        MSG = ResourceBundle.getBundle("i18n/About"); // Must be done after super() called.
        LOGGER.log(Level.CONFIG, MSG.getString("LOG_CREATE"));
        this.listener = parent;

        BackgroundSize backgroundSize = new BackgroundSize(1, 1, true, true, false, false);
        BackgroundImage bgImage = new BackgroundImage(new Image(getClass().getResourceAsStream("/backdrops/about-backdrop.png")),
                BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER, backgroundSize);

        setBackground(new Background(bgImage));
        getStyleClass().add("root-clear");

        // Apply CSS style ID
        doneButton = new Button(MSG.getString("DONE_BUTTON"));
        doneButton.setId("about-done-button"); // CSS ID

        Pane topPane = new Pane();
        topPane.minHeightProperty().bind(heightProperty().multiply(0.15)); // 15% of window height.

        setTop(topPane);

        setCenter(contentNode());
        VBox bottomBox = new VBox(doneButton);
        bottomBox.setAlignment(Pos.CENTER);
        setBottom(bottomBox);

        initListeners();
    }

    private void initListeners() {
        doneButton.setOnAction((t) -> {
            listener.aboutActionPerformed(Action.DONE);
        });
    }

    private Node contentNode() {

        Text title = new Text("Javamancer (" + MSG.getString("CONTENT_TITLE_PHONETIC") + ")"); // Phonetic translation
        title.setFont(Font.font(Font.getDefault().getSize() * 1.2));

        Text description = new Text(MSG.getString("CONTENT_DESCRIPTION"));
        description.setFont(Font.font(Font.getDefault().getSize() * 0.8));
        TextFlow descFlow = new TextFlow(description);
        descFlow.setTextAlignment(TextAlignment.JUSTIFY);
        VBox.setMargin(descFlow, new Insets(0, 30, 0, 30));

        Text copyrightText = new Text(MSG.getString("CONTENT_COPYRIGHT"));
        Text licenseText = new Text(MSG.getString("CONTENT_LICENSE"));
        licenseText.setFont(Font.font(Font.getDefault().getSize() * 0.8));

        VBox contentBox = new VBox(title, descFlow, copyrightText, licenseText);
        contentBox.setSpacing(14);
        contentBox.setAlignment(Pos.TOP_CENTER);
        return contentBox;
    }

    @Override
    public void pushProperties(AppProperties appProperties) {
    }

    @Override
    public void pullProperties(AppProperties appProperties) {
    }

}
