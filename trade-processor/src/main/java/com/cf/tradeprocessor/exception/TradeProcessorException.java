package com.cf.tradeprocessor.exception;

public class TradeProcessorException extends RuntimeException {
	private static final long serialVersionUID = -6553739732696141499L;

	public TradeProcessorException(String message, Throwable throwable) {
		super(message, throwable);
	}

	public TradeProcessorException(String message) {
		super(message);
	}

	public TradeProcessorException(Throwable throwable) {
		super(throwable);
	}
}