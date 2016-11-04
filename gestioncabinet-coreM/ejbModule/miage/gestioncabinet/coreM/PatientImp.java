package miage.gestioncabinet.coreM;

import java.util.Calendar;

import miage.gestioncabinet.api.Patient;

/**
 * Classe qui décrit un patient
 * @author sraybaud
 *
 */
public class PatientImp extends PersonneImp implements Patient{

	/**
	 * serialVersionUID de la classe
	 */
	private static final long serialVersionUID = -5636440052509911308L;
	
	/**
	 * Sexe du patient
	 */
	private String sexe;
	
	/**
	 * Date de naissance du patient
	 */
	private Calendar dateNaissance;
	
	/**
	 * Contructeur du patient
	 */
	public PatientImp(){
		super();
	}
	
	/**
	 * @return the sexe
	 */
	public String getSexe() {
		return sexe;
	}

	/**
	 * @param sexe the sexe to set
	 */
	public void setSexe(String sexe) {
		this.sexe = sexe;
	}

	/**
	 * Retourne la date de naissance du patient
	 * @return la date correspondante
	 */
	@Override
	public Calendar getDateNaissance() {
		return dateNaissance;
	}

	/**
	 * Modifie la date de naissance du patient
	 * @param date la date de naissance à modifier
	 */
	@Override
	public void setDateNaissance(Calendar dateNaissance) {
		this.dateNaissance = (Calendar) dateNaissance.clone();
	}

	/**
	 * Retourne l'âge du patient
	 * @return l'âge correspondant
	 */
	@Override
	public Integer getAge() {
		Calendar now =Calendar.getInstance();
		int age = now.get(Calendar.YEAR) - this.dateNaissance.get(Calendar.YEAR);
		int difMois = now.get(Calendar.MONTH) - this.dateNaissance.get(Calendar.MONTH);
		int difJour = now.get(Calendar.DAY_OF_MONTH) - this.dateNaissance.get(Calendar.DAY_OF_MONTH);
		age -= difMois < 0 ? 1 : difJour < 0 ? 1 : 0;
		return age;
	}

}
