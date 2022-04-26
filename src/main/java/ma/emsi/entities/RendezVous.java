package ma.emsi.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Collection;
import java.util.Date;

@Entity
@Data @AllArgsConstructor @NoArgsConstructor
public class RendezVous {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Date date;
    @Enumerated(EnumType.STRING)
    private  StatusRDV statusRDV;
    @ManyToOne
    @JsonProperty(access = JsonProperty.Access.READ_WRITE)
    private Patient patient;
    @ManyToOne
    private Medecin medecin;
    @OneToMany(mappedBy = "rendezVous")
    private Collection<Consultation> consultation;
    public enum StatusRDV{
        PENDING, CANCELED, DONE;
    }

}
