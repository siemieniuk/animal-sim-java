package com.siemieniuk.animals.gui;

import com.siemieniuk.animals.core.animals.Animal;
import com.siemieniuk.animals.core.typing.WorldObjectType;

public class AnimalViewFactory {
    public static AnimalView getAnimalView(Animal animal) {
        AnimalView view;
        return switch (animal.getMetadataCode()) {
            case PREDATOR -> new AnimalView(animal);
            case PREY -> new AnimalView(animal);
            default -> throw new IllegalArgumentException("Unknown type! Should be animal!");
        };
    }
}
