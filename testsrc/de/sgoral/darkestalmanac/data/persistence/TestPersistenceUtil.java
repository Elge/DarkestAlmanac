package de.sgoral.darkestalmanac.data.persistence;

import de.sgoral.darkestalmanac.data.dataobjects.*;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Test saving and loading of the data state.
 */
public class TestPersistenceUtil {

    @Test
    public void testSave() throws IOException {
        DataStorage dataStorage = new DataStorage();

        Location ruins = new Location(1, "Ruins");
        Location cove = new Location(2, "Cove");

        Curio barnacleCrustedChest = new Curio(3, "Barnacle Crusted Chest", cove);
        Curio heirloomChest = new Curio(4, "Heirloom Chest", ruins);

        Consumable key = new Consumable(5, "Key");
        Consumable shovel = new Consumable(6, "Shovel");

        Effect loot = new Effect(7, "Loot", true);
        Effect bleed = new Effect(8, "Bleed", false);

        Experiment experiment1 = new Experiment(17, new Result(9, loot, "Gems, Gold", 1));
        barnacleCrustedChest.addExperiment(experiment1);
        experiment1.addResult(new Result(10, bleed, 1));

        Result result = new Result(11, loot);
        result.incrementTimes();
        Experiment experiment2 = new Experiment(12, shovel, result);
        barnacleCrustedChest.addExperiment(experiment2);

        heirloomChest.addLocation(cove);
        Experiment experiment3 = new Experiment(18, new Result(13, loot, "Artifacts"));
        heirloomChest.addExperiment(experiment3);
        experiment3.addResult(new Result(14, bleed));
        experiment3.addResult(new Result(15, null, 4));

        Experiment experiment4 = new Experiment(19, key, new Result(16, loot, 2));
        heirloomChest.addExperiment(experiment4);

        dataStorage.addLocation(ruins);
        dataStorage.addLocation(cove);
        dataStorage.addCurio(barnacleCrustedChest);
        dataStorage.addCurio(heirloomChest);
        dataStorage.addConsumable(key);
        dataStorage.addConsumable(shovel);
        dataStorage.addEffect(loot);
        dataStorage.addEffect(bleed);

        File outputFile = File.createTempFile("darkestalmanac_test", "json");
        outputFile.deleteOnExit();

        URL expectedResource = System.class.getResource("/testdata.json");
        File expectedFile = new File(expectedResource.getFile());
        List<String> expectedLines = Files.readAllLines(expectedFile.toPath());

        PersistenceUtil.save(outputFile, dataStorage, true);
        List<String> actualLines = Files.readAllLines(outputFile.toPath());
        compareLinesArrays(expectedLines, actualLines);

        PersistenceUtil.save(outputFile, dataStorage);
        actualLines = Files.readAllLines(outputFile.toPath());
        compareLinesArrays(expectedLines, actualLines);
    }

    private void compareLinesArrays(List<String> expectedLines, List<String> actualLines) {
        if (expectedLines == null) {
            assertNull(actualLines);
        } else {
            assertNotNull(actualLines);

            assertEquals(expectedLines.size(), actualLines.size());
            for (int i = 0; i < expectedLines.size(); i++) {
                assertEquals(expectedLines.get(0), actualLines.get(0));
            }
        }
    }

