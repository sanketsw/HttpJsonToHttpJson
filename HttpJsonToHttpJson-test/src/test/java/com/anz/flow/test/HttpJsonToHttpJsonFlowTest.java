/**
 * 
 */
package com.anz.flow.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.IOException;
import java.io.StringWriter;
import java.util.List;
import java.util.Properties;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPathExpressionException;

import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.XML;
import org.junit.Before;
import org.junit.Test;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

import com.anz.bl.transform.pojo.NumbersInput;
import com.anz.bl.transform.pojo.Result;
import com.anz.common.cache.pojo.CachePojoSample;
import com.anz.common.test.FlowTest;
import com.google.gson.Gson;
import com.ibm.broker.config.proxy.AttributeConstants;
import com.ibm.broker.config.proxy.ConfigManagerProxyLoggedException;
import com.ibm.broker.config.proxy.ConfigManagerProxyPropertyNotInitializedException;
import com.ibm.broker.config.proxy.RecordedTestData;
import com.ibm.broker.plugin.MbElement;
import com.ibm.broker.plugin.MbMessage;
import com.ibm.broker.plugin.MbXML;
import com.ibm.broker.plugin.MbXPath;

/**
 * @author sanketsw
 * 
 */
public class HttpJsonToHttpJsonFlowTest extends FlowTest {

	private static final Logger logger = LogManager.getLogger();
	
	private Gson gson = new Gson();

	private static final String TEST_FILE_001 = "HttpJsonToHttpJson.Test001.xml";
	
	private static boolean dataInjected = false;
	
	@Override
	@Before
	public void setup()
			throws ConfigManagerProxyPropertyNotInitializedException,
			ConfigManagerProxyLoggedException, IOException {
		super.setup();
	}
	
	public void injectData() throws IOException, ConfigManagerProxyPropertyNotInitializedException, ConfigManagerProxyLoggedException {
		if(! dataInjected) {
			logger.info("injecting data...");
			// load test data from file
			String message = IOUtils.toString(HttpJsonToHttpJsonFlowTest.class.getResourceAsStream(TEST_FILE_001));
			logger.info(message);
			
			Properties injectProps = new Properties();
			injectProps.setProperty(AttributeConstants.DATA_INJECTION_APPLICATION_LABEL, "HttpJsonToHttpJson"); 		
			injectProps.setProperty(AttributeConstants.DATA_INJECTION_MESSAGEFLOW_LABEL, "ZOSRest01"); 			
			injectProps.setProperty(AttributeConstants.DATA_INJECTION_NODE_UUID, "ZOSRest01#FCMComposite_1_1");
			injectProps.setProperty(AttributeConstants.DATA_INJECTION_WAIT_TIME, "60000");
			injectProps.setProperty(AttributeConstants.DATA_INJECTION_MESSAGE_SECTION, message);
			
			// execute flow in sychronous mode
			@SuppressWarnings("unused")
			boolean result = getIntegrationServerProxy().injectTestData(injectProps, true);
			dataInjected = true;
		}
	}
	
	@Test
	public void testMainFlow() throws ConfigManagerProxyPropertyNotInitializedException, ConfigManagerProxyLoggedException, IOException, XPathExpressionException, SAXException, ParserConfigurationException, TransformerException, JSONException {
		injectData();
		testMappingNodeOutput();
		testSimpleTransformNodeOutput();
		testHttpRequestNodeOutput();
		testFinalResult();
	}


	
	public void testMappingNodeOutput() throws ConfigManagerProxyPropertyNotInitializedException, XPathExpressionException, SAXException, IOException, ParserConfigurationException, TransformerException, JSONException {
		
		Node n = null;
		
		// Mapping Node
		List<RecordedTestData> dataList = getTestDataList("ZOSRest01#FCMComposite_1_4");
		
		n = getNodeOutput(dataList.get(0), "/message/JSON/Data/left");
		assertNotNull(n);		
		assertEquals("1", n.getTextContent());
		
		n = getNodeOutput(dataList.get(0), "/message/JSON/Data/right");
		assertNotNull(n);		
		assertEquals("2", n.getTextContent());
		
	}
	
	
	public void testSimpleTransformNodeOutput() throws ConfigManagerProxyPropertyNotInitializedException, XPathExpressionException, SAXException, IOException, ParserConfigurationException {	

		Node n = null;
		
		// Pretransform Node
		List<RecordedTestData> dataList = getTestDataList("ZOSRest01#FCMComposite_1_5");
		
		n = getNodeOutput(dataList.get(0), "/message/JSON/Data/left");
		assertNotNull(n);		
		assertEquals("101", n.getTextContent());
		
		n = getNodeOutput(dataList.get(0), "/message/JSON/Data/right");
		assertNotNull(n);		
		assertEquals("2", n.getTextContent());
		
	}
	
	
	public void testHttpRequestNodeOutput() throws ConfigManagerProxyPropertyNotInitializedException, XPathExpressionException, SAXException, IOException, ParserConfigurationException {	

		Node n = null;
		
		// HttpRequest Node
		List<RecordedTestData> dataList = getTestDataList("ZOSRest01#FCMComposite_1_2");
		
		n = getNodeOutput(dataList.get(0), "/message/JSON/Data/imeplementation");
		assertNotNull(n);		
		assertEquals("Java_SpringBoot", n.getTextContent());
		
		n = getNodeOutput(dataList.get(0), "/message/JSON/Data/result");
		assertNotNull(n);		
		assertEquals("103", n.getTextContent());
		
	}
	
	public void testFinalResult() throws XPathExpressionException, SAXException, IOException, ParserConfigurationException, ConfigManagerProxyPropertyNotInitializedException {	
		
		Node n = null;
		
		// PostTransform node
		List<RecordedTestData> dataList = getTestDataList("ZOSRest01#FCMComposite_1_6");
		
		n = getNodeOutput(dataList.get(0), "/message/JSON/Data");
		
		// TODO convert this to Java Pojo and verify
		/*Result out = gson.fromJson(n.getTextContent(), Result.class);
		assertEquals("IIB REST API implementation", out.getImeplementation());
		assertEquals(CachePojoSample.ADD, out.getOperation());
		assertEquals("103", out.getResult());*/
		
	}



}
