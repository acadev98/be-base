package com.acadev.baseprojectname.model.response;

import com.acadev.baseprojectname.model.request.ProductRequest;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Builder;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseDTO {
	private Integer code;
	private String message;
	private Object data;
}
