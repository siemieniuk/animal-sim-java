package siemieniuk.animals.core;

import siemieniuk.animals.core.animals.Animal;
import siemieniuk.animals.core.animals.Predator;
import siemieniuk.animals.core.animals.Prey;
import siemieniuk.animals.core.locations.Location;
import siemieniuk.animals.core.locations.Path;
import siemieniuk.animals.math.Coordinates;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * <p>This class represents a HOBH world; uses singleton pattern</p>
 * <p><strong>REMARK: </strong>Create a reference by getInstance(), create the object by using init()</p>
 * @author Szymon Siemieniuk
 * @version 0.1
 */
public final class World implements Runnable {
	private static volatile World instance;
	private static int HOW_MANY_ANIMALS = 0;
	private final int X_SIZE;
	private final int Y_SIZE;
	private final ConcurrentHashMap<Integer, Animal> animals;
	private final ConcurrentHashMap<Coordinates, Location> locations;
	private final Random random;

	/**
	 * Generates a world
	 * @param locations List containing all locations in the world
	 * @param X_SIZE Size in horizontal axis
	 * @param Y_SIZE Size in vertical axis
	 */
	private World(ConcurrentHashMap<Coordinates, Location> locations, int X_SIZE, int Y_SIZE) {
		this.locations = locations;
		this.X_SIZE = X_SIZE;
		this.Y_SIZE = Y_SIZE;
		this.animals = new ConcurrentHashMap<>();
		this.random = new Random();
	}

	/**
	 * Used in place of constructor
	 * @param locations List of possible locations
	 * @param X_SIZE World's horizontal size
	 * @param Y_SIZE World's vertical size
	 * @return Instance of world
	 */
	public static World init(ConcurrentHashMap<Coordinates, Location> locations, int X_SIZE, int Y_SIZE) {
		if (instance != null) {
			throw new AssertionError("World was previously created!");
		}
		instance = new World(locations, X_SIZE, Y_SIZE);
		return instance;
	}

	/**
	 * Returns an instance to the world.
	 * @return A world's instance
	 */
	public static World getInstance() {
		if (instance == null) {
			throw new AssertionError("World does not exist!");
		}
		return instance;
	}

	@Override
	public void run() {
		final int FPS = 30;
		final int STEP_MS = 1000 / FPS;
		try {
			Thread.sleep(STEP_MS);
			int clock = 0;
			while (true) {
				if (clock == STEP_MS) {
					for (Prey p : getPreys()) {
						p.decreaseStatistics();
					}
					clock = 0;
				} else {
					clock++;
				}
				try {
					TimeUnit.MILLISECONDS.sleep(STEP_MS);
				} catch (InterruptedException e) {
					break;
				}
			}
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
		}
	}

	/**
	 * Adds a new predator to the world
	 * @param predator A predator
	 */
	public void createAnimal(Predator predator) {
		int x = random.nextInt(X_SIZE);
		int y = random.nextInt(Y_SIZE);
		predator.setPos(new Coordinates(x, y));
		pushAnimal(predator);
	}

	/**
	 * Adds a new prey to the world
	 * @param prey A prey
	 */
	public void createAnimal(Prey prey) {
		ArrayList<Location> locCopy = new ArrayList<>(locations.values());
		int idx;
		do {
			idx = random.nextInt(locCopy.size());
		} while (!(locCopy.get(idx) instanceof Path));
		prey.setPos(locCopy.get(idx).getPos());
		pushAnimal(prey);
	}

	private void pushAnimal(Animal animal) {
		animals.put(animal.getId(), animal);

		Thread t = new Thread(animal);
		t.setName(animal.getMetadataCode().toString()+"-"+animal.getId());
		t.start();

		HOW_MANY_ANIMALS++;
	}

	/**
	 * Removes a specific animal from the world
	 * @param animalToRemove Animal to remove
	 */
	public synchronized void removeAnimal(Animal animalToRemove) {
		if (animals.containsValue(animalToRemove)) {
			animalToRemove.kill();
			animalToRemove.releaseResources();
			animals.remove(animalToRemove.getId());
			HOW_MANY_ANIMALS = Math.max(0, HOW_MANY_ANIMALS-1);
		}
	}

	/**
	 * Returns list of DetailsPrintable objects.
	 * @param pos The position for which the list must be generated
	 * @return A list of objects to draw on canvas
	 */
	public List<Object> getObjectsAt(Coordinates pos) {
		List<Object> list = new ArrayList<>();
		if (locations.get(pos) != null) {
			Location loc = locations.get(pos);
			list.add(loc);
		}
		for (Animal a : animals.values()) {
			if (a.getPos().equals(pos)) {
				list.add(a);
			}
		}
		return list;
	}

	@Override
	public String toString() {
		return "World";
	}

	public int getX_SIZE() {
		return X_SIZE;
	}

	public int getY_SIZE() {
		return Y_SIZE;
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

	public ConcurrentHashMap<Coordinates, Location> getLocations() {
		return locations;
	}

	public Location getLocation(Coordinates locPos) {
		return locations.get(locPos);
	}

	public synchronized List<Animal> getAnimals() { return new ArrayList<>(animals.values()); }
}
