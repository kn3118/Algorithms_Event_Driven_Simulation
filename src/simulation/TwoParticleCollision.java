package simulation;

public class TwoParticleCollision extends Collision {

  private Particle particle1;
  private Particle particle2;

  public TwoParticleCollision(Particle particle1, Particle particle2, double t) {
    super(t, new Particle[] {particle1, particle2});
    this.particle1 = particle1;
    this.particle2 = particle2;
  }

  @Override
  public void happen(ParticleEventHandler h) {
    particle1.collide(particle1, particle2);
    h.reactTo(this);
  }
}
