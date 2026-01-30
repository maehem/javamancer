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
package com.maehem.javamancer.neuro.view.cyberspace;

import com.maehem.javamancer.neuro.model.GameState;
import com.maehem.javamancer.neuro.model.database.Database;
import com.maehem.javamancer.neuro.model.item.DeckItem;
import java.util.logging.Level;
import javafx.scene.image.ImageView;
import javafx.scene.transform.Scale;

/**
 *
 * @author Mark J Koch ( @maehem on GitHub )
 */
public class ExploreGridPane extends GridPane {

    // DB position multiplier.
    // Was easier to make this lookup table than figure out the math.
    private final double[] multiX = new double[]{
        2.95, // 0
        2.70,
        2.45,
        2.25,
        2.1, // 4
        2.0,
        1.90,
        1.82,
        1.78 // 8
    };
    private final double[] multiY = new double[]{
        50, // 0
        46,
        34,
        24,
        16, // 4
        12,
        8,
        4,
        0 // 8
    };
    private final int[] dbShow = new int[]{
        0, 0, 0, 1, 1, 1, 2, 2, 2
    };

    private final ImageView cspace;
    private final ImageView[] gridLR;
    private final ImageView[] gridFB;
    private final ImageView[][] dbThing;
    private final ImageView gridbase;
    private final GridSequence gridRight;
    private final GridSequence gridLeft;
    private final GridSequence gridForward;
    private final GridSequence gridBackward;
    private final ImageStack[] database = new ImageStack[3];
    private final ImageStack gridLRPane;
    private final ImageStack gridFBPane;

    public enum Direction {
        LEFT, RIGHT, FORWARD, BACKWARD
    }

    public ExploreGridPane(GameState gs) {
        super(gs);
        // Sky Backdrop
        this.cspace = new ImageView(resourceManager.getSprite("CSPACE_1"));
        // Base Grid Floor
        this.gridbase = new ImageView(resourceManager.getSprite("GRIDBASE_1"));
        gridbase.setLayoutY(HORIZON);

        this.dbThing = new ImageView[][]{
            {
            new ImageView(resourceManager.getSprite("DBSPR_1")), // Close
            new ImageView(resourceManager.getSprite("DBSPR_2")), // Mid
            new ImageView(resourceManager.getSprite("DBSPR_3"))
            },
            {
            new ImageView(resourceManager.getSprite("DBSPR_1")), // Close
            new ImageView(resourceManager.getSprite("DBSPR_2")), // Mid
            new ImageView(resourceManager.getSprite("DBSPR_3"))
            },
            {
            new ImageView(resourceManager.getSprite("DBSPR_1")), // Close
            new ImageView(resourceManager.getSprite("DBSPR_2")), // Mid
            new ImageView(resourceManager.getSprite("DBSPR_3"))
            }
        };// Far
        
        this.gridLR = new ImageView[]{
            new ImageView(resourceManager.getSprite("GRIDS_1")),
            new ImageView(resourceManager.getSprite("GRIDS_2")),
            new ImageView(resourceManager.getSprite("GRIDS_3"))};
        this.gridFB = new ImageView[]{
            new ImageView(resourceManager.getSprite("GRIDS_4")),
            new ImageView(resourceManager.getSprite("GRIDS_5")),
            new ImageView(resourceManager.getSprite("GRIDS_6"))};

        gridLRPane = new ImageStack(0, HORIZON, this.gridLR);
        gridFBPane = new ImageStack(0, HORIZON, this.gridFB);

        database[0] = new ImageStack(272, 92, dbThing[0]);
        database[1] = new ImageStack(272, 92, dbThing[1]);
        database[2] = new ImageStack(272, 92, dbThing[2]);
        
        database[0].show(-1); // Hide it.
        database[1].show(-1); // Hide it.
        database[2].show(-1); // Hide it.

        getChildren().addAll(cspace,
                gridbase,
                gridLRPane, gridFBPane,
                database[0],database[1],database[2]
        );

        gridRight = new GridSequence(gridLR, false, true);
        gridLeft = new GridSequence(gridLR, true, true);
        gridForward = new GridSequence(gridFB, true, false);
        gridBackward = new GridSequence(gridFB, false, false);

        getTransforms().add(new Scale(1.0, 1.14));
    }

    public final void animateTravel(Direction dir) {
        switch (dir) {
            case FORWARD -> {
                gridForward.start();
            }
            case BACKWARD -> {
                gridBackward.start();
            }
            case LEFT -> {
                gridLeft.start();
            }
            case RIGHT -> {
                gridRight.start();
            }
        }
    }

    private class GridSequence extends FrameSequence {

        private final boolean axisX;

        public GridSequence(ImageView[] frames, boolean reverse, boolean axisX) {
            super(frames, reverse, false);
            this.axisX = axisX;
        }

        @Override
        public void indexChanged(int index) {
            DeckItem deck = gameState.usingDeck;
            final int AMOUNT = GRID / 4;

            if (axisX) {
                int newX = deck.getCordX() + (isReverse() ? AMOUNT : -AMOUNT);
                if (newX <= 0) {
                    newX = GameState.GRID_MAX - GameState.GRID_SIZE;
                } else if (newX >= GameState.GRID_MAX ) {
                    newX = GameState.GRID_SIZE;
                }
                deck.setCordX(newX);
            } else {
                int newY = deck.getCordY() + (isReverse() ? AMOUNT : -AMOUNT);
                if (newY <= 0) {
                    newY = GameState.GRID_MAX - GameState.GRID_SIZE;
                } else if (newY >= GameState.GRID_MAX) {
                    newY = GameState.GRID_SIZE;
                }
                deck.setCordY(newY);
            }
            layoutDatabase();
        }

    }

    /**
     * Make Visible, Position and Size database based on proximity to current
     * location.
     *
     */
    private void layoutDatabase() {
        DeckItem deck = gameState.usingDeck;
        
        int xPos = deck.getCordX();
        int yPos = deck.getCordY();
        database[0].show(-1); // Hide DB
        database[1].show(-1); // Hide DB
        database[2].show(-1); // Hide DB
        int dbIndex = 0;
        for (int x = -12; x <= 12; x++) {
            for (int y = 0; y <= 8; y++) {
                //LOGGER.log(Level.SEVERE, "Scan Grid: {0},{1}", new Object[]{x, y});
                int xCord = xPos + (x * GRID / 4);
                int yCord = yPos + (y * GRID / 4);
                Database dbHere = gameState.dbList.whatsAt(
                        xPos + (x * GRID / 4),
                        yPos + (y * GRID / 4)
                );
                if (dbHere != null) {
                    LOGGER.log(Level.FINE, "There is a database nearby at: {0},{1} :: {2}", new Object[]{xCord, yCord, dbHere.name});
                    database[dbIndex].show(dbShow[y]);
                    database[dbIndex].setLayoutX(272 + x * GRID * multiX[y]);
                    database[dbIndex].setLayoutY(42 + multiY[y]);
                    dbIndex++;
                    if (x == 0 && y == 0) { // The DB is right here.
                        gameState.databaseArrived = true; // Handled by next game tick().
                        LOGGER.log(Level.SEVERE, "Arrived: {0},{1} :: {2}", new Object[]{xCord, yCord, dbHere.name});
                    } else {
                        LOGGER.log(Level.SEVERE, "Not a DB here: {0},{1}", new Object[]{x, y});
                    }
                    //x = 9; // End X loop
                    //break;
                }
            }
        }
    }

}
