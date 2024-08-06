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
package com.maehem.javamancer.neuro.view.room;

import com.maehem.javamancer.logging.Logging;
import java.util.logging.Logger;
import javafx.scene.control.ScrollPane;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.scene.transform.Scale;

/**
 *
 * @author Mark J Koch ( @maehem on GitHub )
 */
public class RoomDescriptionPane extends ScrollPane {

    public static final Logger LOGGER = Logging.LOGGER;

    private final Text text = new Text("...");
    private final TextFlow textFlow = new TextFlow(text);

    public RoomDescriptionPane(Scale scale) {
        super();
        setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);

        getTransforms().add(scale);
        setMinWidth(200);
        setMaxWidth(200);
        setMinHeight(120);
        setMaxHeight(120);
        setLayoutX(354);
        setLayoutY(268);

        textFlow.setLineSpacing(-7);
        textFlow.setPrefWidth(180);
        text.setLineSpacing(-7);
        setContent(textFlow);

        setOnMouseClicked((t) -> {
            if (getVvalue() != 1.0) {
                double val = getVvalue() + 0.33;
                if (val > 1.0) {
                    val = 1.0;
                }
                setVvalue(val);
            }
        });
    }

    public void setText(String text) {
        this.text.setText(text);
    }

}
