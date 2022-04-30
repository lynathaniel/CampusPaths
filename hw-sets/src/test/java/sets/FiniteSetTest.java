package sets;

import org.junit.Test;
import static org.junit.Assert.*;
import java.util.Arrays;

/**
 * FiniteSetTest is a glassbox test of the FiniteSet class.
 */
public class FiniteSetTest {

  /** Test creating sets. */
  @Test
  public void testCreation() {
    assertEquals(Arrays.asList(),
        FiniteSet.of(new float[0]).getPoints());         // empty
    assertEquals(Arrays.asList(1f),
        FiniteSet.of(new float[] {1}).getPoints());      // one item
    assertEquals(Arrays.asList(1f, 2f),
        FiniteSet.of(new float[] {1, 2}).getPoints());   // two items
    assertEquals(Arrays.asList(1f, 2f),
        FiniteSet.of(new float[] {2, 1}).getPoints());   // two out-of-order
    assertEquals(Arrays.asList(-2f, 2f),
        FiniteSet.of(new float[] {2, -2}).getPoints());  // negative
  }

  // Some example sets used by the tests below.
  private static FiniteSet S0 = FiniteSet.of(new float[0]);
  private static FiniteSet S1 = FiniteSet.of(new float[] {1});
  private static FiniteSet S12 = FiniteSet.of(new float[] {1, 2});

  /** Test set equality. */
  @Test
  public void testEquals() {
    assertTrue(S0.equals(S0));
    assertFalse(S0.equals(S1));
    assertFalse(S0.equals(S12));

    assertFalse(S1.equals(S0));
    assertTrue(S1.equals(S1));
    assertFalse(S1.equals(S12));

    assertFalse(S12.equals(S0));
    assertFalse(S12.equals(S1));
    assertTrue(S12.equals(S12));
  }

  /** Test set size. */
  @Test
  public void testSize() {
    assertEquals(S0.size(), 0);
    assertEquals(S1.size(), 1);
    assertEquals(S12.size(), 2);
  }
  
  private static FiniteSet T0 = FiniteSet.of(new float[0]);
  private static FiniteSet T1 = FiniteSet.of(new float[] {1});
  private static FiniteSet T12 = FiniteSet.of(new float[] {1, 2});
  private static FiniteSet T345 = FiniteSet.of(new float[] {3, 4, 5});


  /** Tests forming the union of two finite sets. */
  @Test
  public void testUnion() {
    assertEquals(S0.union(S0), S0);
    assertEquals(S1.union(S1), S1);
    assertEquals(S0.union(S1), S1);
    assertEquals(S0.union(S12), S12);
    assertEquals(S1.union(S12), S12);
    assertEquals(S12.union(S12), S12);

    assertEquals(T12.union(T345), FiniteSet.of(new float[] {1, 2, 3, 4, 5}));

    assertEquals(S0.union(T0), FiniteSet.of(new float[0])); // Two empty sets
    assertEquals(S0.union(T1), FiniteSet.of(new float[] {1})); // one point
    assertEquals(S12.union(T12), FiniteSet.of(new float[] {1, 2})); // Same set
    assertEquals(S12.union(T345), FiniteSet.of(new float[] {1, 2, 3, 4, 5})); // Different size/pts
    
  }

  /** Tests forming the intersection of two finite sets. */
  @Test
  public void testIntersection() {

    assertEquals(S0.intersection(S0), S0);
    assertEquals(S0.intersection(S1), S0);
    assertEquals(S1.intersection(S1), S1);
    assertEquals(S1.intersection(S12), S1);
    assertEquals(S1.intersection(S12), S1);
    assertEquals(S12.intersection(S12), S12);

    assertEquals(T12.intersection(T345), T0);

    assertEquals(S0.intersection(T0), S0);
    assertEquals(S0.intersection(T1), S0);
    assertEquals(S12.intersection(T12), S12);
    assertEquals(S1.intersection(T12), S1);
  }

  /** Tests forming the difference of two finite sets. */
  @Test
  public void testDifference() {
    assertEquals(S0.difference(T0), S0);
    assertEquals(S0.difference(T1), S0);
    assertEquals(S12.difference(T12), S0);
    assertEquals(S12.difference(T345), S12);
    assertEquals(S1.difference(T12), S0);
  }

}
