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
import javafx.scene.input.KeyCode;
import static javafx.scene.input.KeyCode.ESCAPE;
import static javafx.scene.input.KeyCode.X;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.scene.transform.Scale;

/**
 *
 * @author Mark J Koch ( @maehem on GitHub )
 */
public abstract class DatabaseView {

    public static final Logger LOGGER = Logging.LOGGER;

    protected enum SubMode {
        LANDING, PASSWORD, CLEAR_WAIT, MAIN
    }

    private enum AccessText {
        NONE, DENIED, CLEARED_1, CLEARED_2
    }

    //protected static final double TEXT_SCALE = 1.5;
    protected static final int CHAR_W = 38; // This many chars accross.
    protected static final double LINE_SPACING = -8.8;
    protected static final Scale TEXT_SCALE = new Scale(1.5, 1.0);
    protected static final double TF_W = 640 / TEXT_SCALE.getX();
    protected static final Insets TF_PADDING = new Insets(8);

    private final StringBuilder typedPassword = new StringBuilder();
    protected final Text enteredText = new Text();
    protected final Text accessStatusText = new Text();
    protected boolean accessDenied = false;

    protected static final String CONTINUE_TEXT = "    Button or [space] to continue.";

    protected final Database database;
    protected final GameState gameState;
    protected final Pane pane;

    protected int accessLevel = 0;
    protected SubMode subMode = SubMode.LANDING;
    private int clearWait = 0;

    public DatabaseView(GameState gs, Pane p) {
        this.database = gs.database;
        this.gameState = gs;
        this.pane = p;

        setAccessText(AccessText.NONE);
    }

    public boolean handleKeyEvent(KeyEvent keyEvent) {
        KeyCode code = keyEvent.getCode();
        switch (subMode) {
            case LANDING -> {
                switch (code) {
                    case KeyCode.SPACE -> {
                        passwordPage();
                    }
                    case X, ESCAPE -> {
                        LOGGER.log(Level.FINER, "User pressed X or ESC Key.");
                        return true;
                    }
                }
            }
            case PASSWORD -> {
                handleEnteredPassword(keyEvent);
            }
        }

        return false;
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

    protected TextFlow pageTextFlow() {
        TextFlow tf = new TextFlow();
        tf.getTransforms().add(TEXT_SCALE);
        tf.setPadding(TF_PADDING);
        tf.setLineSpacing(LINE_SPACING);
        tf.setPrefWidth(TF_W);

        return tf;
    }

    private void passwordPage() {
        subMode = SubMode.PASSWORD;
        pane.getChildren().clear();
        pane.getChildren().add(passwordFoo());
    }

    protected TextFlow passwordFoo() {

        String leadingSpace = "        ";
        Text instructionsText = new Text("\n" + centeredText("Enter password:") + "\n\n");
        Text leadingText1 = new Text(leadingSpace);
        Text leadingText2 = new Text(leadingSpace);
        Text cursorText = new Text("<\n");

        TextFlow tf = new TextFlow(
                leadingText1, instructionsText,
                leadingText2, enteredText, cursorText,
                new Text("\n\n"), accessStatusText, // need blank Text() or FX has rendering issue.
                new Text("\n\n\n\n" + centeredText(CONTINUE_TEXT))
        );
        tf.getTransforms().add(TEXT_SCALE);
        tf.setPadding(TF_PADDING);
        tf.setLineSpacing(LINE_SPACING);
        tf.setPrefWidth(TF_W);

        return tf;
    }

    protected void handleEnteredPassword(KeyEvent ke) {
        LOGGER.log(Level.FINEST, "Handle password key typed.");
        if (typedPassword.length() < 12 && ke.getCode().isLetterKey()) {
            LOGGER.log(Level.FINEST, "Typed: {0}", ke.getText());
            typedPassword.append(ke.getText());
            enteredText.setText(typedPassword.toString());
        } else if (typedPassword.length() > 0 && ke.getCode().equals(KeyCode.BACK_SPACE)) {
            LOGGER.log(Level.FINEST, "Backspace.");
            if (accessDenied) {
                typedPassword.setLength(0);
                accessDenied = false;
                setAccessText(AccessText.NONE);
            } else {
                typedPassword.delete(typedPassword.length() - 1, typedPassword.length());
            }
            enteredText.setText(typedPassword.toString());
        } else if (ke.getCode().equals(KeyCode.ENTER)) {
            LOGGER.log(Level.SEVERE, "entered: {0}  passwords: {1}, {2}",
                    new Object[]{
                        typedPassword,
                        gameState.database.password1,
                        gameState.database.password2 == null ? "none" : gameState.database.password2
                    });
            // Check password valid.
            if (gameState.database.password1.equals(typedPassword.toString())) {
                accessLevel = 1;
                setAccessText(AccessText.CLEARED_1);
                // set cleared for access text.
                accessCleared(2);
            } else if (gameState.database.password2 != null
                    && gameState.database.password2.equals(typedPassword.toString())) {
                accessLevel = 2;
                setAccessText(AccessText.CLEARED_2);
                accessCleared(2);
                // set cleared for access text.
            } else {
                accessDenied = true;
                setAccessText(AccessText.DENIED);
                accessStatusText.setVisible(true);
            }
        }
    }

    private void setAccessText(AccessText state) {
        switch (state) {
            case NONE -> {
                accessStatusText.setText("");
            }
            case DENIED -> {
                accessStatusText.setText(centeredText("Access denied."));
            }
            case CLEARED_1 -> {
                accessStatusText.setText(centeredText("Cleared for Level 1 access."));
            }
            case CLEARED_2 -> {
                accessStatusText.setText(centeredText("Cleared for Level 2 access."));
            }
        }
    }

    protected void accessCleared(int wait) {
        LOGGER.log(Level.SEVERE, "Access Cleared.");
        subMode = SubMode.CLEAR_WAIT;
        clearWait = 30; // 2 seconds.
    }

    protected abstract void landingPage();
    protected abstract void siteContent();

    public void tick() {
        if (subMode.equals(SubMode.CLEAR_WAIT)) {
            clearWait--;
            if (clearWait <= 0) {
                clearWait = 0;
                subMode = SubMode.MAIN;
                LOGGER.log(Level.SEVERE, "ClearWait finished. Load site content.");
                siteContent();
            }

        }
    }
}
