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

import java.util.ArrayList;

/**
 *
 * @author Mark J Koch ( @maehem on GitHub )
 */
public class DatabaseList extends ArrayList<Database> {

    public DatabaseList() {
        add(new AllardTechDatabase());
        add(new AsanosDatabase());
        add(new BankBerneDatabase());
        add(new BankGemeinschaftDatabase());
        add(new BankZurichDatabase());
        add(new BellEuropaDatabase());
        add(new CentralJusticeDatabase());
        add(new CheapHotelDatabase());
        add(new ChibaCityPoliceDatabase());
        add(new ConsumerReviewDatabase());
        add(new CopenhagenUniversityDatabase());
        add(new DARPODatabase());
        add(new EasternSeaFissionDatabase());
        add(new FreeMatrixDatabase());
        add(new FreeSexUnionDatabase());
        add(new FujiElectricDatabase());
        add(new GentlemanLoserDatabase());
        add(new GridpointDatabase());
        add(new HitachiBiotechDatabase());
        add(new HosakaDatabase());
        add(new INSADatabase());
        add(new IRSDatabase());
        add(new KGBDatabase());
        add(new MaasBiolabsDatabase());
        add(new MusaboriDatabase());
        add(new NASADatabase());
        add(new NihilistDatabase());
        add(new PantherModernsDatabase());
        add(new PhantomDatabase());
        add(new PsychologistDatabase());
        add(new RegularFellowsDatabase());
        add(new SatelliteMissionControlDatabase());
        add(new ScreamingFistDatabase());
        add(new SenseNetDatabase());
        add(new SoftwareEnforcementDatabase());
        add(new TessierAshpoolDatabase());
        add(new TozokuImportsDatabase());
        add(new TuringRegistryDatabase());
        add(new WorldChessDatabase());
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
