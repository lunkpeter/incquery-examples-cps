package org.eclipse.incquery.examples.cps.xform.m2m.tests.wrappers

import org.eclipse.incquery.examples.cps.traceability.CPSToDeployment
import org.eclipse.incquery.examples.cps.xform.m2m.incr.expl.CPS2DeploymentTransformation
import org.eclipse.incquery.runtime.api.AdvancedIncQueryEngine
import org.eclipse.incquery.runtime.emf.EMFScope

class ExplicitTraceability extends CPSTransformationWrapper {
	
	CPS2DeploymentTransformation xform 
	AdvancedIncQueryEngine engine
	
	override initializeTransformation(CPSToDeployment cps2dep) {
		engine = AdvancedIncQueryEngine.createUnmanagedEngine(new EMFScope(cps2dep.eResource.resourceSet));
		xform = new CPS2DeploymentTransformation
		xform.initialize(cps2dep, engine)
	}
	
	override executeTransformation() {
		xform.execute()
		debug("Explicit Traceability transformation is incremental")
	}
	
	override cleanupTransformation() {
		if(xform != null){
			xform.dispose
		}
		if(engine != null){
			engine.dispose
		}
		xform = null
		engine = null
	}
}