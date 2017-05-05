package hu.bme.mit.spaceship;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

public class GT4500Test {

  private GT4500 ship;

  @Before
  public void init(){
    TorpedoStore primaryTorpedoStore = new TorpedoStore();
    TorpedoStore secondaryTorpedoStore = new TorpedoStore();

    LaserGun leftLaserGun = new LaserGun();
    LaserGun rightLaserGun = new LaserGun();

    this.ship = new GT4500(primaryTorpedoStore, secondaryTorpedoStore,
        leftLaserGun, rightLaserGun);
  }

  @Test
  public void fireTorpedos_Single_Success() {
    // Arrange

    // Act
    boolean result = ship.fireTorpedos(FiringMode.SINGLE);

    // Assert
    assertEquals(true, result);
  }

  @Test
  public void fireTorpedos_All_Success() {
    // Arrange

    // Act
    boolean result = ship.fireTorpedos(FiringMode.ALL);

    // Assert
    assertEquals(true, result);
  }

  @Test
  public void fireLasers_Single_Success() {
    // Arrange

    // Act
    boolean result = ship.fireLasers(FiringMode.ALL);

    // Assert
    assertEquals(true, result);
  }

  @Test
  public void fireLasers_All_Success() {
    // Arrange

    // Act
    boolean result = ship.fireLasers(FiringMode.ALL);

    // Assert
    assertEquals(true, result);
  }

}
