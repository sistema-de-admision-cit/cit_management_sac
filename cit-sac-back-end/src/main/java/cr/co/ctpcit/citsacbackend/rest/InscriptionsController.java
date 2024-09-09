package cr.co.ctpcit.citsacbackend.rest;

import cr.co.ctpcit.citsacbackend.logic.dto.inscription.StudentDto;
import cr.co.ctpcit.citsacbackend.logic.services.InscriptionsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class InscriptionsController {
    InscriptionsService inscriptionsService;

    @Autowired
    public InscriptionsController(InscriptionsService inscriptionsService) {
        this.inscriptionsService = inscriptionsService;
    }

    @GetMapping("/inscriptions/{id}")
    public ResponseEntity<StudentDto> getInscriptionById(@PathVariable String id) {
        return ResponseEntity.ok().build();
    }

    //TODO: Implement inscriptions get endpoint to retrieve all inscriptions
    @GetMapping("/inscriptions")
    public ResponseEntity<Iterable<StudentDto>> getInscriptions() {
        return ResponseEntity.ok(inscriptionsService.getAllInscriptions());
    }

    //TODO: Implement inscriptions post endpoint to create a new inscription
    @PostMapping("/inscriptions/add")
    public ResponseEntity<?> createInscription() {
        return ResponseEntity.ok().build();
    }
}
