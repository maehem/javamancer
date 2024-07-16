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

import com.maehem.javamancer.logging.Logging;
import java.util.HexFormat;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Mark J Koch ( @maehem on GitHub )
 */
public enum ImhResource implements Resource {

    CURSORS(0, 0x364EE, 0xF3),
    BUBBLES(0, 0x363F1, 0xFD),
    NEURO(0, 0x365E1, 0xA8B),
    SPRITES(0, 0x3706C, 0x25C3),
    TITLE(0, 0x395A8, 0x2BAB),
    AIP0(1, 0x1873B, 0x334),
    AIP1(1, 0x18A6F, 0x32A),
    AIP2(1, 0x18D99, 0x308),
    AIP3(1, 0x190A1, 0x2D2),
    AIP4(1, 0x19373, 0x335),
    AIP5(1, 0x196A8, 0x342),
    AIP6(1, 0x199EA, 0x373),
    AIP7(1, 0x19D5D, 0x2EA),
    AIP8(1, 0x1A047, 0x316),
    AIP9(1, 0x1A35D, 0x2F8),
    AIP10(1, 0x1A655, 0x3D2),
    AIP11(1, 0x1AA27, 0x335),
    CSDB(1, 0x1AD5C, 0x15BD),
    CSPACE(1, 0x1C319, 0x106D),
    CSPANEL(1, 0x1D386, 0x733),
    DBSPR(1, 0x1DAB9, 0x26B),
    ENDGAME(1, 0x1DD24, 0x3E92),
    GRIDBASE(1, 0x21BB6, 0x109B),
    GRIDS(1, 0x22C51, 0x6084),
    ICE(1, 0x28CD5, 0x22E1),
    SHOTS(1, 0x2AFB6, 0x55A),
    VIRUSICE(1, 0x2B510, 0x467F),
    VIRUSROT(1, 0x2FB8F, 0x223F);

    private static final Logger LOGGER = Logging.LOGGER;

    public final int fileNum;
    public final int offset; // DOS long == Java int (32-bits)
    public final int size;

