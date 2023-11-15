package org.institutsaintjean.INFIRMERIE_IUSJC;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Calendar;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import java.util.concurrent.ScheduledExecutorService;


@SpringBootApplication
public class InfirmerieIusjcApplication {

	public static void main(String[] args) {
		SpringApplication.run(InfirmerieIusjcApplication.class, args);

		// Créez un ScheduledExecutorService avec une seule thread
		ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();

		// Obtenez la date actuelle
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.HOUR_OF_DAY,22); // Heure souhaitée
		calendar.set(Calendar.MINUTE, 53);
		calendar.set(Calendar.SECOND, 0);

		// Calculez le délai jusqu'à la prochaine exécution
		long initialDelay = calendar.getTimeInMillis() - System.currentTimeMillis();
		if (initialDelay < 0) {
			// Si l'heure prévue est déjà passée aujourd'hui, planifiez pour demain
			initialDelay += 24 * 60 * 60 * 1000; // 24 heures en millisecondes
		}

		// Planifiez la tâche pour s'exécuter tous les jours à l'heure spécifiée
		//scheduler.scheduleAtFixedRate(new ScheduledTask(), initialDelay, 24 * 60 * 60 * 1000, TimeUnit.MILLISECONDS);


	}


}
