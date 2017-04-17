package hu.bme.mit.spaceship;

/**
 * Class for firing and managing a LaserGun
 */
public class LaserGun implements Fireable {

  private int photons = 0;

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
