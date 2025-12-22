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
import javafx.scene.effect.BoxBlur;
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

    private static final double BORDER_W = 1.8;

    public static final int WIDTH = 200;
    public static final int HEIGHT = 122;
    public static final int POS_X = 347;
    public static final int POS_Y = 267;

    private static final Color GREY_OUT_COLOR = new Color(0.2, 0.2, 0.2, 0.5);
    
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
        textFlow.setPrefWidth(WIDTH-12);
        textFlow.setMinHeight(HEIGHT);
        // For testing
        //textFlow.setBorder(new Border(new BorderStroke(Color.RED, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(1))));
        text.setLineSpacing(-6);
        setContent(textFlow);

        setOnMouseClicked((t) -> {
            DoubleProperty scrollPos = vvalueProperty();
            if (scrollPos.get() != 1.0) {
                double val = scrollPos.get() + 0.15;
                if (val > 1.0) {
                    val = 1.0;
                }
                scrollPos.set(val);
            }
        });
    }

    public void setText(String text) {
        LOGGER.log(Level.FINE, "Set Room Description Text.");
        this.text.setText(text);
    }

    public static final Shape descrptionGreyOut(Scale scale) {
        Rectangle r = new Rectangle(
                NeuroGamePane.WIDTH, NeuroGamePane.HEIGHT
        );
        
        Rectangle dr = new Rectangle(POS_X , POS_Y, WIDTH*1.43, HEIGHT);

        Shape subtract = Shape.subtract(r, dr);
        subtract.setFill(GREY_OUT_COLOR);
        
        return subtract;
    }
    
    public static Shape createDescriptionBorder() {
        Shape s = new Rectangle(
                WIDTH * 1.43 + 2*BORDER_W, // 4:3 aspect == 1.333
                HEIGHT + 2*BORDER_W
        );
        s.setLayoutX(RoomDescriptionPane.POS_X - 2*BORDER_W);
        s.setLayoutY(RoomDescriptionPane.POS_Y - BORDER_W);

        s.setFill(Color.TRANSPARENT);
        s.setStroke(Color.MAGENTA);
        s.setStrokeWidth(BORDER_W);
        
        s.setEffect(new BoxBlur(2*BORDER_W,2*BORDER_W, 2));
        
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
