package siemieniuk.animals.core;

import siemieniuk.animals.core.animals.AnimalRepository;
import siemieniuk.animals.core.animals.Prey;
import siemieniuk.animals.core.locations.LocationRepository;

import java.util.concurrent.TimeUnit;

public final class WorldRunnable implements Runnable {
    AnimalRepository animalRepository;

    public WorldRunnable(AnimalRepository animalRepository) {
        this.animalRepository = animalRepository;
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
                    for (Prey p : animalRepository.getPreys()) {
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
}
