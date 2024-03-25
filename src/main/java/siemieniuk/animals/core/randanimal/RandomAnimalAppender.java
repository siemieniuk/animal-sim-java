package siemieniuk.animals.core.randanimal;

import siemieniuk.animals.core.animals.AnimalRepository;
import siemieniuk.animals.core.animals.Predator;
import siemieniuk.animals.core.animals.Prey;
import siemieniuk.animals.core.locations.LocationRepository;
import siemieniuk.animals.math.Coordinates;

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
    public static void newPrey(AnimalRepository animalRepository, LocationRepository locationRepository) {
        String name = randomName();
        int health = 100;
        int speed = 1;
        int strength = (int) normalDistribution(4.0, 1.0);
        String species = randomPreySpecies();

        Prey prey = new Prey(animalRepository, locationRepository, name, health, speed, strength, species, 2, 3);
        Coordinates coordinates = locationRepository.getRandomPathLocation();
        prey.setPos(coordinates);
//        World.getInstance().createAnimal(prey);
        animalRepository.push(prey);
    }

    public static void newPredator(AnimalRepository animalRepository, LocationRepository locationRepository) {
        String name = randomName();
        int health = 100;
        int speed = 2;
        int strength = (int) normalDistribution(12.0, 2.0);
        String species = randomPredatorSpecies();

        Predator p = new Predator(animalRepository, locationRepository, name, health, speed, strength, species);
        Coordinates coordinates = locationRepository.getRandomValidPosition();
        p.setPos(coordinates);
        animalRepository.push(p);
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
