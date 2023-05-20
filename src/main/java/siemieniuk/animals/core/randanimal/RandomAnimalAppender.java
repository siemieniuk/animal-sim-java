package siemieniuk.animals.core.randanimal;

import siemieniuk.animals.core.World;
import siemieniuk.animals.core.animals.Predator;
import siemieniuk.animals.core.animals.Prey;

import java.util.Random;

public class RandomAnimalAppender {
    private static final Random random = new Random();
    public static void newPrey() {
        Prey prey = new Prey("Affe", 100, 1, 4, "Giraffe", 2, 3);
        World.getInstance().createAnimal(prey);
    }

    public static void newPredator() {
        Predator p = new Predator("Simba", 100, 2, 13, "Lion");
        World.getInstance().createAnimal(p);
    }

    private static double normalDistribution(double mean, double stdev) {
        return random.nextGaussian()*stdev + mean;
    }
}
