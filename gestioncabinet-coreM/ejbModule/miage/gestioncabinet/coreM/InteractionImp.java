package miage.gestioncabinet.coreM;

import miage.gestioncabinet.api.Interaction;
import miage.gestioncabinet.api.Produit;

/**
 * Entité non persistante décrivant une interaction médicamenteuse
 * @author sraybaud
 *
 */
public class InteractionImp implements Interaction{
	
	/**
	 * serialVersionUID de la classe
	 */
	private static final long serialVersionUID = 1112208032256186057L;
	
	/**
	 * Sévérité de l'interaction
	 */
	private String severite;
	
	/**
	 * Risques associés
	 */
	private String risques;
	
	/**
	 * Précuations à prendre
	 */
	private String precautions;
	
	/**
	 * Produit A incriminé
	 */
	private Produit produitA;
	
	/**
	 * Produit B incriminé
	 */
	private Produit produitB;

	/**
	 * Retourne le premier produit incriminé
	 * @return le produit correspondant
	 */
	@Override
	public Produit getProduitA() {
		return produitA;
	}

	/**
	 * Modifie le premier produit incriminé
	 * @param produit le produit à modifier
	 */
	@Override
	public void setProduitA(Produit produit) {
		this.produitA = produit;
	}

	/**
	 * Retourne le second produit incriminé
	 * @return le produit correspondant
	 */
	@Override
	public Produit getProduitB() {
		return produitB;
	}

	/**
	 * Modifie le second produit incriminé
	 * @param produit le produit à modifier
	 */
	@Override
	public void setProduitB(Produit produit) {
		this.produitB = produit;
	}

	/**
	 * Retourne la sévérité de l'interaction
	 * @return la sévérité correspondante
	 */
	@Override
	public String getSeverite() {
		return severite;
	}

	/**
	 * Modifie la sévérité de l'interaction
	 * @param severite la sévérité à modifier
	 */
	@Override
	public void setSeverite(String severite) {
		this.severite = severite;
	}

	/**
	 * Retourne les risques liés de l'interaction
	 * @return les risques correspondants
	 */
	@Override
	public String getRisques() {
		return risques;
	}

	/**
	 * Modifie les risques liés à l'interaction
	 * @param risques les risques à modifier
	 */
	@Override
	public void setRisques(String risques) {
		this.risques = risques;
	}

	/**
	 * Retourne les précautions à prendre vis à vis de l'interaction
	 * @return les précautions correspondantes
	 */
	@Override
	public String getPrecautions() {
		return precautions;
	}

	/**
	 * Modifie les précautions à prendre vis à vis de l'interaction
	 * @return precautions les précautions à modifier
	 */
	@Override
	public void setPrecautions(String precautions) {
		this.precautions = precautions;
	}	
	
	
	
	/**
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((produitA == null) ? 0 : produitA.hashCode());
		result = prime * result + ((produitB == null) ? 0 : produitB.hashCode());
		return result;
	}

	/**
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof InteractionImp))
			return false;
		InteractionImp other = (InteractionImp) obj;
		if (produitA == null) {
			if (other.produitA != null)
				return false;
		} else if (!produitA.equals(other.produitA))
			return false;
		if (produitB == null) {
			if (other.produitB != null)
				return false;
		} else if (!produitB.equals(other.produitB))
			return false;
		return true;
	}

	/**
	 * Retourne l'expression textuelle d'une interaction
	 * 
	 */
	@Override
	public String toString(){
		String texte = this.getClass().getSimpleName()+"["+this.severite+"]"+produitA.getNom()+"<=>"+produitB.getNom();
		texte+=" : risque="+this.risques;
		return texte;
	}

}
