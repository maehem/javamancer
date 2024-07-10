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
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.Tab;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

/**
 *
 * @author Mark J Koch ( @maehem on GitHub )
 */
public class PICTab extends Tab {

    private final ResourceBundle MSG;
    public final ListView<File> list = new ListView<>();

    public PICTab() {
        MSG = ResourceBundle.getBundle("i18n/Browser"); // Must be done after super() called.
        setClosable(false);
        setText(MSG.getString("PIC_TAB_LABEL"));

        AppProperties appProps = AppProperties.getInstance();
        File cacheFolder = appProps.getCacheFolder();
        File picFolder = new File(cacheFolder, "pic");
        File[] listFiles = picFolder.listFiles((File dir, String name) -> name.endsWith(".png"));
        ObservableList<File> items = FXCollections.observableArrayList(Arrays.asList(listFiles)).sorted();

        list.setItems(items);
        configCellFactory(list);

        VBox.setVgrow(list, Priority.ALWAYS);

        setContent(new VBox(list));
    }

    private void configCellFactory(ListView<File> list) {
        list.setCellFactory((p) -> {

            return new ListCell<>() {
                @Override
                protected void updateItem(File file, boolean empty) {
                    super.updateItem(file, empty);
                    if (empty || file == null) {
                        setText(null);
                    } else {
                        setText(file.getName());
                    }
                }

            };
        });
    }

}
