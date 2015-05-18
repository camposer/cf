package com.cf.tradeprocessor.web.rest.response;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class JsonResponse {
	private Boolean success;
	@JsonIgnore
	private Object result;
	
	private JsonResponse(Boolean success, Object result) {
		this.success = success;
		this.result = result;
	}

	public static JsonResponse success(Object result) {
		return new JsonResponse(true, result);
	}
	
	public static JsonResponse success() {
		return success(null);
	}

	public static JsonResponse error(String message) {
		return new JsonResponse(false, message);
	}
	
	public static JsonResponse error() {
		return error(null);
	}

	public Boolean getSuccess() {
		return success;
	}

	public Object getResult() {
		return result;
	}
}
