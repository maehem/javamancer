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

import com.maehem.javamancer.neuro.model.item.Item;
import com.maehem.javamancer.neuro.model.warez.*;

/**
 * Describes what a Warez program does.
 *
 * <pre>
 * Obtained from: Emperor Norton at the Matrix Restaurant for $1000. 
 * 
 * Upgrade: Level of 2 by Emperor Norton
 * 
 * </pre>
 *
 *
 * @author Mark J Koch ( @maehem on GitHub )
 */
public class WarezAnalysisSkill extends Skill {

    public enum Type {
        CYBERSPACE_INFO,
        CYBERSPACE_ICE_BREAKER,
        CYBERSPACE_VIRUS,
        CYBERSPACE_SHIELDING,
        CYBERSPACE_ICE_BYPASS,
        CYBERSPACE_INTERFACE_CORRUPTOR,
        DATABASE_PASSWORD_GENERATOR,
        DATABASE_INFO,
        DATABASE_CHESS,
        SYSTEM_COMMUNICATIONS,
        UNKNOWN
    }
    public WarezAnalysisSkill(int level) {
        super(Item.Catalog.WAREZANALYSIS, level, 2);
    }

    @Override
    public String getDescription() {
        return "Analyse software for it's purpose.";
    }
    
    public Type analyse(Warez warez) {
        if ( warez instanceof ScoutWarez ) {
            return Type.CYBERSPACE_INFO;
        }
        if ( warez instanceof ArmorallWarez ) {
            return Type.CYBERSPACE_SHIELDING;
        }
        if ( warez instanceof MimicWarez ) {
            return Type.CYBERSPACE_ICE_BYPASS;
        }
        if ( warez instanceof UtilityWarez ) {
            return Type.DATABASE_INFO;
        }
        if ( warez instanceof CorruptorWarez ) {
            return Type.CYBERSPACE_INTERFACE_CORRUPTOR;
        }
        if ( warez instanceof ShotgunWarez ) {
            return Type.CYBERSPACE_INTERFACE_CORRUPTOR;
        }
        if ( warez instanceof ChessWarez ) {
            return Type.DATABASE_CHESS;
        }
        if ( warez instanceof LinkWarez ) {
            return Type.SYSTEM_COMMUNICATIONS;
        }
        if ( warez instanceof SequencerWarez ) {
            return Type.DATABASE_PASSWORD_GENERATOR;
        }
        if ( warez instanceof IceBreakerWarez ) {
            return Type.CYBERSPACE_ICE_BREAKER;
        }
        if ( warez instanceof VirusWarez ) {
            return Type.CYBERSPACE_VIRUS;
        }
        
        return Type.UNKNOWN;
    }
    
}
