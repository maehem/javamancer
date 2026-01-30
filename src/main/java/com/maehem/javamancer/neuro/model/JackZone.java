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
package com.maehem.javamancer.neuro.model;

import static com.maehem.javamancer.logging.Logging.LOGGER;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Mark J Koch ( @maehem on GitHub )
 */
public enum JackZone {
    ZERO(0, 112, 80), // Cheap Hotel
    ONE(1, 416, 32), // Gentleman Loser
    TWO(2, 32, 176), // Hitachi?, Hosaka?, Musabori, Fuji
    THREE(3, 336, 128), // Bank of Berne Mgr. Office
    FOUR(4, 48, 304), // Sense/Net
    FIVE(5, 304, 304), // Bank Gemeinschaft
    SIX(6, 128, 432), // No Physical Jack. Use EasyRider.
    SEVEN(7, 384, 400),; // Villa Straylight

    public final int num;
    public final int x;
    public final int y;

    private JackZone(int num, int x, int y) {
        this.num = num;
        this.x = x;
        this.y = y;
    }

    /**
     * Look up JackZone from X/Y coordinates
     *
     * @param x coordinate
     * @param y coordinate
     * @return JackZone of coordinates.
     */
    public static final JackZone lookUp(int x, int y) {
        return numToZone((y%GameState.GRID_MAX-1) / 127 * 2 + (x%GameState.GRID_MAX-1) / 255);
    }

    /**
     * Return JackZone for supplied number (0-7)
     *
     * @param num JackZone number
     * @return JackZone for supplied number (0-7). Null if not in range.
     */
    public static final JackZone numToZone(int num) {
        for (JackZone jz : values()) {
            if (jz.num == num) {
                return jz;
            }
        }

        LOGGER.log(Level.SEVERE, "Zone not found for: {0}", num);
        return null;
    }
}
