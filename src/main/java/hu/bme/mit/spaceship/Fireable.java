package hu.bme.mit.spaceship;

/**
 * For classes that can be fired.
 */
public interface Fireable {
  boolean fire(int count);
  boolean isEmpty();
}
