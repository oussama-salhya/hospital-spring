package ma.emsi.web;

import lombok.AllArgsConstructor;
import ma.emsi.entities.Medecin;
import ma.emsi.entities.Patient;
import ma.emsi.entities.RendezVous;
import ma.emsi.entities.StatusRDV;
import ma.emsi.repositories.RendezVousRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Date;

@Controller
@AllArgsConstructor
public class RDVController {
    RendezVousRepository rendezVousRepository;

    @GetMapping(path = "/user/RDV")
    public String Rendezvous(Model model,
                             @RequestParam(name = "page", defaultValue = "0") int page, // parametre d'url : request.getparametre(page), si on specifie pas le parametre il va prendre la valeur 0 par defaut
                             @RequestParam(name = "size", defaultValue = "5") int size,
                             @RequestParam(name = "date", defaultValue = "today") Date date
    ) {
        Page<RendezVous> pageRDV = rendezVousRepository.findByDate(date, PageRequest.of(page, size));
        model.addAttribute("listRDV", pageRDV.getContent()); // getcontent donne la liste des patients de la page
        model.addAttribute("pages", new int[pageRDV.getTotalPages()]);
        model.addAttribute("currentPage", page);
        model.addAttribute("keyword", date);
        return "RDV/listeRDVs";// nom de la vue
    }

    @GetMapping(path = "/admin/formRendezVous")
    public String formRendezVous(Model model) {
        model.addAttribute("rendezvous", new RendezVous());
        return "RDV/rendezvous";
    }

}


