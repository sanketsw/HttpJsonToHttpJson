/**
 * 
 */
package com.anz.HttpJsonToHttpJson.compute;

import com.anz.HttpJsonToHttpJson.error.TransformFailureResponse;
import com.anz.common.compute.TransformType;
import com.anz.common.compute.impl.CommonErrrorTransformCompute;
import com.anz.common.transform.ITransformer;
import com.ibm.broker.plugin.MbMessageAssembly;

/**
 * @author root
 *
 */
public class FailureTransformCompute extends CommonErrrorTransformCompute {

	@Override
	public ITransformer<MbMessageAssembly, String> getTransformer() {
		return new TransformFailureResponse();
	}

	@Override
	public TransformType getTransformationType() {
		return TransformType.HTTP_HHTP;
	}

}
