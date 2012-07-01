package fi.foyt.ckc;

import org.json.JSONException;
import org.json.JSONObject;

public class SaveResult extends Result {

	public SaveResult(Status status, Long revisionNumber) {
	  super(status);
	  this.revisionNumber = revisionNumber;
  }
	
	@Override
	public JSONObject toJson() throws JSONException {
	  JSONObject jsonObject = super.toJson();
	  jsonObject.put("revisionNumber", revisionNumber);
	  return jsonObject;
	}
	
	private Long revisionNumber;
}
