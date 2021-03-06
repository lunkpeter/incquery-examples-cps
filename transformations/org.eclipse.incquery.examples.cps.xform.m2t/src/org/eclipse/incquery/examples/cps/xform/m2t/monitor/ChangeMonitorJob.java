package org.eclipse.incquery.examples.cps.xform.m2t.monitor;

import java.util.Map;

import org.eclipse.incquery.examples.cps.deployment.DeploymentApplication;
import org.eclipse.incquery.examples.cps.deployment.DeploymentElement;
import org.eclipse.incquery.examples.cps.deployment.DeploymentHost;
import org.eclipse.incquery.runtime.api.IMatchProcessor;
import org.eclipse.incquery.runtime.api.IPatternMatch;
import org.eclipse.incquery.runtime.evm.api.Activation;
import org.eclipse.incquery.runtime.evm.api.Context;
import org.eclipse.incquery.runtime.evm.specific.event.IncQueryActivationStateEnum;
import org.eclipse.incquery.runtime.evm.specific.job.StatelessJob;

import com.google.common.collect.Maps;

public class ChangeMonitorJob<Match extends IPatternMatch> extends StatelessJob<Match> {

	public static final String OUTDATED_ELEMENTS = "changedDeploymentElements";
	public static final String HOSTS = "deploymentHosts";
	public static final String APPLICATIONS = "deploymentApps";

	public ChangeMonitorJob(
			IncQueryActivationStateEnum incQueryActivationStateEnum,
			IMatchProcessor<Match> matchProcessor) {
		super(incQueryActivationStateEnum, matchProcessor);
	}

	@SuppressWarnings("unchecked")
	@Override
	protected void execute(Activation<? extends Match> activation, Context context) {
		super.execute(activation, context);
		// For update jobs, store the old name
		if(getActivationState().equals(IncQueryActivationStateEnum.UPDATED)){
			Map<DeploymentElement, String> map = (Map<DeploymentElement, String>) context.get(OUTDATED_ELEMENTS);
			if (map == null) {
				map = Maps.newHashMap();
				context.put(OUTDATED_ELEMENTS, map);
			}
			DeploymentElement changedElement = (DeploymentElement) activation.getAtom().get(0);
			store(changedElement, context);
		}
		
	}

	@SuppressWarnings("unchecked")
	private void store(DeploymentElement changedElement,Context context) {
		Map<DeploymentElement, String> map = (Map<DeploymentElement, String>) context.get(OUTDATED_ELEMENTS);
		// Sotre the old data in the values of the map
		if(changedElement instanceof DeploymentHost){
			map.put(changedElement, ((Map<DeploymentHost,String>)context.get(HOSTS)).get((DeploymentHost)changedElement));						
		}
		else if(changedElement instanceof DeploymentApplication){
			map.put(changedElement, ((Map<DeploymentApplication,String>)context.get(APPLICATIONS)).get((DeploymentApplication)changedElement));						
		}
	}

	@Override
	protected void handleError(Activation<? extends Match> activation, Exception exception, Context context) {
		context.remove(OUTDATED_ELEMENTS);
		super.handleError(activation,exception,context);
	}

}
