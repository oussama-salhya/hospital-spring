package ma.emsi;

import ma.emsi.entities.*;
import ma.emsi.repositories.MedecinRepository;
import ma.emsi.repositories.PatientRepository;
import ma.emsi.repositories.RendezVousRepository;
import ma.emsi.security.service.SecurityService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.stream.Stream;

@SpringBootApplication
public class PatientApplication {

	public static void main(String[] args) {
		SpringApplication.run(PatientApplication.class, args);
	}
	@Bean
	@Transactional
	CommandLineRunner commandLineRunner(
			PatientRepository patientRepository,
			RendezVousRepository rendezVousRepository,
			MedecinRepository medecinRepository
	){
		return args-> {
			System.out.println("test");
			Stream.of("Xavier", "norman", "kai","sanae").forEach(name->{
						Patient patient= new Patient();
						patient.setNom(name);
						patient.setAdresse("adresse");
						patient.setTitre(Titre.Mr);
						patient.setCodePostal("code" +(int)(Math.random()*58));
						patient.setNumeroTelephone("06"+(int)(Math.random()*90000));
						patient.setDateNaissance(new Date());
						patient.setMalade((Math.random()*568)>0.5);
//						patient.setRendezVous(null);
						patientRepository.save(patient);
					}


			);
			List<Patient> listp= patientRepository.findAll();
			for (Patient p:listp
				 ) {
				p.getNom();

			}
			Stream.of("loubna", "hayat", "hamza","mohamed")
					.forEach(name -> {
								Medecin medecin = new Medecin();
								medecin.setNom(name);
								medecin.setEmail(name + "@gmail.com");
								medecin.setSpecialite("dentaire");
//								medecin.setRendezVous(null);
								medecinRepository.save(medecin);
								System.out.println("done");
							}

					);
			List<Medecin> medecins= medecinRepository.findAll();
			for (Medecin m: medecins
				 ) {

				System.out.println("nom=>"+m.getNom());
			}
			Stream.of(StatusRDV.CANCELED,StatusRDV.DONE,StatusRDV.PENDING )
					.forEach( status->{
						RendezVous rendezVous= new RendezVous();
						rendezVous.setDate(new Date());
						rendezVous.setStatusRDV(status);
						rendezVous.setPatient(listp.get(Math.random()>0.5?(Math.random()<0.5?1:2):(Math.random()<0.5?3:2)));
						rendezVous.setMedecin(medecins.get(Math.random()>0.5?(Math.random()<0.5?1:2):(Math.random()<0.5?3:2)));
						rendezVousRepository.save(rendezVous);
					}

			);
			List<RendezVous> RDVs = rendezVousRepository.findAll();
			for (RendezVous r :
					RDVs) {
				r.getId();
			}


		};





	}
	@Bean
	CommandLineRunner saveUsers(SecurityService securityService){
		return args ->{
			securityService.saveNewUser("loubna","1234","1234");
			securityService.saveNewUser("testuser1","1234","1234");
			securityService.saveNewUser("testuser2","1234","1234");

			securityService.saveNewRole("USER","");
			securityService.saveNewRole("ADMIN","");

			securityService.addRoleToUser("loubna","USER");
			securityService.addRoleToUser("loubna","ADMIN");
			securityService.addRoleToUser("testuser1","USER");
			securityService.addRoleToUser("testuser2","USER");


		};
	}
}
