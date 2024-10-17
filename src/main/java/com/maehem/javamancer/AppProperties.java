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
package com.maehem.javamancer;

import com.maehem.javamancer.logging.Logging;
import com.maehem.javamancer.resource.model.DAT;
import com.maehem.javamancer.resource.view.DATUtil;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.HostServices;

/**
 * Manage properties for the application
 *
 * @author Mark J Koch ( @maehem on GitHub )
 */
public class AppProperties extends Properties {

    private static final Logger LOGGER = Logging.LOGGER;

    public static final String DAT_DIR = "DAT";
    public static final String CACHE_DIR = "cache";
    private static final String APP_VERSION = "0"; // Get from Javamancer.java ???

    private static AppProperties instance = null;
    private final File propFile;// = initPropFile();
    //private HostServices hostServices;
    private HostServices hostServices;

    private AppProperties() {
        propFile = initPropFile();
        if (propFile.exists()) {
            load();
        } else {
            propFile.getParentFile().mkdirs();
            save(true);
        }
        initDatFolder();
    }

    public static AppProperties getInstance() {
        if (instance == null) {
            instance = new AppProperties();
        }

        return instance;
    }

    public final void load() {
        try {
            load(new FileInputStream(propFile));
            LOGGER.log(Level.SEVERE, "Loaded Javamancer settings file: {0}", propFile.getAbsolutePath());
        } catch (FileNotFoundException ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        }

    }

    public final void save() {
        save(false);
    }

    public final void save(boolean asNew) {
        try {
            String msg = "Saved Javamancer Settings File.";
            if (asNew) {
                msg = "Created New Javamancer Settings File.";
            }
            store(new FileOutputStream(propFile), msg);
            LOGGER.log(Level.SEVERE, "{0} :: {1}", new Object[]{msg, propFile.getAbsolutePath()});
        } catch (FileNotFoundException ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        }
    }

    public File getPropFile() {
        return propFile;
    }

    private static File initPropFile() {
        final String os = System.getProperty("os.name");
        if (os != null && os.startsWith("Mac")) {
            // It is said not to hard-code MacOS path. So maybe the other solution below?
            return new File(System.getProperty("user.home")
                    + File.separator + "Library"
                    + File.separator + "Application Support"
                    + File.separator + "Javamancer"
                    + File.separator + APP_VERSION
                    + File.separator + "settings.properties"
            );
        } else {
            // TODO: Sort this out per platform later.
            // On windows/mac/linux? this might work:  System.getenv("APPDATA")
            // or System.getenv("AppData");
            //
            // Windows: C:\Users\<username>\AppData\Roaming\YourCompany\YourAppName
            // macOS: /Users/<username>/Library/Application Support/YourAppName
            // Linux: /home/<username>/.local/share/YourAppName
            //
            // See: https://stackoverflow.com/questions/11113974/what-is-the-cross-platform-way-of-obtaining-the-path-to-the-local-application-da
            return new File(System.getProperty("user.home")
                    + File.separator + "javamancer-settings.properies"
            );
        }
    }

    private void initDatFolder() {
        File df = getDatFolder();
        if (df.exists() && df.isDirectory() && df.canRead() && df.canWrite() && df.canExecute()) {
            LOGGER.log(Level.SEVERE, "DAT Folder exists and seems OK for use.");
            if (!datFilesPresent()) {
                LOGGER.log(Level.SEVERE, "DAT Files do not seem to be installed yet. User must do this manually.");
            }
        } else {
            if (df.exists()) {
                LOGGER.log(Level.SEVERE, "DAT Folder exists but is either a file or permissions are wrong.");
            } else {
                LOGGER.log(Level.SEVERE, "DAT Folder does not exist. Creating...");
                if (df.mkdirs()) {
                    LOGGER.log(Level.SEVERE, "DAT Folder created.");
                } else {
                    LOGGER.log(Level.SEVERE, "DAT Folder could not be created. Unknown reason.");
                }
            }
        }
    }

