package com.clip.payments.ex.utils;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLDecoder;

import com.clip.payments.ex.view.ReadConsole;

public class Util {
	
	
	
	public static String getPath() throws UnsupportedEncodingException {
		URL url = ReadConsole.class.getProtectionDomain().getCodeSource().getLocation();
		String jarPath = URLDecoder.decode(url.getFile(), Constants.CHAR_ENCODING);
		String parentPath = new File(jarPath).getParentFile().getPath();
//		String parentPath2 = new File(parentPath).getParentFile().getPath();
		return parentPath;
	}

}
