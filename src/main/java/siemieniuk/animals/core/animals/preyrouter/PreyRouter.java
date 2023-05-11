package siemieniuk.animals.core.animals.preyrouter;

import siemieniuk.animals.core.World;
import siemieniuk.animals.core.locations.Location;
import siemieniuk.animals.core.typing.WorldObjectType;
import siemieniuk.animals.math.Coordinates;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * This class is used for finding a route from A to B. It can also find the nearest target to the required source.
 * @author  Szymon Siemieniuk
 */
public abstract class PreyRouter {
    private final Coordinates source;
    private Coordinates target;
    private List<Coordinates> plan;
    private static final ConcurrentHashMap<Coordinates, Location> locations = World.getInstance().getLocations();

    /**
     * Constructor by Coordinates
     * @param source object of class Coordinates
     */
    public PreyRouter(Coordinates source) {
        this.source = source;
        this.target = null;
    }

    protected void findPlanToNearest(WorldObjectType type) {
        setTargetToNearest(type);
        setPlan();
    }

    /**
     * Finds the closest target of type T.
     * @param type Object being any class
     */
    private void setTargetToNearest(WorldObjectType type) {
        assert type.isLocation();
        Coordinates res = source;
        int minDistance = Integer.MAX_VALUE;
        for (Location l : locations.values()) {
            if (l.getMetadataCode().equals(type)) {
                Coordinates lPos = l.getPos();
                int dist = lPos.getManhattanDistanceTo(source);
                if (dist < minDistance) {
                    minDistance = dist;
                    res = lPos;
                }
            }
        }
        target = (minDistance == Integer.MAX_VALUE) ? null : res;
    }

    public Location getTarget() {
        return locations.get(target);
    }

    /**
     * Find new plan using BFS algorithm
     */
    private void setPlan() {
        assert source != null;
        // source is equal to target, then no plan
        if (source.equals(target)) {
            return;
        }
        plan = new ArrayList<>();
        HashMap<Coordinates, Coordinates> nodes = new HashMap<>();
        nodes.put(target, null);
        Set<Coordinates> visited = new HashSet<>();
        Queue<Coordinates> q = new LinkedList<>();
        q.add(target);
        Coordinates current;
        while (!q.isEmpty()) {
            current = q.poll();
            List<Coordinates> neighbors = getVonNeumannNeighborhood(current);
            for (Coordinates neighbor : neighbors) {
                if (visited.contains(neighbor)) {
                    continue;
                }
                q.add(neighbor);
                visited.add(current);
                if (neighbor.equals(source)) {
                    plan.add(neighbor);
                    Coordinates it = current;
                    while (it != null) {
                        plan.add(it);
                        it = nodes.get(it);
                    }
                    plan.remove(0);
                    return;
                } else {
                    nodes.put(neighbor, current);
                }
            }
        }
    }

    public List<Coordinates> getPlan() {
        return plan;
    }

    /**
     * Finds all neighbor locations (distance=1 horizontally, diagonally or vertically)
     * @param pos A specific position
     * @return All neighbors
     */
    private List<Coordinates> getMooreNeighborhood(Coordinates pos) {
        List<Coordinates> res = new ArrayList<>();
        for (int x=-1; x<=1; x++) {
            for (int y=-1; y<=1; y++) {
                int newX = pos.getX() + x;
                int newY = pos.getY() + y;
                Coordinates tmp = new Coordinates(newX, newY);
                if (locations.get(tmp) != null) {
                    if (!tmp.equals(pos)) {
                        res.add(tmp);
                    }
                }
            }
        }
        return res;
    }

    /**
     * Finds all neighbor locations (distance=1 horizontally or vertically)
     * @param pos A specific position
     * @return All neighbors
     */
    private List<Coordinates> getVonNeumannNeighborhood(Coordinates pos) {
        List<Coordinates> res = new ArrayList<>();
        Coordinates tmp = new Coordinates(pos.getX() + 1, pos.getY());
        if (locations.get(tmp) != null) {
            res.add(tmp);
        }
        tmp = new Coordinates(pos.getX() - 1, pos.getY());
        if (locations.get(tmp) != null) {
            res.add(tmp);
        }
        tmp = new Coordinates(pos.getX(), pos.getY() + 1);
        if (locations.get(tmp) != null) {
            res.add(tmp);
        }
        tmp = new Coordinates(pos.getX(), pos.getY() - 1);
        if (locations.get(tmp) != null) {
            res.add(tmp);
        }
        return res;
    }
}
