package de.sgoral.darkestalmanac.control;

import de.sgoral.darkestalmanac.data.dataobjects.DataStorage;
import de.sgoral.darkestalmanac.data.persistence.PersistenceUtil;

import java.io.File;
import java.io.IOException;
import java.util.prefs.Preferences;

/**
 * Created by sebastianprivat on 11.07.17.
 */
public class PreferencesUtil {

    public static final String PREF_KEY_DATA_STORAGE = "data_storage";
    private static final Preferences preferences = Preferences.userNodeForPackage(PreferencesUtil.class);

    private PreferencesUtil() {

    }

    public static File getStorageFile() {
        String path = preferences.get(PREF_KEY_DATA_STORAGE, null);
        if (path == null) {
            return null;
        }
        return new File(path);
    }

    public static void setStorageFile(File location) {
        if (location == null) {
            preferences.remove(PREF_KEY_DATA_STORAGE);
        } else {
            preferences.put(PREF_KEY_DATA_STORAGE, location.getAbsolutePath());
        }
    }

    public static void saveStorageData(File file, DataStorage dataStorage) {
        try {
            PersistenceUtil.save(file, dataStorage);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static DataStorage loadStorageData(File file) {
        try {
            return PersistenceUtil.load(file);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
