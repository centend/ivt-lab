package hu.bme.mit.spaceship;

import java.util.Random;

/**
* Class storing and managing the torpedos of a ship
*/
public class TorpedoStore implements Fireable {

  private int torpedos = 0;
  private Random generator = new Random();

  private static final int INITIAL_TORPEDO_COUNT = 10;

  public TorpedoStore() { this.torpedos = INITIAL_TORPEDO_COUNT; }

  public TorpedoStore(int numberOfTorpedos) {
    this.torpedos = numberOfTorpedos;
  }

  @Override
  public boolean fire(int numberOfTorpedos) {
    if(numberOfTorpedos < 1 || numberOfTorpedos > this.torpedos){
      throw new IllegalArgumentException("numberOfTorpedos");
    }

    boolean success = false;

    //simulate random overheating of the launcher bay which prevents firing
    double r = generator.nextDouble();

    if (r > 0.01) {
      // successful firing
      this.torpedos -= numberOfTorpedos;
      success = true;
    } else {
      // failure
      success = false;
    }

    return success;
  }

  @Override
  public boolean isEmpty() {
    return this.torpedos <= 0;
  }

  public int getNumberOfTorpedos() {
    return this.torpedos;
  }
}
