package fr.uga.l3miage.example.controller;

import fr.uga.l3miage.example.endpoint.MiahootEndpoint;
import fr.uga.l3miage.example.request.CreateMiahootRequest;
import fr.uga.l3miage.example.response.Miahoot;
import fr.uga.l3miage.example.service.MiahootService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MiahootController implements MiahootEndpoint {

    private final MiahootService miahootService;

    public Miahoot getEntityMiahoot(final Long userId, final String nom) {
        return miahootService.getMiahoot(userId, nom);
    }

    public void createEntityMiahoot(final CreateMiahootRequest request) {
        miahootService.createMiahoot(request);
    }

    public void updateMiahootEntity(final Long userId, final String nom,final Miahoot miahoot) {
        miahootService.updateMiahoot(userId,nom,miahoot);
    }

    public void deleteMiahootEntity(final Long userId, final String nom) {
        miahootService.deleteMiahoot(userId, nom);
    }

}
