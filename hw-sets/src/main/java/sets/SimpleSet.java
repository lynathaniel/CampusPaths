package sets;

import java.util.List;

/**
 * Represents an immutable set of points on the real line that is easy to
 * describe, either because it is a finite set, e.g., {p1, p2, ..., pN}, or
 * because it excludes only a finite set, e.g., R \ {p1, p2, ..., pN}. As with
 * FiniteSet, each point is represented by a Java float with a non-infinite,
 * non-NaN value.
 */
public class SimpleSet {

  // Points are stored in a FiniteSet, in sorted order, with an extra -infinity at
  // the front and +infinity at the end to simplify union etc.
  // The FiniteSet stores either the points on the real line or the points excluded
  // from the line.
  //
  // RI: if complement (R \) -infinity = points.vals[0] < points.vals[1] < ... < points.vals[points.vals.length-1] = + infinity
  // AF(this) = if complement (R \) {points.vals[1], points.vals[2], ..., points.vals[points.vals.length-2]}

  private final FiniteSet points;

  private final boolean complement;

  /**
   * Creates a simple set containing only the given points.
   * @param vals Array containing the points to make into a SimpleSet
   * @spec.requires points != null and has no NaNs, no infinities, and no dups
   * @spec.effects this = {vals[0], vals[1], ..., vals[vals.length-1]}
   */
  public SimpleSet(float[] vals) {
    this(false, FiniteSet.of(vals));
  }

  /**
   * Private constructor that directly fills in the fields above.
   * @param complement Whether this = points or this = R \ points
   * @param points List of points that are in the set or not in the set
   * @spec.requires points != null
   * @spec.effects this = R \ points if complement else points
   */
  private SimpleSet(boolean complement, FiniteSet points) {
    this.points = points;
    this.complement = complement;
  }

  @Override
  public boolean equals(Object o) {
    if (!(o instanceof SimpleSet))
      return false;

    SimpleSet other = (SimpleSet) o;
    return (this.points.equals(other.points) &&
            this.complement == other.complement);
  }

  @Override
  public int hashCode() {
    return super.hashCode();
  }

  /**
   * Returns the number of points in this set.
   * @return N      if this = {p1, p2, ..., pN} and
   *         infty  if this = R \ {p1, p2, ..., pN}
   */
  public float size() {
    if (this.complement) {
      return Float.POSITIVE_INFINITY;
    }
    return this.points.size();
  }

  /**
   * Returns a string describing the points included in this set.
   * @return the string "R" if this contains every point,
   *     a string of the form "R \ {p1, p2, .., pN}" if this contains all
   *        but {@literal N > 0} points, or
   *     a string of the form "{p1, p2, .., pN}" if this contains
   *        {@literal N >= 0} points,
   *     where p1, p2, ... pN are replaced by the individual numbers.
   */
  public String toString() {
    StringBuilder s = new StringBuilder();
    // If the set is a complement then add "R" to the string.
    if (this.complement) {
      s.append("R");
      // If the set excludes values add "\".
      if (this.points.size() > 0) {
        s.append(" \\ ");
      } else {
        // Return if there are no excluded values.
        return s.toString();
      }
    }
    // Braces for values; whether the set is empty.
    if (this.points.size() >= 0) {
      s.append("{");
      if (this.points.size() > 0) {
        List<Float> pointsList = this.points.getPoints();
        // pointsList = P
        // Inv: s = P[0] + , ... , + P[i - 1]
        for (int i = 0; i < pointsList.size() - 1; i++) {
          s.append(pointsList.get(i) + ", ");
        }
        s.append(pointsList.get(pointsList.size() - 1));
      }
      s.append("}");
    }
    return s.toString();
  }

  /**
   * Returns a set representing the points R \ this.
   * @return R \ this
   */
  public SimpleSet complement() {

    // Returns the complement of the current SimpleSet.
    return new SimpleSet(!complement, this.points);
  }

  /**
   * Returns the union of this and other.
   * @param other Set to union with this one.
   * @spec.requires other != null
   * @return this union other
   */
  public SimpleSet union(SimpleSet other) {
    // Whichever points are shared between the two become the new excluding set.
    if (this.complement && other.complement) {
      return new SimpleSet(true, this.points.intersection(other.points));
    }
    // If only the second set is excluding a finite set, the difference between the second
    // and first sets is equivalent to the union of the two.
    // Difference between the excluding set and the FiniteSet would be the new excluding set.
    if (other.complement) {
      return new SimpleSet(true, other.points.difference(this.points));
    }

    // If the first set is all real numbers minus a finite set, it is the
    // difference between the first and second set (reverse of previous case).
    // Difference between the excluding set and the FiniteSet would be the new excluding set.
    if (this.complement) {
      return new SimpleSet(true, this.points.difference(other.points));
    }
    // If both SimpleSets are FiniteSets, return the FiniteSet union of the two.
    return new SimpleSet(false, this.points.union(other.points));
  }

  /**
   * Returns the intersection of this and other.
   * @param other Set to intersect with this one.
   * @spec.requires other != null
   * @return this intersect other
   */
  public SimpleSet intersection(SimpleSet other) {

    // Check if both sets are infinite.
    // Since both sets include all real numbers except for those specified,
    // all the values being excluded can be combined into a single excluding set
    // using union.
    if (this.complement && other.complement) {
      return new SimpleSet(true, this.points.union(other.points));
    }
    // The difference between the sets provides the values that are
    // present in both sets when compared in the correct order.
    // Next two cases pertain to this.
    // Since the sets that are complements are all real numbers minus specified values,
    // the values that the complement sets does not exclude can be shared with a finite set.
    // So, difference will find which values the finite set has and are not excluded by
    // the complement set.
    if (other.complement) {
      return new SimpleSet(false, this.points.difference(other.points));
    }
    // Switch sets to compare properly.
    if (this.complement) {
      return new SimpleSet(false, other.points.difference(this.points));
    }
    // Both sets are finiteSets so can be found normally.
    return new SimpleSet(false, this.points.intersection(other.points));
  }

  /**
   * Returns the difference of this and other.
   * @param other Set to difference from this one.
   * @spec.requires other != null
   * @return this minus other
   */
  public SimpleSet difference(SimpleSet other) {

    // If both sets are complements, the difference between the two would just be
    // the values they do not both exlude. Thus, the difference between the two
    // can be found by just comparing their finite set fields.
    if (this.complement && other.complement) {
      return new SimpleSet(false, other.points.difference(this.points));
    }
    // If the other set is a complement set, then the difference between other and this
    // would be whichever values that the complement would not be exluding. This means
    // that the values that both sets share in their finite set field would produce the
    // the set of the values that would be the difference between the two.
    if (other.complement) {
      return new SimpleSet(false, other.points.intersection(this.points));
    }
    // If this is a complement set, then the difference it has with other would be
    // any values that the two have in their finite set fields. This is since values
    // held in this field for complement sets are the values that they do not have while
    // a normal finite set is defining the values that it is holding onto. Thus, the union
    // of all these values would produce the difference between the two.
    if (this.complement) {
      return new SimpleSet(true, this.points.union(other.points));
    }
    // If both are finite sets then the difference can be found normally.
    return new SimpleSet(false, this.points.difference(other.points));
  }

}
