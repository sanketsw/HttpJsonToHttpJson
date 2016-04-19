/**
 * 
 */
package com.anz.error;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.anz.common.compute.impl.ComputeUtils;
import com.anz.common.dataaccess.models.iib.IFXCode;
import com.anz.common.domain.IFXCodeDomain;
import com.anz.common.transform.ITransformer;
import com.anz.common.transform.TransformUtils;
import com.ibm.broker.plugin.MbMessage;
import com.ibm.broker.plugin.MbMessageAssembly;

/**
 * @author sanketsw
 * 
 */
public class TransformErrorResponse implements
		ITransformer<MbMessageAssembly, String> {

	private static final Logger logger = LogManager.getLogger();

	@Override
	public String execute(MbMessageAssembly inAssembly) throws Exception {
		String out = null;
		MbMessage inMessage = inAssembly.getMessage();
		String inputString = ComputeUtils.getJsonDataFromBlob(inMessage);

		// Log the error
		logger.error(inputString);

		// Return the error after mapping errorCode from cache/database
		IFXCode errorCode = IFXCodeDomain.getInstance().getErrorCode("305");

		// If error code cannot be mapped, then return the original error
		if (errorCode == null) {
			StringBuilder sb = new StringBuilder();
			if (inputString.length() > 0) {
				sb.append("<h1>");
				sb.append(inputString);
				sb.append("</h1>");
				out = sb.toString();
			}
			logger.info("passing the error over as it is {} ", out);
		} else {
			out = TransformUtils.toJSON(errorCode);
			logger.info("got the error code object from static data: {}", out);
		}
		return out;
	}

}
