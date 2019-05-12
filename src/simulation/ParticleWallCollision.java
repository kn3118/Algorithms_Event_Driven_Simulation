package simulation;

public class ParticleWallCollision extends Collision {

  private Particle particle;
  private Wall wall;

  public ParticleWallCollision(Particle particle, Wall wall, double t) {
    super(t, new Particle[] {particle});
    this.particle = particle;
    this.wall = wall;
  }

  @Override
  public void happen(ParticleEventHandler h) {
    particle.collide(particle, wall);
    h.reactTo(this);
  }
}
