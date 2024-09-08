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
package com.maehem.javamancer.neuro.model.warez;

import com.maehem.javamancer.logging.Logging;
import com.maehem.javamancer.neuro.model.GameState;
import com.maehem.javamancer.neuro.model.item.Item;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;


/**
 *
 * @author Mark J Koch ( @maehem on GitHub )
 */
public abstract class Warez {

    public static final Logger LOGGER = Logging.LOGGER;

    public static final String USE_OK = "OK";

    public final Item.Catalog item;
    public final int version;
    private boolean running;
    private boolean damaged;
    private EventHandler<ActionEvent> finishedHandler;

    public Warez(Item.Catalog catItem, int version) {
        this.item = catItem;
        this.version = version;
    }

    public String getMenuString() {
        return String.format("%-12s", item.itemName)
                + String.format("%2s", String.valueOf(version))
                + ".0";
    }

    public String use(GameState gs) {
        LOGGER.log(Level.SEVERE, "Warez: Use(): " + getClass().getSimpleName());
        return USE_OK;
    }

    public void setRunning(boolean running) {
        if (this.running && !running && finishedHandler != null) {
            finishedHandler.handle(new ActionEvent());
        }
        this.running = running;
    }

    public boolean isRunning() {
        return running;
    }

    public boolean isDamaged() {
        return damaged;
    }

    public void setDamaged(boolean value) {
        this.damaged = value;
    }

    /**
     * How long the software runs for before applying the effect.
     *
     * @return
     */
    public abstract int getRunDuration();

    /**
     * Effect amount to apply to target (usually ICE's constitution).
     *
     * @return
     */
    public abstract int getEffect(GameState gameState);

    public void setOnFinished(EventHandler<ActionEvent> handler) {
        this.finishedHandler = handler;
    }

    public String getSimpleName() {
        return item.itemName + " " + version + ".0";
    }

}
