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
import static com.maehem.javamancer.logging.Logging.LOGGER;
import java.util.HexFormat;
import java.util.logging.Level;

/**
 *
 * @author Mark J Koch ( @maehem on GitHub )
 */
public class Huffman {

//    typedef struct node_t {
//	uint8_t value;
//	struct node_t *left, *right;
//    } node_t;
    private class Node {

        public int value;
        public Node left;
        public Node right;

        public Node(int value, Node left, Node right) {
            this.value = value;
            this.left = left;
            this.right = right;
        }
    }

    private final byte[] compressedData;
    private final byte[] destination;
    Node root;
    Node node;

    int idx = 0;
    //int length = 0;
    int gMask = 0;
    int gByte = 0;

    private Huffman(byte[] compressedData, byte[] destination) {
        this.compressedData = compressedData;
        this.destination = destination;
    }

    public static final int decompress(byte[] compressedData, byte[] destination) {
        // Length is first four bytes of compressedData[] in little-endian format

        return new Huffman(compressedData, destination).doDecompress();
    }

    private int doDecompress() {

        int i = 0;
//	int length, i = 0;
//	node_t *root, *node;
//
//	g_src = src;
//
//	length = fgetl_le();
        int length = fgetl_le();

//	node = root = build_tree();
        node = root = buildTree();
//
//	while (i < length)
//	{
//		node = getbits(1) ? node->left : node->right;
//		if (!node->left) {
//			dst[i++] = node->value;
//			node = root;
//		}
//	}
        while (i < length) {
            node = getBits(1) > 0 ? node.left : node.right;
            if (node.left == null) {
                destination[i++] = (byte) (node.value & 0xff);
                node = root;
            }
        }
//
//	destroy_tree(&root);
        // We have CG?
//
//	g_src = NULL;
//	g_mask = 0;
//	g_byte = 0;
//
//	return length;
        HexFormat hexFormat = HexFormat.of();

        LOGGER.log(Level.FINER, "Huffman Decompress => ");
        for (int ii = 0; ii < length; ii += 16) {
            Logging.LOGGER.log(Level.FINER,
                    String.format("%04X", ii & 0xFFFF) + ": "
                    + hexFormat.toHexDigits(destination[ii]) + hexFormat.toHexDigits(destination[ii + 1]) + " "
                    + hexFormat.toHexDigits(destination[ii + 2]) + hexFormat.toHexDigits(destination[ii + 3]) + " "
                    + hexFormat.toHexDigits(destination[ii + 4]) + hexFormat.toHexDigits(destination[ii + 5]) + " "
                    + hexFormat.toHexDigits(destination[ii + 6]) + hexFormat.toHexDigits(destination[ii + 7]) + " "
                    + hexFormat.toHexDigits(destination[ii + 8]) + hexFormat.toHexDigits(destination[ii + 9]) + " "
                    + hexFormat.toHexDigits(destination[ii + 10]) + hexFormat.toHexDigits(destination[ii + 11]) + " "
                    + hexFormat.toHexDigits(destination[ii + 12]) + hexFormat.toHexDigits(destination[ii + 13]) + " "
                    + hexFormat.toHexDigits(destination[ii + 14]) + hexFormat.toHexDigits(destination[ii + 15]) + " "
            );
        }

        return length;
    }

    /**
     * Convert g_src to little-endian Int
     */
    private int fgetl_le() {

//    static uint32_t fgetl_le()
//{
//	uint8_t c1, c2, c3, c4;
//
//	c1 = *g_src++;
//	c2 = *g_src++;
//	c3 = *g_src++;
//	c4 = *g_src++;
//
        int c1 = compressedData[idx++] & 0xFF;
        int c2 = compressedData[idx++] & 0xFF;
        int c3 = compressedData[idx++] & 0xFF;
        int c4 = compressedData[idx++] & 0xFF;

//	return (c4 << 24) + (c3 << 16) + (c2 << 8) + c1;
//}
        return (c4 << 24) + (c3 << 16) + (c2 << 8) + c1;
    }

    private int getBits(int numBits) {
        //static int getbits(int numbits)
        //{
        //	int i, bits = 0;
        //
        //	for (i = 0; i < numbits; i++)
        //	{
        //		if (g_mask == 0) {
        //			g_byte = *g_src++;
        //			g_mask = 0x80;
        //		}
        //		bits <<= 1;
        //		bits |= g_byte & g_mask ? 1 : 0;
        //		g_mask >>= 1;
        //	}
        //
        //	return bits;
        //}
        int bits = 0;
        for (int i = 0; i < numBits; i++) {
            if (gMask == 0) {
                gByte = compressedData[idx++] & 0xff;
                gMask = 0x80;
            }
            bits <<= 1;
            bits |= (gByte & gMask) > 0 ? 1 : 0;
            gMask >>= 1;
        }

        return bits;
    }

    private Node buildTree() {
//static node_t* build_tree(void)
//{
//	node_t *node = (node_t*)calloc(1, sizeof(node_t));
//	assert(node);
//
//	if (getbits(1)) {
//		node->right = NULL;
//		node->left = NULL;
//		node->value = getbits(8);
//	}
//	else {
//		node->right = build_tree();
//		node->left = build_tree();
//		node->value = 0;
//	}
//
//	return node;
//}
        if (getBits(1) != 0) {
            return new Node(getBits(8), null, null);
        } else {
            Node rightTree = buildTree();
            return new Node(0, buildTree(), rightTree);
        }
    }

//void destroy_tree(node_t **root)
//{
//	node_t *node = *root;
//
//	if (node->left) {
//		destroy_tree(&node->right);
//		destroy_tree(&node->left);
//	}
//
//	free(node);
//	node = NULL;
//}
}
