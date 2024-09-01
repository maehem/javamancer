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
import com.maehem.javamancer.neuro.model.item.Item;
import com.maehem.javamancer.neuro.model.skill.Skill;
import java.util.logging.Level;

/**
 * Attempt to break straight through ICE by mimicking a warranted investigation.
 *
 * @author Mark J Koch ( @maehem on GitHub )
 */
public class MimicWarez extends UtilityWarez {

    public MimicWarez(int version) {
        super(Item.Catalog.MIMIC, version);
    }

    @Override
    public int getRunDuration() {
        return 2000;
    }

    @Override
    public int getEffect(GameState gs) {
        if (gs.activeSkill.type == Skill.Type.COPTALK) {
            int percent = gs.activeSkillLevel * 100;
            int random = (int) (Math.random() * 500);
            if (version * (percent + random) > gs.database.getIce()) {
                return gs.database.getIce();
            }
        } else {
            LOGGER.log(Level.CONFIG, "Mimic requires Coptalk in-use to function.");
        }
        return 0;
    }

    public MimicWarez(Item.Catalog catItem, int version) {
        super(catItem, version);
    }

}
