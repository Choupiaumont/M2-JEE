package miage.gestioncabinet.coreM;

import miage.gestioncabinet.api.Produit;

/**
 * Entité non persistante décrivant un produit
 * @author sraybaud
 *
 */
public class ProduitImp implements Produit{

	/**
	 * serialVersionUID de la classe
	 */
	private static final long serialVersionUID = -2329573594222160001L;

	/**
	 * Nom du produit
	 */
	private String nom;
	
	/**
	 * Numéro CIS du produit
	 */
	private String cis;
	
	/**
	 * Retourne le numéro CIS du produit
	 * @return le code correspondant
	 */
	@Override
	public String getCis() {
		return cis;
	}

	/**
	 * Modifie le code CIS du produit
	 * @param cis le code à modifier
	 */
	@Override
	public void setCis(String cis) {
		this.cis = cis;
	}

	/**
	 * Retourne le nom du produit prescrit
	 * @return le nom correspondant
	 */
	@Override
	public String getNom() {
		return nom;
	}

	/**
	 * Modifie le nom du produit prescrit
	 * @param nom le nom à modifier
	 */
	@Override
	public void setNom(String nom) {
		this.nom = nom;
	}

	/** (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((cis == null) ? 0 : cis.hashCode());
		return result;
	}

	/** (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof ProduitImp))
			return false;
		ProduitImp other = (ProduitImp) obj;
		if (cis == null) {
			if (other.cis != null)
				return false;
		} else if (!cis.equals(other.cis))
			return false;
		return true;
	}
	
	/**
	 * 
	 */
	@Override
	public String toString(){
		return this.getClass().getSimpleName()+"#"+this.cis+" "+this.nom;
	}
	
	

}
