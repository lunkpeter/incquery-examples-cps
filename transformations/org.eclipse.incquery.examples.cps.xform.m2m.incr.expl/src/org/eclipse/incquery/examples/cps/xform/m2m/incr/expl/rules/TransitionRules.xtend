package org.eclipse.incquery.examples.cps.xform.m2m.incr.expl.rules

import org.eclipse.incquery.examples.cps.deployment.BehaviorTransition
import org.eclipse.incquery.examples.cps.xform.m2m.incr.expl.queries.DeletedTransitionMatch
import org.eclipse.incquery.examples.cps.xform.m2m.incr.expl.queries.MonitoredTransitionMatch
import org.eclipse.incquery.examples.cps.xform.m2m.incr.expl.queries.UnmappedTransitionMatch
import org.eclipse.incquery.runtime.api.IncQueryEngine
import org.eclipse.incquery.runtime.evm.specific.Jobs
import org.eclipse.incquery.runtime.evm.specific.Lifecycles
import org.eclipse.incquery.runtime.evm.specific.Rules
import org.eclipse.incquery.runtime.evm.specific.event.IncQueryActivationStateEnum

class TransitionRules {
	static def getRules(IncQueryEngine engine) {
		#{
			new TransitionMapping(engine).specification
			,new TransitionUpdate(engine).specification
			,new TransitionRemoval(engine).specification
		}
	}
}

class TransitionMapping extends AbstractRule<UnmappedTransitionMatch> {
	
	new(IncQueryEngine engine) {
		super(engine)
	}
	
	override getSpecification() {
		Rules.newMatcherRuleSpecification(
			unmappedTransition,
			Lifecycles.getDefault(false, false),
			#{appearedJob}
		)
	}
	
	private def getAppearedJob() {
		Jobs.newStatelessJob(IncQueryActivationStateEnum.APPEARED, [UnmappedTransitionMatch match |
			val transition = match.transition
			val transitionId = transition.id
			debug('''Mapping transition with ID: «transitionId»''')
			val depTransition = createBehaviorTransition => [
				description = transitionId
			]
			match.depBehavior.transitions += depTransition
			
			val matches = engine.mappedTransitionSourceTarget.getAllMatches(transition, null, null, match.depBehavior)
			if(!matches.empty){
				val sourceTargetMatch = matches.head
				sourceTargetMatch.depSource.outgoing += depTransition
				depTransition.to = sourceTargetMatch.depTarget
			}
			
			val traces = engine.cps2depTrace.getAllValuesOftrace(null, transition, null)
			if(traces.empty){
				trace('''Creating new trace for transition ''')
				rootMapping.traces += createCPS2DeplyomentTrace => [
					cpsElements += transition
					deploymentElements += depTransition
				]
			} else {
				trace('''Adding new transition to existing trace''')
				traces.head.deploymentElements += depTransition
			}

			debug('''Mapped transition with ID: «transitionId»''')
		])
	}
}

class TransitionUpdate extends AbstractRule<MonitoredTransitionMatch> {
	
	new(IncQueryEngine engine) {
		super(engine)
	}
	
	override getSpecification() {
		Rules.newMatcherRuleSpecification(
			monitoredTransition,
			Lifecycles.getDefault(true, true),
			#{appearedJob, disappearedJob, updatedJob}
		)
	}
	
	private def getAppearedJob() {
		Jobs.newStatelessJob(IncQueryActivationStateEnum.APPEARED, [MonitoredTransitionMatch match |
			val trId = match.transition.id
			debug('''Starting monitoring mapped transition with ID: «trId»''')
		])
	}
	
	private def getDisappearedJob() {
		Jobs.newStatelessJob(IncQueryActivationStateEnum.DISAPPEARED, [MonitoredTransitionMatch match |
			val trId = match.transition.id
			debug('''Stopped monitoring mapped transition with ID: «trId»''')
		])
	}
	
	private def getUpdatedJob() {
		Jobs.newStatelessJob(IncQueryActivationStateEnum.UPDATED, [MonitoredTransitionMatch match |
			val transition = match.transition
			val trId = transition.id
			debug('''Updating mapped transition with ID: «trId»''')
			val depTransitionMatches = getMappedTransition(engine).getAllMatches(transition, null, null)
			depTransitionMatches.forEach[
				val oldDesc = depTransition.description
				if(oldDesc != trId){
					trace('''ID changed to «oldDesc»''')
					depTransition.description = trId
				}
				
				val matches = engine.mappedTransitionSourceTarget.getAllMatches(transition, null, null, depBehavior)
				if(!matches.empty){
					val sourceTargetMatch = matches.head
					val newSource = sourceTargetMatch.depSource
					if(!newSource.outgoing.contains(depTransition)){
						trace('''Source state changed to «newSource.description»''')
						newSource.outgoing += depTransition
					}
					val newTarget = sourceTargetMatch.depTarget
					if(depTransition.to != newTarget){
						trace('''Target state changed to «newTarget.description»''')
						depTransition.to = newTarget
					}
				}
				
			]
			
			debug('''Updated mapped transition with ID: «trId»''')
		])
	}
}

class TransitionRemoval extends AbstractRule<DeletedTransitionMatch> {
	
	new(IncQueryEngine engine) {
		super(engine)
	}
	
	override getSpecification() {
		Rules.newMatcherRuleSpecification(
			deletedTransition,
			Lifecycles.getDefault(false, false),
			#{appearedJob}
		)
	}
	
	private def getAppearedJob() {
		Jobs.newStatelessJob(IncQueryActivationStateEnum.APPEARED, [DeletedTransitionMatch match |
			val depTransition = match.depTransition as BehaviorTransition
			val trId = depTransition.description
			logger.debug('''Removing transition with ID: «trId»''')
			depTransition.to = null
			val sources = engine.behaviorStateOutgoing.getAllValuesOfdepState(depTransition)
			if(!sources.empty){
				val source = sources.head
				source.outgoing -= depTransition
			}
			val stateMachines = engine.behaviorTransition.getAllValuesOfdepBehavior(depTransition)
			if(!stateMachines.empty){
				val sm = stateMachines.head
				sm.transitions -= depTransition
			}
			val smTrace = match.trace
			smTrace.deploymentElements -= depTransition
			if(smTrace.deploymentElements.empty){
				trace('''Removing empty trace''')
				rootMapping.traces -= smTrace
			}
			logger.debug('''Removed transition with ID: «trId»''')
		])
	} 
}