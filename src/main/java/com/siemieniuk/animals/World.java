package com.siemieniuk.animals;

import com.siemieniuk.animals.math.Coordinates;

import java.util.*;

/**
 * Represents world; uses singleton pattern
 * @author Szymon Siemieniuk
 * @version 0.1
 */
public class World implements Runnable {
	private static World instance;
	public static int HOW_MANY_ANIMALS = 0;
	private final int xSize;
	private final int ySize;
	private final Hashtable<Integer, Animal> animals;
	private final Hashtable<Coordinates, Location> locations;
	private final Hashtable<Integer, Thread> threads;

	private Random rd;

	private World() {
		this.animals = null;
		this.locations = null;
		this.threads = null;
		this.xSize = 0;
		this.ySize = 0;
	}

	/**
	 * Generates a world
	 * @param locations List containing all locations in the world
	 * @param xSize Size in horizontal axis
	 * @param ySize Size in vertical axis
	 */
	private World(Hashtable<Coordinates, Location> locations, int xSize, int ySize) {
		this.locations = locations;
		this.xSize = xSize;
		this.ySize = ySize;
		this.animals = new Hashtable<> ();
		this.threads = new Hashtable<>();
		this.rd = new Random();
	}

	public static World init(Hashtable<Coordinates, Location> locations, int xSize, int ySize) {
		if (World.instance != null) {
			throw new AssertionError("World was previously created!");
		}
		World.instance = new World(locations, xSize, ySize);
		return World.instance;
	}

	public static World getInstance() {
		if (World.instance == null) {
			throw new AssertionError("World does not exist!");
		}
		return World.instance;
	}

	@Override
	public void run() {
		final int FPS = 30;
		final int STEP_MS = 1000 / FPS;
		try {
			Thread.sleep(STEP_MS);
			int clock = 0;
			while (true) {
				assert threads != null;
				for (Integer t : threads.keySet()) {
					assert animals != null;
					if (animals.get(t) == null) {
						removeAnimal(t);
					}
				}
				if (clock == STEP_MS) {
					System.out.println("Animals: " + animals.keySet());
					System.out.println("Threads: " + threads.keySet());
					System.out.println("-----------------------------");
					for (Prey p : getPreys()) {
						p.decreaseStatistics();
					}
					clock = 0;
				} else {
					clock++;
				}
				try {
					Thread.sleep(STEP_MS);
				} catch (InterruptedException e) {
					break;
				}
			}
		} catch (InterruptedException e) {
//			flag = true;
			Thread.currentThread().interrupt();
		}
	}

	/**
	 * Adds a new animal to the world
	 * @param a An animal
	 */
	public void createAnimal(Animal a) {
		if (a instanceof Prey) {
			ArrayList<Location> locCopy = new ArrayList<>(locations.values());
			int idx = -1;
			do {
				idx = rd.nextInt(locCopy.size());
			} while (!(locCopy.get(idx) instanceof Path));
			a.setPos(locCopy.get(idx).getPos());
		} else if (a instanceof Predator) {
			int x = rd.nextInt(xSize);
			int y = rd.nextInt(ySize);
			a.setPos(new Coordinates(x, y));
		}
		animals.put(a.getId(), a);
		Thread t = new Thread(a);
		t.start();
		threads.put(a.getId(), t);
		HOW_MANY_ANIMALS++;
	}

	public synchronized void removeAnimal(Integer id) {
		if (threads.get(id) != null) {
			threads.get(id).interrupt();
		}
		threads.remove(id);
		Location loc = locations.get(animals.get(id).getPos());
		if (loc instanceof Intersection) {
			((Intersection) loc).unsetUsedBy();
		}
		Animal animal = animals.get(id);
		if (animal != null) {
			if (animal instanceof Prey) {
				((Prey) animal).releaseResources();
			}
			animals.remove(id);
		}
		HOW_MANY_ANIMALS = Math.max(0, HOW_MANY_ANIMALS-1);
	}

	public List<DetailsPrintable> getObjectsToDraw(Coordinates pos) {
		List<DetailsPrintable> list = new ArrayList<>();
		if (locations.get(pos) != null) {
			list.add(locations.get(pos));
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

	public int getxSize() {
		return xSize;
	}

	public int getySize() {
		return ySize;
	}

	public synchronized List<Prey> getPreys() {
		List<Prey> l = new ArrayList<>();
		Iterator<Animal> it = animals.values().iterator();
		Animal next;
		while (it.hasNext()) {
			next = it.next();
			if (next instanceof Prey) {
				l.add((Prey) next);
			}
		}
		return l;
	}

	public Hashtable<Coordinates, Location> getLocations() {
		return locations;
	}

	public Location getLocation(Coordinates locPos) {
		return locations.get(locPos);
	}

	public List<Animal> getAnimals() { return new ArrayList<>(animals.values()); }
}
