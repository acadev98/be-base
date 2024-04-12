package com.acadev.baseprojectname.service.impl;

import java.io.File;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.acadev.baseprojectname.handler.exception.ApiException;
import com.acadev.baseprojectname.model.response.FilesResponse;
import com.acadev.baseprojectname.service.FileService;
import com.acadev.baseprojectname.utils.ConstantsUtils;
import com.acadev.baseprojectname.utils.FunctionsUtils;
import com.acadev.baseprojectname.utils.enums.ApiMessage;

@Service
public class FileServiceImpl implements FileService {

	public String echo() {
		return "file echo message service";
	}

	public void saveFilesByProductId(Integer id, List<MultipartFile> files) {
		
    	if (files.isEmpty()) {
			throw new ApiException(ApiMessage.FILE_EMPTY);
		} else {
	    	
	    	String pathFolder = ConstantsUtils.FILES_PATH + ConstantsUtils.SLASH + id + ConstantsUtils.SLASH;
			
            File directory = new File(pathFolder);
            if (!directory.exists()) {
                directory.mkdirs();
            }
	    	
            for (MultipartFile file : files) {
    	    	
    	    	String fileName = file.getOriginalFilename().replace(ConstantsUtils.SPACE, ConstantsUtils.WITHOUT_SPACE);
    	    	
    	    	boolean isImage = FunctionsUtils.fileNameIsImage(fileName);
    	    	boolean isVideo = FunctionsUtils.fileNameIsImage(fileName);
    	    	
    	    	if (isImage || isVideo) {
        	    	
        			try {
        				
        				File dest = new File(pathFolder+fileName);
        	            file.transferTo(dest);
        	            
        	        } catch (Exception e) {
        	            e.printStackTrace();
        				throw new ApiException(ApiMessage.FILE_SAVE_ERROR);
        	        }
        			
    	    	} else { 
    				throw new ApiException(ApiMessage.FILE_NOT_PERMIT);
    	    	}
    			
			}
            
		}
	}
	
	public FilesResponse getFilesByProductId(Integer id) {

        List<String> imageNames = new ArrayList<String>();
        List<String> videosNames = new ArrayList<String>();
        
    	String pathFolder = ConstantsUtils.FILES_PATH + ConstantsUtils.SLASH + id + ConstantsUtils.SLASH;
    	
    	File directory = new File(pathFolder);
        if (directory.exists() && directory.isDirectory()) {
        	File[] files = directory.listFiles();
            if (files != null) {
                for (File file : files) {
                    if (file.isFile()) {
            	    	boolean isImage = FunctionsUtils.fileNameIsImage(file.getName());
            	    	boolean isVideo = FunctionsUtils.fileNameIsVideo(file.getName());
            	    	if (isImage) {
                            imageNames.add(file.getName());
            	    	}
            	    	if (isVideo) {
            	    		videosNames.add(file.getName());
            	    	}
                    }
                }
            }
        }
    	
        return FilesResponse.builder().path(pathFolder).images(imageNames).videos(videosNames).build();
		
	}

	public Resource getFileByProductIdAndFileName(Integer id, String imageName) {
        try {
            
        	String pathFolder = ConstantsUtils.FILES_PATH + ConstantsUtils.SLASH + id + ConstantsUtils.SLASH;
        	
            Path imagePath = Paths.get(pathFolder).resolve(imageName).normalize();
            Resource resource = new UrlResource(imagePath.toUri());
            if (resource.exists() || resource.isReadable()) {
            	return resource;
            } else {
				throw new ApiException(ApiMessage.FILE_SAVE_ERROR);
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
			throw new ApiException(ApiMessage.FILE_SAVE_ERROR);
        }
	}

}
