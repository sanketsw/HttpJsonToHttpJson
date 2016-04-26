/**
 * 
 */
package com.anz.HttpJsonToHttpJson.compute;

import com.anz.HttpJsonToHttpJson.transform.TransformBLSample;
import com.anz.common.compute.impl.CommonJsonJsonTransformCompute;
import com.anz.common.transform.ITransformer;
import com.ibm.broker.plugin.MbMessageAssembly;

/**
 * @author sanketsw
 *
 */
public class RequestTransformCompute extends CommonJsonJsonTransformCompute {

	/* (non-Javadoc)
	 * @see com.anz.common.compute.impl.CommonJsonJsonTransformCompute#getTransformer()
	 */
	@Override
	public ITransformer<String, String> getTransformer() {
		return new TransformBLSample();
	}

	@Override
	public void saveUserProvidedProperties(MbMessageAssembly outAssembly) {
		// TODO get incident area
		// Save to local environment
		
	}

}
