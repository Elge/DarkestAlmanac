package de.sgoral.darkestalmanac.data.persistence;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import de.sgoral.darkestalmanac.data.dataobjects.*;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;

/**
 * Saves and loads data to and from disk.
 */
public class PersistenceUtil {

    private static final Type DATA_STORAGE_TYPE = new TypeToken<DataStorage>() {
    }.getType();

    private PersistenceUtil() {
    }

    public static void save(File outputFile, DataStorage dataStorage) throws IOException {
        prepareForSerialization(dataStorage);

        FileWriter writer = new FileWriter(outputFile, false);
        Gson gson = new GsonBuilder().setPrettyPrinting().serializeNulls().create();
        gson.toJson(dataStorage, writer);
        writer.close();
    }

    public static void saveDebug(File outputFile, DataStorage dataStorage) throws IOException {
        prepareForSerialization(dataStorage);

        FileWriter writer = new FileWriter(outputFile, false);
        Gson gson = new GsonBuilder().setPrettyPrinting().serializeNulls().create();
        String json = gson.toJson(dataStorage);
        System.out.println(json);
        writer.write(json);
        writer.close();
    }

    public static DataStorage load(File inputFile) throws IOException {
        Gson gson = new Gson();
        FileReader reader = new FileReader(inputFile);
        DataStorage dataStorage = gson.fromJson(reader, DATA_STORAGE_TYPE);
        reader.close();

        rebuildAssociations(dataStorage);
        return dataStorage;
    }

    /**
     * Sets IDs for transient properties.
     *
     * @param dataStorage The data storage object to prepare for serialization.
     */
    private static void prepareForSerialization(DataStorage dataStorage) {
        for (Curio curio : dataStorage.getCurios()) {
            curio.setLocationIds(new int[curio.getLocations().size()]);
            for (int i = 0; i < curio.getLocations().size(); i++) {
                curio.getLocationIds()[i] = curio.getLocations().get(i).getId();
            }

            for (Experiment experiment : curio.getExperiments()) {
                if (experiment.getConsumable() != null) {
                    experiment.setConsumableId(experiment.getConsumable().getId());
                }

                for (Result result : experiment.getResults()) {
                    if (result.getEffect() != null) {
                        result.setEffectId(result.getEffect().getId());
                    }
                }
            }
        }
    }

    /**
     * Sets transient object associations using ID fields.
     *
     * @param dataStorage The data storage object to rebuild associations for.
     */
    private static void rebuildAssociations(DataStorage dataStorage) {
        for (Location location : dataStorage.getLocations()) {
            if (location.getCurios() == null) {
                location.setCurios(new ArrayList<>());
            }
        }

        for (Curio curio : dataStorage.getCurios()) {
            if (curio.getLocations() == null) {
                curio.setLocations(new ArrayList<>());
            }

            for (int id : curio.getLocationIds()) {
                Location location = findLocation(dataStorage, id);

                if (location == null) {
                    throw new RuntimeException("Unknown location id"); // TODO handle gracefully
                }

                curio.addLocation(location);
                location.addCurio(curio);
            }

            for (Experiment experiment : curio.getExperiments()) {
                experiment.setConsumable(findConsumable(dataStorage, experiment.getConsumableId()));

                for (Result result : experiment.getResults()) {
                    result.setEffect(findEffect(dataStorage, result.getEffectId()));
                }
            }
        }
    }

    private static Location findLocation(DataStorage dataStorage, int id) {
        for (Location location : dataStorage.getLocations()) {
            if (location.getId() == id) {
                return location;
            }
        }

        return null;
    }

    private static Effect findEffect(DataStorage dataStorage, int id) {
        for (Effect effect : dataStorage.getEffects()) {
            if (effect.getId() == id) {
                return effect;
            }
        }

        return null;
    }

    private static Consumable findConsumable(DataStorage dataStorage, int id) {
        for (Consumable consumable : dataStorage.getConsumables()) {
            if (consumable.getId() == id) {
                return consumable;
            }
        }

        return null;
    }

}
