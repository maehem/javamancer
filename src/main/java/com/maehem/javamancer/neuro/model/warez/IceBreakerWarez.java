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
package com.maehem.javamancer.neuro.model.warez;

import com.maehem.javamancer.neuro.model.GameState;
import static com.maehem.javamancer.neuro.model.item.DeckItem.Mode.CYBERSPACE;
import static com.maehem.javamancer.neuro.model.item.DeckItem.Mode.LINKCODE;
import static com.maehem.javamancer.neuro.model.item.DeckItem.Mode.NONE;
import com.maehem.javamancer.neuro.model.item.Item;
import static com.maehem.javamancer.neuro.model.warez.Warez.USE_OK;

/**
 * 
 * Ice Breaker: ABSTRACT
 *
 * <pre>
 * For breaking ICE / Attack
 *
 * ICE grows resistant to attack software.
 * The next time player uses the same software it will do less damage 
 * one version level lower.
 * Ex., Hammer 4.0 will only do damage of Hammer 3.0 when used next.
 * 
 * Player should use different software for each attack to maximize
 * damage output and only re-use other attacks when they reached the end of
 * a cycle.
 *
 * Weakest to Strongest: 
 *     Hammer, Decoder, Drill, DoorStop, LogicBomb, DepthCharge, BlowTorch, Concrete. 
 * 
 * Top tier attacks:
 * (LogicBomb, DepthCharge,Concrete) are twice as powerful as the mid-tier
 * attacks (Drill, DoorStop) and twice as powerful as the low-tier attacks
 * (Hammer, BlowTorch, Decoder). 
 * 
 * Ex. LogicBomb 1.0 is equivalent to Drill 2.0 and Hammer 4.0. 
 * 
 * TODO: Make spreadsheet chart of these power values.
 * 
 * </pre>
 * @author Mark J Koch ( @maehem on GitHub )
 */
public abstract class IceBreakerWarez extends Warez {

    public IceBreakerWarez(Item.Catalog catItem, int version) {
        super(catItem, version);
    }

    @Override
    public String use(GameState gs) {
        if (gs.usingDeck != null) {
            switch (gs.usingDeck.getMode()) {
                case NONE, LINKCODE -> {
                    // Can't be used here.
                    return "Can't be used here.";
                }
                case CYBERSPACE -> {
                    // TODO: Check if at DB and DB has ICE.

                    return USE_OK;
                }
            }
        }
        return "Can't be used here.";
    }
}
