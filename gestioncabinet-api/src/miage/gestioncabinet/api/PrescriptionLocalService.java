package miage.gestioncabinet.api;

import java.util.List;

import miage.gestioncabinet.api.GestionCabinetException;
import miage.gestioncabinet.api.Interaction;
import miage.gestioncabinet.api.Produit;

/**
 * EJB en charge de la prescription de médicaments
 * @author sraybaud
 */
public interface PrescriptionLocalService {
	
	/**
	 * Initialise la classe d'implantation des produits
	 */
	public void setClasseProduit(Class<? extends Produit> classe);
	
	/**
	 * Initialise la classe d'implantation des produits
	 */
	public void setClasseInteraction(Class<? extends Interaction> classe);

	/**
	 * Retourne la liste des produits correspondant au mot-clé fourni en argument
	 * @param keyword le mot-clé de recherche
	 * @return la liste des produits correspondants
	 * @throws FmDevException
	 */
	public List<Produit> findProductsByKeyword(String keyword) throws GestionCabinetException;

	/**
	 * Réalise une analyse d'interactions médicamenteuses pour la prescription fournie en argument
	 * @param prescription la prescription analysée
	 * @return la consultation une fois l'analyse d'interaction réalisée
	 * @throws FmDevException 
	 */
	public List<Interaction> analyseInteractions(List<Produit> produits) throws GestionCabinetException;




}
