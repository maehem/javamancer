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
package com.maehem.javamancer.resource.file;

/**
 *
 * @author Mark J Koch ( @maehem on GitHub )
 */
public enum GameSaveResource implements Resource {

    DATA;

    public final static String name = "SAVEGAME.SAV";
    public final static int fileNum = 0;
    public final static int offset = 0x3C96E;
    public final static int size = 0x2EE0;

    @Override
    public int getFileNum() {
        return fileNum;
    }

    @Override
    public int getOffset() {
        return offset;
    }

    @Override
    public int getSize() {
        return size;
    }

    @Override
    public int decompress(byte[] compressedData, byte[] destination) {
        System.arraycopy(compressedData, 0, destination, 0, destination.length);
        return destination.length;
    }

    @Override
    public String getName() {
        return name;
    }
}
