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
import javafx.scene.input.KeyCode;
import static javafx.scene.input.KeyCode.DIGIT1;
import static javafx.scene.input.KeyCode.DIGIT2;
import static javafx.scene.input.KeyCode.DIGIT3;
import static javafx.scene.input.KeyCode.DIGIT4;
import static javafx.scene.input.KeyCode.DIGIT5;
import static javafx.scene.input.KeyCode.DOWN;
import static javafx.scene.input.KeyCode.ESCAPE;
import static javafx.scene.input.KeyCode.S;
import static javafx.scene.input.KeyCode.UP;
import static javafx.scene.input.KeyCode.V;
import static javafx.scene.input.KeyCode.X;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.scene.transform.Scale;

/**
 *
 * @author Mark J Koch ( @maehem on GitHub )
 */
public class PaxBbsNode extends PaxNode {

    private static final int NUM_MESSAGES = 5;
    private Text toCaretText;
    private Text msgCaretText;

    private enum Mode {
        MENU, LIST, VIEW, SEND
    }

    private enum SendMode {
        TO, MESSAGE
    }

    private final ResourceManager resourceManager;

    private Mode mode = Mode.LIST;
    private SendMode sendMode = SendMode.TO;

    private int messageIndex = 0;
    private int numMessages = 0;
    private final StringBuilder typedTo = new StringBuilder();
    private final StringBuilder typedMessage = new StringBuilder();
    private final Text sendToText = new Text("");
    private final Text sendMsgText = new Text("");

    public PaxBbsNode(PaxNodeListener l, GameState gs, ResourceManager rm) {
        super(l, gs);
        this.resourceManager = rm;

        modeMenu();
    }

    private void modeMenu() {
        getChildren().clear();
        mode = Mode.MENU;

        messageIndex = 0;

        Text header = new Text("Bulletin Board");
        Text exitItem = new Text("X. Exit To Main\n");
        Text viewItem = new Text("V. View Messages\n");
        Text sendItem = new Text("S. Send Message\n");
        TextFlow menuItems = new TextFlow(
                exitItem,
                viewItem,
                sendItem
        );
        menuItems.setLineSpacing(-6);

        VBox box = new VBox(
                header,
                menuItems
        );
        box.getTransforms().add(new Scale(1.3, 1.0));
        box.setSpacing(8);
        box.setLayoutX(10);

        exitItem.setOnMouseClicked((t) -> {
            listener.paxNodeExit();
        });
        viewItem.setOnMouseClicked((t) -> {
            viewpage();
        });
        sendItem.setOnMouseClicked((t) -> {
            sendPage();
        });

        getChildren().add(box);
    }

    private void viewpage() {
        getChildren().clear();
        mode = Mode.LIST;

        Text header = new Text("Bulletin Board\n"
                + "       date to            from");
        Text previous = new Text("previous");
        previous.setVisible(messageIndex > 0);
        Text exit = new Text("exit");
        Text more = new Text("more");
        more.setVisible(messageIndex < numAvailMessages() - NUM_MESSAGES);
        HBox gapBox = new HBox(new Region());
        HBox navBox = new HBox(previous, exit, more);
        //navBox.setAlignment(Pos.BASELINE_CENTER);
        navBox.setSpacing(20);
        navBox.setPadding(new Insets(0, 0, 0, 130));
        //exit.setTextAlignment(TextAlignment.CENTER);
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

        previous.setOnMouseClicked((t) -> {
            handleEvent(new KeyEvent(KeyEvent.KEY_PRESSED, null, null, UP, true, true, true, true));
        });
        exit.setOnMouseClicked((t) -> {
            modeMenu();
        });
        more.setOnMouseClicked((t) -> {
            handleEvent(new KeyEvent(KeyEvent.KEY_PRESSED, null, null, DOWN, true, true, true, true));
        });
    }

