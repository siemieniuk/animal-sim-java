package siemieniuk.animals.core.animals;

import siemieniuk.animals.math.Coordinates;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public class AnimalRepository {
    private final ConcurrentHashMap<Integer, Animal> animals;

    public AnimalRepository() {
        this.animals = new ConcurrentHashMap<>();
    }

    public void push(Animal animal) {
        animals.put(animal.getId(), animal);

        Thread t = new Thread(animal);
        t.setName(animal.getMetadataCode().toString()+"-"+animal.getId());
        t.start();
    }

    /**
     * Removes a specific animal from the world
     * @param animalToRemove Animal to remove
     */
    public synchronized void remove(Animal animalToRemove) {
        if (animals.containsValue(animalToRemove)) {
            animalToRemove.kill();
            animalToRemove.releaseResources();
            animals.remove(animalToRemove.getId());
        }
    }

    /**
     * Returns list of preys
     * @return list of preys
     */
    public synchronized List<Prey> getPreys() {
        return animals.values()
                .stream()
                .filter(Prey.class::isInstance)
                .map(Prey.class::cast)
                .collect(Collectors.toList());
    }

    public synchronized List<Animal> getAnimals() { return new ArrayList<>(animals.values()); }

    public synchronized List<Animal> getAnimalsAt(Coordinates pos) {
        List<Animal> result = new ArrayList<>();

        for (Animal animal : animals.values()) {
            if (animal.getPos().equals(pos)) {
                result.add(animal);
            }
        }
        return result;
    }
}
