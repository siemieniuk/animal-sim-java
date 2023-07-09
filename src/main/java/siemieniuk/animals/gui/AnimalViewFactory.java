package siemieniuk.animals.gui;

import siemieniuk.animals.core.animals.Animal;
import siemieniuk.animals.core.animals.Predator;
import siemieniuk.animals.core.animals.Prey;

public class AnimalViewFactory {
    public static AnimalView getAnimalView(Animal animal) {
        return switch (animal.getClass().getSimpleName()) {
            case "Prey" -> new PreyView((Prey) animal);
            case "Predator" -> new PredatorView((Predator) animal);
            default -> throw new IllegalArgumentException("Unknown type! Should be animal!");
        };
    }
}
