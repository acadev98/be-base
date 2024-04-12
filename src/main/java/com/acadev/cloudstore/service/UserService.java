package com.acadev.baseprojectname.service;

import com.acadev.baseprojectname.model.request.SignInRequest;
import com.acadev.baseprojectname.model.request.SignUpRequest;
import com.acadev.baseprojectname.model.response.SignInResponse;

public interface UserService {

	String echo();
	
	void signup(SignUpRequest request);
	
	SignInResponse signin(SignInRequest request);

}
