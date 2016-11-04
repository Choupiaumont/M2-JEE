package miage.gestioncabinet.util;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Singleton;

import miage.gestioncabinet.api.GestionCabinetException;
import miage.gestioncabinet.api.Interaction;
import miage.gestioncabinet.api.PrescriptionLocalService;
import miage.gestioncabinet.api.Produit;
import fr.vidal.webservices.interactionservice.ArrayOfInt;
import fr.vidal.webservices.interactionservice.InteractionCouple;
import fr.vidal.webservices.interactionservice.InteractionResult;
import fr.vidal.webservices.interactionservice.InteractionService;
import fr.vidal.webservices.interactionservice.InteractionService_Service;
import fr.vidal.webservices.interactionservice.InteractionSeverityType;
import fr.vidal.webservices.productservice.Product;
import fr.vidal.webservices.productservice.ProductService;
import fr.vidal.webservices.productservice.ProductService_Service;
import fr.vidal.webservices.productservice.ProductType;

/**
 * EJB en charge de la prescription de médicaments
 * @author sraybaud
 *
 */
@Singleton
@Local(PrescriptionLocalService.class)
public class PrescriptionService implements PrescriptionLocalService{
	/**
	 * EJB applicatif
	 */
	@EJB
	private ApplicationService appli;

	/**
	 * ProductService
	 */
	private ProductService productService;

	/**
	 * InteractionService
	 */
	private InteractionService interactionService;
	
	/**
	 * Classe d'instanciation d'un produit
	 */
	private Class<? extends Produit> classeProduit;
	
	/**
	 * Classe d'instanciation d'une interaction
	 */
	private Class<? extends Interaction> classeInteraction;

	/**
	 * Initialise la connexion au webservice
	 */
	@PostConstruct
	private void initialiser(){				
		//On instancie les services
		try{
			productService = new ProductService_Service().getProductServiceHttpPort();
			interactionService = new InteractionService_Service().getInteractionServiceHttpPort();
			this.appli.getLogger().info(this+"Initialisation réussie des connexions aux web services Vidal");
		}catch(Exception e){
			this.appli.getLogger().error(this+"Impossible d'initialiser les connexions aux web services Vidal",e);
		}
	}
	
	/**
	 * Initialise la classe d'implantation des produits
	 */
	public void setClasseProduit(Class<? extends Produit> classe){
		this.classeProduit=classe;
	}
	
	/**
	 * Initialise la classe d'implantation des produits
	 */
	public void setClasseInteraction(Class<? extends Interaction> classe){
		this.classeInteraction=classe;
	}

	/**
	 * Retourne la liste des produits correspondant au mot-clé fourni en argument
	 * @param keyword le mot-clé de recherche
	 * @return la liste des produits correspondants
	 * @throws FmDevException
	 */
	public List<Produit> findProductsByKeyword(String keyword) throws GestionCabinetException{
		List<Produit> produits = null;
		try{
			List<Product> products = this.productService.searchByNameAndType(keyword, ProductType.VIDAL).getProduct();
			produits = new ArrayList<Produit>(products.size());
			for(Product product : products){
				Produit produit = classeProduit.newInstance();
				produit.setCis(product.getCis());
				produit.setNom(product.getName());
				produits.add(produit);
			}
			this.appli.getLogger().debug("Recherche des produits correspondant au mot-clé "+keyword+" : "+produits.size()+" produit(s) trouvé(s)");

		}
		catch(Exception e){
			String message = "Impossible de retrouver la liste des produits correspondant au mot-clé "+keyword;
			this.appli.getLogger().error(this+message,e);
			throw new GestionCabinetException(message, e);
		}
		return produits;
	}

	/**
	 * Réalise une analyse d'interactions médicamenteuses pour la prescription fournie en argument
	 * @param prescription la prescription analysée
	 * @return la consultation une fois l'analyse d'interaction réalisée
	 * @throws FmDevException 
	 */
	public List<Interaction> analyseInteractions(List<Produit> produits) throws GestionCabinetException{
		List<Interaction> interactions = new ArrayList<Interaction>();
		try{
			if(produits==null) produits = new ArrayList<Produit>();
			ArrayOfInt ids = new ArrayOfInt();
			for(Produit produit : produits){
				Product product = this.productService.searchByCis(produit.getCis());
				ids.getInt().add(product.getId());
			}


			//On procède à l'analyse d'interactions
			InteractionResult ir = interactionService.searchInteractionCouplesForProductIds(ids, InteractionSeverityType.TAKE_INTO_ACCOUNT);
			for(InteractionCouple ic : ir.getInteractionCoupleList().getInteractionCouple()){
				Interaction interaction = this.classeInteraction.newInstance();
				Produit p1 = this.classeProduit.newInstance();
				p1.setCis(ic.getProductA().getCis());
				p1.setNom(ic.getProductA().getName());
				interaction.setProduitA(p1);
				Produit p2 = this.classeProduit.newInstance();
				p2.setCis(ic.getProductB().getCis());
				p2.setNom(ic.getProductB().getName());
				interaction.setProduitB(p2);
				interaction.setSeverite(ic.getSeverity().name());
				interaction.setRisques(ic.getRiskComment());
				interaction.setPrecautions(ic.getPrecautionComment());
				interactions.add(interaction);
			}
			return interactions;
		}catch(Exception e){
			String message = "Erreur lors de l'analyse des interactions médicamenteuses de la prescription suivante "+ produits;
			this.appli.getLogger().error(this+message, e);
			throw new GestionCabinetException(message, e);
		}

	}




}
