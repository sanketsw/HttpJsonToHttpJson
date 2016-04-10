package com.anz.compute;

import com.anz.bl.transform.TransformBLSampleWithCache;
import com.anz.common.cache.bean.CachePojoSample;
import com.anz.common.cache.impl.CacheManager;
import com.anz.common.compute.CommonJavaCompute;
import com.anz.common.compute.TransformType;


public class TransformComputeWithCache extends CommonJavaCompute {

	/* (non-Javadoc)
	 * @see com.anz.common.compute.ICommonComputeNode#getTransformationType()
	 */
	public TransformType getTransformationType() {
		return TransformType.JSON_TO_JSON;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.anz.common.compute.CommonJavaCompute#executeJsonToJsonTranform(java
	 * .lang.String)
	 */
	@Override
	public String executeJsonToJsonTranform(String inputJson) throws Exception {

		// Read data from Cache
		String objectKey = CachePojoSample.ADD;
		CachePojoSample op = (CachePojoSample) CacheManager.lookupCache(
				getCacheMapName(), objectKey, CachePojoSample.class,
				true);

		// Transform input message and cache
		String outputJson = new TransformBLSampleWithCache().transformResponseData(inputJson, op);

		return outputJson;
	}

}
