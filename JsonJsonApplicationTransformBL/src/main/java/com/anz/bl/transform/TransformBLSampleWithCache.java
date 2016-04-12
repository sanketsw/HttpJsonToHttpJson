/**
 * 
 */
package com.anz.bl.transform;

import com.anz.bl.transform.pojo.Result;
import com.anz.common.cache.bean.CachePojoSample;
import com.anz.common.cache.data.StaticCacheManager;
import com.anz.common.cache.impl.AbstractCachePojo;
import com.anz.common.transform.IJsonJsonTransformer;
import com.anz.common.transform.ITransformer;
import com.anz.common.transform.TransformUtils;

/**
 * @author sanketsw
 * 
 */
public class TransformBLSampleWithCache implements IJsonJsonTransformer {


	/* (non-Javadoc)
	 * @see com.anz.common.transform.IJsonJsonTransformer#execute(java.lang.String)
	 */
	public String execute(String inputJson) throws Exception {

		Result json = (Result) TransformUtils.fromJSON(inputJson, Result.class);
		if (json.getResult() == null)
			throw new Exception("invalid response data detected");

		// Read data from Cache
		String objectKey = CachePojoSample.ADD;
		CachePojoSample op = (CachePojoSample) new StaticCacheManager()
				.lookupCache("DefaultMap", objectKey,
						CachePojoSample.class, true);
		json.setOperation(op.getOperation());
		json.setImeplementation(op.getImeplementation());

		String out = TransformUtils.toJSON(json);
		return out;
	}

}
