package project.cases.token;

import java.io.Serializable;

/**
 *  Controller返回对象定义
 */

public class Result<T> implements Serializable {
	private static final long serialVersionUID = 1L;
	public T result;
	public String code;
	public String msg;
	public boolean success;
	
	public Result(T result, String code, String msg, boolean success) {
		this.result = result;
		this.code = code;
		this.msg = msg;
		this.success = success;
	}
	
	public T getResult() {
		return this.result;
	}

	public void setResult(T result) {
		this.result = result;
	}

	public String getCode() {
		return this.code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getmsg() {
		return this.msg;
	}

	public void setmsg(String msg) {
		this.msg = msg;
	}
	
	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public Result() {
	}

	public static <T> Result<T> ok() {
		return new Result<T>(null, "1", null, true);
	}
	
	public static <T> Result<T> ok(T result) {
		return new Result<T>(result, "1", null, true);
	}
	
	public static <T> Result<T> ok(T result, String code) {
		return new Result<T>(result, code, null, true);
	}
	
	public static <T> Result<T> fail(String code) {
		return new Result<T>(null, code, code, false);
	}
	
	public static <T> Result<T> fail(T result, String code) {
		return new Result<T>(result, code, code, false);
	}
	
	public static <T> Result<T> fail(String code, String msg) {
		return new Result<T>(null, code, msg, false);
	}

}