/**
 * 
 */
package com.anz.bl.compute;

import com.anz.common.compute.impl.CommonErrrorTransformCompute;
import com.anz.common.transform.ITransformer;
import com.anz.error.TransformErrorResponse;
import com.ibm.broker.plugin.MbMessageAssembly;

/**
 * @author root
 *
 */
public class ErrorTransformCompute extends CommonErrrorTransformCompute {

	/* (non-Javadoc)
	 * @see com.anz.common.compute.impl.CommonErrrorTransformCompute#getTransformer()
	 */
	@Override
	public ITransformer<MbMessageAssembly, String> getTransformer() {
		return new TransformErrorResponse();
	}

}
