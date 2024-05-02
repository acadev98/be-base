package com.acadev.baseprojectname.utils;

public class FunctionsUtils {

	public static boolean fileNameIsImage(String fileName) {
		
		if (fileName != null && (fileName.endsWith(".jpg") || fileName.endsWith(".jpeg") || fileName.endsWith(".png"))) {
		    // Es una imagen
			return true;
		}

		return false;
	}

	public static boolean fileNameIsVideo(String fileName) {
		
		if (fileName != null && (fileName.endsWith(".mp4") || fileName.endsWith(".avi") || fileName.endsWith(".mov"))) {
		    // Es un vídeo
			return true;
		}

		return false;
	}

}
