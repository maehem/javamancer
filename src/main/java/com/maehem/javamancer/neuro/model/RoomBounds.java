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
 * zone defined above. Door positions: T(x1,x2), R(y1,y2),B(x1,x2),L(xy1,y2)
 * Doors rest on edge define above with opening defined by two numbers x1/x2 or
 * y1/y2.
 *
 * @author Mark J Koch ( @maehem on GitHub )
 */
public enum RoomBounds { // and Doors

    R1(10, 630, 60, 40, 0, 0, 0, 0, 500, 600, 0, 0);

    public final int tBound;
    public final int rBound;
    public final int bBound;
    public final int lBound;
    public final int tx1;
    public final int tx2;
    public final int ry1;
    public final int ry2;
    public final int bx1;
    public final int bx2;
    public final int ly1;
    public final int ly2;

    private RoomBounds(
            int tBound, int rBound, int bBound, int lBound,
            int tx1, int tx2, int ry1, int ry2, int bx1, int bx2, int ly1, int ly2
    ) {
        this.tBound = tBound;
        this.rBound = rBound;
        this.bBound = bBound;
        this.lBound = lBound;
        this.tx1 = tx1;
        this.tx2 = tx2;
        this.ry1 = ry1;
        this.ry2 = ry2;
        this.bx1 = bx1;
        this.bx2 = bx2;
        this.ly1 = ly1;
        this.ly2 = ly2;
    }
}
