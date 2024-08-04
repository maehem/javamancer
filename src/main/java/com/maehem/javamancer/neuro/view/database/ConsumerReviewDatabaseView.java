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

import com.maehem.javamancer.neuro.model.GameState;
import com.maehem.javamancer.neuro.model.TextResource;
import java.util.logging.Level;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

/**
 *
 * @author Mark J Koch ( @maehem on GitHub )
 */
public class ConsumerReviewDatabaseView extends DatabaseView {

    private enum Mode {
        LANDING, PASSWORD, MAIN_1
    }

    private final Text headingText = new Text();
    private final Text continueText = new Text();

    private Mode mode = Mode.LANDING;

    public ConsumerReviewDatabaseView(GameState gameState, Pane pane) {
        super(gameState, pane);
        //headingText.setText(MSG.getString("HEADING"));
        //continueText.setText(MSG.getString("CONTINUE"));

        landingPage();
    }

    private void landingPage() {
        pane.getChildren().clear();
        mode = Mode.LANDING;

        TextResource dbTextResource = gameState.resourceManager.getDatabaseText(gameState.database.number);
        //36
        //String headingStr = dbTextResource.get(0);
        //int pad = headingStr.length() + (CHAR_W - headingStr.length()) / 2;
        //headingText.setText(String.format("%" + pad + "s", headingStr) + "\n\n");
        headingText.setText(centeredText(dbTextResource.get(0)) + "\n\n");

        Text helloText = new Text(dbTextResource.get(2) + "\n\n\n");
        Text continueText = new Text(CONTINUE_TEXT);

//        TextFlow tf = new TextFlow(headingText, helloText, continueText);
//        tf.getTransforms().add(TEXT_SCALE);
//        tf.setPadding(TF_PADDING);
//        tf.setLineSpacing(LINE_SPACING);
//        tf.setPrefWidth(TF_W);
        TextFlow tf = pageTextFlow();
        tf.getChildren().addAll(headingText, helloText, continueText);
        pane.getChildren().add(tf);
    }

    private void passwordPage() {
        mode = Mode.PASSWORD;
        pane.getChildren().clear();
        pane.getChildren().add(passwordFoo());
    }

    @Override
    public boolean handleKeyEvent(KeyEvent keyEvent) {
        KeyCode code = keyEvent.getCode();

        switch (mode) {
            case LANDING -> {
                if (code.equals(KeyCode.SPACE)) {
                    passwordPage();
                }
            }
            case PASSWORD -> {
                handleEnteredPassword(keyEvent);
            }
        }
        return super.handleKeyEvent(keyEvent);
    }

    @Override
    protected void siteContent() {
        LOGGER.log(Level.SEVERE, "Do site content main_1.");
        mode = Mode.MAIN_1;
        pane.getChildren().clear();

        pane.getChildren().add(new Text("Main 1"));
    }

}
