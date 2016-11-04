package miage.gestioncabinet.coreDB;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import miage.gestioncabinet.api.Consultation;
import miage.gestioncabinet.api.Interaction;
import miage.gestioncabinet.api.Medecin;
import miage.gestioncabinet.api.Patient;
import miage.gestioncabinet.api.Produit;
import miage.gestioncabinet.api.Traitement;

/**
 * Entité non persistante décrivant une consultation
 * @author sraybaud
 *
 */
@Entity
@Table(name="t_consultation")
@SequenceGenerator(name="sequence_consutation",sequenceName="consultation_id_seq", allocationSize=1)
public class ConsultationDB implements Consultation{

	/**
	 * serialVersionUID de la classe
	 */
	private static final long serialVersionUID = 1591820003118188327L;
	
	/**
	 * Identifiant de la conbsultation
	 */
	@Id
	@Column(name="c_idconsultation")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="sequence_consutation")
	private Long id;
	
	/**
	 * Heure de début de consultation
	 */
	@Column(name="c_datedebut")
	@Temporal(TemporalType.TIMESTAMP)
	private Calendar heureDebut;
	
	/**
	 * Heure de fin de consultation
	 */
	@Column(name="c_datefin")
	@Temporal(TemporalType.TIMESTAMP)
	private Calendar heureFin;
	
	/**
	 * Compte-rendu de consultation
	 */
	@Column(name="c_compterendu")
	private String compteRendu;
	
	/**
	 * Patient consultant
	 */
	@ManyToOne(targetEntity=PatientDB.class, fetch=FetchType.EAGER, cascade=CascadeType.ALL)
	@JoinColumn(name="c_fidpatient")
	private Patient patient;
	
	/**
	 * Médecin réalisant la consultation
	 */
	@ManyToOne(targetEntity=MedecinDB.class, fetch=FetchType.EAGER, cascade=CascadeType.ALL)
	@JoinColumn(name="c_fidmedecin")
	private Medecin medecin;
	
	/**
	 * Contructeur de consultation
	 */
	public ConsultationDB(){
		this.id = Calendar.getInstance().getTimeInMillis();		
	}
	
	/**
	 * Retourne l'identifiant de la consultation
	 */
	public Long getId(){
		return this.id;
	}

	/**
	 * Retourne le patient qui consulte
	 * @return le patient correspondant
	 */
	@Override
	public Patient getPatient() {
		if(this.patient==null) this.patient = new PatientDB();
		return patient;
	}

	/**
	 * Modifie le patient qui consulte
	 * @param patient le patient à modifier
	 */
	@Override
	public void setPatient(Patient patient) {
		this.patient = patient;
	}

	/**
	 * Retourne le médecin consultant
	 * @return le médecin correspondant
	 */
	@Override
	public Medecin getMedecin() {
		return medecin;
	}
	
	/**
	 * Modifie le médecin consultant
	 * @param medecin le médecin à modifier
	 */
	@Override
	public void setMedecin(Medecin medecin) {
		this.medecin = medecin;
	}
	
	/**
	 * Retourne la date de début de la consultation
	 * @return la date correspondante
	 */
	@Override
	public Calendar getDebut() {
		if(heureDebut == null)
			heureDebut = Calendar.getInstance();
		return heureDebut;
	}
	
	/**
	 * Modifie la date de rendez-vous de la consultation
	 * @param date la date à modifier
	 */
	@Override
	public void setDebut(Calendar date) {
		heureDebut = date;
	}
	
	/**
	 * Retourne la date de fin de la consultation
	 * @return la date correspondante
	 */
	@Override
	public Calendar getFin() {
		if(heureFin == null)
			heureFin = Calendar.getInstance();
		return heureFin;
	}
	
	/**
	 * Modifie la date de fin de la consultation
	 * @param date la date à modifier
	 */
	@Override
	public void setFin(Calendar date) {
		heureFin = date;
	}
	
	/**
	 * Retourne le compte-rendu de la consultation
	 * @return le compte rendu correspondant
	 */
	@Override
	public String getCompteRendu() {
		return compteRendu;
	}
	
	/**
	 * Modifie le compte-rendu de la consultation
	 * @param texte le compte rendu correspondant
	 */
	@Override
	public void setCompteRendu(String texte) {
		this.compteRendu = texte;
	}

	/**
	 * Compare la consultation courante à la consultation passée en argument
	 * @param o l'autre consultation à comparer
	 * @return -1 si la consultation courante est antérieure, 0 si elle est concomitante ou 1 si elle est postérieure à la consultation passée en argument
	 */
	@Override
	public int compareTo(Consultation o) {
		if(this.heureDebut==null){
			if(o.getDebut()!=null) return -1;
		}else{
			if(o.getDebut()==null) return 1;
			else{
				int i = this.heureDebut.compareTo(o.getDebut());
				if(i!=0) return i;
			}
		}
		if(this.heureFin==null){
			if(o.getFin()!=null) return -1;
		}else{
			if(o.getFin()==null) return 1;
			else{
				int i = this.heureFin.compareTo(o.getFin());
				if(i!=0) return i;
			}
		}
		return 0;
	}
	
	/**
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
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
		if (!(obj instanceof ConsultationDB))
			return false;
		ConsultationDB other = (ConsultationDB) obj;
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
	public String toString() {
		String text = this.getClass().getSimpleName()+"#"+this.id;
		if(this.heureDebut!=null){
			text+=" à ";
			SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy HH:mm");
			text+=format.format(heureDebut.getTime());
		}
		if(this.patient!=null){
			text+=" de "+this.patient;
		}
		if(this.medecin!=null){
			text+=" par "+this.medecin;
		}
		return text;
	}

	@Override
	public List<Traitement> getPrescription() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Boolean ajouterTraitement(Produit produit) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Boolean supprimerTraitement(Traitement medicament) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Interaction> getInteractions() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setInteractions(List<Interaction> interactions) {
		// TODO Auto-generated method stub
		
	}
}
