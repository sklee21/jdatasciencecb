package net.uxl21.jdatasciencecb.util;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;

public class TextEncoder {
	
	private String sourceStr;
	
	

	public TextEncoder(String sourceStr) {
		this.sourceStr = sourceStr;
	}
	
	
	public String encode(String newEncoding) {
		Charset eucKRCharset = Charset.forName(newEncoding);
		CharBuffer sourceBuffer = CharBuffer.wrap(sourceStr.toCharArray());
		ByteBuffer resultByteBuffer = eucKRCharset.encode(sourceBuffer);
		
		byte[] resultBytes =  resultByteBuffer.array();
		
		return new String(resultBytes, eucKRCharset);
	}

}