    private ImhResource(int fileNum, int offset, int size) {
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
    public String getName() {
        return name();
    }

    @Override
    public int decompress(byte[] compressedData, byte[] destination) {
        byte mid[] = new byte[64000];

//	uint32_t len = huffman_decompress(src, imh);
        int len = Huffman.decompress(compressedData, mid);

//
//	return decode_imh(imh, len, dst);
        return decode(mid, len, destination);
    }

    private void nothing(byte[] mid, int len, byte[] destination) {

//      static int decode_imh(uint8_t *_src, uint32_t len, uint8_t *_dst)
//{
//	uint32_t total_len = 0;
        int totalLen = 0;

//	uint8_t *src = _src, *dst = _dst;
        int srcIdx = 0;
        int dstIdx = 0;

        int imhSize = 2 * 4; // 2 bytes times 4 values
//	imh_hdr_t *imh;
//
//	while (len)
//	{
//		uint32_t size = 0, processed = 0;
//
//		imh = (imh_hdr_t*)src;
//		size = imh->width * imh->height;
//
//		memmove(dst, src, sizeof(imh_hdr_t));
//
//		total_len += sizeof(imh_hdr_t) + size;
//		src += sizeof(imh_hdr_t);
//		dst += sizeof(imh_hdr_t);
//		len -= sizeof(imh_hdr_t);
//
//		processed = decode_rle(src, imh->width * imh->height, dst);
//		xor_rows(dst, imh->width, imh->height);
//		src += processed;
//		len -= processed;
//		dst += size;
//	}
        Logging.LOGGER.log(Level.SEVERE, "IMH Decode...");
        while (len > 0) {
            int processed = 0;

            int width = ((mid[srcIdx + 5] & 0xFF) << 8) + ((mid[srcIdx + 4] & 0xFF));
            int height = ((mid[srcIdx + 7] & 0xFF) << 8) + ((mid[srcIdx + 6] & 0xFF));
            int size = width * height;

            //memmove(dst, src, sizeof(imh_hdr_t))
            for (int ii = 0; ii < imhSize; ii++) {
                destination[dstIdx + ii] = mid[srcIdx + ii];
            }
            totalLen += imhSize + size;
            srcIdx += imhSize;
            dstIdx += imhSize;
            len -= imhSize;

            processed = Util.decodeRLE(mid, srcIdx, size, destination, dstIdx);
            Util.xorRows(destination, dstIdx, width, height);
            srcIdx += processed;
            len -= processed;
            dstIdx += size;

        }

//
//	return total_len;
        //return totalLen;
//}
    }

    /*
    static int decode_imh(uint8_t *_src, uint32_t len, uint8_t *_dst) {
	uint32_t total_len = 0;
	uint8_t *src = _src, *dst = _dst;
	imh_hdr_t *imh;

	while (len) {
		uint32_t size = 0, processed = 0;

		imh = (imh_hdr_t*)src;
		size = imh->width * imh->height;

		memmove(dst, src, sizeof(imh_hdr_t));

		total_len += sizeof(imh_hdr_t) + size;
		src += sizeof(imh_hdr_t);
		dst += sizeof(imh_hdr_t);
		len -= sizeof(imh_hdr_t);

		processed = decode_rle(src, imh->width * imh->height, dst);
		xor_rows(dst, imh->width, imh->height);
		src += processed;
		len -= processed;
		dst += size;
	}

	return total_len;
    }
     */
    private static int decode(byte[] src, int len, byte[] dst) {
        int totalLen = 0;
        int srcIdx = 0;
        int dstIdx = 0;
        int imhSize = 2 * 4; // 2 bytes times 4 values

        while (len > 0) {
            int processed = 0;
            int width = ((src[srcIdx + 5] & 0xFF) << 8) + ((src[srcIdx + 4] & 0xFF));
            int height = ((src[srcIdx + 7] & 0xFF) << 8) + ((src[srcIdx + 6] & 0xFF));
            int size = width * height;
            LOGGER.log(Level.SEVERE, "\nSub-image: {0}x{1}", new Object[]{width, height});

            //memmove(dst, src, sizeof(imh_hdr_t))
            for (int ii = 0; ii < imhSize; ii++) { // Copy x/y w/h values to dst[].
                dst[dstIdx + ii] = src[srcIdx + ii];
            }
            totalLen += imhSize + size;
            srcIdx += imhSize;
            dstIdx += imhSize;
            len -= imhSize;
            processed = Util.decodeRLE(src, srcIdx, size, dst, dstIdx);
            srcIdx += processed; // Add processed to srcIdx
            len -= processed;

            Util.xorRows(dst, dstIdx, width, height);
            dstIdx += size; // Add size to dstIdx

            LOGGER.log(Level.SEVERE, "Pretty XOR Result with header ===>");
            HexFormat hexFormat = HexFormat.of();
            int prettyIdx = dstIdx - size - imhSize;
            LOGGER.log(Level.SEVERE,
                    "#### XY: {1}{0} {3}{2}    W:{5}{4}   H:{7}{6}",
                    new Object[]{
                        dst[prettyIdx++], dst[prettyIdx++], dst[prettyIdx++], dst[prettyIdx++],
                        dst[prettyIdx++], dst[prettyIdx++], dst[prettyIdx++], dst[prettyIdx++]
                    }
            );
            // Reset pretty index
            //prettyIdx = dstIdx - size;
            for (int j = prettyIdx; j < dstIdx; j += width) {
                StringBuilder sb = new StringBuilder(String.format("%04X", j & 0xFFFF) + ": ");
                for (int ww = 0; ww < width; ww++) {
                    sb.append(hexFormat.toHexDigits(dst[j + ww])).append(" ");
                }
                LOGGER.log(Level.SEVERE, sb.toString());
            }

        }

        return dstIdx; // Length of data
        //return totalLen;
    }

}
