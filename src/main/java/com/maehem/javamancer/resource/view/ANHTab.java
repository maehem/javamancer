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
package com.maehem.javamancer.resource.view;

import com.maehem.javamancer.AppProperties;
import java.io.File;
import java.util.Arrays;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Tab;
import javafx.scene.control.TreeCell;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

/**
 *
 * @author Mark J Koch ( @maehem on GitHub )
 */
public class ANHTab extends Tab {

    private final ResourceBundle MSG;
    public final TreeItem<File> rootNode = new TreeItem<>();
    public final TreeView<File> treeView = new TreeView<>(rootNode);

    public ANHTab() {
        MSG = ResourceBundle.getBundle("i18n/Browser"); // Must be done after super() called.
        setClosable(false);
        setText(MSG.getString("ANH_TAB_LABEL"));

        AppProperties appProps = AppProperties.getInstance();
        File cacheFolder = appProps.getCacheFolder();
        File anhFolder = new File(cacheFolder, "anh");
        File[] listFiles = anhFolder.listFiles((File dir, String name) -> (dir.isDirectory() && name.startsWith("R")));
        ObservableList<File> rItems = FXCollections.observableArrayList(Arrays.asList(listFiles)).sorted();

        treeView.setShowRoot(false);
        rootNode.setExpanded(true);
        configCellFactory(treeView);

        //list.setItems(items);
        rItems.forEach((file) -> {
            TreeItem<File> rItem = new TreeItem<>(file);
            rootNode.getChildren().add(rItem);
            processRoomItems(rItem);
        });

        VBox.setVgrow(treeView, Priority.ALWAYS);

        setContent(new VBox(treeView));
    }

    private void configCellFactory(TreeView<File> list) {
        list.setCellFactory((p) -> new TreeCell<>() {
            @Override
            protected void updateItem(File file, boolean empty) {
                super.updateItem(file, empty);
                if (empty || file == null) {
                    setText(null);
                } else {
                    setText(file.getName());
                }
            }
        });
    }

    private void processRoomItems(TreeItem<File> roomTree) {
        File roomFolder = roomTree.getValue();
        if (roomFolder.isDirectory()) { //entry1, entry2, etc.
            File[] listFiles = roomFolder.listFiles((File dir, String name) -> (dir.isDirectory() && name.startsWith("entry")));
            ObservableList<File> entryItems = FXCollections.observableArrayList(Arrays.asList(listFiles)).sorted();

            entryItems.forEach((file) -> {
                TreeItem<File> entryItem = new TreeItem<>(file);
                roomTree.getChildren().add(entryItem);
                processEntryItems(entryItem);
            });
        }
    }

    private void processEntryItems(TreeItem<File> entryTree) {
        File entryFolder = entryTree.getValue();
        if (entryFolder.isDirectory()) { // anim1, anim2, etc.
            File[] listFiles = entryFolder.listFiles((File dir, String name) -> (dir.isDirectory() && name.startsWith("anim")));
            ObservableList<File> animItems = FXCollections.observableArrayList(Arrays.asList(listFiles)).sorted();

            animItems.forEach((file) -> {
                TreeItem<File> animItem = new TreeItem<>(file);
                entryTree.getChildren().add(animItem);
                //processAnimItems(entryItem);
            });
        }
    }
}
