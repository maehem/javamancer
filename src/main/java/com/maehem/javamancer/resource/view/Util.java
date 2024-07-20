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

/**
 *
 * @author Mark J Koch ( @maehem on GitHub )
 */
public class Util {

    /*
    uint8_t DosPal[1024] = {
    0x00, 0x00, 0x00, 0x00, // black
    0x80, 0x00, 0x00, 0x00, // blue
    0x00, 0x80, 0x00, 0x00, // green
    0x80, 0x80, 0x00, 0x00, // cyan
    0x00, 0x00, 0x80, 0x00, // red
    0x80, 0x00, 0x80, 0x00, // magnetta
    0x00, 0x80, 0x80, 0x00, // brown
    0xC0, 0xC0, 0xC0, 0x00, // light gray
    0x80, 0x80, 0x80, 0x00, // dark gray
    0xFF, 0x00, 0x00, 0x00, // light blue
    0x00, 0xFF, 0x00, 0x00, // light green
    0xFF, 0xFF, 0x00, 0x00, // light cyan
    0x00, 0x00, 0xFF, 0x00, // light red
    0xFF, 0x00, 0xFF, 0x00, // light magnetta
    0x00, 0xFF, 0xFF, 0x00, // yellow
    0xFF, 0xFF, 0xFF, 0x00, // white
    0x00,
};
     */
    /**
     * Color Palette
     *
     * Stored as web color string red[23..16] grn:[15..0] blu:[7..0] For JavaFX
     * color creation with: Color.web(palette[index], double alpha)
     *
     * Raw color values, i.e. #FF0000 for red were found to appear too bright
     * and garish, so they were tuned to be more pleasing.
     */
    public static final String[] palette = {
        "0x000000", //black
        "0x0000B0", //blue
        "0x006600", //green
        "0x44AAAA", //cyan
        "0xAA2222", //red
        "0xAA22AA", //magenta
        "0xAA6622", //brown
        "0xBBBBBB", //light gray
        "0x505050", //dark gray
        "0x5555EE", //light blue
        "0x77DD77", //light green
        "0x99EEEE", //light cyan
        "0xFF6666", //light red
        "0xAA22AA", //light magenta
        "0xEEEE77", //yellow
        "0xF0F0F0" // white
    };

}
