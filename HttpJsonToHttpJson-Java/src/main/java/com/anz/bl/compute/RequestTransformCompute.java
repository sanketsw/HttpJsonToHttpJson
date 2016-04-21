/**
 * 
 */
package com.anz.bl.compute;

import com.anz.bl.transform.TransformBLSample;
import com.anz.common.compute.impl.CommonJsonJsonTransformCompute;
import com.anz.common.transform.ITransformer;

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

}
