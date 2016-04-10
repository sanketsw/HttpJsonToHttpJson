/**
 * 
 */
package com.anz.bl.transform;

import com.anz.bl.transform.pojo.NumbersInput;
import com.anz.common.transform.ITransform;
import com.anz.common.transform.TransformUtils;


/**
 * @author sanketsw
 * 
 */
public class TransformBLSample implements ITransform {

	/**
	 * Transform the input request data here
	 * 
	 * @param jsonInput
	 * @return JSON String after conversion
	 */
	public String transformRequestData(String jsonInput) {
		NumbersInput json = (NumbersInput) TransformUtils.fromJSON(jsonInput,
				NumbersInput.class);
		json.setLeft(json.getLeft() + 100);
		String out = TransformUtils.toJSON(json);
		return out;
	}


}
