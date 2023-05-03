package com.siemieniuk.animals.gui;

import com.siemieniuk.animals.core.animals.Animal;
import com.siemieniuk.animals.core.animals.Predator;
import com.siemieniuk.animals.core.animals.Prey;

public class AnimalViewFactory {
    public static AnimalView getAnimalView(Animal animal) {
        return switch (animal.getMetadataCode()) {
            case PREY -> new PreyView((Prey) animal);
            case PREDATOR -> new PredatorView((Predator) animal);
            default -> throw new IllegalArgumentException("Unknown type! Should be animal!");
        };
    }
}
