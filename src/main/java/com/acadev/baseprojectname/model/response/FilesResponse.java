package com.acadev.baseprojectname.model.response;

import java.util.List;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FilesResponse {
	private String path;
	private List<String> images;
	private List<String> videos;
}
