package com.ensta.librarymanager.exception;

public class DaoException extends Exception{
	public DaoException(){
		super();
	}
	
	public DaoException(String message){
		super(message);
	}

	public DaoException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}
}
