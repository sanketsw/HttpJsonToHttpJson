/**
 * 
 */
package com.anz.bl.transform;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.anz.bl.transform.pojo.Result;
import com.anz.common.cache.domain.CachePojoSampleDomain;
import com.anz.common.cache.impl.GlobalCacheHandler;
import com.anz.common.cache.impl.InMemoryCacheManager;
import com.anz.common.cache.pojo.CachePojoSample;
import com.anz.common.transform.ITransformer;
import com.anz.common.transform.TransformUtils;

/**
 * @author sanketsw
 * 
 */
public class TransformBLSampleWithCache implements ITransformer<String, String> {

	private static final Logger logger = LogManager.getLogger();

	/* (non-Javadoc)
	 * @see com.anz.common.transform.IJsonJsonTransformer#execute(java.lang.String)
	 */
	public String execute(String inputJson) throws Exception {

		Result json = (Result) TransformUtils.fromJSON(inputJson, Result.class);
		logger.info("inputJson= {}", inputJson);
		if (json.getResult() == null)
			throw new Exception("invalid response data detected");

		// Read data from Cache
		String objectKey = CachePojoSample.ADD;
		CachePojoSample op = CachePojoSampleDomain.getInstance().getOperation(objectKey);
		logger.info("operation domian object= {}", op.toJSON());
		json.setOperation(op.getOperation());
		json.setImeplementation(op.getImeplementation());

		String out = TransformUtils.toJSON(json);
		return out;
	}

}
