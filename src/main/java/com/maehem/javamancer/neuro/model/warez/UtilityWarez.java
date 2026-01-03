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
 * @author Mark J Koch ( @maehem on GitHub )
 */
public abstract class UtilityWarez extends Warez {

    public UtilityWarez(Item.Catalog catItem, int version) {
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

    @Override
    public int getRunDuration() {
        return 0;
    }

    @Override
    public int getEffect(GameState gs) {
        return 0;
    }
}
