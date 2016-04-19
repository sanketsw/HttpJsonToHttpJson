package com.anz.error;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.anz.common.compute.impl.ComputeUtils;
import com.anz.common.dataaccess.models.iib.IFXCode;
import com.anz.common.transform.TransformUtils;
import com.ibm.broker.javacompute.MbJavaComputeNode;
import com.ibm.broker.plugin.MbBLOB;
import com.ibm.broker.plugin.MbElement;
import com.ibm.broker.plugin.MbException;
import com.ibm.broker.plugin.MbMessage;
import com.ibm.broker.plugin.MbMessageAssembly;
import com.ibm.broker.plugin.MbOutputTerminal;
import com.ibm.broker.plugin.MbUserException;

public class FailureHandler_JavaCompute extends MbJavaComputeNode {
	static final Logger logger = LogManager.getLogger(FailureHandler_JavaCompute.class.getName());

	public void evaluate(MbMessageAssembly inAssembly) throws MbException {
		MbOutputTerminal out = getOutputTerminal("out");
		MbOutputTerminal alt = getOutputTerminal("alternate");
	
		

		MbMessage inMessage = inAssembly.getMessage();
		MbMessageAssembly outAssembly = null;
		try {
			// create new message as a copy of the input
			MbMessage outMessage = new MbMessage(inMessage);
			MbElement outRoot = outMessage.getRootElement();
			//outAssembly = new MbMessageAssembly(inAssembly, outMessage);
			MbMessage inLocalEnvironment = inAssembly.getLocalEnvironment();
			MbMessage outLocalEnviroment = new MbMessage(inLocalEnvironment);
			outAssembly = new MbMessageAssembly(inAssembly, outLocalEnviroment,inAssembly.getExceptionList(),outMessage);
			// ----------------------------------------------------------
			// Add user code below
			
			StringBuilder sb = new StringBuilder();
			// To handle exception from exceptionlist
			MbElement exception = inAssembly.getExceptionList().getRootElement().getFirstElementByPath("RecoverableException");
			String text;
			while (exception != null) {
				MbElement insert = exception.getFirstElementByPath("Insert");
				text = (String)insert.getFirstElementByPath("Text").getValue();
				//text = (String)exception.getFirstElementByPath("Text").getValue();
				
				if (text.length() > 0) {
					  sb.append("<h1>");					
					  sb.append(text);
					  sb.append("</h1>");					
				}
				exception = exception.getFirstElementByPath("RecoverableException");
			}
			
			// TODO Chamun Map the exception from ErrorCodeMappingDomain to user understandable String
			// if exception nullpointer exception then String message is "Error occurred processing the input message. please verify the input data"
			/*IFXCode code = new IFXCode();
			code.setCode(code)
			code.setDescr(descr)
			code.setSeverity(severity);
			code.setStatus(status);*/
			ComputeUtils.replaceJsonDataToBlob(outMessage, TransformUtils.toJSON(sb.toString()));
			
			// TODO Chamun Copy the HTTP response header entirely
			
			//Create the Broker Blob Parser element
			//MbElement outParser = outRoot.createElementAsLastChild(MbBLOB.PARSER_NAME);
			//outParser.createElementAsLastChild(MbElement.TYPE_NAME_VALUE, "BLOB", sb.toString().getBytes());
			logger.info("FailureHandler_JavaCompute error message"+sb.toString());

			// End of user code
			// ----------------------------------------------------------
		} catch (MbException e) {
			// Re-throw to allow Broker handling of MbException
			logger.error("error from FailureHandler_JavaCompute", e);
			throw e;
		} catch (RuntimeException e) {
			// Re-throw to allow Broker handling of RuntimeException
			logger.error("error from FailureHandler_JavaCompute", e);
			throw e;
		} catch (Exception e) {
			// Consider replacing Exception with type(s) thrown by user code
			// Example handling ensures all exceptions are re-thrown to be handled in the flow
			logger.error("error from FailureHandler_JavaCompute", e);
			throw new MbUserException(this, "evaluate()", "", "", e.toString(),
					null);
		}
		// The following should only be changed
		// if not propagating message to the 'out' terminal
		out.propagate(outAssembly);

	}

}
