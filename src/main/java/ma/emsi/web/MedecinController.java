package ma.emsi.web;

import lombok.AllArgsConstructor;
import ma.emsi.entities.Medecin;
import ma.emsi.entities.Patient;
import ma.emsi.repositories.MedecinRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.util.List;

@Controller
@AllArgsConstructor
public class MedecinController {
    MedecinRepository medecinRepository;
    @GetMapping(path= "/user/medecins")
    public String medecin (Model model,
                           @RequestParam(name="page", defaultValue = "0") int page,
                           @RequestParam(name="size", defaultValue = "5") int size,
                           @RequestParam(name="keyword", defaultValue = "") String keyword){
        Page<Medecin> pagemedecins= medecinRepository.findByNomContains(keyword, PageRequest.of(page, size));
        List<Medecin> medecinList = medecinRepository.findAll();
        model.addAttribute("listMedecins",pagemedecins);
        model.addAttribute("pages", new int[pagemedecins.getTotalPages()]);
        model.addAttribute("currentPage", page);
        model.addAttribute("keyword",keyword);
        return "medecin/medecins";
    }
    @GetMapping(path="/admin/formMedecin")
    public String formMedecin(Model model){
        model.addAttribute("medecin", new Medecin());
        return "medecin/formMedecin";
    }
    @GetMapping(path="/admin/deleteMedecin")
    public String delete(Long id, String keyword, int page){
        medecinRepository.deleteById(id);
        return "redirect:/user/medecins?page="+page+"&keyword="+keyword;
    }

    @PostMapping(path="/admin/saveMedecin")
    public String save(Model model,
                       @Valid Medecin medecin,
                       BindingResult bindingResult, // =>stock les erreurs
                       @RequestParam(defaultValue = "0") int page,
                       @RequestParam(defaultValue = "") String keyword){
        if (bindingResult.hasErrors()) return "formMedecin";
        medecinRepository.save(medecin);
        return "redirect:/user/medecins?page"+page+"&keyword"+keyword;
    }
    @GetMapping(path="/admin/EditMedecin")
    public String EditPatient(Model model, Long id, String keyword, int page){
        Medecin medecin = medecinRepository.findById(id).orElse(null); // avec .get je le recuper s'il existe mais on peut utiliser orElse(null) null s'il ne trouve pas le patient
        if(medecin==null) throw new RuntimeException("Medecin introuvable");
        model.addAttribute("medecin", medecin);
        model.addAttribute("page", page);
        model.addAttribute("keyword",keyword);
        return "Medecin/EditMedecin";
    }
}
