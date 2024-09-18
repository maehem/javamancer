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
import com.maehem.javamancer.neuro.model.BbsMessage;
import com.maehem.javamancer.neuro.model.GameState;
import com.maehem.javamancer.neuro.model.Person;
import com.maehem.javamancer.neuro.model.TextResource;
import com.maehem.javamancer.neuro.model.database.Database;
import com.maehem.javamancer.neuro.model.warez.Warez;
import com.maehem.javamancer.neuro.view.PopupListener;
import com.maehem.javamancer.neuro.view.popup.PopupPane;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.KeyCode;
import static javafx.scene.input.KeyCode.ESCAPE;
import static javafx.scene.input.KeyCode.X;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
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

    private static final int UPLOAD_LIST_WIDTH = 420;
    private static final int UPLOAD_LIST_HEIGHT = 140;
    private static final int UPLOAD_LIST_X = 80;
    private static final int UPLOAD_LIST_Y = 234;

    private static final String CURSOR = "<";

    private final Text typedToText = new Text();
    private final Text typedMessageText = new Text();
    private final Text TO_CURSOR_TEXT = new Text(CURSOR);
    private final Text messageCursor = new Text(CURSOR);
    private final Text sendYN = new Text("\n        Send message? Y/N\n");
    private final Text instructions = new Text("\n\nPress ESC to end.");

    public final static String[] WANTED = {
        "Smuggling",
        "Software pandering",
        "Piracy",
        "Supercode programming"
    };
    private int uploadIndexPass = -1;
    private int uploadIndexFail = -1;
    private Class<? extends Warez> uploadClassPass;
    private int uploadClassVersion;
    private Text nameCursorText;
    private Text idCursorText;
    private Text nameValueText;
    private Text idValueText;

    // Cached in the event DB has a person list and player
    // views it for the first time.
    private Person editingPerson = null;
    private ArrayList<Person> personEditList = null;
    private int personEditHeading = -1;
    private String personEditResourceName = null;
    private boolean personEditIsWarrant = false;

    protected enum SubMode {
        LANDING, PASSWORD, CLEAR_WAIT, MAIN,
        MSG_LIST, MSG_SHOW, MSG_SEND,
        VIEW_TEXT,
        PERSON_LIST, PERSON_VIEW,
        UPLOAD, UPLOAD_DONE
    }

    private enum AccessText {
        NONE, DENIED, CLEARED_1, CLEARED_2
    }

    protected static final int CHAR_W = 39; // This many chars accross.
    protected static final double LINE_SPACING = -8.4;
    protected static final double TF_W = 420;
    protected static final Insets TF_PADDING = new Insets(8);
    private static final String PADDING = "\n          ";

    protected final TextResource dbTextResource;

    private final StringBuilder typedPassword = new StringBuilder();
    private final StringBuilder typedTo = new StringBuilder();
    private final StringBuilder typedMessage = new StringBuilder();

    protected final Text headingText = new Text();
    protected final Text enteredPasswordText = new Text();
    protected final Text accessStatusText = new Text();
    protected boolean accessDenied = false;

    protected final Text CONTINUE_TEXT = new Text(centeredText("    Button or [space] to continue."));

    protected final Database database;
    protected final GameState gameState;
    protected final Pane pane;
    protected final PopupListener listener;

    protected int accessLevel = 0;
    protected SubMode subMode = SubMode.LANDING;
    private int clearWait = 0;
    protected int messageIndex = 0;
    private final ArrayList<BbsMessage> visibleMessages = new ArrayList<>();
    private final ArrayList<BbsMessage> visibleMessages2 = new ArrayList<>();

    // Uploads
    private final Pane uploadSubPop = new Pane();
    private static final int SOFT_LIST_SIZE = 4;
    private int slotBase = 0; // Slot menu in groups of 4.

    protected DatabaseView(GameState gs, Pane p, PopupListener l) {
        this.database = gs.database;
        this.gameState = gs;
        this.pane = p;
        this.listener = l;

        initUploadSubPop();

        buildVisibleMessagesList();

        dbTextResource = gameState.resourceManager.getDatabaseText(gameState.database.number);

        if (!dbTextResource.isEmpty()) {
            headingText.setText(centeredText(dbTextResource.get(0)) + "\n\n");
        }

        setAccessText(AccessText.NONE);
    }

    protected abstract void landingPage();

    protected abstract void siteContent();

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
            case MSG_SEND -> {
                if (TO_CURSOR_TEXT.isVisible()) {
                    handleTypedBBSTo(keyEvent);
                } else if (messageCursor.isVisible()) {
                    handleTypedBBSMessage(keyEvent);
                } else if (sendYN.isVisible()) {
                    LOGGER.log(Level.SEVERE, "Handle Yes/No answer.");
                    // Y  or N
                    switch (code) {
                        case Y -> {
                            // Send the message.
                            sendMessage(typedTo.toString(), typedMessage.toString());
                            siteContent();
                        }
                        case N -> {
                            // Back to menu
                            siteContent();
                        }
                    }
                } else {
                    LOGGER.log(Level.SEVERE, "ERROR: Unknown Message Mode state!");
                }
            }
            case UPLOAD_DONE -> {
                switch (code) {
                    case KeyCode.SPACE -> {
                        keyEvent.consume();
                        siteContent();
                    }
                }
            }
            case PERSON_VIEW -> {
                if (handlePersonEditKeyEvent(keyEvent)) {
                    keyEvent.consume();
                    editablePersonList();
                }

                return false;
            }
        }

        return false;
    }

    public static DatabaseView getView(GameState gs, Pane p, PopupListener l) {
        Class<? extends DatabaseView> viewClass = DatabaseViewList.getViewClass(gs.database.getClass());
        try {
            Constructor<?> ctor = viewClass.getConstructor(new Class[]{
                GameState.class,
                Pane.class,
                PopupListener.class
            });
            Object object = ctor.newInstance(new Object[]{gs, p, l});
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

    protected final String centeredText(String str) {
        int pad = str.length() + (CHAR_W - str.length()) / 2;
        return String.format("%" + pad + "s", str);
    }

    /**
     * Text Flow with spacing, padding, not scaled.
     *
     * @param nodes
     * @return
     */
    protected TextFlow simpleTextFlow(Node... nodes) {
        TextFlow tf = new TextFlow(nodes);
        tf.setPadding(TF_PADDING);
        tf.setLineSpacing(LINE_SPACING);
        tf.setPrefWidth(TF_W);

        return tf;
    }

    protected TextFlow pageHeadingTextFlow(Node... node) {
        TextFlow tf = pageTextFlow(node);
        tf.getChildren().addFirst(headingText);

        return tf;
    }

    /**
     * TextFlow with scaling along with spacing and padding.
     *
     * @param node
     * @return
     */
    protected TextFlow pageTextFlow(Node... node) {
        TextFlow tf = simpleTextFlow(node);
        tf.getTransforms().add(new Scale(PopupPane.TEXT_SCALE, 1.0));

        return tf;
    }

    protected Text pageText(String text) {
        Text t = new Text(text);
        t.getTransforms().add(new Scale(1.5, 1.0));

        return t;
    }

    protected TextFlow pageTextScrolledFlow(Node header, Node node) {
        ScrollPane sp;
        if (node instanceof Text t) {
            TextFlow tf = new TextFlow(t);
            tf.setPadding(TF_PADDING);
            tf.setLineSpacing(LINE_SPACING);
            tf.setPrefWidth(TF_W - 10);
            sp = new ScrollPane(tf);
        } else {
            sp = new ScrollPane(node);
        }
        sp.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        sp.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        sp.setMinSize(420, 234);
        sp.setMaxSize(420, 234);

        TextFlow mainTf = pageTextFlow(header, new Text("\n"), sp);
        mainTf.setLineSpacing(LINE_SPACING);

        return mainTf;
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

        TextFlow tf = pageTextFlow(leadingText1, instructionsText,
                leadingText2, enteredPasswordText, cursorText,
                new Text("\n\n"), accessStatusText, // need blank Text() or FX has rendering issue.
                new Text("\n\n\n\n"), CONTINUE_TEXT
        );
        //tf.setPadding(TF_PADDING);
        //tf.setLineSpacing(LINE_SPACING);
        //f.setPrefWidth(TF_W);

        return tf;
    }

    protected void handleEnteredPassword(KeyEvent ke) {
        LOGGER.log(Level.FINEST, "Handle password key typed.");
        KeyCode code = ke.getCode();
        if (typedPassword.length() < 12
                && (code.isLetterKey() || code == KeyCode.SPACE)) {
            LOGGER.log(Level.FINEST, "Typed: {0}", ke.getText());
            typedPassword.append(ke.getText());
            enteredPasswordText.setText(typedPassword.toString());
        } else if (typedPassword.length() > 0 && code.equals(KeyCode.BACK_SPACE)) {
            LOGGER.log(Level.FINEST, "Backspace.");
            if (accessDenied) {
                typedPassword.setLength(0);
                accessDenied = false;
                setAccessText(AccessText.NONE);
            } else {
                typedPassword.delete(typedPassword.length() - 1, typedPassword.length());
            }
            enteredPasswordText.setText(typedPassword.toString());
        } else if (code.equals(KeyCode.ENTER)) {
            LOGGER.log(Level.SEVERE, "entered: {0}  passwords: {1}, {2}",
                    new Object[]{
                        typedPassword,
                        gameState.database.password1,
                        gameState.database.password2 == null ? "none" : gameState.database.password2
                    });
            // Check password valid.
            if (gameState.database.password1 != null
                    && gameState.database.password1.equals(typedPassword.toString())) {
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

    protected final void buildVisibleMessagesList() {
        // clear list
        visibleMessages.clear();
        database.bbsMessages.forEach((t) -> {
            if (t.show) {
                visibleMessages.add(t);
            }
        });
        // If there is a second list (Gentleman Loser)
        visibleMessages2.clear();
        database.bbsMessages2.forEach((t) -> {
            if (t.show) {
                visibleMessages2.add(t);
            }
        });
    }

    protected void messages() {
        messages(visibleMessages);
    }

    protected void messages2() {
        messages(visibleMessages2);
    }

    private void messages(ArrayList<BbsMessage> list) {
        LOGGER.log(Level.SEVERE, "{0}: Messages", database.name);
        pane.getChildren().clear();
        //ArrayList<BbsMessage> messageList = database.bbsMessages;
        //mode = Mode.MSG;
        TextFlow tf = pageHeadingTextFlow();
        if (!list.isEmpty() && list.get(0).to == null) {
            tf.getChildren().add(new Text("     date      subject\n"));
        } else {
            tf.getChildren().add(new Text("     date     to            from\n"));
        }

        list.size();
        for (int n = 0; n < 9; n++) {
            int nn = n + 1;
            try {
                BbsMessage msg = list.get(messageIndex + n);
                String nStr = String.format("%2d", nn);

                Text mmenuItem = new Text("\n " + nStr + ". " + msg.toListString(gameState.name));
                tf.getChildren().add(mmenuItem);
                mmenuItem.setOnMouseClicked((t) -> {
                    t.consume();
                    LOGGER.log(Level.SEVERE, "User clicks message: {0}", nn);
                    showMessage(msg);
                });
            } catch (IndexOutOfBoundsException ex) {
                break;
            }
        }

        Text exitText = new Text("eXit");
        exitText.setOnMouseClicked((t) -> {
            t.consume();
            siteContent();
        });
        Text prevText = new Text("    ");
        if (messageIndex > 0) {
            prevText.setText("prev");
            prevText.setOnMouseClicked((t) -> {
                LOGGER.log(Level.SEVERE, "User clicked messages prev.");
                messageIndex -= 9;
                t.consume();
                messages();
            });
        }
        Text nextText = new Text("    ");
        if (list.size() - (messageIndex + 9) > 0) {
            nextText.setText("next");
            nextText.setOnMouseClicked((t) -> {
                LOGGER.log(Level.SEVERE, "User clicked messages next.");
                messageIndex += 9;
                t.consume();
                messages();
            });
        }
        tf.getChildren().addAll(
                new Text("\n\n           "),
                prevText,
                new Text("    "),
                exitText,
                new Text("   "),
                nextText);

        pane.getChildren().add(tf);
    }

    protected void showMessage(BbsMessage msg) {
        LOGGER.log(Level.SEVERE, "{0}: Show Message {1}", new Object[]{msg.prefillIndex, database.name});
        pane.getChildren().clear();
        subMode = SubMode.MSG_SHOW;
        //mode = Mode.SHOW;
        //BbsMessage message = database.bbsMessages.get(n);

        TextFlow tf;
        if (msg.prefillIndex >= 0) {
            // Message is static from the game resources.
            tf = pageHeadingTextFlow(new Text(dbTextResource.get(msg.prefillIndex).replace("\1", gameState.name)));
        } else {
            // Message is user generated.
            tf = pageHeadingTextFlow(new Text(msg.body.replace("\1", gameState.name)));
        }

        pane.getChildren().add(tf);
        pane.setOnMouseClicked((t) -> {
            t.consume();
            pane.setOnMouseClicked(null);
            if (visibleMessages.contains(msg)) {
                messages();
            } else {
                messages2();
            }
        });
    }

    protected void downloads() {
        downloads(-1);
    }

    protected void downloads(int index) {
        LOGGER.log(Level.SEVERE, "{0}: Downloads", database.name);
        pane.getChildren().clear();
        //mode = Mode.DOWNLOADS;
        TextFlow tf = pageHeadingTextFlow();
        tf.getChildren().add(new Text(centeredText(" Software Library") + "\n"));
        if (index >= 0) {
            tf.getChildren().add(new Text("\n" + dbTextResource.get(index) + "\n"));
        }
        Text menuItem = new Text(PADDING + "X. Exit to main");
        tf.getChildren().add(menuItem);
        menuItem.setOnMouseClicked((t) -> {
            t.consume();
            LOGGER.log(Level.SEVERE, "User exit to main: ");
            siteContent();
        });

        int i = 1;
        i = addSoftware(i, database.warez1, tf);
        i = addSoftware(i, database.warez2, tf);
        addSoftware(i, database.warez3, tf);

        pane.getChildren().add(tf);
    }

    protected Node kgbDownloads() {
        LOGGER.log(Level.SEVERE, "{0}: Downloads", database.name);
        pane.getChildren().clear();
        //mode = Mode.DOWNLOADS;
        TextFlow tf = pageHeadingTextFlow();
        tf.getChildren().add(new Text(centeredText(" Software Library") + "\n"));
        Text exitItem = new Text(PADDING + "X. Exit System");
        tf.getChildren().add(exitItem);

        int i = 1;
        i = addSoftware(i, database.warez1, tf);
        i = addSoftware(i, database.warez2, tf);
        addSoftware(i, database.warez3, tf);

        pane.getChildren().add(tf);

        return exitItem; // Add a mouseClick listener for menu exit.
    }

    /**
     * DB Site expects this Class of Warez and version number for Pass.
     *
     * @param clazz
     * @param version
     * @param indexPass
     * @param indexFail
     */
    protected void uploads(Class<? extends Warez> clazz, int version, int indexPass, int indexFail) {
        LOGGER.log(Level.SEVERE, "{0}: Uploads", database.name);
        this.uploadIndexPass = indexPass;
        this.uploadIndexFail = indexFail;
        this.uploadClassPass = clazz;
        this.uploadClassVersion = version;

        pane.getChildren().clear();
        //mode = Mode.DOWNLOADS;
        TextFlow tf = pageHeadingTextFlow();
        //tf.getChildren().add(new Text(centeredText("Upload Software") + "\n"));
        //Text menuItem = new Text(PADDING + "X. Exit to main");
        //tf.getChildren().add(menuItem);
//        menuItem.setOnMouseClicked((t) -> {
//            t.consume();
//            LOGGER.log(Level.SEVERE, "User exit to main: ");
//            siteContent();
//        });

        refreshUploadSubPopup(); // Contains exit link.

        pane.getChildren().add(uploadSubPop);

        int i = 1;
//        i = addSoftware(i, database.warez1, tf);
//        i = addSoftware(i, database.warez2, tf);
//        addSoftware(i, database.warez3, tf);

        // TODO Add software in Deck.
        pane.getChildren().add(tf);
    }

    private void initUploadSubPop() {
        uploadSubPop.setId("neuro-popup");
        uploadSubPop.setPrefSize(UPLOAD_LIST_WIDTH, UPLOAD_LIST_HEIGHT);
        uploadSubPop.setMinSize(UPLOAD_LIST_WIDTH, UPLOAD_LIST_HEIGHT);
        uploadSubPop.setMaxSize(UPLOAD_LIST_WIDTH, UPLOAD_LIST_HEIGHT);
        uploadSubPop.setLayoutX(UPLOAD_LIST_X);
        uploadSubPop.setLayoutY(UPLOAD_LIST_Y);
    }

    private void refreshUploadSubPopup() {
        final int TF_PAD = 20;

        LOGGER.log(Level.SEVERE, "Show Deck Upload Software Prompt");
        subMode = SubMode.UPLOAD;

        uploadSubPop.getChildren().clear();
        Text softwareHeading = new Text("          Warez");
        Text exitButton = new Text("exit");
        Text prevButton = new Text("prev");
        Text nextButton = new Text("next");
        //TextFlow tf = textFlow(softwareHeading);
        TextFlow tf = new TextFlow(softwareHeading);
        tf.setLineSpacing(LINE_SPACING);
        tf.setPrefSize(UPLOAD_LIST_WIDTH - 2 * TF_PAD, UPLOAD_LIST_HEIGHT - 10);
        tf.setPadding(new Insets(4, 0, 0, TF_PAD));

        HBox navBox = new HBox(prevButton, exitButton, nextButton);
        navBox.setSpacing(24);
        navBox.setPadding(new Insets(6, 0, 0, 32));

        for (int i = 0; i < SOFT_LIST_SIZE; i++) {
            try {
                Warez w = gameState.usingDeck.softwarez.get(slotBase + i);
                Text itemText = new Text("\n" + (i + 1) + ". " + w.getMenuString());
                tf.getChildren().add(itemText);

                // Add onMouseClick()
                itemText.setOnMouseClicked((t) -> {
                    uploadDone(uploadSoftware(w));
                });
            } catch (IndexOutOfBoundsException ex) {
                tf.getChildren().add(new Text("\n"));
            }
        }
        tf.getChildren().add(new Text("\n"));
        tf.getChildren().add(navBox);
        prevButton.setVisible(slotBase >= SOFT_LIST_SIZE);
        nextButton.setVisible(slotBase + SOFT_LIST_SIZE < gameState.usingDeck.softwarez.size());

        uploadSubPop.getChildren().add(tf);

        if (prevButton.isVisible()) {
            prevButton.setOnMouseClicked((t) -> {
                slotBase -= SOFT_LIST_SIZE;
                refreshUploadSubPopup();
            });
        }
        if (nextButton.isVisible()) {
            nextButton.setOnMouseClicked((t) -> {
                slotBase += SOFT_LIST_SIZE;
                t.consume();
                refreshUploadSubPopup();
            });
        }
        exitButton.setOnMouseClicked((t) -> {
            siteContent();

            LOGGER.log(Level.SEVERE, "Exit Uploads (via mouse click).");

            t.consume();
            listener.popupExit();
        });

    }

    private boolean uploadSoftware(Warez w) {
        // Compare the uploaded Warez to the required warez.
        return w.getClass().equals(uploadClassPass) && w.version == uploadClassVersion;
    }

    /**
     *
     * @param uploadOK DB wants this software.
     */
    private void uploadDone(boolean uploadOK) {
        subMode = SubMode.UPLOAD_DONE;
        pane.getChildren().clear();
        if (!onUploadDone(uploadOK)) {
            // not a duplicate upload.
            uploadOK = false; // Sub-class rejected upload. Probably duplicate.
        }
        String okMessage;
        if (uploadIndexPass >= 0 && uploadOK) {
            okMessage = dbTextResource.get(uploadIndexPass);
        } else if (uploadIndexFail >= 0 && !uploadOK) {
            okMessage = dbTextResource.get(uploadIndexFail);
        } else {
            okMessage = "\n\n";
        }

        Text thankYouText = new Text("\n" + okMessage);

        TextFlow tf = pageTextFlow(thankYouText,
                new Text("\n\n\n\n\n\n\n"), CONTINUE_TEXT
        );

        // TODO: CONTIMUE_TEXY set onClicked?
        pane.getChildren().add(tf);
    }

    /**
     * Override to handle event of software upload.
     *
     * @param uploadOK
     */
    protected boolean onUploadDone(boolean uploadOK) {
        // Override in sub-class if needed.
        return false;
    }

    private int addSoftware(int i, HashMap<Class< ? extends Warez>, Integer> map, TextFlow tf) {
        // TODO: Filter based on accessLevel.
        for (Map.Entry<Class<? extends Warez>, Integer> m : map.entrySet()) {
            try {
                Warez w = m.getKey().getConstructor(int.class).newInstance(m.getValue());
                Text menuItem = new Text(PADDING + i + ". " + w.getMenuString());
                tf.getChildren().add(menuItem);
                menuItem.setOnMouseClicked((t) -> {
                    t.consume();
                    LOGGER.log(Level.SEVERE, "User clicks download: {0}", w.getMenuString());
                    attemptSoftwareDownload(w);
                });
                i++;
            } catch (NoSuchMethodException | SecurityException
                    | InstantiationException | IllegalAccessException
                    | IllegalArgumentException | InvocationTargetException ex) {
                LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
                ex.printStackTrace();
            }
        }

        return i;
    }

    private void attemptSoftwareDownload(Warez w) {
        LOGGER.log(Level.SEVERE, "Software Download: Attempt to download: {0}", w.getSimpleName());

        // TODO: Deck software compatibility check
        // RESULT = deck.installSoftware( Warez )
        gameState.usingDeck.softwarez.add(w);
    }

    protected void composeMessage() {
        LOGGER.log(Level.SEVERE, "{0}: Compose BBS Message", database.name);
        pane.getChildren().clear();
        subMode = SubMode.MSG_SEND;

        Text dateText = new Text("\nDate: " + gameState.getDateString());
        Text fromText = new Text("\nFrom: " + gameState.name);
        Text toLabel = new Text("\n  To: ");
        typedToText.setText(null);

        Text bodyStart = new Text("\n\n");
        typedMessageText.setText("");

        TextFlow tf = pageHeadingTextFlow(
                dateText, fromText, toLabel, typedToText, TO_CURSOR_TEXT,
                bodyStart, typedMessageText, messageCursor,
                instructions, sendYN
        );

        sendYN.setVisible(false);
        TO_CURSOR_TEXT.setVisible(true);
        messageCursor.setVisible(false);

        pane.getChildren().add(tf);
        pane.setOnMouseClicked((t) -> {
            t.consume();
            siteContent();
        });
    }

    protected void handleTypedBBSTo(KeyEvent ke) {
        LOGGER.log(Level.FINEST, "Handle message 'to' typed.");
        KeyCode code = ke.getCode();
        if (typedTo.length() < GameState.NAME_LEN_MAX
                && (code.isLetterKey() || code.isDigitKey())) {
            LOGGER.log(Level.FINEST, "Typed: {0}", ke.getText());
            typedTo.append(ke.getText());
            typedToText.setText(typedTo.toString());
        } else if (typedMessage.length() > 0
                && ke.getCode().equals(KeyCode.BACK_SPACE)) {
            LOGGER.log(Level.FINEST, "Backspace.");
            typedTo.delete(typedTo.length() - 1, typedTo.length());
            typedToText.setText(typedTo.toString());
        } else if (ke.getCode().equals(KeyCode.ENTER)) { // Done with To, now Body field
            LOGGER.log(Level.SEVERE, "Message 'to' complete ==> {0}",
                    new Object[]{typedTo});
            TO_CURSOR_TEXT.setVisible(false);
            messageCursor.setVisible(true);
            //messageCursor.setText(CURSOR);
        }
    }

    protected void handleTypedBBSMessage(KeyEvent ke) {
        LOGGER.log(Level.FINEST, "Handle message body typed.");
        KeyCode code = ke.getCode();
        if (typedMessage.length() < 160
                && (code.isLetterKey() || code.isDigitKey()
                || code.isWhitespaceKey()
                || code.equals(KeyCode.PERIOD)
                || code.equals(KeyCode.COMMA)
                || code.equals(KeyCode.SEMICOLON)
                || code.equals(KeyCode.QUOTE)
                || code.equals(KeyCode.QUOTEDBL)
                || code.equals(KeyCode.ENTER)
                || code.equals(KeyCode.DOLLAR)
                || code.equals(KeyCode.POUND)
                || code.equals(KeyCode.EXCLAMATION_MARK)
                || code.equals(KeyCode.AMPERSAND)
                || code.equals(KeyCode.ASTERISK)
                || code.equals(KeyCode.OPEN_BRACKET)
                || code.equals(KeyCode.CLOSE_BRACKET)
                || code.equals(KeyCode.SLASH)
                || code.equals(KeyCode.BRACELEFT)
                || code.equals(KeyCode.BRACERIGHT)
                || code.equals(KeyCode.MINUS)
                || code.equals(KeyCode.EQUALS)
                || code.equals(KeyCode.PLUS)
                || code.equals(KeyCode.UNDERSCORE)
                || code.equals(KeyCode.RIGHT_PARENTHESIS)
                || code.equals(KeyCode.LEFT_PARENTHESIS)
                || code.equals(KeyCode.CIRCUMFLEX)
                || "~".equals(ke.getCharacter())
                || "%".equals(ke.getCharacter())
                || code.equals(KeyCode.ENTER))) {
            LOGGER.log(Level.FINEST, "Typed: {0}", ke.getText());
            typedMessage.append(ke.getText());
            typedMessageText.setText(typedMessage.toString());
        } else if (typedMessage.length() > 0
                && ke.getCode().equals(KeyCode.BACK_SPACE)) {
            LOGGER.log(Level.FINEST, "Backspace.");
            typedMessage.delete(typedMessage.length() - 1, typedMessage.length());
            typedMessageText.setText(typedMessage.toString());
        } else if (ke.getCode().equals(KeyCode.ESCAPE)) { // Send message
            LOGGER.log(Level.SEVERE, "Ready to send message.");
            instructions.setVisible(false);
            messageCursor.setVisible(false);
            sendYN.setVisible(true);
            pane.requestLayout();
        }
        ke.consume();
    }

    /**
     * So far, we never send a message in bbsMessages2, so we don't worry about
     * it, yet.
     *
     * @param to
     * @param message
     */
    protected BbsMessage sendMessage(String to, String message) {
        LOGGER.log(Level.SEVERE, "Send Message:");
        String refinedMessage = "To: " + to + "\nFrom: \1\n" + message;
        BbsMessage msg = new BbsMessage(database.number, gameState.getDateString(), to, "\1", refinedMessage, true);
        database.bbsMessages.add(msg);
        gameState.messageSent.add(msg);
        buildVisibleMessagesList();

        return msg;
    }

    protected void enableMessage(BbsMessage item) {
        database.enableMessage(item);
        buildVisibleMessagesList();
    }

    protected void viewText(int index) {
        viewText(index, -1, -1, -1);
    }

    protected void viewText(int index1, int index2) {
        viewText(index1, index2, -1, -1);
    }

    protected void viewText(int index1, int index2, int index3) {
        viewText(index1, index2, index3, -1);
    }

    protected void viewText(int index1, int index2, int index3, int index4) {
        LOGGER.log(Level.SEVERE, "View Text Resource: {0}", new Object[]{index1, index2, index3});
        pane.getChildren().clear();
        subMode = SubMode.VIEW_TEXT;

        Text text = new Text(dbTextResource.get(index1));
        TextFlow contentFlow = simpleTextFlow(text);

        if (index2 >= 0) {
            contentFlow.getChildren().add(new Text(dbTextResource.get(index2)));
        }
        if (index3 >= 0) {
            contentFlow.getChildren().add(new Text(dbTextResource.get(index3)));
        }
        if (index4 >= 0) {
            contentFlow.getChildren().add(new Text(dbTextResource.get(index4)));
        }

        TextFlow pageTf = pageTextScrolledFlow(headingText, contentFlow);

        pane.getChildren().add(pageTf);
        pane.setOnMouseClicked((t) -> {
            t.consume();
            siteContent();
        });
    }

    /**
     * Read only person list.
     *
     * @param headingIndex
     * @param resourceName
     * @param isWarrant
     */
    protected void personList(int headingIndex, String resourceName, boolean isWarrant) {
        editablePersonList(null, headingIndex, resourceName, isWarrant);
    }

    /**
     * Editable Person List
     *
     * @param list
     * @param headingIndex
     * @param resourceName
     * @param isWarrant
     */
    protected void editablePersonList(ArrayList<Person> list, int headingIndex, String resourceName, boolean isWarrant) {
        LOGGER.log(Level.FINE, "Database View: Person list");
        // Cache the values
        this.personEditList = list;
        this.personEditHeading = headingIndex;
        this.personEditResourceName = resourceName;
        this.personEditIsWarrant = isWarrant;

        editablePersonList();
    }

    private void editablePersonList() {
        LOGGER.log(Level.FINE, "Database View: Person list (cached)");
        pane.getChildren().clear();
        subMode = SubMode.PERSON_LIST;

        Text subHeadingText;
        if (personEditHeading >= 0) {
            subHeadingText = new Text(dbTextResource.get(personEditHeading) + "\n\n");
        } else {
            subHeadingText = new Text();
        }

        TextFlow contentTf = simpleTextFlow(subHeadingText);
        contentTf.setPadding(new Insets(0, 0, 0, 30));

        TextResource bamaList = gameState.resourceManager.getTextResource(personEditResourceName);
        // TODO:  If supplied person list is empty. Copy the bamaList over and use that.
        if (personEditList != null) {
            if (personEditList.isEmpty()) {
                LOGGER.log(Level.FINER, "Person List cache is empty. Let's fill it.");
                // Copy text resource into gameState list.
                for (String item : bamaList) {
                    personEditList.add(new Person(item));
                }
            }
            int i = 1;
            for (Person p : personEditList) {
                String reasonStr = " ";
                if (personEditIsWarrant) {
                    reasonStr += WANTED[p.getReason()];
                }
                Text t = new Text("\n" + i + ". "
                        + String.format("%-18s  ", p.getName().trim())
                        + String.format("%-9s  ", p.getBama())
                        + reasonStr
                );

                t.setOnMouseClicked((ev) -> {
                    // Item details/editing.
                    LOGGER.log(Level.FINER, "User clicked: {0}", p.getName());
                    ev.consume();
                    editingPerson = p;
                    personEdit();
                });

                contentTf.getChildren().add(t);
                i++;
            }
        } else { // Read only list just print it.
            int i = 0;
            for (String item : bamaList) {
                String[] split = item.split("\t");
                int reason = split[2].charAt(3);
                String reasonStr;
                if (personEditIsWarrant) {
                    reasonStr = " " + WANTED[reason];
                } else {
                    reasonStr = "";
                }
                Text t = new Text("\n" + split[0] + split[1] + reasonStr);

                contentTf.getChildren().add(t);
                i++;
            }
        }

        TextFlow pageTf = pageTextFlow(headingText, contentTf);

        contentTf.requestLayout(); // List items wouldn't show up without this.

        pane.getChildren().add(pageTf);
        pane.setOnMouseClicked((t) -> {
            t.consume();
            siteContent();
        });
    }

    private void personEdit() {
        LOGGER.log(Level.SEVERE, "Database View: Edit Person");
        pane.getChildren().clear();
        subMode = SubMode.PERSON_VIEW;

        Text subHeadingText;
        if (personEditHeading >= 0) {
            subHeadingText = new Text(dbTextResource.get(personEditHeading) + "\n\n");
        } else {
            subHeadingText = new Text("\n\n");
        }

        TextFlow contentTf = simpleTextFlow(subHeadingText);
        contentTf.setPadding(new Insets(0, 0, 0, 30));
        TextFlow pageTf = pageTextFlow(headingText, contentTf);

        Text nameLabelText = new Text("Name: ");
        Text idLabelText = new Text("\n  ID: ");
        nameValueText = new Text(editingPerson.getName().trim());
        idValueText = new Text(editingPerson.getBama().trim());
        Text auxDataText = new Text("\nAux Data Text -- TODO");

        nameCursorText = new Text(CURSOR);
        idCursorText = new Text(CURSOR);
        Platform.runLater(() -> {
            nameCursorText.setVisible(false);
            idCursorText.setVisible(false);
        });

        contentTf.getChildren().addAll(
                nameLabelText, nameValueText, nameCursorText,
                idLabelText, idValueText, idCursorText,
                auxDataText
        );

        // NEED Exit and Edit links
        Text leftPadText = new Text("\n\n\n\n\n\n\n            ");
        Text exitLinkText = new Text("exit");
        Text gapText = new Text("  ");
        Text editLinkText = new Text("edit");

        contentTf.getChildren().addAll(leftPadText, exitLinkText, gapText, editLinkText);

        exitLinkText.setOnMouseClicked((t) -> {
            t.consume();
            editablePersonList();
        });

        editLinkText.setOnMouseClicked((t) -> {
            LOGGER.log(Level.SEVERE, "Editing user name: " + editingPerson.getName());
            t.consume();
            nameCursorText.setVisible(true);

        });

        pane.getChildren().add(pageTf);
    }

    private boolean handlePersonEditKeyEvent(KeyEvent ke) {
        LOGGER.log(Level.SEVERE, "Pane Key Pressed. Mode is: {0}", nameCursorText.isVisible() ? "NAME" : "ID");
        ke.consume();
        if (nameCursorText.isVisible()) {
            KeyCode code = ke.getCode();
            LOGGER.log(Level.SEVERE, "Name Field something typed: {0}", code.getChar());
            if (code == KeyCode.ENTER) {
                nameCursorText.setVisible(false);
                // Move to ID field
                idCursorText.setVisible(true);
            } else if (code == KeyCode.BACK_SPACE) {
                if (!nameValueText.getText().isEmpty()) {
                    String txt = nameValueText.getText();
                    nameValueText.setText(txt.substring(0, txt.length() - 1));
                }
            } else if (Person.isValidNameCharacter(code.getChar().getBytes()[0])) {
                if (nameValueText.getText().length() < Person.MAX_NAME_LENGTH) {
                    nameValueText.setText(nameValueText.getText() + code.getChar());
                }
            } else {
                LOGGER.log(Level.SEVERE, "Not a valid character.");
            }
        } else if (idCursorText.isVisible()) {
            LOGGER.log(Level.SEVERE, "ID Field something typed.");
            KeyCode code = ke.getCode();
            if (code == KeyCode.ENTER) {
                nameCursorText.setVisible(false);
                idCursorText.setVisible(false);
                // Finish edit value
                // Collect name and ID value
                editingPerson.setName(nameValueText.getText().trim().toUpperCase());
                editingPerson.setBama(idValueText.getText());
                //pane.setOnKeyPressed(null); // No more typing.
                // Do exit of edit mode. Back to list view.
                LOGGER.log(Level.SEVERE, "Person changes recorded. Reload list.");
                return true;
            } else if (code == KeyCode.BACK_SPACE) {
                if (!idValueText.getText().isEmpty()) {
                    String txt = idValueText.getText();
                    idValueText.setText(txt.substring(0, txt.length() - 1));
                }
            } else if (code.isDigitKey()) {
                if (idValueText.getText().length() < 9) {
                    idValueText.setText(idValueText.getText() + code.getChar());
                }
            }
        } else {
            LOGGER.log(Level.SEVERE, "Edit Person still has key events. How did we get here? Fix me.");
            //pane.setOnKeyPressed(null);
        }

        return false;
    }

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
