package simulation;

public abstract class Collision extends AbstractEvent{

    private Particle[] ps;
    private Particle particle1, particle2;
    private final int collisionCount1, collisionCount2;

    /**
     * Constructor for Collision
     */
    public Collision(double t, Particle[] ps) {
        super(t);
        this.ps = ps;


        if (ps[0] != null) {
            particle1 = ps[0];
            collisionCount1 = particle1.collisions();
        } else {
            particle1 = null;
            collisionCount1 = -1;
        }

        if (ps.length > 1 && ps[1] != null) {
            particle2 = ps[1];
            collisionCount2 = particle2.collisions();
        } else {
            particle2 = null;
            collisionCount2 = -1;
        }
    }

    /**
     * Returns true if this Collision is (still) valid.
     */
    @Override
    public boolean isValid() {
        if (particle1 != null && particle1.collisions() != collisionCount1) {
            return false;
        }
        if (particle2 != null && particle2.collisions() != collisionCount2) {
            return false;
        }
        return true;
    }

    /**
     * Returns an array containing the Particles involved in this Collision.
     */
    public Particle[] getParticles() {
        return ps;
    }
}