package hu.bme.mit.spaceship;

/**
 * Class for firing and managing a LaserGun
 */
public class LaserGun implements Fireable {

  private int photons = 0;

  private static final int INITIAL_PHOTON_COUNT = 1000000;

  public LaserGun() { this.photons = INITIAL_PHOTON_COUNT; }

  public LaserGun(int numberOfPhotons) {
    this.photons = numberOfPhotons;
  }

  @Override
  public boolean fire(int numberOfPhotons) {
    if (numberOfPhotons < 1 || numberOfPhotons > this.photons) {
      throw new IllegalArgumentException("numberOfPhotons");
    }

    photons -= numberOfPhotons;

    return true;
  }

  @Override
  public boolean isEmpty() {
    return this.photons <= 0;
  }
}
