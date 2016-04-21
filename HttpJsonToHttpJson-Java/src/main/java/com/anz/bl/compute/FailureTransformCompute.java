/**
 * 
 */
package com.anz.bl.compute;

import com.anz.common.compute.impl.CommonErrrorTransformCompute;
import com.anz.common.transform.ITransformer;
import com.anz.error.TransformFailureResponse;
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

}
