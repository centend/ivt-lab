package hu.bme.mit.spaceship;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

public class GT4500Test {

  private GT4500 ship;

  private TorpedoStore primaryTorpedoStore;
  private TorpedoStore secondaryTorpedoStore;

  private LaserGun leftLaserGun;
  private LaserGun rightLaserGun;

  @Before
  public void init() {
    primaryTorpedoStore = mock(TorpedoStore.class);
    secondaryTorpedoStore = mock(TorpedoStore.class);

    leftLaserGun = mock(LaserGun.class);
    rightLaserGun = mock(LaserGun.class);

    this.ship = new GT4500(primaryTorpedoStore, secondaryTorpedoStore,
        leftLaserGun, rightLaserGun);
  }

  @Test
  public void fireTorpedos_Single_Primary_Success() {
    // Arrange
    when(primaryTorpedoStore.fire(1)).thenReturn(true);
    when(primaryTorpedoStore.isEmpty()).thenReturn(false);

    // Act
    boolean result = ship.fireTorpedos(FiringMode.SINGLE);

    // Assert
    assertEquals(true, result);
  }

  @Test
  public void fireTorpedos_Single_Primary_Failure() {
    // Arrange
    when(primaryTorpedoStore.fire(1)).thenReturn(false);
    when(primaryTorpedoStore.isEmpty()).thenReturn(false);

    // Act
    boolean result = ship.fireTorpedos(FiringMode.SINGLE);

    // Assert
    assertEquals(false, result);
  }

  @Test
  public void fireTorpedos_Single_Secondary_Success_Primary_Empty() {
    // Arrange
    when(primaryTorpedoStore.isEmpty()).thenReturn(true);
    when(secondaryTorpedoStore.isEmpty()).thenReturn(false);
    when(secondaryTorpedoStore.fire(1)).thenReturn(true);

    // Act
    boolean result = ship.fireTorpedos(FiringMode.SINGLE);

    // Assert
    assertEquals(true, result);
  }

  @Test
  public void fireTorpedos_Single_Secondary_Success_Primary_Last_Fired() {
    // Arrange
    when(primaryTorpedoStore.isEmpty()).thenReturn(false);
    when(secondaryTorpedoStore.isEmpty()).thenReturn(true);
    when(primaryTorpedoStore.fire(1)).thenReturn(true);
    when(secondaryTorpedoStore.fire(1)).thenReturn(true);

    // Act
    ship.fireTorpedos(FiringMode.SINGLE);
    boolean result = ship.fireTorpedos(FiringMode.SINGLE);

    // Assert
    assertEquals(true, result);
  }

  @Test
  public void fireTorpedos_Single_Secondary_Failure_Primary_Empty() {
    // Arrange
    when(primaryTorpedoStore.isEmpty()).thenReturn(true);
    when(secondaryTorpedoStore.isEmpty()).thenReturn(false);
    when(secondaryTorpedoStore.fire(1)).thenReturn(false);

    // Act
    boolean result = ship.fireTorpedos(FiringMode.SINGLE);

    // Assert
    assertEquals(false, result);
  }

  @Test
  public void fireTorpedos_Single_Secondary_Failure_Primary_Last_Fired() {
    // Arrange
    when(primaryTorpedoStore.isEmpty()).thenReturn(false);
    when(secondaryTorpedoStore.isEmpty()).thenReturn(false);
    when(primaryTorpedoStore.fire(1)).thenReturn(true);
    when(secondaryTorpedoStore.fire(1)).thenReturn(false);

    // Act
    ship.fireTorpedos(FiringMode.SINGLE);
    boolean result = ship.fireTorpedos(FiringMode.SINGLE);

    // Assert
    assertEquals(false, result);
  }

  @Test
  public void fireTorpedos_Single_Failure_All_Empty() {
    // Arrange
    when(primaryTorpedoStore.isEmpty()).thenReturn(true);
    when(secondaryTorpedoStore.isEmpty()).thenReturn(true);

    // Act
    boolean result = ship.fireTorpedos(FiringMode.SINGLE);

    // Assert
    assertEquals(false, result);
  }

  @Test
  public void fireTorpedos_All_Success() {
    // Arrange
    when(primaryTorpedoStore.fire(1)).thenReturn(true);
    when(secondaryTorpedoStore.fire(1)).thenReturn(true);

    // Act
    boolean result = ship.fireTorpedos(FiringMode.ALL);

    // Assert
    assertEquals(true, result);
  }

  @Test
  public void fireTorpedos_All_Failure_Both_Fail() {
    // Arrange
    when(primaryTorpedoStore.fire(1)).thenReturn(false);
    when(secondaryTorpedoStore.fire(1)).thenReturn(false);

    // Act
    boolean result = ship.fireTorpedos(FiringMode.ALL);

    // Assert
    assertEquals(false, result);
  }

  @Test
  public void fireTorpedos_All_Failure_Primary_Fails() {
    // Arrange
    when(primaryTorpedoStore.fire(1)).thenReturn(false);
    when(secondaryTorpedoStore.fire(1)).thenReturn(true);

    // Act
    boolean result = ship.fireTorpedos(FiringMode.ALL);

    // Assert
    assertEquals(false, result);
  }

  @Test
  public void fireTorpedos_All_Failure_Secondary_Fails() {
    // Arrange
    when(primaryTorpedoStore.fire(1)).thenReturn(true);
    when(secondaryTorpedoStore.fire(1)).thenReturn(false);

    // Act
    boolean result = ship.fireTorpedos(FiringMode.ALL);

    // Assert
    assertEquals(false, result);
  }

  @Test
  public void fireLasers_Single_Success_Left() {
    // Arrange
    when(leftLaserGun.fire()).thenReturn(true);

    // Act
    boolean result = ship.fireLasers(FiringMode.SINGLE);

    // Assert
    assertEquals(true, result);
  }

  @Test
  public void fireLasers_Single_Success_Right() {
    // Arrange
    when(leftLaserGun.fire()).thenReturn(true);
    when(rightLaserGun.fire()).thenReturn(true);

    // Act
    ship.fireLasers(FiringMode.SINGLE);
    boolean result = ship.fireLasers(FiringMode.SINGLE);

    // Assert
    assertEquals(true, result);
  }

  @Test
  public void fireLasers_Single_Failure_Left() {
    // Arrange
    when(leftLaserGun.fire()).thenReturn(false);

    // Act
    boolean result = ship.fireLasers(FiringMode.SINGLE);

    // Assert
    assertEquals(false, result);
  }

  @Test
  public void fireLasers_Single_Failure_Right() {
    // Arrange
    when(leftLaserGun.fire()).thenReturn(true);
    when(rightLaserGun.fire()).thenReturn(false);

    // Act
    ship.fireLasers(FiringMode.SINGLE);
    boolean result = ship.fireLasers(FiringMode.SINGLE);

    // Assert
    assertEquals(false, result);
  }

  @Test
  public void fireLasers_All_Success() {
    // Arrange
    when(leftLaserGun.fire()).thenReturn(true);
    when(rightLaserGun.fire()).thenReturn(true);


    // Act
    boolean result = ship.fireLasers(FiringMode.ALL);

    // Assert
    assertEquals(true, result);
  }

}
