/**
 * 
 */
package miage.gestioncabinet.api;

import miage.gestioncabinet.api.GestionCabinetException;

import org.jboss.logging.Logger;


/**
 * This service manages main application settings
 * @author sraybaud - NovaRem
 *
 */
public interface ApplicationLocalService {

	
	/**
	 * Returns the application logger
	 * @return the logger
	 */
	public Logger getLogger();

	/**
	 * Returns the property matching the given name
	 * @param key the property key 
	 * @return the matching value
	 * @throws GestionCabinetException
	 */
	public String getProperty(String key) throws GestionCabinetException;
	
	/**
	 * Returns the property matching the given name in an integer format
	 * @param key the property key 
	 * @return the matching value
	 * @throws GestionCabinetException
	 */
	public Integer getIntegerProperty(String key) throws GestionCabinetException;

}
