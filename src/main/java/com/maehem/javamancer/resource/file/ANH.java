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
public enum ANH implements Resource {

    R1(0, 0x1934, 0x2E5),
    R2(0, 0x3993, 0x340),
    R4(0, 0x8B90, 0x58F),
    R6(0, 0xBD12, 0xD9C),
    R8(0, 0x10A46, 0x1BC),
    R11(0, 0x15F2A, 0x372),
    R12(0, 0x1861D, 0xB68),
    R19(0, 0x24F99, 0x1E33),
    R22(0, 0x29DFE, 0xA9),
    R24(0, 0x2E4B6, 0xB9C),
    R26(0, 0x32575, 0xB8B),
    R27(0, 0x34C29, 0x232),
    R29(1, 0x34170, 0x10A),
    R34(1, 0x38A3C, 0x420),
    R36(1, 0x3BB7A, 0x566),
    R41(1, 0x4191D, 0x13E),
    R44(1, 0x44101, 0x3CE),
    R45(1, 0x45C51, 0x489),
    R50(1, 0x4C436, 0x6D4),
    R52(1, 0x4FC43, 0x31B),
    R53(1, 0x50CE4, 0x74B);

    public final int fileNum;
    public final int offset; // DOS long == Java int (32-bits)
    public final int size;

    private ANH(int fileNum, int offset, int size) {
        this.fileNum = fileNum;
        this.offset = offset;
        this.size = size;
    }

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
    public void decompress(byte[] compressedData, byte[] destination) {
        Huffman.decompress(compressedData, destination);
    }
}
