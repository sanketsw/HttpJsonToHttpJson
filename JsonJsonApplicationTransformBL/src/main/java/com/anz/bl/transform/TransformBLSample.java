/**
 * 
 */
package com.anz.bl.transform;

import com.anz.bl.transform.pojo.NumbersInput;
import com.anz.common.transform.IJsonJsonTransformer;
import com.anz.common.transform.TransformUtils;


/**
 * @author sanketsw
 * 
 */
public class TransformBLSample implements IJsonJsonTransformer {

	
	/* (non-Javadoc)
	 * @see com.anz.common.transform.IJsonJsonTransformer#execute(java.lang.String)
	 */
	public String execute(String inputJson) throws Exception {
		NumbersInput json = (NumbersInput) TransformUtils.fromJSON(inputJson,
				NumbersInput.class);
		json.setLeft(json.getLeft() + 100);
		String out = TransformUtils.toJSON(json);
		return out;
	}


}
