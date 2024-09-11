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
package com.maehem.javamancer.neuro.model.database;

import com.maehem.javamancer.neuro.view.ResourceManager;
import java.util.ArrayList;

/**
 *
 * @author Mark J Koch ( @maehem on GitHub )
 */
public class DatabaseList extends ArrayList<Database> {

    public DatabaseList(ResourceManager rm) {
        add(new AllardTechDatabase(rm));
        add(new AsanosDatabase(rm));
        add(new BankBerneDatabase(rm));
        add(new BankGemeinschaftDatabase(rm));
        add(new BankZurichDatabase(rm));
        add(new BellEuropaDatabase(rm));
        add(new CentralJusticeDatabase(rm));
        add(new CheapHotelDatabase(rm));
        add(new ChibaCityPoliceDatabase(rm));
        add(new ConsumerReviewDatabase(rm));
        add(new CopenhagenUniversityDatabase(rm));
        add(new DARPODatabase(rm));
        add(new EasternSeaFissionDatabase(rm));
        add(new FreeMatrixDatabase(rm));
        add(new FreeSexUnionDatabase(rm));
        add(new FujiElectricDatabase(rm));
        add(new GentlemanLoserDatabase(rm));
        add(new GridpointDatabase(rm));
        add(new HitachiBiotechDatabase(rm));
        add(new HosakaDatabase(rm));
        add(new INSADatabase(rm));
        add(new IRSDatabase(rm));
        add(new KGBDatabase(rm));
        add(new MaasBiolabsDatabase(rm));
        add(new MusaboriDatabase(rm));
        add(new NASADatabase(rm));
        add(new NihilistDatabase(rm));
        add(new PantherModernsDatabase(rm));
        add(new PhantomDatabase(rm));
        add(new PsychologistDatabase(rm));
        add(new RegularFellowsDatabase(rm));
        add(new ComSatDatabase(rm));
        add(new ScreamingFistDatabase(rm));
        add(new SenseNetDatabase(rm));
        add(new SoftwareEnforcementDatabase(rm));
        add(new TessierAshpoolDatabase(rm));
        add(new TozokuImportsDatabase(rm));
        add(new TuringRegistryDatabase(rm));
        add(new WorldChessDatabase(rm));
    }

    public Database whoIs(String linkCode) {
        for (Database db : this) {
            if (db.linkCode != null && db.linkCode.equals(linkCode)) {
                return db;
            }
        }
        return null;
    }

    public Database whatsAt(int x, int y) {
        for (Database db : this) {
            if (x == db.matrixX && y == db.matrixY) {
                return db;
            }
        }
        return null;
    }

}
