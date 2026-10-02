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
package com.maehem.javamancer.neuro.model.skill;

import com.maehem.javamancer.neuro.model.item.DeckItem;
import com.maehem.javamancer.neuro.model.item.Item;
import java.util.logging.Level;

/**
 * Hardware Repair. Fixes corrupted decks.
 *
 * <pre>
 * Obtained from: Shiva at the Gentlemen Loser for $1000.
 *
 * Upgrade: To a max level of 2 by Emperor Norton at Matrix Restaurant.
 *
 * </pre>
 *
 * @author Mark J Koch ( @maehem on GitHub )
 */
public class HardwareRepairSkill extends Skill {

    public enum Result {
        NO_ITEM, NO_BUGS, NOT_HARDWARE, LOW_SKILL, SUCCESS
    }

    public HardwareRepairSkill(int level) {
        super(Item.Catalog.HARDWAREREPAIR, level, 2);
    }

    @Override
    public void use() {
        LOGGER.log(Level.INFO, "Use skill: {0}", catalog.itemName);
    }

    @Override
    public String getDescription() {
        return "Repair your deck.";
    }

    public Result doRepair(Item item) {
        if (item == null ) {
            LOGGER.log(Level.WARNING, "Hardware Repair invoked null item.");
            return Result.NO_ITEM;
        }
        
        Result result;
        if (item instanceof DeckItem deckItem) {
            // Attempt repair.
            if (deckItem.needsRepair()) {

                // Check skill level
                if (level < deckItem.getDamage()) { // Skill level no high enough.
                    result = Result.LOW_SKILL;
                } else {
                    // skill level OK. Do repair.
                    deckItem.setDamage(0);
                    result = Result.SUCCESS;
                }
            } else {
                result = Result.NO_BUGS;
            }
        } else {
            result = Result.NOT_HARDWARE;
        }
        LOGGER.log(Level.INFO, "Hardware Repair invoked on {0} with result {1}", new Object[]{item.getName(),result.name()});
        
        return result;
    }
}
