package fi.foyt.ckc;

import java.util.HashMap;
import java.util.Map;

public class Revision {
	
	public Revision(Long number, String patch) {
	  this.number = number;
	  this.patch = patch;
  }
	
	public Long getNumber() {
	  return number;
  }
	
	public String getPatch() {
	  return patch;
  }
	
	public void addProperty(String property, String value) {
		if (properties == null) {
			properties = new HashMap<String, String>();
		}
		
		properties.put(property, value);
	}
	
	public Map<String, String> getProperties() {
	  return properties;
  }

	private Long number;
	private String patch;
	private Map<String, String> properties;
}