    private void buildMessageList(TextFlow tf) {
        LOGGER.log(Level.FINER, "Build BBS Message List");
        ArrayList<BbsMessage> articles = gameState.bbs;
        for (int i = 0; i < NUM_MESSAGES; i++) {
            if (i + messageIndex < articles.size()) {
                BbsMessage article = articles.get(i + messageIndex);
                if (article.show) {
                    Text messageItem = new Text((i + 1) + ". " + article.toListString() + "\n");
                    tf.getChildren().add(messageItem);
                    final int n = i + messageIndex;
                    messageItem.setOnMouseClicked((t) -> {
                        showMessage(gameState.bbs.get(n));
                    });
                }
            }
        }
        numMessages = tf.getChildren().size();
        LOGGER.log(Level.SEVERE, "Num Messages: {0}", numMessages);
    }

    private void showMessage(BbsMessage message) {
        getChildren().clear();
        mode = Mode.VIEW;
        Text heading = new Text(message.date);
        Text back = new Text("back");
        HBox navBox = new HBox(back);
        navBox.setAlignment(Pos.BASELINE_CENTER);
        Text toText = new Text("TO: " + message.to + "\n");
        Text fromtext = new Text("FROM: " + message.from + "\n");
        TextFlow tf = new TextFlow(toText, fromtext, new Text(message.body));
        tf.setPrefWidth(450);
        tf.setLineSpacing(-8);

        ScrollPane sp = new ScrollPane(tf);
        sp.setPrefSize(470, 180);
        sp.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        sp.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);

        VBox box = new VBox(heading, sp, navBox);
        box.getTransforms().add(new Scale(1.33, 1.0));
        box.setMinSize(470, 202);
        box.setMaxSize(470, 202);
        box.setPadding(new Insets(0, 0, 0, 10));

        getChildren().add(box);

