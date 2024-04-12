package com.acadev.baseprojectname.model.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SignUpRequest {
	private String name;
	private String mail;
	private String password;
}
