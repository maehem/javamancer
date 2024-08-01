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
public enum TxhResource implements Resource {

    PAXCODES(0, 0x3C159, 0x018A),
    FTUSER(0, 0x3C2E3, 0x362),
    ROMCON0(0, 0x3C645, 0x014D),
    ROMCON1(0, 0x3C792, 0x010F),
    ROMCON2(0, 0x3C8A1, 0x00CD),
    ICE(1, 0x32762, 0x0451),
    AITALK(1, 0x31DCE, 0x059B),
    ENDGAME(1, 0x32369, 0x03F9),;

    public final int fileNum;
    public final int offset; // DOS long == Java int (32-bits)
    public final int size;

    private TxhResource(int fileNum, int offset, int size) {
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
    public int decompress(byte[] compressedData, byte[] destination) {
        return Huffman.decompress(compressedData, destination);
    }

    @Override
    public String getName() {
        return name();
    }
}
