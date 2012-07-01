package fi.foyt.ckc;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class UpdateResult extends Result {

	public UpdateResult(Status status, List<Revision> revisions) {
	  super(status);
	  this.revisions = revisions;
  }
	
	public List<Revision> getRevisions() {
	  return revisions;
  }
	
	@Override
	public JSONObject toJson() throws JSONException {
	  JSONObject jsonObject = super.toJson();
	  
	  JSONArray revisionsJson = new JSONArray();
	  for (Revision revision : revisions) {
	  	JSONObject revisionJson = new JSONObject();
			revisionJson.put("number", revision.getNumber());
			
			if (revision.getPatch() != null)
			  revisionJson.put("patch", revision.getPatch());
			
			if (revision.getProperties() != null) {
				JSONArray propertiesJson = new JSONArray();
				for (String property : revision.getProperties().keySet()) {
					JSONObject propertyJson = new JSONObject();
					propertyJson.put("name", property);
					propertyJson.put("value", revision.getProperties().get(property));
					propertiesJson.put(propertyJson);
				}
				revisionJson.put("properties", propertiesJson);
			}
			
			revisionsJson.put(revisionJson);
	  }
	  jsonObject.put("revisions", revisionsJson);
	  
	  return jsonObject;
	}
	
	private List<Revision> revisions;
}
