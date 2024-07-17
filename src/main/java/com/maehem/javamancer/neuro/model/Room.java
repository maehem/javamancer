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
package com.maehem.javamancer.neuro.model;

/**
 *
 * @author Mark J Koch ( @maehem on GitHub )
 */
public enum Room {

    R1("Chatsubo"), R2("Street Chatsubo"), R3("Cyber Justice"),
    R4("Body Shop"), R5("Street Body Shop"), R6("Donut World"),
    R7("Cheap Hotel"), R8("Gentleman Loser"), R9("Maas Biolabs"),
    R10("JAL Shuttle"), R11("Zion"), R12("Larry's Software"),
    R13("Street Microsofts"), R14("Street Shin's"), R15("Street Cheap Hotel"),
    R16("Street G-Loser"), R17("Street Maas Biolabs"), R18("Street Spaceport"),
    R19("Spaceport"), R20("Marcus Garvey"), R21("Villa Straylight, Ext."),
    R22("Villa Straylight"), R23("Panther Moderns"), R24("Brothel"),
    R25("Shin's Pawn Shop"), R26("Street Light Pole"), R27("Julius Dean"),
    R28("JAL Shuttle"), R29("Freeside Spacedock"), R30("SpaceDock Ext."),
    R31("Street Metro Holographix"), R32("Metro Holographix"), R33("Bank of Berne Ext."),
    R34("Bank of Berne Lobby"), R35("Bank Manager's Office"), R36("House of Pong"),
    R37("Street House of Pong"), R38("Street Asano's"), R39("Street Matrix Restaurant"),
    R40("Crazy Edo's"), R41("Bank Gemeinschaft"), R42("Bank Gemeinschaft Ext."),
    R43("Unused"), R44("Asano's"), R45("Street Security Robot"),
    R46("Matrix Restaurant"), R47("Bank Gemeinschaft Vault"), R48("Unused"),
    R49("Street Burned Building"), R50("Cyberspace Beach"), R51("Fuji Electric"),
    R52("Security Gate"), R53("Hitachi Biotech"), R54("High Tech Area 2"),
    R55("High Tech Area 2"), R56("SenseNet Headquarters"), R57("Hosaka Headquarters"),
    R58("Musabori Headquarters");

    public final String roomName;

    private Room(String name) {
        this.roomName = name;
    }
}
