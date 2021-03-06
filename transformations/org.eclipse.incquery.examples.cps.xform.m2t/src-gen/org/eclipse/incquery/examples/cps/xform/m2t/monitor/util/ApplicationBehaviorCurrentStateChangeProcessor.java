package org.eclipse.incquery.examples.cps.xform.m2t.monitor.util;

import org.eclipse.incquery.examples.cps.deployment.DeploymentApplication;
import org.eclipse.incquery.examples.cps.deployment.DeploymentBehavior;
import org.eclipse.incquery.examples.cps.xform.m2t.monitor.ApplicationBehaviorCurrentStateChangeMatch;
import org.eclipse.incquery.runtime.api.IMatchProcessor;

/**
 * A match processor tailored for the org.eclipse.incquery.examples.cps.xform.m2t.monitor.applicationBehaviorCurrentStateChange pattern.
 * 
 * Clients should derive an (anonymous) class that implements the abstract process().
 * 
 */
@SuppressWarnings("all")
public abstract class ApplicationBehaviorCurrentStateChangeProcessor implements IMatchProcessor<ApplicationBehaviorCurrentStateChangeMatch> {
  /**
   * Defines the action that is to be executed on each match.
   * @param pApp the value of pattern parameter app in the currently processed match
   * @param pBeh the value of pattern parameter beh in the currently processed match
   * 
   */
  public abstract void process(final DeploymentApplication pApp, final DeploymentBehavior pBeh);
  
  @Override
  public void process(final ApplicationBehaviorCurrentStateChangeMatch match) {
    process(match.getApp(), match.getBeh());
  }
}
