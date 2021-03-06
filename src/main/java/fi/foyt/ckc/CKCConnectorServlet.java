package fi.foyt.ckc;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.json.JSONException;

public class CKCConnectorServlet extends HttpServlet {
	
  private static final long serialVersionUID = 1L;
  
  public CKCConnectorServlet() {
	  logger.info("CKC Connector servlet created");
  }
  
  @SuppressWarnings("unchecked")
  @Override
  public void init(ServletConfig config) throws ServletException {
    super.init(config);
    
	  logger.info("CKC Connector servlet init");
    
    String connectorClassName = config.getInitParameter("connector-class");
    if (StringUtils.isBlank(connectorClassName))
    	throw new ServletException("Mandatory init paramter 'connector-class' is missing");
    
	  logger.info("CKC Connector servlet connector class: " + connectorClassName);

    Class<CKCConnector> connectorClass;
    try {
	    connectorClass = (Class<CKCConnector>) Class.forName(connectorClassName);
    } catch (ClassNotFoundException e1) {
    	throw new ServletException("Could not find class specified in 'connector-class' init parameter" );
    }
    
  	try {
  		setConnector(connectorClass.newInstance());
    } catch (InstantiationException e) {
    	throw new ServletException("Error occured while initializing connector", e);
    } catch (IllegalAccessException e) {
    	throw new ServletException("Error occured while initializing connector", e);
    }	
  }
  
  protected void setConnector(CKCConnector connector) {
  	this.connector = connector;
  }
  
  private Logger logger = Logger.getLogger(CKCConnectorServlet.class.getName());
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Action action = Action.valueOf(request.getParameter("action"));
		String documentId = request.getParameter("documentId");
		Result result = null;

		try {
			boolean valid = (action == Action.INIT)||(action == Action.CREATE) ? true : connector.validateToken(request, getToken(request));
			
			if (valid) {
    		switch (action) {
    			case INIT:
    				result = connector.init(request, documentId);
    			break;
    			case LOAD:
    				result = connector.load(request, documentId);
    			break;
    			case CREATE:
    				result = connector.create(request, request.getParameter("content"), request.getParameter("properties"));
    			break;
    			case UPDATE:
    				result = connector.update(request, documentId, NumberUtils.createLong(request.getParameter("revisionNumber")));
    			break;
    			case SAVE:
    				result = connector.save(request, documentId, request.getParameter("patch"), request.getParameter("properties"));
    			break;
    		}
    		
    		if (result != null) {
    			if (result.getStatus() == Status.FORBIDDEN) {
    				logger.log(Level.INFO, "Forbidden");
          	response.sendError(HttpServletResponse.SC_FORBIDDEN);
    			} else {
      			try {
      				ServletOutputStream outputStream = response.getOutputStream();
      		    outputStream.write(result.toJson().toString(1).getBytes("UTF-8"));
      		    outputStream.flush();
      		    outputStream.close();
            } catch (JSONException e) {
            	logger.log(Level.SEVERE, e.getMessage(), e);
            	response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            }
    			}
    		} else {
        	logger.log(Level.SEVERE, "result is null");
      		response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
    		}
			} else {
      	logger.log(Level.INFO, "Forbidden");
      	response.sendError(HttpServletResponse.SC_FORBIDDEN);
			}
		} catch (Exception e) {
    	logger.log(Level.SEVERE, e.getMessage(), e);
    	response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		}
	}
	
	private String getToken(HttpServletRequest request) {
		String value = request.getHeader("Authorization");
		if (value.startsWith("token ")) {
			return value.substring(6);
		}
		
		return null;
	}

	private CKCConnector connector;
}