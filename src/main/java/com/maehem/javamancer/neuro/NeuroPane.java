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

import com.maehem.javamancer.AppProperties;
import com.maehem.javamancer.JavamancerPane;
import com.maehem.javamancer.neuro.view.NeuroGamePane;
import com.maehem.javamancer.neuro.view.NeuroPaneListener;
import com.maehem.javamancer.neuro.view.NeuroPaneListener.Action;
import java.util.ResourceBundle;
import java.util.logging.Level;
import javafx.scene.Group;

/**
 * Acts as a resizable container for the game's fixed size. Also provides system
 * level resource folder location to game.
 *
 * @author Mark J Koch ( @maehem on GitHub )
 */
public class NeuroPane extends JavamancerPane {

    private final NeuroPaneListener listener;

    private final ResourceBundle MSG;
    private final NeuroGamePane gamePane;

    public NeuroPane(NeuroPaneListener parent) {
        super();
        MSG = ResourceBundle.getBundle("i18n/Neuro"); // Must be done after super() called.

        LOGGER.log(Level.CONFIG, MSG.getString("LOG_CREATE"));
        this.listener = parent;

        // Apply CSS style ID
        setId("neuro-game");

        gamePane = new NeuroGamePane(AppProperties.getInstance().getCacheFolder());

        // Placing 'gamePane' in a group helps it stay in place when scaled
        // by the parent Pane.
        setCenter(new Group(gamePane));

        bindWidth(); // Cause the game to scale with the Pane.

        initListeners();
    }

    private void initListeners() {
        gamePane.setOnAction((t) -> {
            if ( t.getSource().equals(NeuroGamePane.Action.QUIT ) ) {
                listener.neuroActionPerformed(Action.DONE);
            }
        });
    }

    private void bindWidth() {
        widthProperty().addListener((ov, oldNumber, newNumber) -> {
            double scale = newNumber.doubleValue() / NeuroGamePane.WIDTH;
            gamePane.setScaleX(scale);
            gamePane.setScaleY(scale);
        });
    }

    @Override
    public void pushProperties(AppProperties appProperties) {
        gamePane.pushProperties(appProperties);
    }

    @Override
    public void pullProperties(AppProperties appProperties) {
        gamePane.pullProperties(appProperties);
    }

}
