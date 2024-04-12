package com.acadev.baseprojectname.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.acadev.baseprojectname.handler.ResponseHandler;
import com.acadev.baseprojectname.service.FileService;
import com.acadev.baseprojectname.utils.MessagesUtils;

@RestController
@RequestMapping("/api/files")
public class FileController {
	
	@Autowired
	private FileService service;

	@GetMapping("/echo")
	public ResponseEntity<Object> echoTest() {
		return ResponseHandler.generateResponse(service.echo(), HttpStatus.OK);
	}

	@PostMapping("/{id}")
	public ResponseEntity<Object> saveImageByProductId(@PathVariable("id") Integer id, @RequestParam("files") List<MultipartFile> files) {
		service.saveFilesByProductId(id, files);
		return ResponseHandler.generateResponse(MessagesUtils.FILE_SAVED, HttpStatus.CREATED);
	}

	@GetMapping("/{id}")
	public ResponseEntity<Object> getFilesByProductId(@PathVariable("id") Integer id) {
		return ResponseHandler.generateResponse(MessagesUtils.LIST_OF_FILES, HttpStatus.OK, service.getFilesByProductId(id));
	}

	@GetMapping("/{id}/{fileName}")
	public ResponseEntity<Resource> getResourceFileByProductId(@PathVariable("id") Integer id, @PathVariable("fileName") String fileName) {
		return ResponseEntity.ok().body(service.getFileByProductIdAndFileName(id, fileName));
	}
	
}
