package org.bitsquad.warzone.cli;

/**
 *	This class represents the CLI class will return after processing an inputed command 
 */
public class CliResponse {
	boolean d_error;
	String d_err_msg;
	boolean d_executeAssignCountries;
	
	
	public boolean isError() {
		return d_error;
	}

	public void setError(boolean p_error) {
		this.d_error = p_error;
	}

	public String getErr_msg() {
		return d_err_msg;
	}

	public void setErr_msg(String p_err_msg) {
		this.d_err_msg = p_err_msg;
	}

	public boolean isExecuteAssignCountries() {
		return d_executeAssignCountries;
	}

	public void setExecuteAssignCountries(boolean p_executeAssignCountries) {
		this.d_executeAssignCountries = p_executeAssignCountries;
	}
	
	CliResponse(boolean p_new_error, String p_err_msg, boolean p_executeAssignCountries){
		d_error = p_new_error;
		d_err_msg = p_err_msg;
		d_executeAssignCountries = p_executeAssignCountries;
	}
}
