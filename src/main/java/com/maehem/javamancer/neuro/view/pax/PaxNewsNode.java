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

import com.maehem.javamancer.neuro.model.GameState;
import com.maehem.javamancer.neuro.model.NewsArticle;
import com.maehem.javamancer.neuro.view.ResourceManager;
import java.util.ArrayList;
import java.util.logging.Level;
import javafx.geometry.Pos;
import javafx.scene.control.ScrollPane;
import static javafx.scene.input.KeyCode.DIGIT1;
import static javafx.scene.input.KeyCode.DIGIT2;
import static javafx.scene.input.KeyCode.DIGIT3;
import static javafx.scene.input.KeyCode.DIGIT4;
import static javafx.scene.input.KeyCode.DIGIT5;
import static javafx.scene.input.KeyCode.DOWN;
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

/**
 *
 * @author Mark J Koch ( @maehem on GitHub )
 */
public class PaxNewsNode extends PaxNode {

    private int newsIndex = 0;
    private static int NUM_ARTICLES = 5;

    private final ResourceManager resourceManager;
    private int numArticles = 0;

    private enum Mode {
        LIST, ARTICLE
    }

    private Mode mode = Mode.LIST;

    public PaxNewsNode(PaxNodeListener l, GameState gs, ResourceManager rm) {
        super(l, gs);
        this.resourceManager = rm;

        newsListPage();
    }

    private void newsListPage() {

        getChildren().clear();
        mode = Mode.LIST;
        Text header = new Text("     Night City News\n"
                + "   date     subject");
        Text exit = new Text("exit");
        HBox gapBox = new HBox(new Region());
        HBox exitBox = new HBox(exit);
        exitBox.setAlignment(Pos.BASELINE_CENTER);
        exit.setTextAlignment(TextAlignment.CENTER);
        TextFlow tf = new TextFlow();
        tf.setLineSpacing(LINE_SPACING);
        addBox(header, tf, gapBox, exitBox);
//        VBox box = new VBox(
//                header,
//                tf,
//                gapBox,
//                exitBox
//        );
//        box.setSpacing(0);
//        box.getTransforms().add(new Scale(1.25, 1.0));
//        box.setMinWidth(518);
//        box.setPrefWidth(518);
//        box.setMinHeight(200);
//        box.setMaxHeight(200);
//        box.setPadding(new Insets(0, 0, 0, 6));
        VBox.setVgrow(gapBox, Priority.ALWAYS);

        buildNewsList(tf);

//        getChildren().add(box);

        exit.setOnMouseClicked((t) -> {
            listener.paxNodeExit();
        });
    }

    private void buildNewsList(TextFlow tf) {
        LOGGER.log(Level.FINER, "Build News List");
        ArrayList<NewsArticle> articles = gameState.news;
        for (int i = 0; i < NUM_ARTICLES; i++) {
            if (i + newsIndex < articles.size()) {
                NewsArticle article = articles.get(i + newsIndex);
                if (article.show) {
                    final int n = i;
                    Text lineItem = new Text((n + 1) + ". " + article.toListString() + "\n");
                    tf.getChildren().add(lineItem);
                    lineItem.setOnMouseClicked((t) -> {
                        showArticle(gameState.news.get(newsIndex + n));
                    });
                }
            }
        }
        numArticles = tf.getChildren().size();
        LOGGER.log(Level.FINER, "Num Articles: {0}", numArticles);
    }

    @Override
    public boolean handleEvent(KeyEvent ke) {
        switch (mode) {
            case LIST -> {
                try {
                    switch (ke.getCode()) {
                        case X -> {
                            listener.paxNodeExit();
                        }
                        case DIGIT1 -> {
                            showArticle(gameState.news.get(newsIndex));
                        }
                        case DIGIT2 -> {
                            showArticle(gameState.news.get(newsIndex + 1));
                        }
                        case DIGIT3 -> {
                            showArticle(gameState.news.get(newsIndex + 2));
                        }
                        case DIGIT4 -> {
                            showArticle(gameState.news.get(newsIndex + 3));
                        }
                        case DIGIT5 -> {
                            showArticle(gameState.news.get(newsIndex + 4));
                        }
                        case UP -> {
                            if (newsIndex > 0) {
                                newsIndex--;
                                LOGGER.log(Level.FINEST, "News Index: {0}/{1}", new Object[]{newsIndex, gameState.news.size()});
                                newsListPage();
                            }
                        }
                        case DOWN -> {
                            if (newsIndex < numAvailArticles() - NUM_ARTICLES) {
                                newsIndex++;
                                LOGGER.log(Level.FINEST, "News Index: {0}/{1}", new Object[]{newsIndex, gameState.news.size()});
                                newsListPage();
                            }
                        }
                    }
                } catch (IndexOutOfBoundsException ex) {
                    // Not a article at selected index, ignore.
                    LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
                }
            }
            case ARTICLE -> {
                // Up down keys
                // ESC and X
                switch (ke.getCode()) {
                    case ESCAPE, X -> {
                        newsListPage();
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

    private void showArticle(NewsArticle article) {
        getChildren().clear();
        mode = Mode.ARTICLE;
        Text heading = new Text(article.headline);
        Text back = new Text("back");
        HBox navBox = new HBox(back);
        navBox.setAlignment(Pos.BASELINE_CENTER);
        TextFlow tf = new TextFlow(new Text(article.body));
        tf.setPrefWidth(380);
        tf.setPrefHeight(140);
        tf.setLineSpacing(LINE_SPACING);

        ScrollPane sp = new ScrollPane(tf);
        sp.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        sp.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        VBox.setVgrow(sp, Priority.ALWAYS);

        addBox(heading, sp, navBox);
//        VBox box = new VBox(heading, sp, navBox);
//        //box.getTransforms().add(new Scale(1.33, 1.0));
//        box.setMinSize(470, 200);
//        box.setMaxSize(470, 200);
//        box.setPadding(new Insets(0, 0, 0, 10));
//
//        getChildren().add(box);

        back.setOnMouseClicked((t) -> {
            handleEvent(new KeyEvent(KeyEvent.KEY_PRESSED, null, null, X, true, true, true, true));
        });
    }

    private int numAvailArticles() {
        int i = 0;
        for (NewsArticle a : gameState.news) {
            if (a.show) {
                i++;
            }
        }
        return i;
    }
}
