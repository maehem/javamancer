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
import com.maehem.javamancer.neuro.view.NeuroGamePane;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.DoubleProperty;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.scene.transform.Scale;

/**
 *
 * @author Mark J Koch ( @maehem on GitHub )
 */
public class RoomDescriptionPane extends ScrollPane {

    public static final Logger LOGGER = Logging.LOGGER;

    private static final int BORDER_W = 4;

    public static final int WIDTH = 200;
    public static final int HEIGHT = 120;
    public static final int POS_X = 354;
    public static final int POS_Y = 268;

    private final Text text = new Text("...");
    private final TextFlow textFlow = new TextFlow(text);

    public RoomDescriptionPane(Scale scale) {
        super();

        setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);

        getTransforms().add(scale);
        setMinWidth(WIDTH);
        setMaxWidth(WIDTH);
        setMinHeight(HEIGHT);
        setMaxHeight(HEIGHT);
        setLayoutX(POS_X);
        setLayoutY(POS_Y);

        textFlow.setLineSpacing(-7);
        textFlow.setPrefWidth(180);
        text.setLineSpacing(-7);
        setContent(textFlow);

        setOnMouseClicked((t) -> {
            DoubleProperty scrollPos = vvalueProperty();
            if (scrollPos.get() != 1.0) {
                double val = scrollPos.get() + 0.15;
                if (val > 1.0) {
                    val = 1.0;
                }
                //setVvalue(val);
                scrollPos.set(val);
            }
        });
    }

    public void setText(String text) {
        LOGGER.log(Level.FINE, "Set Room Description Text.");
        this.text.setText(text);
    }

    public static final Shape descrptionGreyOut() {
        Rectangle r = new Rectangle(
                NeuroGamePane.WIDTH, NeuroGamePane.HEIGHT
        );
        // TODO: Add blur.

        Rectangle dr = new Rectangle(POS_X - 10, POS_Y, WIDTH * 1.44, HEIGHT);

        Shape subtract = Shape.subtract(r, dr);
        subtract.setFill(new Color(0.7, 0.7, 0.7, 0.4));
        //subtract.setEffect(new GaussianBlur(4.0));
        return subtract;
    }

    public static Shape createDescriptionBorder() {
        Shape s = new Rectangle(
                RoomDescriptionPane.WIDTH * 1.44, RoomDescriptionPane.HEIGHT
        );
        s.setLayoutX(RoomDescriptionPane.POS_X - 10);
        s.setLayoutY(RoomDescriptionPane.POS_Y);

        s.setFill(Color.TRANSPARENT);
        s.setStroke(Color.MAGENTA);
        s.setStrokeWidth(BORDER_W);
        
        s.setMouseTransparent(true);

        return s;
    }

    public void addMessage(String message) {
        Text mText = new Text("\n" + message);
        textFlow.getChildren().add(mText);

        textFlow.heightProperty().addListener(observable -> setVvalue(1D));
    }

    public boolean handleEvent(KeyEvent ke) {
        switch (ke.getCode()) {
            case UP -> {
                setVvalue(getVvalue() - 0.1);
            }
            case DOWN -> {
                setVvalue(getVvalue() + 0.1);
            }
            case SPACE -> {
                setVvalue(getVvalue() + 0.2);
            }
        }
        return false;
    }
}
