package org.eclipse.incquery.examples.cps.generator.dtos

import org.eclipse.incquery.examples.cps.generator.interfaces.ICPSConstraints
import org.eclipse.incquery.examples.cps.generator.dtos.MinMaxData
import org.eclipse.incquery.examples.cps.generator.dtos.AppClass
import org.eclipse.incquery.examples.cps.generator.dtos.HostClass

/**
 * Simple implementation of {@link ICPSConstraints}. Every data shall be passed to the constructor. 
 *
 */
class BuildableCPSConstraint implements ICPSConstraints {
	
	val String name;
	val MinMaxData<Integer> numberOfSignals;
	val Iterable<AppClass> applicationClasses;
	val Iterable<HostClass> hostClasses;
	
	new(String name, MinMaxData<Integer> numberOfSignals, Iterable<AppClass> applicationClasses, Iterable<HostClass> hostClasses){
		this.name = name;
		this.numberOfSignals = numberOfSignals;
		this.applicationClasses = applicationClasses;
		this.hostClasses = hostClasses;
	}
	
	override getNumberOfSignals() {
		return numberOfSignals;
	}
	
	override getApplicationClasses() {
		return applicationClasses;
	}
	
	override getHostClasses() {
		return hostClasses;
	}
	
	override getName() {
		return name;
	}
	
}