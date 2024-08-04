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
package com.maehem.javamancer.neuro.view.database;

import com.maehem.javamancer.logging.Logging;
import com.maehem.javamancer.neuro.model.GameState;
import com.maehem.javamancer.neuro.model.database.Database;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.geometry.Insets;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.scene.transform.Scale;

/**
 *
 * @author Mark J Koch ( @maehem on GitHub )
 */
public class DatabaseView {

    public static final Logger LOGGER = Logging.LOGGER;
    //protected static final double TEXT_SCALE = 1.5;
    protected static final int CHAR_W = 38; // This many chars accross.
    protected static final double LINE_SPACING = -8.8;
    protected static final Scale TEXT_SCALE = new Scale(1.5, 1.0);
    protected static final double TF_W = 640 / TEXT_SCALE.getX();
    protected static final Insets TF_PADDING = new Insets( 8 );


    protected static final String CONTINUE_TEXT = "    Button or [space] to continue.";

    protected final Database database;
    protected final GameState gameState;
    protected final Pane pane;

    public DatabaseView(GameState gs, Pane p) {
        this.database = gs.database;
        this.gameState = gs;
        this.pane = p;
    }

    public boolean handleKeyEvent(KeyEvent keyEvent) {
        switch (keyEvent.getCode()) {
            case X, ESCAPE -> {
                LOGGER.log(Level.FINER, "User pressed X or ESC Key.");
                return true;
            }
            default -> {
            }
        }

        return false;
    }

    protected Text NL() {
        return new Text("\n");
    }

    public static DatabaseView getView(GameState gs, Pane p) {
        Class<? extends DatabaseView> viewClass = DatabaseViewList.getViewClass(gs.database.getClass());
        try {
            Constructor<?> ctor = viewClass.getConstructor(new Class[]{
                GameState.class,
                Pane.class
            });
            Object object = ctor.newInstance(new Object[]{gs, p});
            LOGGER.log(Level.SEVERE, "Database View created.");
            if (object instanceof DatabaseView re) {
                return re;
            } else {
                LOGGER.log(Level.SEVERE, "Database View Creation Failed.");
                return null;
            }
        } catch (InstantiationException
                | IllegalAccessException
                | IllegalArgumentException
                | InvocationTargetException
                | NoSuchMethodException
                | SecurityException ex) {
            LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
            ex.printStackTrace();
        }
        LOGGER.log(Level.SEVERE, "Was not able to create a view for {0}", gs.database.name);
        return null;
    }

    protected String centeredText(String str) {
        int pad = str.length() + (CHAR_W - str.length()) / 2;
        return String.format("%" + pad + "s", str);
    }
}
