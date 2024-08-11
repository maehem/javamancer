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
 *
 * @author Mark J Koch ( @maehem on GitHub )
 */
public enum BodyPart {

    HEART(0, "Heart", 12000, 6000, 6600, 200),
    EYES(1, "Eyes (2)", 10000, 5000, 6500, 150),
    LUNGS(2, "Lungs (2)", 6000, 3000, 3300, 150),
    STOMACH(3, "Stomach", 3000, 1500, 1650, 100),
    LIVER(4, "Liver", 2500, 1250, 1375, 75),
    KIDNEYS(5, "Kidneys (2)", 2100, 1050, 1155, 75),
    GALL_BLADDER(6, "Gall Bladder", 2100, 1050, 1155, 75),
    PANCREAS(7, "Pancreas", 1000, 500, 550, 75),
    LEGS(8, "Legs (2)", 600, 300, 330, 50),
    ARMS(9, "Arms (2)", 600, 300, 330, 50),
    TONGUE(10, "Tongue", 300, 150, 165, 25),
    LARYNX(11, "Larynx", 300, 150, 165, 25),
    NODE(12, "Nose", 300, 150, 165, 25),
    EARS(13, "Ears (2)", 200, 100, 110, 25),
    INTESTINE_L(14, "Intestine (large)", 100, 50, 78, 10),
    INTESTINE_S(15, "Intestine (small)", 100, 50, 78, 10),
    SPLEEN(16, "Spleen", 90, 45, 55, 10),
    BONE_MARROW(17, "Bone Marrow", 90, 45, 55, 10),
    SPINE_FLUID(18, "Spinal Fluid", 60, 30, 33, 10),
    APPENDIX(19, "Appendix", 6, 3, 3, 10);

//uint16_t g_body_parts_buy_prices[20] = { /* 0x2314 */
//	12000, 10000, 6000, 3000,
//	2500, 2100, 2100, 1000,
//	600, 600, 300, 300, 300,
//	200, 100, 100, 90, 90, 60, 6
//}, g_body_parts_sell_prices[20] = { /* 0x233C */
//	6000, 5000, 3000, 1500,
//	1250, 1050, 1050, 500,
//	300, 300, 150, 150, 150,
//	100, 50, 50, 45, 45, 30, 3
//}, g_body_parts_discounted_prices[20] = { /* 0x2364 */
//	6600, 6500, 3300, 1650,
//	1375, 1155, 1155, 550,
//	330, 330, 165, 165, 165,
//	110, 78, 78, 55, 55, 33, 3
//}, g_constitution_damage[20] = { /* 0x238C */
//	200, 150, 150, 100,
//	75, 75, 75, 75, 50, 50,
//	25, 25, 25, 25, 10, 10,
//	10, 10, 10, 10
//};
    public final int index;
    public final String itemName;
    public final int buyPrice;
    public final int sellPrice;
    public final int discPrice;
    public final int constDamage;

    private BodyPart(int index, String name, int buyPrice, int sellPrice, int discPrice, int constDamage) {
        this.index = index;
        this.itemName = name;
        this.buyPrice = buyPrice;
        this.sellPrice = sellPrice;
        this.discPrice = discPrice;
        this.constDamage = constDamage;
    }

}
