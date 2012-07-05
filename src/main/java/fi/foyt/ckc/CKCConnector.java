package fi.foyt.ckc;

import javax.servlet.http.HttpServletRequest;

public interface CKCConnector {

	/**
	 * Initializes session. 
	 * 
	 * Besides preparing the document, implementing method should at least provide token for future authorization.
	 * 
	 * @param request HttpServletRequest object
	 * @param documentId id of document to be initialized
	 * @return InitResult object
	 * @throws CKCConnectorException
	 */
	public InitResult init(HttpServletRequest request, String documentId) throws CKCConnectorException;
	
	/**
	 * Validates that access token is valid.
	 * 
	 * @param request HttpServletRequest object
	 * @param token access token
	 * @return either true or false depending whether token was valid or not.
	 * @throws CKCConnectorException
	 */
	public boolean validateToken(HttpServletRequest request, String token) throws CKCConnectorException;

	/**
	 * Creates new document. Method is called on the first save of the document if no documentId was provided
	 * 
	 * @param request HttpServletRequest object
	 * @param content content of the document
	 * @return CreateResult
	 */
	public CreateResult create(HttpServletRequest request, String content);
	
	/**
	 * Returns updates for the document since the revisionNumber
	 * 
	 * @param request HttpServletRequest object
	 * @param documentId id of the document
	 * @param revisionNumber current revision number of the client's document
	 * @return UpdateResult
	 * @throws CKCConnectorException
	 */
	public UpdateResult update(HttpServletRequest request, String documentId, Long revisionNumber) throws CKCConnectorException;
	
	/**
	 * Loads latest revision of the document.
	 * 
	 * @param request HttpServletRequest object
	 * @param documentId id of the document
	 * @return LoadResult
	 * @throws CKCConnectorException
	 */
	public LoadResult load(HttpServletRequest request, String documentId) throws CKCConnectorException;
	
	/**
	 * Saves changes to the document.
	 * 
	 * @param request HttpServletRequest object
	 * @param documentId id of the document
	 * @param patch content updates patch
	 * @param properties property updates
	 * @return SaveResult
	 * @throws CKCConnectorException
	 */
	public SaveResult save(HttpServletRequest request, String documentId, String patch, String properties) throws CKCConnectorException;
	
}
