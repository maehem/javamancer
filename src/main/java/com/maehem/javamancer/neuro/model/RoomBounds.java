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

/**
 * Top, Right, Bottom, Left of walk area. Player can walk inside a rectangle
 * zone defined above.
 *
 * Door positions: T(x,w), R(y,w),B(x,w),L(y,w) Doors rest on edge define above
 * with position defined by two numbers x or y and width defined by w.
 *
 * @author Mark J Koch ( @maehem on GitHub )
 */
public enum RoomBounds { // and Doors

    R1(184, 580, 228, 40, 0, 0, 0, 0, 436, 144, 0, 0);

    public enum Door {
        NONE, TOP, RIGHT, BOTTOM, LEFT
    };

    public final int tBound; // Upper walk Line
    public final int rBound; // Right Walk Line
    public final int bBound; // Bottom Walk Line
    public final int lBound; // Left Walk Line
    public final int tx; // Top Door x location. 0 = none.
    public final int tw; // Top Door width. 0 = none.
    public final int ry; // Right Door  y location.
    public final int rw; // Right Door width.
    public final int bx; // Bottom Door x location.
    public final int bw; // Bottom Door width.
    public final int ly; // Left Door y location.
    public final int lw; // Left Door width.

    private RoomBounds(
            int tBound, int rBound, int bBound, int lBound,
            int tx, int tw, int ry, int rw, int bx, int bw, int ly, int lw
    ) {
        this.tBound = tBound;
        this.rBound = rBound;
        this.bBound = bBound;
        this.lBound = lBound;
        this.tx = tx;
        this.tw = tw;
        this.ry = ry;
        this.rw = rw;
        this.bx = bx;
        this.bw = bw;
        this.ly = ly;
        this.lw = lw;
    }

    public static RoomBounds get(Room room) {
        for (RoomBounds rb : values()) {
            if (rb.name().equals(room.name())) {
                return rb;
            }
        }

        return null;
    }
}
