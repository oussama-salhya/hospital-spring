package ma.emsi;

import ma.emsi.entities.Patient;
import ma.emsi.repositories.PatientRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Date;
import java.util.List;
import java.util.stream.Stream;

@SpringBootApplication
public class PatientApplication {

	public static void main(String[] args) {
		SpringApplication.run(PatientApplication.class, args);
	}
	@Bean
	CommandLineRunner commandLineRunner(
			PatientRepository patientRepository
	){
		return args->{
			System.out.println("test");
			Stream.of("Xavier", "norman", "kai").forEach(name->{
						Patient patient= new Patient();
						patient.setNom(name);
						patient.setDateNaissance(new Date());
						patient.setScore((int)(Math.random()*4688));
						patient.setMalade(Math.random()*1000>0.5);
						patientRepository.save(patient);
					}


			);
			List<Patient> listp= patientRepository.findAll();
			for (Patient p:listp
				 ) {
				p.getNom();

			}




		};
	}
}
