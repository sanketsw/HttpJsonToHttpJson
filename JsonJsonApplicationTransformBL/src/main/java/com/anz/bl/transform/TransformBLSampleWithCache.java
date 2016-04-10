/**
 * 
 */
package com.anz.bl.transform;

import com.anz.bl.transform.pojo.Result;
import com.anz.common.cache.bean.CachePojoSample;
import com.anz.common.cache.impl.CacheableObject;
import com.anz.common.transform.ITransform;
import com.anz.common.transform.TransformUtils;


/**
 * @author sanketsw
 *
 */
public class TransformBLSampleWithCache implements ITransform {
	

	/**
	 * @param jsonData
	 *            JSON String
	 * @param cachedData
	 *            cached Data
	 * @return JSON string
	 * @throws Exception
	 *             is any
	 */
	public String transformResponseData(String jsonData,
			CacheableObject cachedData) throws Exception {

		Result json = (Result) TransformUtils.fromJSON(jsonData, Result.class);
		if (json.getResult() == null)
			throw new Exception("invalid response data detected");

		CachePojoSample op = (CachePojoSample) cachedData;
		json.setOperation(op.getOperation());
		json.setImeplementation(op.getImeplementation());

		String out = TransformUtils.toJSON(json);
		return out;
	}

}
