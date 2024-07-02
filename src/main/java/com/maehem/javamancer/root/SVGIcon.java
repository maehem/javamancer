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

import java.util.List;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;

/**
 *
 * @author Mark J Koch ( @maehem on GitHub )
 */
public class SVGIcon extends StackPane {

    public SVGIcon(List<String> cssStyles) {
        //setPrefSize(64, 64);
        //setBackground(Background.EMPTY);

//        SVGPath round = new SVGPath();
//        SVGPath bar1 = new SVGPath();
//        SVGPath bar2 = new SVGPath();
//        SVGPath bar3 = new SVGPath();
//
//        round.setContent("M253.5,336.5c-18.9,0-34.2,15.3-34.2,34.1c0,18.8,15.4,34.1,34.2,34.1c18.9,0,34.2-15.3,34.2-34.1 C287.7,351.8,272.4,336.5,253.5,336.5z");
//        bar1.setContent("M337,290.1c-22.3-22.3-51.9-34.5-83.5-34.5c-31.4,0-61,12.2-83.3,34.3c-9,9-9,23.5,0,32.5 c4.4,4.4,10.2,6.8,16.3,6.8c6.2,0,11.9-2.4,16.3-6.7c13.6-13.5,31.6-20.9,50.7-20.9c19.2,0,37.3,7.5,50.8,21 c4.4,4.4,10.2,6.8,16.3,6.8c6.2,0,11.9-2.4,16.3-6.7C346,313.6,346,299,337,290.1z");
//        bar2.setContent("M389.3,238c-36.3-36.2-84.5-56.1-135.8-56.1c-51.2,0-99.3,19.9-135.6,55.9c-4.4,4.3-6.8,10.1-6.8,16.3 c0,6.1,2.4,11.9,6.7,16.3c4.4,4.3,10.2,6.7,16.3,6.7c6.2,0,11.9-2.4,16.3-6.7c27.5-27.4,64.1-42.5,103-42.5 c39,0,75.6,15.1,103.1,42.6c4.4,4.4,10.2,6.8,16.3,6.8c6.2,0,12-2.4,16.3-6.7c4.4-4.3,6.8-10.1,6.8-16.3 C396,248.1,393.6,242.3,389.3,238z");
//        bar3.setContent("M444.3,183.2c-50.9-50.8-118.7-78.8-190.8-78.8c-72,0-139.7,27.9-190.6,78.6c-9,9-9,23.5,0,32.5 c4.4,4.3,10.2,6.7,16.3,6.7c6.2,0,12-2.4,16.3-6.7c42.2-42,98.3-65.2,158-65.2c59.7,0,115.9,23.2,158.1,65.3 c4.4,4.3,10.2,6.7,16.3,6.7c6.2,0,11.9-2.4,16.3-6.7C453.2,206.7,453.3,192.1,444.3,183.2z");
//
//        round.getStyleClass().add("wifi-base");
//        bar1.getStyleClass().add("wifi-bar1");
//        bar2.getStyleClass().add("wifi-bar2");
//        bar3.getStyleClass().add("wifi-bar3");

//        this.getChildren().addAll(round, bar1, bar2, bar3);
        cssStyles.forEach((style) -> {
            Button path = new Button();

            path.getStyleClass().add(style);
            getChildren().add(path);
        });
    }

}
