/**
 * 
 */
package miage.gestioncabinet.util;

import java.util.Properties;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.Local;
import javax.ejb.LocalBean;
import javax.ejb.SessionContext;
import javax.ejb.Singleton;

import miage.gestioncabinet.api.ApplicationLocalService;
import miage.gestioncabinet.api.GestionCabinetException;

import org.jboss.logging.Logger;


/**
 * This service manages main application settings
 * @author sraybaud - NovaRem
 *
 */
@Singleton
@Local(ApplicationLocalService.class)
@LocalBean
public class ApplicationService implements ApplicationLocalService {
	
	/**
	 * Relative path to teamium properties
	 */
	private static final String CONFIG_PATH = "/META-INF/gestioncabinet.properties";
	
	/**
	 * Teamium properties
	 */
	private Properties props;
	
	/**
	 * The session context
	 */
	@Resource
	private SessionContext context;

	/**
	 * Logger used by the service
	 */
	private Logger logger = Logger.getLogger("miage.gestioncabinet");
	
	/**
	 * Initialize application settings
	 */
	@PostConstruct
	private void init() 
	{
		try{
			this.logger.info(this+"Démarrage du programme... Chargement de "+CONFIG_PATH);
			props = new Properties();
			props.load(this.getClass().getResourceAsStream(CONFIG_PATH));
		}
		catch(Exception e){
			this.logger.error(this+"Une erreur inattendue s'est produite au moment du chargement de fichier de propriétés",e);
		}
	}
	
	/**
	 * Returns the application logger
	 * @return the logger
	 */
	public Logger getLogger(){
		return this.logger;
	}

	/**
	 * Returns the property matching the given name
	 * @param key the property key 
	 * @return the matching value
	 * @throws GestionCabinetException
	 */
	public String getProperty(String key) throws GestionCabinetException{
		String value=null;
		try{
			value = this.props.getProperty(key);
			if(value==null) throw new NullPointerException("No property exists matching the key "+key);
			this.logger.debug(this+"Retrieving property "+key+"="+value);
		}
		catch(Exception e){
			String message="La propriété '"+key+"' est inconnue dans le fichier "+CONFIG_PATH;
			this.logger.error(this+message);
			throw new GestionCabinetException(message,e);
		}
		return value;
	}
	
	/**
	 * Returns the property matching the given name in an integer format
	 * @param key the property key 
	 * @return the matching value
	 * @throws GestionCabinetException
	 */
	public Integer getIntegerProperty(String key) throws GestionCabinetException{
		Integer value=null;
		try{
			value = Integer.parseInt(this.getProperty(key));
		}
		catch(Exception e){
			String message="La propriété '"+key+"' ne peut pas être récupérée comme un entier";
			this.logger.error(this+message);
			throw new GestionCabinetException(message,e);
		}
		return value;
	}
	
	/**
	 * Return the string expression of the current object
	 * @return the resulting text
	 */
	@Override
	public String toString()
	{
		return "["+this.getClass().getSimpleName()+"]";
	}

}
