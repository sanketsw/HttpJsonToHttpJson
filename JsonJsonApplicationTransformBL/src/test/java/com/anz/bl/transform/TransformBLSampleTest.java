/**
 * 
 */
package com.anz.bl.transform;

import junit.framework.TestCase;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;
import org.skyscreamer.jsonassert.JSONAssert;

/**
 * @author sanketsw
 *
 */
public class TransformBLSampleTest extends TestCase {

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
	public void testTransformRequestData() throws JSONException {
		String in  = "{  \"left\": 0,  \"right\": 0 }";
		String expected = "{  \"right\": 0, \"left\": 100 }";
		String notExpected = "{  \"left\": 0,  \"right\": 0 }";
		
		String out =  new TransformBLSample().transformRequestData(in);
		
		JSONObject json = new JSONObject(out);
		JSONAssert.assertEquals(expected, json, false);
		JSONAssert.assertNotEquals(notExpected, json, false);
	}
	
	
}
