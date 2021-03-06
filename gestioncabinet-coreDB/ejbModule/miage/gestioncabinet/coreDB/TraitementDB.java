package miage.gestioncabinet.coreDB;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import miage.gestioncabinet.api.Consultation;
import miage.gestioncabinet.api.Produit;
import miage.gestioncabinet.api.Traitement;


/**
 * Décrit un traitement prescrit et stocké en mémoire
 * @author sraybaud
 *
 */
@Entity
@Table(name="t_traitement")
@SequenceGenerator(name="sequence_traitement",sequenceName="traitement_id_seq", allocationSize=1)
public class TraitementDB implements Traitement{

	/**
	 * serialVersionUID de la classe
	 */
	private static final long serialVersionUID = -6675736174210846249L;

	@Id
	@Column(name="c_idtraitement")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="sequence_traitement")
	private Long id;
	/**
	 * Posologie du traitement
	 */
	@Column(name="c_posologie")
	private String posologie;
	
	/**
	 * Produit prescrit
	 */
	@Embedded
	@AttributeOverrides({
		@AttributeOverride(name="nom", column=@Column(name="c_produit_nom")),
		@AttributeOverride(name="cis", column=@Column(name="c_produit_cis"))
	})
	private ProduitDB produit;
	
	@ManyToOne(targetEntity=ConsultationDB.class, fetch=FetchType.EAGER, cascade=CascadeType.ALL)
	@JoinColumn(name="c_fidconsultation")
	private Consultation consultation;

	/**
	 * Retourne la posologie
	 * @return la posologie
	 */
	@Override
	public String getPosologie() {
		return posologie;
	}

	/**
	 * Modifie la posologie de la prescription
	 * @param posologie la posologie à modifier
	 */
	@Override
	public void setPosologie(String posologie) {
		this.posologie = posologie;
	}


	
	/**
	 * Retourne le produit prescrit
	 * @return la spécialité médicamenteuse
	 */
	@Override
	public Produit getProduit() {
		return produit;
	}

	/**
	 * Modifie le produit prescrire
	 * @param produit le produit à modifier
	 */
	@Override
	public void setProduit(Produit produit) {
		this.produit = (ProduitDB) produit;
	}
	
	/**
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((produit == null) ? 0 : produit.hashCode());
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
		if (!(obj instanceof TraitementDB))
			return false;
		TraitementDB other = (TraitementDB) obj;
		if (produit == null) {
			if (other.produit != null)
				return false;
		} else if (!produit.equals(other.produit))
			return false;
		return true;
	}

	/**
	 * Représentation textuel d'un traitement
	 * @return le texte correspondant
	 */
	@Override
	public String toString(){
		return this.getClass().getSimpleName()+" "+this.produit;
	}
}
