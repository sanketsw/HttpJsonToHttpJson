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
import com.ibm.broker.plugin.MbElement;
import com.ibm.broker.plugin.MbMessage;
import com.ibm.broker.plugin.MbMessageAssembly;

/**
 * @author sanketsw
 * 
 */
public class TransformFailureResponse implements
		ITransformer<MbMessageAssembly, String> {

	private static final Logger logger = LogManager.getLogger();

	@Override
	public String execute(MbMessageAssembly outAssembly) throws Exception {
		String out = null;
		
		MbElement exception = outAssembly.getExceptionList().getRootElement().getFirstElementByPath("RecoverableException");
		String exceptionText = "Error";
		while (exception != null) {
			MbElement insert = exception.getFirstElementByPath("Insert");
			while(insert != null) {
				String text = (String)insert.getFirstElementByPath("Text").getValue();			
				if(text != null && !text.isEmpty()) {
					exceptionText = exceptionText + ": " + text;
				}
				insert = insert.getNextSibling();
			}
			exception = exception.getFirstElementByPath("RecoverableException");
		}
		logger.error(exceptionText);

		// Log the error
		//logger.error(inputString);

		// Return the error after mapping errorCode from cache/database
		IFXCode errorCode = IFXCodeDomain.getInstance().getErrorCode("BL305657E");

		// If error code cannot be mapped, then return the original error
		if (errorCode == null) {
			//out = inputString;
			logger.info("passing the error over as it is {} ", out);
		} else {
			errorCode.setDescr(exceptionText);
			out = TransformUtils.toJSON(errorCode);
			logger.info("got the error code object from static data: {}", out);
		}
		return out;
	}

}
