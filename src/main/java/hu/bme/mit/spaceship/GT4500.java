package hu.bme.mit.spaceship;

/**
* A simple spaceship with two proton torpedos and four lasers
*/
public class GT4500 implements SpaceShip {

  private boolean wasPrimaryFiredLast = false;
  private TorpedoStore primaryTorpedoStore;
  private TorpedoStore secondaryTorpedoStore;

  private static final int STANDARD_PHOTON_BURST_COUNT = 100;

  private boolean wasLeftLaserFiredLast = false;
  private LaserGun leftLaserGun;
  private LaserGun rightLaserGun;

  public GT4500(TorpedoStore primaryTorpedoStore, TorpedoStore secondaryTorpedoStore,
                LaserGun leftLaserGun, LaserGun rightLaserGun) {
    this.primaryTorpedoStore = primaryTorpedoStore;
    this.secondaryTorpedoStore = secondaryTorpedoStore;

    this.leftLaserGun = leftLaserGun;
    this.rightLaserGun = rightLaserGun;
  }

  /**
   * Tries to fire the lasers of the ship.
   *
   * @param firingMode how to fire the laser guns of the ship\
   * 	SINGLE: fires only one of the laser guns.
   * 			- For the first time the left laser gun is fired.
   * 			- To give some cooling time to the laser guns, they are fired in an alternating pattern.
   * 			- But if the laser gun next in line is empty the ship tries to fire the other gun.
   * 			- If the fired gun reports a failure, the ship does not try to fire the other one.
   * 	ALL:	tries to fire both of the laser guns.
   *
   * @return whether at least one laser was fired successfully
   */
  @Override
  public boolean fireLasers(FiringMode firingMode) {
    boolean firingSuccess = false;

    switch (firingMode) {
      case SINGLE:
        firingSuccess = fireSingleLaser();
        break;
      case ALL:
        firingSuccess = fireAllLasers();
        break;
    }

    return firingSuccess;
  }

  private boolean fireAllLasers() {

    boolean firingSuccess = false;

    try {
      leftLaserGun.fire(STANDARD_PHOTON_BURST_COUNT);
      rightLaserGun.fire(STANDARD_PHOTON_BURST_COUNT);
      firingSuccess = true;
    } catch (IllegalArgumentException e) {
      return false;
    }

    return firingSuccess;
  }

  private boolean fireSingleLaser() {

    boolean firingSuccess = false;

    try {
      if (wasLeftLaserFiredLast) {
        rightLaserGun.fire(STANDARD_PHOTON_BURST_COUNT);
        wasLeftLaserFiredLast = false;
      } else {
        leftLaserGun.fire(STANDARD_PHOTON_BURST_COUNT);
        wasLeftLaserFiredLast = true;
      }
      firingSuccess = true;
    } catch (IllegalArgumentException e) {
      return false;
    }

    return firingSuccess;
  }

  /**
  * Tries to fire the torpedo stores of the ship.
  *
  * @param firingMode how many torpedo bays to fire
  * 	SINGLE: fires only one of the bays.
  * 			- For the first time the primary store is fired.
  * 			- To give some cooling time to the torpedo stores, torpedo stores are fired alternating.
  * 			- But if the store next in line is empty the ship tries to fire the other store.
  * 			- If the fired store reports a failure, the ship does not try to fire the other one.
  * 	ALL:	tries to fire both of the torpedo stores.
  *
  * @return whether at least one torpedo was fired successfully
  */
  @Override
  public boolean fireTorpedos(FiringMode firingMode) {

    boolean firingSuccess = false;

    switch (firingMode) {
      case SINGLE:
        firingSuccess = fireSingleTorpedo();
        break;
      case ALL:
        firingSuccess = fireAllTorpedos();
        break;
    }

    return firingSuccess;
  }

  private boolean fireAllTorpedos() {

    boolean firingSuccess = false;

    // try to fire both of the torpedos
    if (! primaryTorpedoStore.isEmpty()) {
      firingSuccess = primaryTorpedoStore.fire(1);
      wasPrimaryFiredLast = true;
    }
    if (! secondaryTorpedoStore.isEmpty()) {
      firingSuccess = secondaryTorpedoStore.fire(1);
      wasPrimaryFiredLast = false;
    }

    return firingSuccess;
  }

  private boolean fireSingleTorpedo() {

    boolean firingSuccess = false;

    if (wasPrimaryFiredLast) {
      // try to fire the secondary first
      if (! secondaryTorpedoStore.isEmpty()) {
        firingSuccess = secondaryTorpedoStore.fire(1);
        wasPrimaryFiredLast = false;
      }
      else {
        // although primary was fired last time, but the secondary is empty
        // thus try to fire primary again
        if (! primaryTorpedoStore.isEmpty()) {
          firingSuccess = primaryTorpedoStore.fire(1);
          wasPrimaryFiredLast = true;
        }

        // if both of the stores are empty, nothing can be done, return failure
      }
    }
    else {
      // try to fire the primary first
      if (! primaryTorpedoStore.isEmpty()) {
        firingSuccess = primaryTorpedoStore.fire(1);
        wasPrimaryFiredLast = true;
      }
      else {
        // although secondary was fired last time, but primary is empty
        // thus try to fire secondary again
        if (! secondaryTorpedoStore.isEmpty()) {
          firingSuccess = secondaryTorpedoStore.fire(1);
          wasPrimaryFiredLast = false;
        }

        // if both of the stores are empty, nothing can be done, return failure
      }
    }

    return firingSuccess;
  }

}
