package fi.foyt.ckc;

import java.lang.reflect.Type;
import java.util.Set;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;

import org.apache.commons.lang3.StringUtils;

public class CKCConnectorServletEE extends CKCConnectorServlet {
	
  private static final long serialVersionUID = 1L;
  
  @SuppressWarnings("unchecked")
  @Override
  public void init(ServletConfig config) throws ServletException {
    String connectorClassType = config.getInitParameter("connector-class-type");
    if ("managedBean".equals(connectorClassType)) {
      String connectorClassName = config.getInitParameter("connector-class");
      if (StringUtils.isBlank(connectorClassName))
      	throw new ServletException("Mandatory init paramter 'connector-class' is missing");
  
      Class<CKCConnector> connectorClass;
      try {
  	    connectorClass = (Class<CKCConnector>) Class.forName(connectorClassName);
      } catch (ClassNotFoundException e1) {
      	throw new ServletException("Could not find class specified in 'connector-class' init parameter" );
      }
      
      setConnector((CKCConnector) getManagedBean(connectorClass));
    } else {
    	super.init(config);
    }
  }
  
  private Object getManagedBean(Type beanType) {
  	javax.enterprise.inject.spi.BeanManager beanManager = null;
  	try {
  		beanManager = (javax.enterprise.inject.spi.BeanManager) new javax.naming.InitialContext().lookup("java:comp/BeanManager");
    } catch (javax.naming.NamingException e) {
    }	
		Set<javax.enterprise.inject.spi.Bean<?>> beans = beanManager.getBeans(beanType);
		javax.enterprise.inject.spi.Bean<?> bean = beans.iterator().next();
		javax.enterprise.context.spi.CreationalContext<?> creationalContext = beanManager.createCreationalContext(bean);
		return beanManager.getReference(bean, beanType, creationalContext);
	}

}