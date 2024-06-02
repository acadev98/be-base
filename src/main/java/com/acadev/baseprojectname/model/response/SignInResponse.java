package com.acadev.baseprojectname.model.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SignInResponse {
	private String email;
	private String token;
}
