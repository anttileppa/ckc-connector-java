package fi.foyt.ckc;

import javax.servlet.http.HttpServletRequest;

public interface CKCConnector {

	public InitResult init(HttpServletRequest request, String documentId) throws CKCConnectorException;
	public CreateResult create(HttpServletRequest request, String parentId, String title, String content);
	public UpdateResult update(HttpServletRequest request, String documentId, Long revisionNumber) throws CKCConnectorException;
	public SaveResult save(HttpServletRequest request, String documentId, String patch) throws CKCConnectorException;
	public boolean validateToken(HttpServletRequest request, String token) throws CKCConnectorException;
	
}
