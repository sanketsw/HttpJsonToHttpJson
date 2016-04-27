/**
 * 
 */
package com.anz.HttpJsonToHttpJson.compute;

import com.anz.HttpJsonToHttpJson.error.TransformErrorResponse;
import com.anz.common.compute.TransformType;
import com.anz.common.compute.impl.CommonErrorTransformCompute;
import com.anz.common.transform.ITransformer;
import com.ibm.broker.plugin.MbMessageAssembly;

/**
 * @author root
 *
 */
public class ErrorTransformCompute extends CommonErrorTransformCompute {

	/* (non-Javadoc)
	 * @see com.anz.common.compute.impl.CommonErrrorTransformCompute#getTransformer()
	 */
	@Override
	public ITransformer<MbMessageAssembly, String> getTransformer() {
		return new TransformErrorResponse();
	}

	@Override
	public TransformType getTransformationType() {
		return TransformType.HTTP_HHTP;
	}

}
