package ma.emsi.repositories;

import ma.emsi.entities.RendezVous;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface RendezVousRepository extends JpaRepository<RendezVous, Long> {
}
