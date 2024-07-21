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
package com.maehem.javamancer.neuro.view.pax;

import com.maehem.javamancer.neuro.view.ResourceManager;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.scene.transform.Scale;

/**
 *
 * @author Mark J Koch ( @maehem on GitHub )
 */
public class PaxFirstTimeNode extends VBox {

    public PaxFirstTimeNode(ResourceManager rm) {
        getTransforms().add(new Scale(1.333, 1.0));
        setLayoutX(20);
        setLayoutY(0);
        String firstTimeText = rm.getFirstTimeText();

        int indexOfLF = firstTimeText.indexOf(0x0a) + 1;
        //firstTimeText = firstTimeText.substring(indexOfLF); // Remove comment at top

        int indexOfZero = firstTimeText.indexOf(0);
        String titleString = firstTimeText.substring(indexOfLF, indexOfZero);

        Text title = new Text(titleString);

        Text contentText = new Text(firstTimeText.substring(indexOfZero));
        TextFlow tf = new TextFlow(contentText);
        tf.setLineSpacing(-8);
        tf.setPrefSize(444, 170);

        ScrollPane sp = new ScrollPane(tf);
        //sp.setMaxSize(460, 180);
        sp.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        sp.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);

        getChildren().addAll(title, sp);

    }

}
