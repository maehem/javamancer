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
package com.maehem.javamancer.resource;

import com.maehem.javamancer.AppProperties;
import com.maehem.javamancer.JavamancerPane;
import com.maehem.javamancer.resource.view.ANHTab;
import com.maehem.javamancer.resource.view.BIHTab;
import com.maehem.javamancer.resource.view.IMHTab;
import com.maehem.javamancer.resource.view.PICTab;
import com.maehem.javamancer.root.*;
import java.util.ResourceBundle;
import java.util.logging.Level;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TabPane;
import javafx.scene.layout.HBox;

/**
 *
 * @author Mark J Koch ( @maehem on GitHub )
 */
public class BrowserPane extends JavamancerPane {

    public enum Action {
        DONE
    };

    public final ANHTab anhTab = new ANHTab();
    public final BIHTab bihTab = new BIHTab();
    public final IMHTab imhTab = new IMHTab();
    public final PICTab picTab = new PICTab();

    private final BrowserPaneListener listener;

    private final Button doneButton; //= new Button("Done");
    private final ResourceBundle MSG;

    public BrowserPane(BrowserPaneListener parent) {
        super();
        MSG = ResourceBundle.getBundle("i18n/Browser"); // Must be done after super() called.
        LOGGER.log(Level.CONFIG, MSG.getString("LOG_CREATE"));
        this.listener = parent;

        // Apply CSS style ID
        setId("root-opaque");

        doneButton = new Button(MSG.getString("BACK_BUTTON"));
        //doneButton.setId("browser-back-button"); // CSS ID

        HBox topPane = new HBox(doneButton);
        topPane.setPrefHeight(48);
        setTop(topPane);

        TabPane tabPane = new TabPane(imhTab, picTab, bihTab, anhTab);
        ContentPreviewPane contentPane = new ContentPreviewPane();
        SplitPane contentBox = new SplitPane(tabPane, contentPane);
        contentBox.setId("root-opaque");
        contentBox.setDividerPosition(0, 0.25);
        setCenter(contentBox);

        HBox bottomBox = new HBox();
        bottomBox.setAlignment(Pos.CENTER);
        setBottom(bottomBox);

        initListeners();

        picTab.list.getSelectionModel().selectedItemProperty().addListener(contentPane);
        imhTab.list.getSelectionModel().selectedItemProperty().addListener(contentPane);
        bihTab.list.getSelectionModel().selectedItemProperty().addListener(contentPane);
        anhTab.treeView.getSelectionModel().selectedItemProperty().addListener(contentPane);
    }

    private void initListeners() {
        doneButton.setOnAction((t) -> {
            listener.broswerActionPerformed(Action.DONE);
        });
    }

    @Override
    public void pushProperties(AppProperties appProperties) {
    }

    @Override
    public void pullProperties(AppProperties appProperties) {
    }

}
