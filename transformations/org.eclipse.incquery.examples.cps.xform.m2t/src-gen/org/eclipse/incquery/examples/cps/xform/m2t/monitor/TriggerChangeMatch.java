package org.eclipse.incquery.examples.cps.xform.m2t.monitor;

import java.util.Arrays;
import java.util.List;
import org.eclipse.incquery.examples.cps.deployment.BehaviorTransition;
import org.eclipse.incquery.examples.cps.deployment.DeploymentBehavior;
import org.eclipse.incquery.examples.cps.xform.m2t.monitor.util.TriggerChangeQuerySpecification;
import org.eclipse.incquery.runtime.api.IPatternMatch;
import org.eclipse.incquery.runtime.api.impl.BasePatternMatch;
import org.eclipse.incquery.runtime.exception.IncQueryException;

/**
 * Pattern-specific match representation of the org.eclipse.incquery.examples.cps.xform.m2t.monitor.triggerChange pattern,
 * to be used in conjunction with {@link TriggerChangeMatcher}.
 * 
 * <p>Class fields correspond to parameters of the pattern. Fields with value null are considered unassigned.
 * Each instance is a (possibly partial) substitution of pattern parameters,
 * usable to represent a match of the pattern in the result of a query,
 * or to specify the bound (fixed) input parameters when issuing a query.
 * 
 * @see TriggerChangeMatcher
 * @see TriggerChangeProcessor
 * 
 */
@SuppressWarnings("all")
public abstract class TriggerChangeMatch extends BasePatternMatch {
  private DeploymentBehavior fBehavior;
  
  private BehaviorTransition fTransition;
  
  private static List<String> parameterNames = makeImmutableList("behavior", "transition");
  
  private TriggerChangeMatch(final DeploymentBehavior pBehavior, final BehaviorTransition pTransition) {
    this.fBehavior = pBehavior;
    this.fTransition = pTransition;
  }
  
  @Override
  public Object get(final String parameterName) {
    if ("behavior".equals(parameterName)) return this.fBehavior;
    if ("transition".equals(parameterName)) return this.fTransition;
    return null;
  }
  
  public DeploymentBehavior getBehavior() {
    return this.fBehavior;
  }
  
  public BehaviorTransition getTransition() {
    return this.fTransition;
  }
  
  @Override
  public boolean set(final String parameterName, final Object newValue) {
    if (!isMutable()) throw new java.lang.UnsupportedOperationException();
    if ("behavior".equals(parameterName) ) {
    	this.fBehavior = (org.eclipse.incquery.examples.cps.deployment.DeploymentBehavior) newValue;
    	return true;
    }
    if ("transition".equals(parameterName) ) {
    	this.fTransition = (org.eclipse.incquery.examples.cps.deployment.BehaviorTransition) newValue;
    	return true;
    }
    return false;
  }
  
  public void setBehavior(final DeploymentBehavior pBehavior) {
    if (!isMutable()) throw new java.lang.UnsupportedOperationException();
    this.fBehavior = pBehavior;
  }
  
  public void setTransition(final BehaviorTransition pTransition) {
    if (!isMutable()) throw new java.lang.UnsupportedOperationException();
    this.fTransition = pTransition;
  }
  
  @Override
  public String patternName() {
    return "org.eclipse.incquery.examples.cps.xform.m2t.monitor.triggerChange";
  }
  
  @Override
  public List<String> parameterNames() {
    return TriggerChangeMatch.parameterNames;
  }
  
  @Override
  public Object[] toArray() {
    return new Object[]{fBehavior, fTransition};
  }
  
  @Override
  public TriggerChangeMatch toImmutable() {
    return isMutable() ? newMatch(fBehavior, fTransition) : this;
  }
  
  @Override
  public String prettyPrint() {
    StringBuilder result = new StringBuilder();
    result.append("\"behavior\"=" + prettyPrintValue(fBehavior) + ", ");
    
    result.append("\"transition\"=" + prettyPrintValue(fTransition)
    );
    return result.toString();
  }
  
  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((fBehavior == null) ? 0 : fBehavior.hashCode());
    result = prime * result + ((fTransition == null) ? 0 : fTransition.hashCode());
    return result;
  }
  
  @Override
  public boolean equals(final Object obj) {
    if (this == obj)
    	return true;
    if (!(obj instanceof TriggerChangeMatch)) { // this should be infrequent
    	if (obj == null) {
    		return false;
    	}
    	if (!(obj instanceof IPatternMatch)) {
    		return false;
    	}
    	IPatternMatch otherSig  = (IPatternMatch) obj;
    	if (!specification().equals(otherSig.specification()))
    		return false;
    	return Arrays.deepEquals(toArray(), otherSig.toArray());
    }
    TriggerChangeMatch other = (TriggerChangeMatch) obj;
    if (fBehavior == null) {if (other.fBehavior != null) return false;}
    else if (!fBehavior.equals(other.fBehavior)) return false;
    if (fTransition == null) {if (other.fTransition != null) return false;}
    else if (!fTransition.equals(other.fTransition)) return false;
    return true;
  }
  
  @Override
  public TriggerChangeQuerySpecification specification() {
    try {
    	return TriggerChangeQuerySpecification.instance();
    } catch (IncQueryException ex) {
     	// This cannot happen, as the match object can only be instantiated if the query specification exists
     	throw new IllegalStateException (ex);
    }
  }
  
  /**
   * Returns an empty, mutable match.
   * Fields of the mutable match can be filled to create a partial match, usable as matcher input.
   * 
   * @return the empty match.
   * 
   */
  public static TriggerChangeMatch newEmptyMatch() {
    return new Mutable(null, null);
  }
  
  /**
   * Returns a mutable (partial) match.
   * Fields of the mutable match can be filled to create a partial match, usable as matcher input.
   * 
   * @param pBehavior the fixed value of pattern parameter behavior, or null if not bound.
   * @param pTransition the fixed value of pattern parameter transition, or null if not bound.
   * @return the new, mutable (partial) match object.
   * 
   */
  public static TriggerChangeMatch newMutableMatch(final DeploymentBehavior pBehavior, final BehaviorTransition pTransition) {
    return new Mutable(pBehavior, pTransition);
  }
  
  /**
   * Returns a new (partial) match.
   * This can be used e.g. to call the matcher with a partial match.
   * <p>The returned match will be immutable. Use {@link #newEmptyMatch()} to obtain a mutable match object.
   * @param pBehavior the fixed value of pattern parameter behavior, or null if not bound.
   * @param pTransition the fixed value of pattern parameter transition, or null if not bound.
   * @return the (partial) match object.
   * 
   */
  public static TriggerChangeMatch newMatch(final DeploymentBehavior pBehavior, final BehaviorTransition pTransition) {
    return new Immutable(pBehavior, pTransition);
  }
  
  private static final class Mutable extends TriggerChangeMatch {
    Mutable(final DeploymentBehavior pBehavior, final BehaviorTransition pTransition) {
      super(pBehavior, pTransition);
    }
    
    @Override
    public boolean isMutable() {
      return true;
    }
  }
  
  private static final class Immutable extends TriggerChangeMatch {
    Immutable(final DeploymentBehavior pBehavior, final BehaviorTransition pTransition) {
      super(pBehavior, pTransition);
    }
    
    @Override
    public boolean isMutable() {
      return false;
    }
  }
}
