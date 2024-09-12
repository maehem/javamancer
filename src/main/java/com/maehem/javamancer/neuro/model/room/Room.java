/*
 * MIT License
 *
 * Copyright (c) 2024 Mark J. Koch ( @maehem on GitHub )
 *
 * Portions of this software are Copyright (c) 2018 Henadzi Matuts and are
 * derived from their project: https://github.com/HenadziMatuts/Reuromancer
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software", null), to deal
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
package com.maehem.javamancer.neuro.model.room;

import static com.maehem.javamancer.logging.Logging.LOGGER;
import com.maehem.javamancer.neuro.model.room.extra.*;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.logging.Level;

/**
 *
 * @author Mark J Koch ( @maehem on GitHub )
 */
public enum Room {

    R1("Chatsubo", R1Extras.class),
    R2("Street Chatsubo", R2Extras.class),
    R3("Cyber Justice", R3Extras.class),
    R4("Body Shop", R4Extras.class),
    R5("Street Body Shop", null),
    R6("Donut World", R6Extras.class),
    R7("Cheap Hotel", R7Extras.class),
    R8("Gentleman Loser", R8Extras.class),
    R9("Maas Biolabs", R9Extras.class),
    R10("JAL Shuttle", R10Extras.class),
    R11("Zion", R11Extras.class),
    R12("Larry's Software", R12Extras.class),
    R13("Street Microsofts", null),
    R14("Street Shin's", null),
    R15("Street Cheap Hotel", null),
    R16("Street G-Loser", null),
    R17("Street Maas Biolabs", null),
    R18("Street Spaceport", null),
    R19("Spaceport", R19Extras.class),
    R20("Marcus Garvey", null),
    R21("Villa Straylight, Ext.", null),
    R22("Villa Straylight", R22Extras.class),
    R23("Panther Moderns", R23Extras.class),
    R24("Brothel", R24Extras.class),
    R25("Shin's Pawn Shop", R25Extras.class),
    R26("Street Light Pole", R26Extras.class),
    R27("Julius Dean", R27Extras.class),
    R28("JAL Shuttle", R28Extras.class),
    R29("Freeside Spacedock", R29Extras.class),
    R30("SpaceDock Ext.", null),
    R31("Street Metro Holographix", null),
    R32("Metro Holographix", R32Extras.class),
    R33("Bank of Berne Ext.", null),
    R34("Bank of Berne Lobby", R34Extras.class),
    R35("Bank Manager's Office", null),
    R36("House of Pong", R36Extras.class),
    R37("Street House of Pong", null),
    R38("Street Asano's", null),
    R39("Street Crazy Edo's", null),
    R40("Crazy Edo's", R40Extras.class),
    R41("Bank Gemeinschaft", R41Extras.class),
    R42("Bank Gemeinschaft Ext.", null),
    R43("Unused", null),
    R44("Asano's", R44Extras.class),
    R45("Street Security Robot", R45Extras.class),
    R46("Matrix Restaurant", R46Extras.class),
    R47("Bank Gemeinschaft Vault", null),
    R48("Unused", null),
    R49("Street Burned Building", null),
    R50("Cyberspace Beach", R50Extras.class),
    R51("Fuji Electric", R51Extras.class),
    R52("Security Gate", R52Extras.class),
    R53("Hitachi Biotech", R53Extras.class),
    R54("High Tech Area 1", null),
    R55("High Tech Area 2", null),
    R56("SenseNet Headquarters", R56Extras.class),
    R57("Hosaka Headquarters", R57Extras.class),
    R58("Musabori Headquarters", R58Extras.class);

    public final String roomName;
    public final Class<? extends RoomExtras> extraClazz;
    public RoomExtras extras = null;
    private final ArrayList<RoomBounds.Door> locked = new ArrayList<>();

    private Room(String name, Class<? extends RoomExtras> extra) {
        this.roomName = name;
        this.extraClazz = extra;

    }

    public int getIndex() {
        return Integer.parseInt(name().substring(1)) - 1;
    }

    public RoomExtras getExtras() {
        if (extras != null) {
            return extras;
        }

        if (extraClazz != null) {
            try {
                Constructor<?> ctor = extraClazz.getConstructor();
                Object object = ctor.newInstance(new Object[]{});
                LOGGER.log(Level.SEVERE, "Extras Room Object created.");
                if (object instanceof RoomExtras re) {
                    extras = re;
                } else {
                    LOGGER.log(Level.SEVERE, "Extras Creation Failed.");
                    extras = null;
                }
            } catch (InstantiationException
                    | IllegalAccessException
                    | IllegalArgumentException
                    | InvocationTargetException
                    | NoSuchMethodException
                    | SecurityException ex) {
                LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
                ex.printStackTrace();

            }
        }

        return extras;
    }

    public boolean hasJack() {
        return getJack() != -1;
    }

    public int getJack() {
        if (extras != null) {
            return extras.jackZone();
        }

        return -1;
    }

    public boolean hasPax() {
        if (extras != null) {
            return extras.hasPAX();
        }

        return false;
    }

    public void lockDoor(RoomBounds.Door d) {
        locked.add(d);
    }

    public void unlockDoor(RoomBounds.Door d) {
        locked.remove(d);
    }

    public boolean isDoorLocked(RoomBounds.Door d) {
        return locked.contains(d);
    }

}
