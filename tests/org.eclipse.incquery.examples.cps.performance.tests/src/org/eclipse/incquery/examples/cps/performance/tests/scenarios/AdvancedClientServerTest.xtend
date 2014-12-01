package org.eclipse.incquery.examples.cps.performance.tests.scenarios

import java.util.Random
import org.eclipse.incquery.examples.cps.xform.m2m.tests.wrappers.CPSTransformationWrapper

class AdvancedClientServerTest extends BasicScenarioXformTest {
	
	new(CPSTransformationWrapper wrapper, String wrapperType) {
		super(wrapper, wrapperType)
	}
	
	override getScenario(Random rand) {
		new AdvancedClientServerScenario(rand)
	}
	
	override getModificationLabel() {
		"Adding2NewClients"
	}
	
}