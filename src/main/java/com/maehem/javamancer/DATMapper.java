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
package com.maehem.javamancer;

import com.maehem.javamancer.logging.Logging;
import com.maehem.javamancer.resource.file.AnhResource;
import com.maehem.javamancer.resource.file.BihResource;
import com.maehem.javamancer.resource.file.ImhResource;
import com.maehem.javamancer.resource.file.PicResource;
import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

/**
 *
 * @author Mark J Koch ( @maehem on GitHub )
 */
public class DATMapper extends Application {

    public static final Logger LOGGER = Logging.LOGGER;

    private final Pane contentPane = new Pane();
    private final ScrollPane rootPane = new ScrollPane(contentPane);
    private final int BYTE_W = 128;
    private final int BYTE_SIZE = 2; // Draw size in Pixels
    private AppProperties appProperties;
    private Color IMH_COLOR = Color.AQUA;
    private Color PIC_COLOR = Color.CORAL;
    private Color BIH_COLOR = Color.FORESTGREEN;
    private Color ANH_COLOR = Color.GOLDENROD;

    private int fileNum = 1;

    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle("DAT Mapper");

        appProperties = AppProperties.getInstance();

        rootPane.setPrefSize(512, 1200);
        Scene scene = new Scene(rootPane);  // Create the Scene
        stage.setScene(scene); // Add the scene to the Stage

        // Add icon for the app
        Image appIcon = new Image(getClass().getResourceAsStream("/icons/icon-circuit-heart.png"));
        stage.getIcons().add(appIcon);

        stage.show(); // Display the Stage

        File outFile = new File(
                System.getProperty("user.home") + File.separator
                + "Desktop" + File.separator
                + "dat-map.txt");

        stage.setOnCloseRequest((t) -> {
            Platform.exit();
        });

        map();
    }

    private void map() {
        File dat1 = appProperties.getDatFiles()[1];
        long fileLength = dat1.length();
        int w = BYTE_W * 2;
        int h = (int) (2 * fileLength / BYTE_W);
        LOGGER.log(Level.INFO, () -> "Map has " + (h / 2) + " rows. Image is 2X");
        WritableImage img = new WritableImage(w, h);
        PixelWriter pw = img.getPixelWriter();
        // Fill background
        for (int i = 0; i < w * h; i++) {
            pw.setColor(i % w, i / w, Color.WHITE);
        }

        fillIMHData(pw);
        fillPICData(pw);
        fillBIHData(pw);
        fillANHData(pw);

        contentPane.getChildren().add(new ImageView(img));

    }

    private void fillIMHData(PixelWriter pw) {
        ImhResource[] imhValues = ImhResource.values();
        for (ImhResource imh : imhValues) {
            if (imh.getFileNum() == fileNum) {
                fillThing(imh.getOffset(), imh.getSize(), IMH_COLOR, pw);
            }
        }
    }

    private void fillPICData(PixelWriter pw) {
        PicResource[] values = PicResource.values();
        for (PicResource resource : values) {
            if (resource.getFileNum() == fileNum) {
                fillThing(resource.getOffset(), resource.getSize(), PIC_COLOR, pw);
            }
        }
    }

    private void fillBIHData(PixelWriter pw) {
        BihResource[] values = BihResource.values();
        for (BihResource resource : values) {
            if (resource.getFileNum() == fileNum) {
                fillThing(resource.getOffset(), resource.getSize(), BIH_COLOR, pw);
            }
        }
    }

    private void fillANHData(PixelWriter pw) {
        AnhResource[] values = AnhResource.values();
        for (AnhResource resource : values) {
            if (resource.getFileNum() == fileNum) {
                fillThing(resource.getOffset(), resource.getSize(), ANH_COLOR, pw);
            }
        }
    }

    private void fillThing(int offset, int size, Color color, PixelWriter pw) {
        for (int i = 0; i < size; i++) {
            int iii = offset + i;
            int x = 2 * (iii % BYTE_W);
            int y = 2 * (iii / BYTE_W);
            if (x == 0) {
                //LOGGER.log(Level.SEVERE, "Y=" + y / 2);
            }

            pw.setColor(x, y, color);
            pw.setColor(x + 1, y, color);
            pw.setColor(x, y + 1, color);
            pw.setColor(x + 1, y + 1, color);
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
