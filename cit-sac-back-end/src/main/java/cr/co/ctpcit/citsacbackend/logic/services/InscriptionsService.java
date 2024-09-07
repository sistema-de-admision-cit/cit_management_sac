package cr.co.ctpcit.citsacbackend.logic.services;

import cr.co.ctpcit.citsacbackend.logic.dto.inscription.InscriptionDto;

import java.util.List;

public interface InscriptionsService {
    InscriptionDto findById(Integer id);
    List<InscriptionDto> findAll();
    InscriptionDto addInscription(InscriptionDto inscriptionDto);
}
