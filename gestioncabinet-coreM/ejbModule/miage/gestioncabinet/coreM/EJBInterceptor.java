package miage.gestioncabinet.coreM;

import javax.inject.Inject;
import javax.interceptor.AroundInvoke;
import javax.interceptor.InvocationContext;

import miage.gestioncabinet.api.ApplicationLocalService;

import org.jboss.logging.Logger;

public class EJBInterceptor {
	@Inject
	private ApplicationLocalService ALS;
	
	@AroundInvoke
	public Object logMethod(InvocationContext ic) throws Exception {
		long timeDebut = System.currentTimeMillis();
		ALS.getLogger().info("--------------------------");
		ALS.getLogger().info("Début de " + ic.getMethod().getName());
		try {
			Thread.sleep(5000);
			return ic.proceed();
		} finally {
			long timeFin = System.currentTimeMillis();
			long temps = timeFin-timeDebut;
			ALS.getLogger().info("Temps d'execution " + temps + "ms");
			ALS.getLogger().info("--------------------------");
		}
		
	}	
}
