package com.siemieniuk.animals;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Random;

/**
 * Represents world; uses singleton pattern
 * @author Szymon Siemieniuk
 * @version 0.1
 */
public class World implements Runnable {
	private static World instance;
	private int howManySources = -1;
	private int howManyHideouts = -1;
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
		getHowManyHideouts();
		getHowManySources();
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

	// TODO: AnimatioTimer
	@Override
	public void run() {
		final int FPS = 30;
		final int STEP_MS = 1000/FPS;
		boolean flag = false;
		try {
			Thread.sleep(STEP_MS);
		} catch (InterruptedException e) {
			flag = true;
		}

		int clock = 0;
		while (!flag) {
			for (Integer t : threads.keySet()) {
				if (animals.get(t) == null) {
					removeAnimal(t);
				}
			}
			if (clock == STEP_MS) {
				System.out.println(animals.keySet());
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
	}
	
	/* TODO: Implement */
	/**
	 * Changes route of animal
	 * @param a Animal to modify a route
	 * @param l A new destination
	 */
	public void changeRouteOfAnimal(Animal a, Location l) {
		
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
	}

	public void removeAnimal(Integer id) {
		for (Animal a : animals.values()) {
			if (a.getId().equals(id)) {
				if (threads.get(id) != null) {
					threads.get(id).interrupt();
				}
				threads.remove(id);
				animals.remove(id);
				System.out.println("Removed animal ID=" + id);
				break;
			}
		}
	}

	public int getHowManySources() {
		if (howManySources == -1) {
			howManySources = 0;
			for (Location l : locations.values()) {
				if (l instanceof Source) {
					howManySources++;
				}
			}
		}
		return howManySources;
	}

	public int getHowManyHideouts() {
		if (howManyHideouts == -1) {
			howManyHideouts = 0;
			for (Location l : locations.values()) {
				if (l instanceof Hideout) {
					howManyHideouts++;
				}
			}
		}
		return howManyHideouts;
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
		return "Hideouts: " + howManyHideouts + ", sources: " + howManySources + "\n" + locations.toString();
	}

	public int getxSize() {
		return xSize;
	}

	public int getySize() {
		return ySize;
	}

	public List<Prey> getPreys() {
		List<Prey> l = new ArrayList<>();
		for (Animal a : animals.values()) {
			if (a instanceof Prey) {
				l.add((Prey) a);
			}
		}
		return l;
	}

	public Hashtable<Coordinates, Location> getLocations() {
		return locations;
	}

	public List<Animal> getAnimals() { return new ArrayList<>(animals.values()); }
}