        back.setOnMouseClicked((t) -> {
            handleEvent(new KeyEvent(KeyEvent.KEY_PRESSED, null, null, X, true, true, true, true));
        });
    }

    private void sendPage() {
        getChildren().clear();
        mode = Mode.SEND;
        sendMode = SendMode.TO;

        Text header = new Text("     Send Message\nPress ESC when done");

        Text dateText = new Text("date:  " + gameState.getDateString() + "\n");
        Text toText = new Text("to  :  ");
        toCaretText = new Text("<");
        //Text msgText = new Text("");
        msgCaretText = new Text("");
        Text newLineText = new Text("\n");

        TextFlow tf = new TextFlow(dateText,
                toText, sendToText, toCaretText,
                newLineText,
                sendMsgText, msgCaretText);
        tf.setLineSpacing(-8);
        VBox box = new VBox(
                header,
                tf
        );
        box.setSpacing(8);
        box.getTransforms().add(new Scale(1.33, 1.0));
        box.setMinWidth(518);
        box.setPrefWidth(518);
        box.setMinHeight(200);
        box.setPadding(new Insets(0, 0, 0, 6));
        VBox.setVgrow(tf, Priority.ALWAYS);

        getChildren().add(box);

    }

    @Override
    public boolean handleEvent(KeyEvent ke) {
        switch (mode) {
            case MENU -> {
                switch (ke.getCode()) {
                    case X -> {
                        listener.paxNodeExit();
                    }
                    case V -> {
                        viewpage();
                    }
                    case S -> {
                        sendPage();
                    }
                }
            }
            case LIST -> {
                try { // TODO: Still need this try statment??
                    switch (ke.getCode()) {
                        case X -> {
                            listener.paxNodeExit();
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
                            if (messageIndex >= NUM_MESSAGES) {
                                messageIndex -= NUM_MESSAGES;
                                LOGGER.log(Level.FINEST, "BBS Message Index: {0}/{1}", new Object[]{messageIndex, gameState.news.size()});
                                viewpage();
                            }
                        }
                        case DOWN -> {
                            if (messageIndex < numAvailMessages() - NUM_MESSAGES) {
                                messageIndex += NUM_MESSAGES;
                                LOGGER.log(Level.FINEST, "BBS Message Index: {0}/{1}", new Object[]{messageIndex, gameState.news.size()});
                                viewpage();
                            }
                        }
                    }
                } catch (IndexOutOfBoundsException ex) {
                    // Not a article at selected index, ignore.
                    LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
                }
            }
            case VIEW -> {
                // Up down keys
                // ESC and X
                switch (ke.getCode()) {
                    case ESCAPE, X -> {
                        viewpage();
                    }
                    case UP -> {
                        // Scroll text
                    }
                    case DOWN -> {
                        // Scroll text
                    }
                }
            }
            case SEND -> {
                switch (ke.getCode()) {
                    case ESCAPE -> {
                        // Send message
                        sendMessage();
                        modeMenu();
                    }
                    case ENTER, TAB -> { // Toggle send sub-mode
                        if (sendMode.equals(SendMode.TO)) {
                            updateSendMode(SendMode.MESSAGE);
                        } else {
                            updateSendMode(SendMode.TO);
                        }
                    }
                    default -> {
                        if (ke.getCode().isDigitKey()
                                || ke.getCode().isLetterKey()
                                || ke.getCode().equals(KeyCode.BACK_SPACE)) {
                            // Typed value
                            switch (sendMode) {
                                case TO -> {
                                    handleTypedTo(ke);
                                }
                                case MESSAGE -> {
                                    handleTypedMsg(ke);
                                }
                            }
                        }
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

    private void updateSendMode(SendMode mode) {
        sendMode = mode;
        switch (mode) {
            case TO -> {
                toCaretText.setText("<");
                msgCaretText.setText("");
            }
            case MESSAGE -> {
                toCaretText.setText("");
                msgCaretText.setText("<");
            }
        }
    }
    private void handleTypedTo(KeyEvent ke) {
        if (typedTo.length() < 12 && ke.getCode().isLetterKey()) {
            LOGGER.log(Level.FINEST, "Typed: {0}", ke.getText());
            typedTo.append(ke.getText());
            sendToText.setText(typedTo.toString());

        } else if (typedTo.length() > 0 && ke.getCode().equals(KeyCode.BACK_SPACE)) {
            LOGGER.log(Level.FINEST, "Backspace.");
            typedTo.delete(typedTo.length() - 1, typedTo.length());
            sendToText.setText(typedTo.toString());
        } else if (ke.getCode().equals(KeyCode.ENTER)) {
            // Change to msgText entry

        }
    }

    private void handleTypedMsg(KeyEvent ke) {
        if (typedMessage.length() < 255 && ke.getCode().isLetterKey()) {
            LOGGER.log(Level.FINEST, "Typed: {0}", ke.getText());
            typedMessage.append(ke.getText());
            sendMsgText.setText(typedMessage.toString());

        } else if (typedMessage.length() > 0 && ke.getCode().equals(KeyCode.BACK_SPACE)) {
            LOGGER.log(Level.FINEST, "Backspace.");
            typedMessage.delete(typedMessage.length() - 1, typedMessage.length());
            sendMsgText.setText(typedMessage.toString());
        } else if (ke.getCode().equals(KeyCode.ENTER)) {
            // Change to msgText entry

        }
    }

    private void sendMessage() {
        BbsMessage message = new BbsMessage(
                gameState.getDateString(),
                typedTo.toString(),
                gameState.name,
                typedMessage.toString(),
                true
        );

        ArrayList<BbsMessage> bbs = gameState.bbs;
        int index = 0;
        for (int i = 0; i < bbs.size(); i++) {
            if (bbs.get(i).show) {
                index = i;
            }
        }
        bbs.add(index + 1, message);

        LOGGER.log(Level.SEVERE, "Message sent to: {0}", typedTo);
        // Check if to armitage and contains BAMA id.
    }
}
