package com.anz.compute;

import com.anz.bl.transform.TransformBLSample;
import com.anz.common.compute.CommonJavaCompute;
import com.anz.common.compute.TransformType;
import com.ibm.broker.javacompute.MbJavaComputeNode;
import com.ibm.broker.plugin.MbException;
import com.ibm.broker.plugin.MbMessage;
import com.ibm.broker.plugin.MbMessageAssembly;
import com.ibm.broker.plugin.MbOutputTerminal;
import com.ibm.broker.plugin.MbUserException;

/**
 * @author sanketsw
 *
 */
public class SimpleTransformCompute extends CommonJavaCompute {

	/* (non-Javadoc)
	 * @see com.anz.common.compute.ICommonComputeNode#getTransformationType()
	 */
	public TransformType getTransformationType() {
		return TransformType.JSON_TO_JSON; 
	}

	/* (non-Javadoc)
	 * @see com.anz.common.compute.CommonJavaCompute#executeJsonToJsonTranform(java.lang.String)
	 */
	@Override
	public String executeJsonToJsonTranform(String inputJson) throws Exception {
		String outJson = new TransformBLSample().transformRequestData(inputJson);
		return outJson;
	}
	
	
	
}