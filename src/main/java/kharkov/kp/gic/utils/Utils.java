package kharkov.kp.gic.utils;

import org.springframework.http.MediaType;
import org.springframework.util.StringUtils;

public class Utils {

	public static String getFileExtension(String fileName) {
		if (StringUtils.hasText(fileName)) {
			int dot = fileName.lastIndexOf('.');
		    return dot == -1 ? "" : fileName.substring(dot + 1).trim().toLowerCase();
		}
		else
			return "";
	}
	
	public static MediaType getMediaTypeByExt(String extension) {
		switch (extension) {
		case "pdf":
			return MediaType.APPLICATION_PDF;
		case "png":
			return MediaType.IMAGE_PNG;
		case "jpg":
		case "jpeg":	
			return MediaType.IMAGE_JPEG;
		default:
			return null;
		}
	}
}
