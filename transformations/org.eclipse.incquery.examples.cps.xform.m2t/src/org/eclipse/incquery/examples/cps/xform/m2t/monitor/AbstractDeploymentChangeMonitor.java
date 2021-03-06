package org.eclipse.incquery.examples.cps.xform.m2t.monitor;

import org.eclipse.incquery.examples.cps.deployment.Deployment;
import org.eclipse.incquery.runtime.api.IncQueryEngine;
import org.eclipse.incquery.runtime.exception.IncQueryException;

public abstract class AbstractDeploymentChangeMonitor {

    protected Deployment deployment;
    protected IncQueryEngine engine;

    public AbstractDeploymentChangeMonitor(Deployment deployment, IncQueryEngine engine){
        this.deployment = deployment;
        this.engine = engine;
		
	}
	
	/**
	 * Sets the model whose changes are observed. Also creates an initial checkpoint with no changes registered.
	 * @param deployment the deployment model
	 * @param engine engine associated with the 
	 * @throws IncQueryException 
	 */
	public abstract void startMonitoring() throws IncQueryException;
	
	/**
	 * Creates a checkpoint which means:
	 * <li>Model changes since the last checkpont are saved</li>
	 * <li>The model changes in the future are tracked separately from the changes before the checkpoint</li>
	 * @return the DTO containing the changed elements since the last checkpoint
	 */
	public abstract DeploymentChangeDelta createCheckpoint();
	
	/**
	 * Returns all changed elements between the last two checkpoints
	 * @return the DTO containing the changed elements
	 */
	public abstract DeploymentChangeDelta getDeltaSinceLastCheckpoint();
	
}
