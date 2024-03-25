package siemieniuk.animals.gui;

import siemieniuk.animals.core.animals.Animal;
import siemieniuk.animals.core.animals.AnimalRepository;
import siemieniuk.animals.core.animals.Predator;
import siemieniuk.animals.core.animals.Prey;

public class AnimalViewFactory {
    public static AnimalView getAnimalView(AnimalRepository animalRepository, Animal animal) {
        return switch (animal.getClass().getSimpleName()) {
            case "Prey" -> new PreyView(animalRepository, (Prey) animal);
            case "Predator" -> new PredatorView(animalRepository, (Predator) animal);
            default -> throw new IllegalArgumentException("Unknown type! Should be animal!");
        };
    }
}
