/**
 * 
 */
package miage.gestioncabinet.coreDB;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.Remote;
import javax.ejb.Stateful;
import javax.inject.Inject;

import miage.gestioncabinet.api.ApplicationLocalService;
import miage.gestioncabinet.api.Consultation;
import miage.gestioncabinet.api.GestionCabinetException;
import miage.gestioncabinet.api.Medecin;
import miage.gestioncabinet.api.Patient;
import miage.gestioncabinet.api.PlanningRemoteService;
import miage.gestioncabinet.api.Utilisateur;

/**
 * @author nicolascapiaumont
 */
@Stateful
@Remote(PlanningRemoteService.class)
public class PlanningDBService implements PlanningRemoteService {
	@Inject
	private ApplicationLocalService conf;
	
	private Utilisateur utilisateur;
	
	private Calendar dateDebut;
	
	private Calendar dateFin;

	private Medecin medecin;
		
	private Consultation rdv;
	
	@PostConstruct
	public void init() {
		this.medecin = new MedecinDB();
		this.medecin.setNom("Choukri");
		this.medecin.setPrenom("Ibtissam");
		
		this.utilisateur = new UtilisateurDB();
		//nom fictif de l'utisateur, récupère l'adresse mémoire de l'utilisateur
		this.utilisateur.setNom("@" + Integer.toHexString(hashCode()));
		this.dateDebut = Calendar.getInstance();
		this.dateFin = Calendar.getInstance();
		this.dateFin.add(Calendar.DAY_OF_WEEK, 1);
	}
	
	/**
	 * Retourne l'utilisateur authentifié
	 */
	@Override
	public Utilisateur getUtilisateur() {
		return this.utilisateur;
	}

	/**
	 * Retourne la liste des médecins du cabinet
	 * @return la liste des médecins
	 */
	@Override
	public List<Medecin> rechercherMedecins() throws GestionCabinetException {
		return null;
	}

	/**
	 * Recherche un patient par un mot clé
	 * @param keyword le mot clé
	 * @return la liste des patients correspondants
	 */
	@Override
	public List<Patient> rechercherPatients(
		String nom, 
		String prenom,
		Calendar dateNaissance
	) throws GestionCabinetException {
		return null;
	}

	/**
	 * Retourne la date de début de période des rendez-vous du médecin courant
	 * @return la date correspondante
	 */
	@Override
	public Calendar getDateDebut() {
		return this.dateDebut;
	}
	
	/**
	 * Modifie la date de début de période des rendez-vous du médecin courant
	 * @param date la date à modifier
	 */
	@Override
	public void setDateDebut(Calendar date) {
		this.dateDebut = date;
	}

	/**
	 * Retourne la date de fin de période des rendez-vous du médecin courant
	 * @return la date correspondante
	 */
	@Override
	public Calendar getDateFin() {
		return this.dateFin;
	}

	/**
	 * Modifie la date de fin de période des rendez-vous du médecin courant
	 * @param date la date à modifier
	 */
	@Override
	public void setDateFin(Calendar date) {
		this.dateFin = date;
	}

	/**
	 * Retourne le médecin courant
	 * @return le médecin correspondant
	 */
	@Override
	public Medecin getMedecin() {
		return this.medecin;
	}

	/**
	 * Modifie le médecin courant
	 * @param medecin le médecin à modifier
	 */
	@Override
	public void setMedecin(Medecin medecin) {
		this.medecin = medecin;
	}

	/**
	 * Liste les rendez-vous du médecin courant sur la période correspondante
	 * @return la liste des consultations correspondantes
	 */
	@Override
	public List<Consultation> listerRdv() {
		List<Consultation> listConsultation = null;
		return listConsultation;
	}

	/**
	 * Retourne le rendez-vous de consultation courant
	 * @return le rendez-vous correspondant
	 */
	@Override
	public Consultation getRdvCourant() {
		return this.rdv;
	}
	
	/**
	 * Modifie le rendez-vous de consultation courante
	 * @param rdv le rendez-vous à sélectionner
	 */
	@Override
	public void setRdvCourant(Consultation rdv) {
		this.rdv = rdv;
	}

	/**
	 * Crée un nouveau rendez-vous de consultation
	 * @param date la date du rendez-vous
	 * @return la consultation créée
	 */
	@Override
	public Consultation creerRdv(Calendar date) {
		Consultation rdv = new ConsultationDB();
		Calendar dateFin = (Calendar) date.clone();
		try {
			dateFin.add(Calendar.MINUTE, Integer.parseInt(conf.getProperty("planning.rdv.duree")));
		} catch (NumberFormatException | GestionCabinetException e) {
			e.printStackTrace();
		}
		
		rdv.setDebut(date);
		rdv.setFin(dateFin);
		return rdv;
	}

	/**
	 * Sauvegarde le rendez-vous de consultation courant
	 * @return le rendez-vous après avoir été sauvegardé
	 */
	@Override
	public Consultation enregistrerRdv() throws GestionCabinetException {
		if (this.rdv == null) {
			throw new GestionCabinetException("Aucun rendez vous courant");
		}
		//this.dataMService.saveConsultation(rdv);
		return this.rdv;
	}

	/**
	 * Supprime le rendez-vous de consultation courant
	 */
	@Override
	public void supprimerRdv() throws GestionCabinetException {
		//TODO throw l'exception si le rdv n'existe pas dans la liste
		//this.dataMService.removeConsultation(rdv);
		this.rdv = null;
	}

}
