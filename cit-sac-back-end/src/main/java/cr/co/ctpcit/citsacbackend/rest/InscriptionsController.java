package cr.co.ctpcit.citsacbackend.rest;

import cr.co.ctpcit.citsacbackend.logic.dto.inscription.InscriptionDto;
import cr.co.ctpcit.citsacbackend.logic.services.InscriptionsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class InscriptionsController {
    InscriptionsService inscriptionsService;

    @Autowired
    public InscriptionsController(InscriptionsService inscriptionsService) {
        this.inscriptionsService = inscriptionsService;
    }

    @GetMapping("/inscriptions/{id}")
    public ResponseEntity<InscriptionDto> getInscriptionById(@PathVariable Integer id) {
        InscriptionDto inscription = inscriptionsService.findById(id);
        if(inscription == null) return ResponseEntity.notFound().build();

        return ResponseEntity.ok(inscription);
    }

    //TODO: Implement inscriptions get endpoint to retrieve all inscriptions
    @GetMapping("/inscriptions")
    public ResponseEntity<?> getInscriptions() {
        return ResponseEntity.ok().build();
    }

    //TODO: Implement inscriptions post endpoint to create a new inscription
    @PostMapping("/inscriptions/add")
    public ResponseEntity<?> createInscription() {
        return ResponseEntity.ok().build();
    }
}
