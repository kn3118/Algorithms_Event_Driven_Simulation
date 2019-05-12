package simulation;

import java.lang.reflect.InvocationTargetException;
import javax.swing.SwingUtilities;
import utils.MinPriorityQueue;

public class ParticleSimulation implements Runnable, ParticleEventHandler{

    private static final long FRAME_INTERVAL_MILLIS = 40;
    
    private final ParticlesModel          model;
    private final ParticlesView           screen;

    private MinPriorityQueue<Event> priorityQueue;
    private double time;

    /**
     * Constructor.
     */
    public ParticleSimulation(String name, ParticlesModel m) {
        this.model = m;
        this.screen = new ParticlesView(name, m);
        priorityQueue = new MinPriorityQueue<>();
        time = 0.0;

        priorityQueue.add(new Tick(1));
        Iterable<Collision> collisions = model.predictAllCollisions(time);
        for(Collision c : collisions) {
            priorityQueue.add(c);
        }
    }

    /**
     * Runs the simulation.
     */
    @Override
    public void run() {
        try {
            SwingUtilities.invokeAndWait(screen);
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        while (!priorityQueue.isEmpty()) {
            Event event = priorityQueue.remove();
            if (event.isValid()) {
                model.moveParticles(event.time() - time);
                time = event.time();
                event.happen(this);
            }
        }
    }

    @Override
    public void reactTo(Tick tick) {
        try {
            Thread.sleep(FRAME_INTERVAL_MILLIS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        screen.update();
        priorityQueue.add(new Tick(time + 1));
    }

    @Override
    public void reactTo(Collision c) {
        Particle[] particleArray = c.getParticles();

        for (Particle particle : particleArray) {
            for (Collision collision : model.predictCollisions(particle, time)) {
                priorityQueue.add(collision);
            }
        }
    }
}