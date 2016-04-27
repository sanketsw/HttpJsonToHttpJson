/**
 * 
 */
package com.anz.flow.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.IOException;
import java.util.List;
import java.util.Properties;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.xpath.XPathExpressionException;

import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONException;
import org.junit.Before;
import org.junit.Test;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

import com.anz.HttpJsonToHttpJson.transform.pojo.NumbersInput;
import com.anz.HttpJsonToHttpJson.transform.pojo.Result;
import com.anz.common.dataaccess.models.iib.Operation;
import com.anz.common.test.FlowTest;
import com.anz.common.transform.TransformUtils;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.ibm.broker.config.proxy.AttributeConstants;
import com.ibm.broker.config.proxy.ConfigManagerProxyLoggedException;
import com.ibm.broker.config.proxy.ConfigManagerProxyPropertyNotInitializedException;
import com.ibm.broker.config.proxy.MessageFlowProxy;
import com.ibm.broker.config.proxy.RecordedTestData;

/**
 * @author sanketsw
 * 
 */
public class HttpJsonToHttpJsonFlowTest extends FlowTest {

	private static final Logger logger = LogManager.getLogger();
	
	private Gson gson = new Gson();
	ObjectMapper objectMapper = new ObjectMapper();

	private static final String TEST_FILE_001 = "HttpJsonToHttpJson.Test001.xml";
	private static final String applicationName = "HttpJsonToHttpJson";
	private static final String flowName = "Main";
	private static final String injectNodeName ="HTTP Input";
	
	
	private static final String MESSAGE_FORMAT = "MessageFormat.xml";
	
	@Override
	@Before
	public void setup()
			throws ConfigManagerProxyPropertyNotInitializedException,
			ConfigManagerProxyLoggedException, IOException {
		super.setup();	
		
		MessageFlowProxy flowProxy = getIntegrationServerProxy().getMessageFlowByName(flowName, applicationName, null);
		setFlowProxy(flowProxy);
	}
	
	public void injectData() throws IOException, ConfigManagerProxyPropertyNotInitializedException, ConfigManagerProxyLoggedException {
		
		logger.info("injecting data...");
		// load test data from file
		String message = IOUtils.toString(HttpJsonToHttpJsonFlowTest.class.getResourceAsStream(TEST_FILE_001));
		String jsonBlob = TransformUtils.getBlob(message);
		String messageFormat = IOUtils.toString(HttpJsonToHttpJsonFlowTest.class.getResourceAsStream(MESSAGE_FORMAT));
		message = messageFormat.replace("MESSAGE_FORMAT", jsonBlob);
		logger.info(message);
		
		Properties injectProps = new Properties();
		injectProps.setProperty(AttributeConstants.DATA_INJECTION_APPLICATION_LABEL, applicationName); 		
		injectProps.setProperty(AttributeConstants.DATA_INJECTION_MESSAGEFLOW_LABEL, flowName); 			
		injectProps.setProperty(AttributeConstants.DATA_INJECTION_NODE_UUID, getNodeUUID(injectNodeName));
		injectProps.setProperty(AttributeConstants.DATA_INJECTION_WAIT_TIME, "60000");
		injectProps.setProperty(AttributeConstants.DATA_INJECTION_MESSAGE_SECTION, message);
		
		// execute flow in sychronous mode
		@SuppressWarnings("unused")
		boolean result = getIntegrationServerProxy().injectTestData(injectProps, true);
		
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
		
		// Mapping Node
		List<RecordedTestData> dataList = getTestDataList("Mapping1");
		
		String json = getNodeOutputJsonStringFromBlob(dataList.get(0));
		JsonNode root = objectMapper.readTree(json);
		
		String element = root.at("/left").asText(); // The element can be specified as /Data/Account/right
		assertEquals("6", element);
		
		element = root.at("/right").asText();
		assertEquals("3", element);
		
	}
	
	
	public void testSimpleTransformNodeOutput() throws ConfigManagerProxyPropertyNotInitializedException, XPathExpressionException, SAXException, IOException, ParserConfigurationException {	
		// Pretransform Node
		List<RecordedTestData> dataList = getTestDataList("Transform Request");
		
		String json = getNodeOutputJsonStringFromBlob(dataList.get(0));
		NumbersInput out = gson.fromJson(json, NumbersInput.class);
		
		assertNotNull(out);
		assertEquals(106, out.getLeft());
		assertEquals(3, out.getRight());
		
	}
	


	public void testHttpRequestNodeOutput() throws ConfigManagerProxyPropertyNotInitializedException, XPathExpressionException, SAXException, IOException, ParserConfigurationException {	

		// HttpRequest Node
		List<RecordedTestData> dataList = getTestDataList("HTTP Request");
		
		String json = getNodeOutputJsonStringFromBlob(dataList.get(0));
		JsonNode root = objectMapper.readTree(json);
		
		String element = root.at("/imeplementation").asText(); // The element can be specified as /Data/Account/right
		assertEquals("Java_SpringBoot", element);
		
		element = root.at("/result").asText();
		assertEquals("109", element);	
				
	}
	
	public void testFinalResult() throws XPathExpressionException, SAXException, IOException, ParserConfigurationException, ConfigManagerProxyPropertyNotInitializedException {	
		
		// PostTransform node
		List<RecordedTestData> dataList = getTestDataList("Transform Response");
		
		String json = getNodeOutputJsonStringFromBlob(dataList.get(0));
		Result out = gson.fromJson(json, Result.class);
		
		assertEquals("IIB REST API implementation", out.getImeplementation());
		assertEquals(Operation.ADD, out.getOperation());
		assertEquals("109", out.getResult());
		
	}



}
