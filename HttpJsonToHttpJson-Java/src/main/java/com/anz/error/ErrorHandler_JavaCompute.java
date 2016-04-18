package com.anz.error;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.ibm.broker.javacompute.MbJavaComputeNode;
import com.ibm.broker.plugin.MbBLOB;
import com.ibm.broker.plugin.MbElement;
import com.ibm.broker.plugin.MbException;
import com.ibm.broker.plugin.MbMessage;
import com.ibm.broker.plugin.MbMessageAssembly;
import com.ibm.broker.plugin.MbOutputTerminal;
import com.ibm.broker.plugin.MbUserException;

public class ErrorHandler_JavaCompute extends MbJavaComputeNode {
	static final Logger logger = LogManager.getLogger(ErrorHandler_JavaCompute.class.getName());


	public void evaluate(MbMessageAssembly inAssembly) throws MbException {
		MbOutputTerminal out = getOutputTerminal("out");
		MbOutputTerminal alt = getOutputTerminal("alternate");
		

		MbMessage inMessage = inAssembly.getMessage();
		MbMessageAssembly outAssembly = null;
		try {
			// create new message as a copy of the input
			MbMessage outMessage = new MbMessage(inMessage);
			MbElement outRoot = outMessage.getRootElement();
			outAssembly = new MbMessageAssembly(inAssembly, outMessage);
			// ----------------------------------------------------------
			// Add user code below
			
			StringBuilder sb = new StringBuilder();
			
			// To handle error details information
			if(inMessage.getRootElement().getFirstElementByPath("/BLOB/BLOB")!=null){
				byte[] MessageBodyByteArray  = (byte[])(inMessage.getRootElement().getFirstElementByPath("/BLOB/BLOB").getValue());
				String messageBodyString  = new String(MessageBodyByteArray);
				messageBodyString = messageBodyString + ", adding error text"; 
			
				if(messageBodyString.length()>0){
					sb.append("<h1>");					
					sb.append(messageBodyString);
					sb.append("</h1>");
				}
			}
			
			/*if(inMessage.getRootElement().getFirstElementByPath("/HTTPResponseHeader/X-Original-HTTP-Status-Code")!=null){
				int messageBodyString  = Integer.parseInt(inMessage.getRootElement().getFirstElementByPath("/HTTPResponseHeader/X-Original-HTTP-Status-Code").getValueAsString());
				if(messageBodyString!=0){
					sb.append("<h1>");	
					sb.append(messageBodyString);
					sb.append("</h1>");
				}
				
			}*/
			
			

			// End of user code
			
			
			//Create the Broker Blob Parser element
			MbElement outParser = outRoot.createElementAsLastChild(MbBLOB.PARSER_NAME);
			outParser.createElementAsLastChild(MbElement.TYPE_NAME_VALUE, "BLOB", sb.toString().getBytes());
			logger.info("ErrorHandler_JavaCompute error message"+sb.toString());
			// ----------------------------------------------------------
		} catch (MbException e) {
			// Re-throw to allow Broker handling of MbException
			logger.error("error from ErrorHandler_JavaCompute", e);
			throw e;
		} catch (RuntimeException e) {
			// Re-throw to allow Broker handling of RuntimeException
			logger.error("error from ErrorHandler_JavaCompute", e);
			throw e;
		} catch (Exception e) {
			// Consider replacing Exception with type(s) thrown by user code
			// Example handling ensures all exceptions are re-thrown to be handled in the flow
			logger.error("error from ErrorHandler_JavaCompute", e);
			throw new MbUserException(this, "evaluate()", "", "", e.toString(),
					null);
		}
		// The following should only be changed
		// if not propagating message to the 'out' terminal
		out.propagate(outAssembly);

	}

}
