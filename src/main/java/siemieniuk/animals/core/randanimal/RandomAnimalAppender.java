package siemieniuk.animals.core.randanimal;

import siemieniuk.animals.core.World;
import siemieniuk.animals.core.animals.Predator;
import siemieniuk.animals.core.animals.Prey;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RandomAnimalAppender {
    private static List<String> preySpecies;
    private static List<String> predatorSpecies;
    private static List<String> names;

    public static void loadConfig(URL pathToPreys, URL pathToPredators, URL pathToNames) {
        preySpecies = new ArrayList<>();
        predatorSpecies = new ArrayList<>();
        names = new ArrayList<>();

        loadSpeciesTo(pathToPreys, preySpecies);
        loadSpeciesTo(pathToPredators, predatorSpecies);
        loadSpeciesTo(pathToNames, names);
    }

    private static void loadSpeciesTo(URL path, List<String> species) {
        BufferedReader reader;
        try {
            reader = new BufferedReader(new InputStreamReader(path.openStream()));

            String line = reader.readLine();
            while (line != null) {
                species.add(line);
                line = reader.readLine();
            }

            reader.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static final Random random = new Random();
    public static void newPrey() {
        String name = randomName();
        int health = 100;
        int speed = 1;
        int strength = (int) normalDistribution(4.0, 1.0);
        String species = randomPreySpecies();
        Prey prey = new Prey(name, health, speed, strength, species, 2, 3);
        World.getInstance().createAnimal(prey);
    }

    public static void newPredator() {
        String name = randomName();
        int health = 100;
        int speed = 2;
        int strength = (int) normalDistribution(12.0, 2.0);
        String species = randomPredatorSpecies();
        Predator p = new Predator(name, health, speed, strength, species);
        World.getInstance().createAnimal(p);
    }

    private static double normalDistribution(double mean, double stdev) {
        return random.nextGaussian()*stdev + mean;
    }

    private static String randomName() {
        return names.get(random.nextInt(names.size()));
    }

    private static String randomPreySpecies() {
        return preySpecies.get(random.nextInt(preySpecies.size()));
    }

    private static String randomPredatorSpecies() {
        return predatorSpecies.get(random.nextInt(predatorSpecies.size()));
    }
}
