package fi.foyt.ckc;

import org.json.JSONException;
import org.json.JSONObject;

public class Result {
	
	public Result(Status status) {
	  this.status = status;
  }
	
	public Status getStatus() {
	  return status;
  }
	
	public JSONObject toJson() throws JSONException {
		JSONObject result = new JSONObject();
		result.put("status", status);
		return result;
	}

	private Status status;
}
