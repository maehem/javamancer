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
package com.maehem.javamancer.neuro.view.database;

import com.maehem.javamancer.neuro.model.database.*;

/**
 *
 * @author Mark J Koch ( @maehem on GitHub )
 */
public enum DatabaseViewList {
    DB0(RegularFellowsDatabase.class, RegularFellowsDatabaseView.class),
    DB1(ConsumerReviewDatabase.class, ConsumerReviewDatabaseView.class),
    DB2(AsanosDatabase.class, AsanosDatabaseView.class),
    DB3(WorldChessDatabase.class, WorldChessDatabaseView.class),
    DB4(CheapHotelDatabase.class, CheapHotelDatabaseView.class),
    DB5(PsychologistDatabase.class, PsychologistDatabaseView.class),
    DB6(PantherModernsDatabase.class, PantherModernsDatabaseView.class),
    DB7(IRSDatabase.class, IRSDatabaseView.class),
    DB8(FujiElectricDatabase.class, FujiElectricDatabaseView.class),
    DB9(ChibaCityPoliceDatabase.class, ChibaCityPoliceDatabaseView.class),
    DB10(HitachiBiotechDatabase.class, HitachiBiotechDatabaseView.class),
    DB11(CopenhagenUniversityDatabase.class, CopenhagenUniversityDatabaseView.class),
    DB12(SoftwareEnforcementDatabase.class, SoftwareEnforcementDatabaseView.class),
    DB13(FreeMatrixDatabase.class, FreeMatrixDatabaseView.class),
    DB14(EasternSeaFissionDatabase.class, EasternSeaFissionDatabaseView.class),
    DB15(GentlemanLoserDatabase.class, GentlemanLoserDatabaseView.class),
    DB16(TozokuImportsDatabase.class, TozokuImportsDatabaseView.class),
    DB17(HosakaDatabase.class, HosakaDatabaseView.class),
    DB19(MusaboriDatabase.class, MusaboriDatabaseView.class),
    DB20(NASADatabase.class, NASADatabaseView.class),
    DB22(CentralJusticeDatabase.class, CentralJusticeDatabaseView.class),
    DB29(FreeSexUnionDatabase.class, FreeSexUnionDatabaseView.class),
    DB30(BankBerneDatabase.class, BankBerneDatabaseView.class),
    D31(DARPODatabase.class, DARPODatabaseView.class);

    public final Class<? extends Database> database;
    public final Class<? extends DatabaseView> view;

    private DatabaseViewList(Class<? extends Database> db, Class<? extends DatabaseView> view) {
        this.database = db;
        this.view = view;
    }

    public static Class<? extends DatabaseView> getViewClass(Class<? extends Database> db) {
        for (DatabaseViewList item : values()) {
            if (item.database.equals(db)) {
                return item.view;
            }
        }

        return null;
    }

}
