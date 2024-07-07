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

import com.maehem.javamancer.resource.file.PNGWriter;
import java.io.File;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 *
 * @author Mark J Koch ( @maehem on GitHub )
 */
public class PNGTest extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        // Set the title of the Stage
        stage.setTitle("PNG Tester");

        Pane rootPane = new Pane();
        rootPane.setPrefSize(200, 200);
        Scene scene = new Scene(rootPane);  // Create the Scene
        stage.setScene(scene); // Add the scene to the Stage

        // Always keep the aspect ratio for any window size.
        final double aspectRatio = 4.0 / 3.0; // 4:3 old style aspect ratio

        stage.show(); // Display the Stage

        Image img = new Image("file://"
                +                System.getProperty("user.home")
                + File.separator + "Desktop"
                + File.separator + "app-icon.png"
        );

        File outFile = new File(
                System.getProperty("user.home") + File.separator
                + "Desktop" + File.separator
                + "test-out.png");

        PNGWriter pngWriter = new PNGWriter();
        pngWriter.write(outFile, img);

        stage.setOnCloseRequest((t) -> {
            Platform.exit();
        });
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
