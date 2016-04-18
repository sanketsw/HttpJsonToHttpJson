/**
 * 
 */
package com.anz.bl.transform;

import static org.junit.Assert.assertTrue;

import org.json.JSONObject;
import org.junit.Test;
import org.skyscreamer.jsonassert.JSONAssert;

import com.anz.common.dataaccess.models.iib.Operation;

/**
 * @author sanketsw
 *
 */
public class TransformBLSampleWithCacheTest {

	
	//@Test
	public void testTransformResponseData() throws Exception {
		String in  = "{\"imeplementation\":\"Java_SpringBoot\",\"result\":\"35\"}";
		String expected = "{\"imeplementation\":\"IIB REST API implementation\"}";
		String notExpected = "{\"imeplementation\":\"Java_SpringBoot\"}";
		Operation op = new Operation();	
		op.setOperation(Operation.ADD);
		op.setImeplementation("IIB REST API implementation");
		
		String out =  new TransformBLSampleWithCache().execute(in);
		
		JSONObject json = new JSONObject(out);
		JSONAssert.assertEquals(expected, json, false);
		JSONAssert.assertNotEquals(notExpected, json, false);
	}
	
	@Test
	public void testInvalidResponseDataWithoutResultField() {
		String in  = "{\"imeplementation\":\"Java_SpringBoot\"}";
		Operation op = new Operation();	
		op.setOperation(Operation.ADD);
		op.setImeplementation("IIB REST API implementation");
		
		boolean exceptionThrown =  false;
		try {
			String out = new TransformBLSampleWithCache().execute(in);
		} catch (Exception e) {
			exceptionThrown = true;
		}
		assertTrue(exceptionThrown);
		
	}
	
	//@Test
	public void testCachedDataTransformation() throws Exception {
		String in  = "{\"imeplementation\":\"Java_SpringBoot\",\"result\":\"35\"}";
		String expected = "{\"operation\":\"Add\"}";
		
		Operation op = new Operation();	
		op.setOperation(Operation.ADD);
		op.setImeplementation("IIB REST API implementation");
		String out = null;
		try {
			out =  new TransformBLSampleWithCache().execute(in);		
		} catch(Exception e) {
			e.printStackTrace();
		}
		JSONObject json = new JSONObject(out);
		JSONAssert.assertEquals(expected, json, false);
	}

}
