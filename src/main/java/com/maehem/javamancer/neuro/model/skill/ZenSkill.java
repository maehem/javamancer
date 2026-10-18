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

import com.maehem.javamancer.neuro.model.GameState;
import com.maehem.javamancer.neuro.model.item.Item;
import java.util.logging.Level;

/**
 * Heals your Constitution fully during AI battle. 
 * 
 * <pre>
 * Obtained from:  Nolan at House of Pong
 * by giving him the joystick from Metro Holografix. 
 * 
 * Upgrade: Level 2 by Emperor Norton at Matrix Rest.
* </pre>
* 
 * @author Mark J Koch ( @maehem on GitHub )
 */
public class ZenSkill extends Skill {

    public ZenSkill(int level) {
        super(Item.Catalog.ZEN, level, 2);
    }

    @Override
    public void use(GameState gs) { // use() called by BattlePane.tick()
        int gsDamageOld = gs.getConstitution();
        gs.damage -= GameState.CONSTITUTION_MAX/(MAX_LEVEL/level);
        if ( gs.damage < 0 ) {
            gs.damage = 0;
        }
        gs.activeSkill = gs.previousSkill;
        gs.previousSkill = null;
        
        LOGGER.log(Level.INFO, "Zen :: Heal Constitution: {0}  {1} ==> {2}", new Object[]{catalog.itemName, gsDamageOld, gs.getConstitution() });
        
    }

    @Override
    public String getDescription() {
        return "Restore constitution level when fighing AI.";
    }
}
