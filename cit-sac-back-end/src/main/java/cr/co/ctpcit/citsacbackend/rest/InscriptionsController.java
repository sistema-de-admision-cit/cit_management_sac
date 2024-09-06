package cr.co.ctpcit.citsacbackend.rest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("/api")
public class InscriptionsController {

    //TODO: Implement inscriptions get endpoint to retrieve all inscriptions
    @GetMapping("/inscriptions")
    public ResponseEntity<?> getInscriptions() {
        return ResponseEntity.ok().build();
    }

    //TODO: Implement inscriptions post endpoint to create a new inscription
    @PostMapping("/inscriptions")
    public ResponseEntity<?> createInscription() {
        return ResponseEntity.ok().build();
    }

    //TODO: Implement inscriptions get endpoint to retrieve an inscription by id
    @GetMapping("/inscriptions/{id}")
    public ResponseEntity<?> getInscriptionById(@PathVariable String id) {
        return ResponseEntity.ok().build();
    }
}
