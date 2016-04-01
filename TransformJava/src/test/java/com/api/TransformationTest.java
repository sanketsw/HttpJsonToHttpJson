/**
 * 
 */
package com.api;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;
import org.skyscreamer.jsonassert.JSONAssert;

import junit.framework.TestCase;

/**
 * @author root
 *
 */
public class TransformationTest extends TestCase {

	/* (non-Javadoc)
	 * @see junit.framework.TestCase#setUp()
	 */
	protected void setUp() throws Exception {
		super.setUp();
	}

	/* (non-Javadoc)
	 * @see junit.framework.TestCase#tearDown()
	 */
	protected void tearDown() throws Exception {
		super.tearDown();
	}
	
	@Test
	public void testConvertJsonToJson() throws JSONException {
		String in  = "{  \"left\": 0,  \"right\": 0 }";
		String expected = "{  \"right\": 0, \"left\": 10 }";
		String notExpected = "{  \"left\": 0,  \"right\": 0 }";
		
		String out =  new Transformation().convertJsonToJson(in);
		
		JSONObject json = new JSONObject(out);
		JSONAssert.assertEquals(expected, json, false);
		JSONAssert.assertNotEquals(notExpected, json, false);
	}

}
