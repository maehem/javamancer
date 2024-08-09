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
import com.maehem.javamancer.neuro.view.room.Music;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.Animation;
import javafx.animation.Transition;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

/**
 *
 * @author Mark J Koch ( @maehem on GitHub )
 */
public class MusicManager {

    private static final Logger LOGGER = Logging.LOGGER;

    public enum Track {
        TITLE("Daisy"),
        CHATSUBO("isosolation"),
        STREET_1("ketamine-infusion");

        public final String fileName;

        private Track(String simpleFileName) {
            this.fileName = "/music/" + simpleFileName + ".mp3";
        }
    }

    public enum Fade {
        IN, OUT
    }

    private final ArrayList<MediaItem> activeMedia = new ArrayList<>();

    public MusicManager() {
    }

    public void playTrack(Music m) {
        playTrack(m.track, m.volume, m.startTime, m.fadeIn, m.fadeOut);
    }

    // TODO: Fade in. Fade out. Start point. end point. loop.
    // TODO: Unfade.  fade and Remove track.
    // TODO: Tick?
    public void playTrack(Track track, double volume, double startTime, int fadeIn, int fadeOut) {
        MediaItem found = findTrack(track);
        if (found == null) {
            try {
                Media trackMedia = new Media(getClass().getResource(track.fileName).toURI().toString());
                MediaPlayer player = new MediaPlayer(trackMedia);
                MediaItem mediaItem = new MediaItem(player, track);
                if (startTime > 0) {
                    player.setStartTime(Duration.millis(startTime));
                }
                if (fadeIn > 0) {
                    player.volumeProperty().set(0);
                    mediaItem.fadeIn = fadeInPlayer(player, volume, fadeIn);

                } else {
                    player.volumeProperty().set(volume);
                }
                player.play();
                LOGGER.log(Level.SEVERE, "Track begin: {0}", track.name());
                activeMedia.add(mediaItem);

                player.setOnEndOfMedia(() -> {
                    LOGGER.log(Level.SEVERE, "Track end: {0}", track.name());
                    activeMedia.remove(findTrack(track));
                });
            } catch (URISyntaxException e) {
                LOGGER.log(Level.SEVERE, e.getMessage(), e);
            }
        } else {
            LOGGER.log(Level.SEVERE, "Track already active. Play again.");
            found.player.play();
        }
    }

    public void stopTrack(Track track) {
        MediaItem found = findTrack(track);
        if (found != null) {
            found.player.stop();
        }
    }

    public void fadeOutTrack(Track track, int milliSeconds) {
        MediaItem found = findTrack(track);
        if (found != null) {
            final Animation animation = new Transition() {
                double startVol = found.player.getVolume();

                {
                    setCycleDuration(Duration.millis(milliSeconds));
                }

                @Override
                protected void interpolate(double frac) {
                    found.player.volumeProperty().set(startVol * (1.0 - frac));
                }
            };

            LOGGER.log(Level.SEVERE, "Begin Track fade out: {0} --> {1}ms", new Object[]{track.name(), milliSeconds});
            animation.play();
        }
    }

    public Animation fadeInPlayer(MediaPlayer player, double finalLevel, int milliSeconds) {
        final Animation animation = new Transition() {

            {
                setCycleDuration(Duration.millis(milliSeconds));
            }

            @Override
            protected void interpolate(double frac) {
                player.volumeProperty().set(finalLevel * frac);
            }
        };

        animation.play();

        return animation;
    }

    public void setVolume(Track track, double value) {
        MediaItem found = findTrack(track);
        if (found != null) {
            found.player.volumeProperty().setValue(value);
        }
    }

    private MediaItem findTrack(Track track) {
        for (MediaItem item : activeMedia) {
            if (item.track.equals(track)) {
                return item;
            }
        }
        return null;
    }

    public void stopAll() {
        LOGGER.log(Level.SEVERE, "Music Manager: All music stop.");
        for (MediaItem item : activeMedia.toArray(MediaItem[]::new)) {
            item.player.stop();
            activeMedia.remove(item);
        }
    }

    private class MediaItem {

        public final MediaPlayer player;
        public final Track track;
        private Animation fadeIn = null;
        private Animation fadeOut = null;

        public MediaItem(MediaPlayer player, Track track) {
            this.player = player;
            this.track = track;
        }

        public void setFadeIn(Animation fadeIn) {
            // TODO: can't in in while fading out.
            if (this.fadeOut != null) {
                this.fadeOut.stop();
                this.fadeOut = null;
            }
            this.fadeIn = fadeIn;
            fadeIn.setOnFinished((t) -> {
                this.fadeIn = null;
            });
        }

        public void setFadeOut(Animation fadeOut) {
            if (this.fadeIn != null) {
                this.fadeIn.stop();
                this.fadeIn = null;
            }
            this.fadeOut = fadeOut;
            fadeOut.setOnFinished((t) -> {
                this.fadeOut = null;
            });
        }

    }

}
