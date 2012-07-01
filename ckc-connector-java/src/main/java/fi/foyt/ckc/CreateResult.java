package fi.foyt.ckc;

import org.json.JSONException;
import org.json.JSONObject;

public class CreateResult extends Result {

	public CreateResult(Status status, String token, String documentId, Long revisionNumber) {
	  super(status);
	  this.token = token;
	  this.documentId = documentId;
	  this.revisionNumber = revisionNumber;
  }
	
	@Override
	public JSONObject toJson() throws JSONException {
	  JSONObject jsonObject = super.toJson();
	  jsonObject.put("token", token);
	  jsonObject.put("documentId", documentId);
	  jsonObject.put("revisionNumber", revisionNumber);
	  return jsonObject;
	}
	
	private String documentId;
	private Long revisionNumber;
	private String token;
}
