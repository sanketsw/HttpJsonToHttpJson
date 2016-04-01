package com.api.beans;

import java.io.Serializable;

public class Result implements Serializable {
	

	public static final String IMPLEMENTATION = "Java_SpringBoot";
	/**
	 * 
	 */
	private static final long serialVersionUID = 3955074699838807226L;
	String imeplementation = IMPLEMENTATION;
	String result;
	
	public Result() {
		super();
	}
	public Result(String value) {
		super();
		this.result = value;
	}
	public String getImeplementation() {
		return imeplementation;
	}
	public void setImeplementation(String imeplementation) {
		this.imeplementation = imeplementation;
	}
	public String getResult() {
		return result;
	}
	public void setResult(String value) {
		this.result = value;
	}
	
	
}
