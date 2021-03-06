package org.eclipse.incquery.examples.cps.performance.tests

import eu.mondo.sam.core.BenchmarkEngine
import eu.mondo.sam.core.results.JsonSerializer
import eu.mondo.sam.core.scenarios.BenchmarkScenario
import java.util.Random
import org.apache.log4j.Logger
import org.eclipse.incquery.examples.cps.performance.tests.config.CPSDataToken
import org.eclipse.incquery.examples.cps.performance.tests.config.GeneratorType
import org.eclipse.incquery.examples.cps.performance.tests.config.TransformationType
import org.eclipse.incquery.examples.cps.tests.CPSTestBase
import org.eclipse.incquery.examples.cps.xform.m2m.tests.wrappers.CPSTransformationWrapper
import org.junit.After
import org.junit.AfterClass
import org.junit.BeforeClass
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.Parameterized

@RunWith(Parameterized)
abstract class CPSPerformanceTest extends CPSTestBase {
	protected extension CPSTransformationWrapper xform
	protected extension Logger logger = Logger.getLogger("cps.performance.tests.CPSPerformanceTest")
	
	public static val RANDOM_SEED = 11111
	val Random rand = new Random(RANDOM_SEED);
	
	val int scale
	val BenchmarkScenario scenario
	val GeneratorType generatorType
	val TransformationType wrapperType
//	IProject project

    
    new(TransformationType wrapperType,	int scale, GeneratorType generatorType) {
		this.wrapperType = wrapperType
		this.scale = scale 
		this.generatorType = generatorType
		this.xform = wrapperType.wrapper
		this.scenario = getScenario(scale, rand)
		this.scenario.tool = xform.class.simpleName + "-" + generatorType.name
	}
	
	def startTest(){
    	info('''START TEST: Xform: «wrapperType», Gen: «generatorType», Scale: «scale», Scenario: «scenario.class.name»''')
    }
    
    def endTest(){
    	info('''END TEST: Xform: «wrapperType», Gen: «generatorType», Scale: «scale», Scenario: «scenario.class.name»''')
    }
	
	@BeforeClass
	static def callGCBefore(){
		callGC
	}
	
	@After
	def cleanup() {
		cleanupTransformation;
	}

	@AfterClass
	static def callGC(){
		(0..4).forEach[Runtime.getRuntime().gc()]
		
		try{
			Thread.sleep(1000)
		} catch (InterruptedException ex) {
			Logger.getLogger("cps.performance.tests.CPSPerformanceTest").warn("Sleep after System GC interrupted")
		}
	}

	@Test(timeout=600000)
	def void completeToolchainIntegrationTest() {
		startTest
		
		// communication unit between the phases
		val CPSDataToken token = new CPSDataToken
		token.scenarioName = scenario.class.simpleName
		token.instancesDirPath = instancesDirPath
		token.seed = RANDOM_SEED
		token.size = scale
		token.xform = xform
		token.generatorType = generatorType
		
		val engine = new BenchmarkEngine
		JsonSerializer::setResultPath("./results/json/")
		
		engine.runBenchmark(scenario, token)

		endTest
	}
	
	def BenchmarkScenario getScenario(int scale, Random rand)
}