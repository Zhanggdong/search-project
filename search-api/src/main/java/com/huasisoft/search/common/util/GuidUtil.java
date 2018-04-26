package com.huasisoft.search.common.util;

import java.nio.ByteBuffer;
import java.util.UUID;

import org.apache.commons.codec.binary.Base64;

public class GuidUtil {
	/*
	 * 根据UUID生成guid
	 */
	private static String guidLength = "36";

	public static String to32(String s){ 
		return s.replace("{", "").replace("}", "").replace("-", "");
	}
	
	public static String genGuid(){
		String guid = null;
		switch (Integer.valueOf(guidLength)) {
			case 22:
				guid = genGuid22();
				break;
			case 32:
				guid = genGuid32();
				break;
			case 36:
				guid = genGuid36();
				break;
			case 38:
				guid = genGuid38();
				break;
			case 58:
				guid = genGuidBase58();
				break;
			case 64:
				guid = genGuidBase64();
				break;
			default:
		}
		return guid;
	}

	public static String[] chars = new String[] { "a", "b", "c", "d", "e", "f",  
		 "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s",  
		 "t", "u", "v", "w", "x", "y", "z", "0", "1", "2", "3", "4", "5",  
		 "6", "7", "8", "9", "A", "B", "C", "D", "E", "F", "G", "H", "I",  
		 "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V",  
		 "W", "X", "Y", "Z", "-", "_" };
	
	private synchronized static String genGuid22() {
		 StringBuffer shortBuffer = new StringBuffer();  
		 String uuid = UUID.randomUUID().toString().replace("-", "");  
		                 // 每3个十六进制字符转换成为2个字符   
		 for (int i = 0; i < 10; i++) {  
	         String str = uuid.substring(i * 3, i * 3 + 3);  
	                         //转成十六进制    
	         int x = Integer.parseInt(str, 16);  
	                         //除64得到前面6个二进制数的   
	         shortBuffer.append(chars[x / 0x40]);  
	                          // 对64求余得到后面6个二进制数1   
	         shortBuffer.append(chars[x % 0x40]);  
		 }
		                 //加上后面两个没有改动的    
		 shortBuffer.append(uuid.charAt(30));  
		 shortBuffer.append(uuid.charAt(31));  
		 return shortBuffer.toString();
	}
	
	public synchronized static String genGuid32() {
		String s = UUID.randomUUID().toString();
		s = s.replaceAll("-", "");
		return s;
	}

	private synchronized static String genGuid36() {
		return UUID.randomUUID().toString();
	}

	private synchronized static String genGuid38() {
		String s = UUID.randomUUID().toString();
		return "{" + s + "}";
	}
	
	private synchronized static String genGuidBase58(){
		UUID uuid = UUID.randomUUID();
		ByteBuffer bb = ByteBuffer.wrap(new byte[16]);
        bb.putLong(uuid.getMostSignificantBits());
        bb.putLong(uuid.getLeastSignificantBits());
        return Base58.encode(bb.array());
	}

	private synchronized static String genGuidBase64() {
        UUID uuid = UUID.randomUUID();
        return base64Uuid(uuid);
    }

    protected static String base64Uuid(UUID uuid) {
        ByteBuffer bb = ByteBuffer.wrap(new byte[16]);
        bb.putLong(uuid.getMostSignificantBits());
        bb.putLong(uuid.getLeastSignificantBits());
        return Base64.encodeBase64URLSafeString(bb.array());
    }

    public static String encodeBase64Uuid(String uuidString) {
        UUID uuid = UUID.fromString(uuidString);
        return base64Uuid(uuid);
    }
    
    public static String decodeBase64Uuid(String compressedUuid) {
        byte[] byUuid = Base64.decodeBase64(compressedUuid);
        ByteBuffer bb = ByteBuffer.wrap(byUuid);
        UUID uuid = new UUID(bb.getLong(), bb.getLong());
        return uuid.toString();
    }

}
