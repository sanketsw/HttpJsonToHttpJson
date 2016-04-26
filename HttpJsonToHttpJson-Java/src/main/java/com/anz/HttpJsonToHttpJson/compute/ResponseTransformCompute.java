/**
 * 
 */
package com.anz.HttpJsonToHttpJson.compute;

import com.anz.HttpJsonToHttpJson.transform.TransformBLSampleWithCache;
import com.anz.common.compute.impl.CommonJsonJsonTransformCompute;
import com.anz.common.transform.ITransformer;
import com.ibm.broker.plugin.MbMessageAssembly;

/**
 * @author sanketsw
 *
 */
public class ResponseTransformCompute extends CommonJsonJsonTransformCompute {

	/* (non-Javadoc)
	 * @see com.anz.common.compute.impl.CommonJsonJsonTransformCompute#getTransformer()
	 */
	@Override
	public ITransformer<String, String> getTransformer() {
		return new TransformBLSampleWithCache();
	}

	@Override
	public void saveUserProvidedProperties(MbMessageAssembly outAssembly) {
		// Nothing to do here.
		
	}

}
