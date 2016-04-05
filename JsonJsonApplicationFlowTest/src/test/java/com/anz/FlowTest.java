/**
 * 
 */
package com.anz;


import java.util.ArrayList;
import java.util.List;

import junit.framework.TestCase;

import org.junit.Before;
import org.junit.Test;

import com.anz.util.TestNode;
import com.anz.util.TestUtil;

/**
 * @author root
 *
 */
public class FlowTest extends TestCase {

	/* (non-Javadoc)
	 * @see junit.framework.TestCase#setUp()
	 */
	@Before
	protected void setUp() throws Exception {
		super.setUp();
	}

	@Test
	public void testFlow() throws Exception {
		//USER INPUT DATA
		String integrationNodeName = "TESTNODE_root";
		String integrationServerName = "default";
		String applicationName = "JsonJsonApplication";
		String flowName = "ZOSRest01";
		//File name, containing data to inject
		String inputMessageFile = "inputData.xml";
		/*Injection node UUID:
		* 	<FlowName>#<NodeID>
		* 	NodeIDs can be found within the .msgflow file
		* Supported by the following nodes:
		*   MQ Input
		*   MQTT Subscribe
		*   SOAP Input
		*   HTTP Input
		*   File Input
		*/
		String injectNodeUUID = "ZOSRest01#FCMComposite_1_1";
		
		
		/* Assertion data */
		List<TestNode> testNodeList = new ArrayList<TestNode>();
		
		TestNode mappingNode = new TestNode("ZOSRest01#FCMComposite_1_4");
		mappingNode.addxPathAssertion("/message/JSON/Data/left[.='1']");
		mappingNode.addxPathAssertion("/message/JSON/Data/right[.='2']");
		testNodeList.add(mappingNode);
		
		TestNode finalNode = new TestNode("ZOSRest01#FCMComposite_1_2");
		finalNode.addxPathAssertion("/message/JSON/Data/imeplementation[.='Java_SpringBoot']");
		finalNode.addxPathAssertion("/message/JSON/Data/result[.='3']");
		testNodeList.add(finalNode);	
		
		System.out.println(testNodeList);
		
		new TestUtil().testOnServerUsingIntegrationAPI(integrationNodeName, integrationServerName, inputMessageFile, applicationName, flowName,  injectNodeUUID, testNodeList);
		
	}

}
