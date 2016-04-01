/**
 * 
 */
package com.api;

import com.api.beans.NumbersInput;
import com.google.gson.Gson;

/**
 * @author root
 *
 */
public class Transformation {
	
	public String convertJsonToJson(String jsonInput) {
		NumbersInput json = new Gson().fromJson(jsonInput, NumbersInput.class);
		json.setLeft(json.getLeft() + 10);
		String out = new Gson().toJson(json);
		return out;
	}

}
