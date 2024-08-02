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
package com.maehem.javamancer.neuro.model.deck;

import com.maehem.javamancer.neuro.model.item.DeckItem;
import com.maehem.javamancer.neuro.model.item.Item.Catalog;
import com.maehem.javamancer.neuro.model.warez.AcidWarez;
import com.maehem.javamancer.neuro.model.warez.BlammoWarez;
import com.maehem.javamancer.neuro.model.warez.ComLinkWarez;
import com.maehem.javamancer.neuro.model.warez.ConcreteWarez;
import com.maehem.javamancer.neuro.model.warez.DecoderWarez;
import com.maehem.javamancer.neuro.model.warez.DoorStopWarez;
import com.maehem.javamancer.neuro.model.warez.DrillWarez;
import com.maehem.javamancer.neuro.model.warez.EasyRiderWarez;
import com.maehem.javamancer.neuro.model.warez.HammerWarez;
import com.maehem.javamancer.neuro.model.warez.HemlockWarez;
import com.maehem.javamancer.neuro.model.warez.InjectorWarez;
import com.maehem.javamancer.neuro.model.warez.JammiesWarez;
import com.maehem.javamancer.neuro.model.warez.KGBWarez;
import com.maehem.javamancer.neuro.model.warez.LogicBombWarez;
import com.maehem.javamancer.neuro.model.warez.ProbeWarez;
import com.maehem.javamancer.neuro.model.warez.SequencerWarez;
import com.maehem.javamancer.neuro.model.warez.SlowWarez;
import com.maehem.javamancer.neuro.model.warez.ThunderheadWarez;

/**
 *
 * @author Mark J Koch ( @maehem on GitHub )
 */
public class UXBDeckItem extends DeckItem {

    public UXBDeckItem() {
        super(Catalog.UXB, 5);
        softwarez.add(new ComLinkWarez(1));
        softwarez.add(new AcidWarez(2));
        softwarez.add(new ConcreteWarez(3));
        softwarez.add(new DecoderWarez(1));
        softwarez.add(new DrillWarez(6));
        softwarez.add(new DoorStopWarez(7));
        softwarez.add(new HammerWarez(4));
        softwarez.add(new HemlockWarez(12));
        softwarez.add(new InjectorWarez(4));
        softwarez.add(new JammiesWarez(11));
        softwarez.add(new KGBWarez(4));
        softwarez.add(new LogicBombWarez(4));
        softwarez.add(new ProbeWarez(18));
        softwarez.add(new SlowWarez(9));
        softwarez.add(new ThunderheadWarez(1));
        softwarez.add(new SequencerWarez(8));
        softwarez.add(new BlammoWarez(4));
        softwarez.add(new EasyRiderWarez(2));
    }

    @Override
    public void use() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

}