    public void initCacheFolder(DAT dat) {
        File cacheFolder = getCacheFolder();
        if (cacheFolder.exists() && cacheFolder.isDirectory() && cacheFolder.canRead() && cacheFolder.canWrite() && cacheFolder.canExecute()) {
            LOGGER.log(Level.SEVERE, "Cache Folder exists and seems OK for use.");
            if (!cacheFilesPresent()) {
                LOGGER.log(Level.SEVERE, "Cache Files do not seem to be installed yet. Need to regenerate from DAT.");

                // Generate from DAT.
                DATUtil.createCache(dat, cacheFolder);
            }
        } else {
            if (cacheFolder.exists()) {
                LOGGER.log(Level.SEVERE, "Cache Folder exists but is either a file or permissions are wrong.");
            } else {
                LOGGER.log(Level.SEVERE, "Cache Folder does not exist. Creating...");
                if (cacheFolder.mkdirs()) {
                    LOGGER.log(Level.SEVERE, "Cache Folder created.");

                    // Generate from DAT.
                    DATUtil.createCache(dat, cacheFolder);
                } else {
                    LOGGER.log(Level.SEVERE, "Cache Folder could not be created. Unknown reason.");
                }
            }
        }
    }

    public HostServices getHostServices() {
        return hostServices;
    }

    public void setHostServices(HostServices hostServices) {
        this.hostServices = hostServices;
    }

    public File[] getDatFiles() {
        return new File[]{
            new File(getDatFolder(), "NEURO1.DAT"),
            new File(getDatFolder(), "NEURO2.DAT")
        };
    }

    public boolean datFilesPresent() {

        boolean retVal = true;
        File datFolder = getDatFolder();
        if (datFolder.isDirectory()) {
            File dat1 = new File(datFolder, "NEURO1.DAT");
            File dat2 = new File(datFolder, "NEURO2.DAT");
            if (dat1.isFile() && dat1.canRead()) {
                LOGGER.log(Level.CONFIG, "  Found: NEURO1.DAT");
            } else {
                LOGGER.log(Level.SEVERE, "Missing: NEURO1.DAT");
                retVal = false;
            }
            if (dat2.isFile() && dat2.canRead()) {
                LOGGER.log(Level.CONFIG, "  Found: NEURO2.DAT");
            } else {
                LOGGER.log(Level.SEVERE, "Missing: NEURO2.DAT");
                retVal = false;
            }
        } else {
            LOGGER.log(Level.SEVERE, "DAT Directory missing or is not a directory!");
            retVal = false;
        }

        return retVal;
    }

    public boolean cacheFilesPresent() {

        boolean retVal = true;
        File cacheFolder = getCacheFolder();
        if (cacheFolder.isDirectory()) {
            File anhDir = new File(cacheFolder, "ANH");
            File bihDir = new File(cacheFolder, "BIH");
            File imhDir = new File(cacheFolder, "IMH");
            File picDir = new File(cacheFolder, "PIC");

            if (anhDir.isDirectory() && anhDir.canRead()) {
                LOGGER.log(Level.CONFIG, "  Found: ANH Folder");
                // Check sub-folders against ANH model list.
            } else {
                LOGGER.log(Level.SEVERE, "Missing: ANH Folder");
                retVal = false;
            }
            if (bihDir.isDirectory() && bihDir.canRead()) {
                LOGGER.log(Level.CONFIG, "  Found: BIH Folder");
                // Check sub-folders against BIH model list.
            } else {
                LOGGER.log(Level.SEVERE, "Missing: BIH Folder");
                retVal = false;
            }
            if (imhDir.isDirectory() && imhDir.canRead()) {
                LOGGER.log(Level.CONFIG, "  Found: IMH Folder");
                // Check sub-folders against IMH model list.
            } else {
                LOGGER.log(Level.SEVERE, "Missing: IMH Folder");
                retVal = false;
            }
            if (picDir.isDirectory() && picDir.canRead()) {
                LOGGER.log(Level.CONFIG, "  Found: PIC Folder");
                // Check sub-folders against PIC model list.
            } else {
                LOGGER.log(Level.SEVERE, "Missing: PIC Folder");
                retVal = false;
            }
        } else {
            LOGGER.log(Level.SEVERE, "Cache Directory missing or is not a directory!");
            retVal = false;
        }

        return retVal;
    }

    public File getDatFolder() {
        return new File(getPropFile().getParentFile(), DAT_DIR);
    }

    public File getCacheFolder() {
        return new File(getPropFile().getParentFile(), CACHE_DIR);
    }

    public File getImhFolder() {
        return new File(getCacheFolder(), "imh");
    }

    public File getPicFolder() {
        return new File(getCacheFolder(), "pic");
    }

    public File getBihFolder() {
        return new File(getCacheFolder(), "bih");
    }

    public File getAnhFolder() {
        return new File(getCacheFolder(), "anh");
    }

    public String getSaveFolder() {
        return getPropFile().getParent();
    }
}
