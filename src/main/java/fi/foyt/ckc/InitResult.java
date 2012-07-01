package fi.foyt.ckc;

import org.json.JSONException;
import org.json.JSONObject;

public class InitResult extends Result {

	public InitResult(Status status, String token) {
	  super(status);
	  this.token = token;
  }
	
	@Override
	public JSONObject toJson() throws JSONException {
	  JSONObject jsonObject = super.toJson();
	  jsonObject.put("token", token);
	  return jsonObject;
	}
	
	private String token;
}
