package ma.emsi.web;

import lombok.AllArgsConstructor;
import ma.emsi.entities.Consultation;
import ma.emsi.entities.Medecin;
import ma.emsi.entities.Patient;
import ma.emsi.entities.RendezVous;
import ma.emsi.repositories.ConsultationRepository;
import ma.emsi.repositories.RendezVousRepository;
import ma.emsi.service.IHopitalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;

@Controller
@AllArgsConstructor
public class ConsultationController {
    @Autowired
    ConsultationRepository consultationRepository;
    IHopitalService hopitalService;
    RendezVousRepository rendezVousRepository;
    @GetMapping(path= "/user/consultations")
    public String consultation (Model model,
                           @RequestParam(name= "page", defaultValue = "0") int page,
                           @RequestParam(name="size", defaultValue = "5") int size){
        Page<Consultation> pageconsultation= consultationRepository.findAll(PageRequest.of(page, size));

        model.addAttribute("listConsultation",pageconsultation);
        model.addAttribute("pages", new int[pageconsultation.getTotalPages()]);
        model.addAttribute("currentPage", page);
        return "consultation/consultations";
    }
    @GetMapping(path="/admin/deleteConsultation")
    public String deleteConsultation(Long id,  int page){
        consultationRepository.deleteById(id);
        return "redirect:/user/consultations?page="+page;
    }
    @PostMapping(path="/admin/saveConsultation")
    public String saveConsultation(Model model,
                       @Valid Consultation consultation,
                       BindingResult bindingResult,
                       Long rdvID,
                       @RequestParam(defaultValue = "0") int page){
        if (bindingResult.hasErrors()) return "formConsultation";
        System.out.println(consultation.toString());
        System.out.println("helloooooooooooo");
        RendezVous rendezVous = rendezVousRepository.findById(rdvID).orElse(null);
        consultation.setRendezVous(rendezVous);
        hopitalService.saveConsultation(consultation);
        return "redirect:/user/consultations?page="+page;
    }
    @GetMapping(path="/admin/formConsultation")
    public String formConsultation(Model model){
        model.addAttribute("consultation",new Consultation());
        model.addAttribute("rendezvous",rendezVousRepository.findAll());
        return "consultation/formConsultation";
    }
    @GetMapping(path="/admin/EditConsultation")
    public String EditConsultation(Model model, Long id,int page){
        Consultation consultation = consultationRepository.findById(id).orElse(null); // avec .get je le recuper s'il existe mais on peut utiliser orElse(null) null s'il ne trouve pas le patient
        if(consultation==null) throw new RuntimeException("Consultation introuvable");
        model.addAttribute("consultation", consultation);
        model.addAttribute("rendezvous",rendezVousRepository.findAll());
        model.addAttribute("page", page);
        return "consultation/EditConsultation";
    }
}
