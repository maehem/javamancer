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

import com.maehem.javamancer.neuro.model.BbsMessage;
import com.maehem.javamancer.neuro.model.GameState;
import com.maehem.javamancer.neuro.view.ResourceManager;
import static com.maehem.javamancer.neuro.view.pax.PaxNode.LOGGER;
import java.util.ArrayList;
import java.util.logging.Level;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.ScrollPane;
import static javafx.scene.input.KeyCode.DIGIT1;
import static javafx.scene.input.KeyCode.DIGIT2;
import static javafx.scene.input.KeyCode.DIGIT3;
import static javafx.scene.input.KeyCode.DIGIT4;
import static javafx.scene.input.KeyCode.DIGIT5;
import static javafx.scene.input.KeyCode.DOWN;
import static javafx.scene.input.KeyCode.ESCAPE;
import static javafx.scene.input.KeyCode.UP;
import static javafx.scene.input.KeyCode.X;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.text.TextFlow;
import javafx.scene.transform.Scale;

/**
 *
 * @author Mark J Koch ( @maehem on GitHub )
 */
public class PaxBbsNode extends PaxNode {

    private static final int NUM_MESSAGES = 5;

    private enum Mode {
        LIST, MESSAGE
    }

    private final ResourceManager resourceManager;

    private Mode mode = Mode.LIST;
    private int messageIndex = 0;
    private int numMessages = 0;

    public PaxBbsNode(GameState gs, ResourceManager rm) {
        super(gs);
        this.resourceManager = rm;

        messageListPage();
    }

    private void messageListPage() {
        getChildren().clear();
        mode = Mode.LIST;

        Text header = new Text("     Bulletin Board\n"
                + "   date     to            from");
        Text exit = new Text("exit");
        Text more = new Text("more");
        HBox gapBox = new HBox(new Region());
        HBox navBox = new HBox(exit, more);
        navBox.setAlignment(Pos.BASELINE_CENTER);
        navBox.setSpacing(20);
        exit.setTextAlignment(TextAlignment.CENTER);
        TextFlow tf = new TextFlow();
        tf.setLineSpacing(-8);
        VBox box = new VBox(
                header,
                tf,
                gapBox,
                navBox
        );
        box.setSpacing(0);
        box.getTransforms().add(new Scale(1.25, 1.0));
        box.setMinWidth(518);
        box.setPrefWidth(518);
        box.setMinHeight(200);
        box.setMaxHeight(200);
        box.setPadding(new Insets(0, 0, 0, 6));
        VBox.setVgrow(gapBox, Priority.ALWAYS);

        buildMessageList(tf);

        getChildren().add(box);
    }

    private void buildMessageList(TextFlow tf) {
        LOGGER.log(Level.FINER, "Build BBS Message List");
        ArrayList<BbsMessage> articles = gameState.bbs;
        for (int i = 0; i < NUM_MESSAGES; i++) {
            if (i + messageIndex < articles.size()) {
                BbsMessage article = articles.get(i + messageIndex);
                if (article.show) {
                    tf.getChildren().add(new Text((i + 1) + ". " + article.toListString() + "\n"));
                }
            }
        }
        numMessages = tf.getChildren().size();
        LOGGER.log(Level.FINER, "Num Messages: {0}", numMessages);
    }

    private void showMessage(BbsMessage message) {
        getChildren().clear();
        mode = Mode.MESSAGE;
        Text heading = new Text(message.date);
        Text toText = new Text("TO: " + message.to + "\n");
        Text fromtext = new Text("FROM: " + message.from + "\n");
        TextFlow tf = new TextFlow(toText, fromtext, new Text(message.body));
        tf.setPrefWidth(450);
        tf.setLineSpacing(-8);

        ScrollPane sp = new ScrollPane(tf);
        sp.setPrefSize(470, 176);
        sp.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        sp.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);

        VBox box = new VBox(heading, sp);
        box.getTransforms().add(new Scale(1.33, 1.0));
        box.setMinSize(470, 204);
        box.setMaxSize(470, 204);
        box.setPadding(new Insets(0, 0, 0, 10));

        getChildren().add(box);
    }

    @Override
    public boolean handleEvent(KeyEvent ke) {
        switch (mode) {
            case LIST -> {
                try {
                    switch (ke.getCode()) {
                        case X -> {
                            return true;
                        }
                        case DIGIT1 -> {
                            showMessage(gameState.bbs.get(messageIndex));
                        }
                        case DIGIT2 -> {
                            showMessage(gameState.bbs.get(messageIndex + 1));
                        }
                        case DIGIT3 -> {
                            showMessage(gameState.bbs.get(messageIndex + 2));
                        }
                        case DIGIT4 -> {
                            showMessage(gameState.bbs.get(messageIndex + 3));
                        }
                        case DIGIT5 -> {
                            showMessage(gameState.bbs.get(messageIndex + 4));
                        }
                        case UP -> {
                            if (messageIndex > 0) {
                                messageIndex--;
                                LOGGER.log(Level.FINEST, "BBS Message Index: {0}/{1}", new Object[]{messageIndex, gameState.news.size()});
                                messageListPage();
                            }
                        }
                        case DOWN -> {
                            if (messageIndex < numAvailMessages() - NUM_MESSAGES) {
                                messageIndex++;
                                LOGGER.log(Level.FINEST, "BBS Message Index: {0}/{1}", new Object[]{messageIndex, gameState.news.size()});
                                messageListPage();
                            }
                        }
                    }
                } catch (IndexOutOfBoundsException ex) {
                    // Not a article at selected index, ignore.
                    LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
                }
            }
            case MESSAGE -> {
                // Up down keys
                // ESC and X
                switch (ke.getCode()) {
                    case ESCAPE, X -> {
                        messageListPage();
                    }
                    case UP -> {
                        // Scroll text
                    }
                    case DOWN -> {
                        // Scroll text
                    }
                }
            }
        }

        return false;
    }

    private int numAvailMessages() {
        int i = 0;
        for (BbsMessage a : gameState.bbs) {
            if (a.show) {
                i++;
            }
        }
        return i;
    }
}