    @Test
    public void testLoad() throws IOException, ClassNotFoundException {
        URL resource = System.class.getResource("/testdata.json");
        File inputFile = new File(resource.getFile());
        DataStorage dataStorage = PersistenceUtil.load(inputFile);

        assertNotNull(dataStorage);

        // Validate locations
        assertNotNull(dataStorage.getLocations());
        assertEquals(2, dataStorage.getLocations().size());

        Location ruins = dataStorage.getLocations().get(0);
        assertNotNull(ruins);
        assertEquals(1, ruins.getId());
        assertEquals("Ruins", ruins.getName());

        Location cove = dataStorage.getLocations().get(1);
        assertNotNull(cove);
        assertEquals(2, cove.getId());
        assertEquals("Cove", cove.getName());

        // Validate consumables
        assertNotNull(dataStorage.getConsumables());
        assertEquals(2, dataStorage.getConsumables().size());

        Consumable key = dataStorage.getConsumables().get(0);
        assertNotNull(key);
        assertEquals(5, key.getId());
        assertEquals("Key", key.getName());

        Consumable shovel = dataStorage.getConsumables().get(1);
        assertNotNull(shovel);
        assertEquals(6, shovel.getId());
        assertEquals("Shovel", shovel.getName());

        // Validate effects
        assertNotNull(dataStorage.getEffects());
        assertEquals(2, dataStorage.getEffects().size());

        Effect loot = dataStorage.getEffects().get(0);
        assertNotNull(loot);
        assertEquals(7, loot.getId());
        assertEquals("Loot", loot.getName());
        assertTrue(loot.isPositive());

        Effect bleed = dataStorage.getEffects().get(1);
        assertNotNull(bleed);
        assertEquals(8, bleed.getId());
        assertEquals("Bleed", bleed.getName());
        assertFalse(bleed.isPositive());

        // Validate curios
        assertNotNull(dataStorage.getCurios());
        assertEquals(2, dataStorage.getCurios().size());

        Curio barnacleCrustedChest = dataStorage.getCurios().get(0);
        assertNotNull(barnacleCrustedChest);
        assertEquals(3, barnacleCrustedChest.getId());
        assertEquals("Barnacle Crusted Chest", barnacleCrustedChest.getName());

        assertNotNull(barnacleCrustedChest.getLocations());
        assertEquals(1, barnacleCrustedChest.getLocations().size());
        assertEquals(cove, barnacleCrustedChest.getLocations().get(0));

        assertNotNull(barnacleCrustedChest.getExperiments());
        assertEquals(2, barnacleCrustedChest.getExperiments().size());

        Experiment experiment1 = barnacleCrustedChest.getExperiments().get(0);
        assertNotNull(experiment1);
        assertEquals(17, experiment1.getId());
        assertNull(experiment1.getConsumable());
        assertNotNull(experiment1.getResults());
        assertEquals(2, experiment1.getResults().size());

        Result result1 = experiment1.getResults().get(0);
        assertNotNull(result1);
        assertEquals(9, result1.getId());
        assertEquals(loot, result1.getEffect());
        assertEquals(1, result1.getTimes());
        assertEquals("Gems, Gold", result1.getComment());

        Result result2 = experiment1.getResults().get(1);
        assertNotNull(result2);
        assertEquals(10, result2.getId());
        assertEquals(bleed, result2.getEffect());
        assertEquals(1, result2.getTimes());
        assertNull(result2.getComment());

        Experiment experiment2 = barnacleCrustedChest.getExperiments().get(1);
        assertNotNull(experiment2);
        assertEquals(12, experiment2.getId());
        assertEquals(shovel, experiment2.getConsumable());
        assertNotNull(experiment2.getResults());
        assertEquals(1, experiment2.getResults().size());

        Result result3 = experiment2.getResults().get(0);
        assertNotNull(result3);
        assertEquals(11, result3.getId());
        assertEquals(loot, result3.getEffect());
        assertNull(result3.getComment());
        assertEquals(2, result3.getTimes());

        Curio heirloomChest = dataStorage.getCurios().get(1);
        assertNotNull(heirloomChest);
        assertEquals(4, heirloomChest.getId());
        assertEquals("Heirloom Chest", heirloomChest.getName());

        assertNotNull(heirloomChest.getLocations());
        assertEquals(2, heirloomChest.getLocations().size());
        assertEquals(ruins, heirloomChest.getLocations().get(0));
        assertEquals(cove, heirloomChest.getLocations().get(1));

        assertNotNull(heirloomChest.getExperiments());
        assertEquals(2, heirloomChest.getExperiments().size());

        Experiment experiment3 = heirloomChest.getExperiments().get(0);
        assertNotNull(experiment3);
        assertEquals(18, experiment3.getId());
        assertNull(experiment3.getConsumable());
        assertNotNull(experiment3.getResults());
        assertEquals(3, experiment3.getResults().size());

        Result result4 = experiment3.getResults().get(0);
        assertNotNull(result4);
        assertEquals(13, result4.getId());
        assertEquals(loot, result4.getEffect());
        assertEquals("Artifacts", result4.getComment());
        assertEquals(1, result4.getTimes());

        Result result5 = experiment3.getResults().get(1);
        assertNotNull(result5);
        assertEquals(14, result5.getId());
        assertEquals(bleed, result5.getEffect());
        assertNull(result5.getComment());
        assertEquals(1, result5.getTimes());

        Result result6 = experiment3.getResults().get(2);
        assertNotNull(result6);
        assertEquals(15, result6.getId());
        assertNull(result6.getComment());
        assertNull(result6.getEffect());
        assertEquals(4, result6.getTimes());

        Experiment experiment4 = heirloomChest.getExperiments().get(1);
        assertNotNull(experiment4);
        assertEquals(19, experiment4.getId());
        assertEquals(key, experiment4.getConsumable());
        assertNotNull(experiment4.getResults());
        assertEquals(1, experiment4.getResults().size());

        Result result7 = experiment4.getResults().get(0);
        assertNotNull(result7);
        assertEquals(16, result7.getId());
        assertEquals(loot, result7.getEffect());
        assertEquals(2, result7.getTimes());
        assertNull(result7.getComment());
    }

}