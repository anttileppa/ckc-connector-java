package fi.foyt.ckc;

import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class LoadResult extends Result {

	public LoadResult(Status status, Long revisionNumber, String content, Map<String, String> properties) {
	  super(status);
	  
	  this.revisionNumber = revisionNumber;
	  this.content = content;
	  this.properties = properties;
  }
	
	public String getContent() {
	  return content;
  }
	
	public Map<String, String> getProperties() {
	  return properties;
  }
	
	public Long getRevisionNumber() {
	  return revisionNumber;
  }
	
	@Override
	public JSONObject toJson() throws JSONException {
	  JSONObject jsonObject = super.toJson();
	  
	  jsonObject.put("revisionNumber", revisionNumber);
	  jsonObject.put("content", content);
	  if (getProperties() != null) {
			JSONArray propertiesJson = new JSONArray();
			for (String property : getProperties().keySet()) {
				JSONObject propertyJson = new JSONObject();
				propertyJson.put("name", property);
				propertyJson.put("value", getProperties().get(property));
				propertiesJson.put(propertyJson);
			}
			jsonObject.put("properties", propertiesJson);
		}
	  
	  return jsonObject;
	}
	
	private Long revisionNumber;
	private String content;
	private Map<String, String> properties;
}
