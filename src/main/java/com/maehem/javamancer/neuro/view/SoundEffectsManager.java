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
package com.maehem.javamancer.neuro.view;

import com.maehem.javamancer.logging.Logging;
import java.net.URISyntaxException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.media.AudioClip;

/**
 *
 * @author Mark J Koch ( @maehem on GitHub )
 */
public class SoundEffectsManager {

    private static final Logger LOGGER = Logging.LOGGER;

    public enum Sound {
        DENIED("denied"),
        TRANSMIT("transmit"),
        ICE_HIT("ice-hit"),
        PLAYER_FIRE("player-fire"),
        ICE_BROKEN("ice-broken");

        public final String fileName;
        public AudioClip audioClip;

        private Sound(String simpleFileName) {
            this.fileName = "/sounds/" + simpleFileName + ".wav";
            try {
                this.audioClip = new AudioClip(getClass().getResource(fileName).toURI().toString());
                audioClip.setPriority(1);
            } catch (URISyntaxException ex) {
                LOGGER.log(Level.SEVERE, null, ex);
            }
        }
    }

    public SoundEffectsManager() {
    }

    public void playTrack(Sound m) {
        playTrack(m, 1.0);
    }

    public void playTrack(Sound track, double volume) {
        track.audioClip.play();
        LOGGER.log(Level.FINER, "SoundEffect begin: {0}", track.name());
    }

    public void setVolume(Sound track, double value) {
        track.audioClip.volumeProperty().setValue(value);
    }

}
