package miage.gestioncabinet.coreM;

import miage.gestioncabinet.api.Utilisateur;

/**
 * Classe qui décrit un utilisateur
 * @author sraybaud
 *
 */
public class UtilisateurImp extends PersonneImp implements Utilisateur{

	/**
	 * serialVersionUID de la classe
	 */
	private static final long serialVersionUID = -3174132545608484831L;
	
	/**
	 * Identifiant de connexion
	 */
	private String compte;
	
	/**
	 * Contructeur d'un utilisateur
	 */
	public UtilisateurImp(){
		super();
	}

	/**
	 * Retourne le nom de compte de l'utilisateur
	 * @return son nom de compte
	 */
	@Override
	public String getCompte() {
		return compte;
	}
	
	/**
	 * 
	 * @param compte
	 * @return
	 */
	public void setCompte(String compte){
		this.compte=compte;
	}

}
