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
package com.maehem.javamancer.neuro.model.room;

import com.maehem.javamancer.logging.Logging;
import com.maehem.javamancer.neuro.model.GameState;
import com.maehem.javamancer.neuro.model.JackZone;
import com.maehem.javamancer.neuro.model.item.Item;
import com.maehem.javamancer.neuro.model.item.SkillItem;
import com.maehem.javamancer.neuro.model.item.SoftwareItem;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Mark J Koch ( @maehem on GitHub )
 */
public abstract class RoomExtras {

    public static final Logger LOGGER = Logging.LOGGER;

    public boolean onVendFinishedOpenDialog = false; // Semaphote to open dialog when vend finished.

    /**
     * Request the talk dialog. Handled by RoomMode.tick() Flag is cleared when
     * handled.
     */
    private boolean requestDialogPoppup = false;

    public RoomExtras() {
        LOGGER.log(Level.CONFIG, "New RoomExtra Created.");
    }

    /**
     * Called when player gives item to NPC. Over-ride if NPC can receive
     * things.
     *
     */
    public boolean give(GameState gs, Item item, int aux) {
        return false;
    }

    public abstract int[][] getDialogChain();

    public abstract int dialogWarmUp(GameState gs);

    public void dialogNoMore(GameState gs) {
        LOGGER.log(Level.INFO, "Something set room to no more dialog.");
        gs.setRoomTalk(false);
        //gs.roomNpcTalk[gs.room.getIndex()] = false;
    }

    public abstract void initRoom(GameState gs);

    public JackZone jackZone() {
        return null;
    }

    public boolean hasPAX() {
        return false;
    }

    /**
     * Player gets item from NPC. ex. Player receives ComLink 2.0 in exchange
     * for Caviar
     *
     *
     * @param gs
     * @param item
     * @return
     */
    public boolean getItem(GameState gs, Item item) {
        LOGGER.log(Level.WARNING, "Room called get() but it's not overridden!");
        return false;
    }

    /**
     * Override if NPC has skills to sell.
     *
     * @return
     */
    public ArrayList<SkillItem> getVendSkillItems(GameState gs) {
        LOGGER.log(Level.WARNING, "Room called getVendSkillItems() but it's not overridden!");
        return null;
    }

    public boolean onSkillVendFinished(GameState gs) {
        LOGGER.log(Level.WARNING, "Room called onSkillVendFinished() but it's not overridden!");
        return false;
    }

    /**
     * Override if NPC has skills to upgrade.
     *
     * @return
     */
    public ArrayList<SkillItem> getUpgradeSkillItems(GameState gs) {
        LOGGER.log(Level.WARNING, "Room called getUpgradeSkillItems() but it's not overridden!");
        return null;
    }

    /**
     * Override if NPC has items to sell.
     *
     * @return
     */
    public ArrayList<Item> getVendItems(GameState gs) {
        LOGGER.log(Level.WARNING, "Room called getVendItems() but it's not overridden!");
        return null;
    }

    public boolean onVendItemsFinished(GameState gs) {
        LOGGER.log(Level.WARNING, "Room called onVendItemsFinished() but it's not overridden!");
        return false;
    }

    /**
     * Override if NPC has skills to sell.
     *
     * @return
     */
    public ArrayList<SoftwareItem> getVendSoftwareItems(GameState gs) {
        LOGGER.log(Level.WARNING, "Room called getVendSoftwareItems() but it's not overridden!");
        return null;
    }

    /**
     * Over ride with logic for word. Use WORD1 Mnemonic.
     *
     *
     * @param word
     * @return
     */
    public int askWord1(GameState gs, String word) { // or phrase
        return -1; // Not found
    }

    /**
     * Over ride with logic for word. Use WORD2 Mnemonic.
     *
     *
     * @param word
     * @return
     */
    public int askWord2(GameState gs, String word) { // or phrase
        return -1; // Not found
    }

    public int exitX(GameState gs) {
        return DialogCommand.EXIT_R.num;
    }

    public boolean chipDeduct(GameState gs, int amt) {
        if (amt > gs.chipBalance) {
            return false;
        }

        gs.chipBalance -= amt;
        return true;
    }

    public void applyDiscount(GameState gs) {
        LOGGER.log(Level.WARNING, "Room called applyDiscount but it's not overridden!");
    }

    public int getDiscount(GameState gs) {
        LOGGER.log(Level.WARNING, "Discount is not overridden.");
        return 0;
    }

    public int getSkillDiscount(GameState gs) {
        LOGGER.log(Level.WARNING, "Skill Discount is not overridden.");
        return 0;
    }

    public void onDialog(GameState gs, int newDialog) {
        // Override to take actions when certain dialogs are reached.
    }

    public void tick(GameState gs) {
        // Override to handle things each frame.
    }

    public void setRequestDialogPopup(boolean value) {
        LOGGER.log(Level.INFO, "Set requestPopup: {0}", value ? "TRUE" : "FALSE");
        requestDialogPoppup = value;
    }

    public boolean isRequestDialogPopup() {
        //LOGGER.log(Level.INFO, "Request DialogPopup is: {0}", requestDialogPoppup ? "TRUE" : "FALSE");
        return requestDialogPoppup;
    }

    public int onInfoBuy(GameState gs) {
        return 2;
    }

    public int[] onFilter1(GameState gs) {
        LOGGER.log(Level.WARNING, "onFilter1() called but not over-ridden!");
        return new int[]{2};
    }

    public double getNpcPosition() {
        return 300;
    }

    /**
     * Override to take action on a dialog command.
     *
     * @param command
     * @return true if command can run
     */
    public int onDialogCommand(GameState gs, DialogCommand command) {
        LOGGER.log(Level.WARNING, "onDialogCommand() called but not over-ridden!");
        return -1;
    }

}
