package fi.foyt.ckc.utils;

import java.util.HashMap;
import java.util.Map;

public class CKCUtils {

	public static Map<String, String> parseProperties(String properties) {
		Map<String, String> result = new HashMap<String, String>();
		
		int i = 0;
		int l = properties.length();
		boolean escaped = false;
		StringBuilder buffer = new StringBuilder();
		String name = null;
		
		while (i < l) {
			char c = properties.charAt(i);
			if (c == '\\') {
				escaped = true;
			} else {
				if (escaped) {
					buffer.append(c);
				} else {
  				switch (c) {
  					case ':':
  						name = buffer.toString();
  						buffer = new StringBuilder();
  				  break;
  					case ';':
  						result.put(name, buffer.toString());
  						buffer = new StringBuilder();
  				  break;
  				  default:
  				  	buffer.append(c);
  				  break;
  				}
				}
				escaped = false;
			}

			i++;
		}
		
		result.put(name, buffer.toString());

	  return result;
  }
	
}
