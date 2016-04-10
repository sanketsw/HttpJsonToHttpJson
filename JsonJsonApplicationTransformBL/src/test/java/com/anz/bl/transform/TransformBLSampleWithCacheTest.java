/**
 * 
 */
package com.anz.bl.transform;

import junit.framework.TestCase;

import org.json.JSONObject;
import org.junit.Test;
import org.skyscreamer.jsonassert.JSONAssert;

import com.anz.common.cache.bean.CachePojoSample;

/**
 * @author sanketsw
 *
 */
public class TransformBLSampleWithCacheTest extends TestCase {

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
	public void testTransformResponseData() throws Exception {
		String in  = "{\"imeplementation\":\"Java_SpringBoot\",\"result\":\"35\"}";
		String expected = "{\"imeplementation\":\"IIB REST API implementation\"}";
		String notExpected = "{\"imeplementation\":\"Java_SpringBoot\"}";
		CachePojoSample op = new CachePojoSample(CachePojoSample.ADD, "IIB REST API implementation");
		
		String out =  new TransformBLSampleWithCache().transformResponseData(in, op);
		
		JSONObject json = new JSONObject(out);
		JSONAssert.assertEquals(expected, json, false);
		JSONAssert.assertNotEquals(notExpected, json, false);
	}
	
	@Test
	public void testInvalidResponseDataWithoutResultField() {
		String in  = "{\"imeplementation\":\"Java_SpringBoot\"}";
		CachePojoSample op = new CachePojoSample(CachePojoSample.ADD, "IIB REST API implementation");
		
		boolean exceptionThrown =  false;
		try {
			String out = new TransformBLSampleWithCache().transformResponseData(in, op);
		} catch (Exception e) {
			exceptionThrown = true;
		}
		assertTrue(exceptionThrown);
		
	}
	
	public void testCachedDataTransformation() throws Exception {
		String in  = "{\"imeplementation\":\"Java_SpringBoot\",\"result\":\"35\"}";
		String expected = "{\"operation\":\"Add\"}";
		
		CachePojoSample op = new CachePojoSample(CachePojoSample.ADD, "IIB REST API implementation");		
		String out =  new TransformBLSampleWithCache().transformResponseData(in, op);		
		JSONObject json = new JSONObject(out);
		JSONAssert.assertEquals(expected, json, false);
	}

}
