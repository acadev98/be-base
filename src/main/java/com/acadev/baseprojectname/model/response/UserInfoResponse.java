package com.acadev.baseprojectname.model.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserInfoResponse {
	private String email;
	private String username;
	private String name;
	private String lastname;
}
