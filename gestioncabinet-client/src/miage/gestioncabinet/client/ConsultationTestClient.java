package miage.gestioncabinet.client;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import miage.gestioncabinet.api.Consultation;
import miage.gestioncabinet.api.ConsultationRemoteService;
import miage.gestioncabinet.api.Interaction;
import miage.gestioncabinet.api.Medecin;
import miage.gestioncabinet.api.PlanningRemoteService;
import miage.gestioncabinet.api.Produit;

import com.novarem.jndi.ServiceLocator;
import com.novarem.jndi.ServiceLocatorException;

public class ConsultationTestClient {
	
	private PlanningRemoteService ejb;
	
	private ConsultationRemoteService ejb2;
	
	public ConsultationTestClient() {
		String service = "ejb:GestionCabinet/gestioncabinet-coreM//PlanningService!miage.gestioncabinet.api.PlanningRemoteService?stateful";
		String service2 = "ejb:GestionCabinet/gestioncabinet-coreM//ConsultationService!miage.gestioncabinet.api.ConsultationRemoteService?stateful";
		
		try{
			ServiceLocator locator = ServiceLocator.INSTANCE;
			this.ejb = (PlanningRemoteService) locator.getRemoteInterface(service);
			this.ejb2 = (ConsultationRemoteService) locator.getRemoteInterface(service2);
		}
		catch(ServiceLocatorException e){
			System.out.println("Le service "+service+" est introuvable");
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		ConsultationTestClient app = new ConsultationTestClient();
		
		System.out.println("On développe un scénario de test du planning de consultation");
		try{
			Medecin medecin = app.ejb.getMedecin();
			System.out.println("Sélection du médecin courant : "+medecin);
	
			DateFormat df = DateFormat.getDateTimeInstance(DateFormat.MEDIUM, DateFormat.MEDIUM);
			System.out.println("Planning du jour : du "+df.format(app.ejb.getDateDebut().getTime())+" au "+df.format(app.ejb.getDateFin().getTime()));
			String[] noms = {"MARTIN", "DUPOND", "GIUDICELLI"};
			String[] prenoms = {"Jean", "Henri", "Jeannette"};
			String[] datesNaissance = {"12/03/1964", "23/02/1958", "20/07/1943"};
			for(int i=0 ; i<3 ; i++){
				Calendar date = (Calendar) app.ejb.getDateDebut().clone();
				date.set(Calendar.HOUR_OF_DAY, 9);
				date.add(Calendar.HOUR_OF_DAY, i);
				Consultation rdv = app.ejb.creerRdv(date);
				rdv.getPatient().setNom(noms[i]);
				rdv.getPatient().setPrenom(prenoms[i]);
				Calendar dateNaissance = Calendar.getInstance();
				dateNaissance.setTime(new SimpleDateFormat("dd/MM/yyyy").parse(datesNaissance[i]));
				rdv.getPatient().setDateNaissance(dateNaissance);
				app.ejb.setRdvCourant(rdv);
				app.ejb.enregistrerRdv();
				System.out.println("Enregistrement réussi du rdv de "+rdv+" (patient(e) de "+rdv.getPatient().getAge()+" ans).");
			}
			
			List<Consultation> rdvs = app.ejb.listerRdv();
			System.out.println("Rercherche des "+rdvs.size()+" rendez-vous pris pour la journée de "+medecin+" :");
			for(Consultation rdv : rdvs){
				System.out.println("- "+rdv);
			}
			
			Consultation rdv = rdvs.get(1);
			app.ejb.setRdvCourant(rdv);
			app.ejb.supprimerRdv();
			System.out.println("Annulation du rdv de "+rdv);
			
			rdvs = app.ejb.listerRdv();
			System.out.println("Il ne reste plus que "+rdvs.size()+" rendez-vous pour le "+medecin+" :");
			for(Consultation r : rdvs){
				System.out.println("- "+r);
			}
			
			Consultation test = rdvs.get(0);
			List<Produit> produits = app.ejb2.rechercherMedicament("asp");
			for(int i = 2;i<=5;i++){
				test.ajouterTraitement(produits.get(i));
				System.out.println("Le Produit "+ produits.get(i).getNom() + " a été ajouté");
			}
			produits = app.ejb2.rechercherMedicament("plavix");
			test.ajouterTraitement(produits.get(0));
			System.out.println("Le Produit "+ produits.get(0).getNom() + " a été ajouté");
			app.ejb2.setConsultation(test);
			app.ejb2.enregistrer();
			app.ejb2.analyserPrescription();
			for(Interaction inter : app.ejb2.getConsultation().getInteractions()){
				System.out.println("Interaction trouvée entre "+inter.getProduitA().getNom()+" et "+inter.getProduitB().getNom()+" ");
			}
			
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
}
