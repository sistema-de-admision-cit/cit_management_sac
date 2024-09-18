package cr.co.ctpcit.citsacbackend.rest.inscriptions;


import cr.co.ctpcit.citsacbackend.logic.dto.inscription.StudentDto;
import cr.co.ctpcit.citsacbackend.logic.services.inscriptions.InscriptionsService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/api/inscription")
@CrossOrigin("*")
@Validated
public class InscriptionFormController {
    private final InscriptionsService inscriptionsService;

    public InscriptionFormController(InscriptionsService inscriptionsService) {
        this.inscriptionsService = inscriptionsService;
    }

    /**
     * This method creates a new inscription in the database following RFC 9110 as
     * the official Request for Comments for HTTP Semantics and Content.
     * @return a response entity with the status code and Location header
     */
    @PostMapping("/add")
    public ResponseEntity<Void> createInscription(@RequestBody @Valid StudentDto student, UriComponentsBuilder ucb) {
        String id = inscriptionsService.addInscription(student).idNumber();
        URI location = ucb.path("/api/inscriptions/{id}").buildAndExpand(id).toUri();
        return ResponseEntity.created(location).build();
    }

    //TODO: Implement an endpoint where to receive a POST request with a file in pdf or any other format and save it in the storage
    @PostMapping(name = "/upload", consumes = "application/octet-stream")
    public ResponseEntity<Void> uploadDocument(@RequestParam("file") MultipartFile file) {
        return ResponseEntity.ok().build();
    }
}
