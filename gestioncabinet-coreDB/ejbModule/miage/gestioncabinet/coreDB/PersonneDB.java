package miage.gestioncabinet.coreDB;

import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import miage.gestioncabinet.api.Personne;

/**
 * Classe qui décrit une personne
 * @author marjolaine
 *
 */
@Entity
@Table(name="t_personne")
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="c_type")
@SequenceGenerator(name="sequence_personne",sequenceName="personne_id_seq", allocationSize=1)
public abstract class PersonneDB implements Personne{
	/**
	 * serialVersionUID de la classe
	 */
	private static final long serialVersionUID = -4012627669499150421L;
	
	/**
	 * Id de la personne
	 */
	@Id
	@Column(name="c_idpersonne")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="sequence_personne")
	private Long id;
	
	/**
	 * Nom de la personne
	 */
	@Column(name="c_nom")
	private String nom;
	
	/**
	 * Prénom de la personne
	 */
	@Column(name="c_prenom")
	private String prenom;
	
	/**
	 * Contructeur d'une personne
	 */
	public PersonneDB(){
		this.id = Calendar.getInstance().getTimeInMillis();
	}
	
	/**
	 * Retourne l'id de la personne
	 */
	@Override
	public Long getId() {
		return id;
	}
	
	/**
	 * Retourne le nom de la personne
	 * @return son nom
	 */
	@Override
	public String getNom() {
		return nom;
	}
	
	/**
	 * Modifie le nom de la personne
	 * @param nom le nom à modifier
	 */
	@Override
	public void setNom(String nom) {
		this.nom = nom;
	}
	
	/**
	 * Retourne le prénom de la personne
	 * @return son prénom
	 */
	@Override
	public String getPrenom() {
		return prenom;
	}
	
	/**
	 * Modifie le prénom de la personne
	 * @param prenom le prénom à modifier
	 */
	@Override
	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}
	
	/**
	 * 
	 * @param obj
	 * @return
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PersonneDB other = (PersonneDB) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	/**
	 * 
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	/**
	 * 
	 * @return
	 */
	@Override
	public String toString() {
		return this.getClass().getSimpleName()+"#"+this.id+" "+nom + " " + prenom;
	}
}
