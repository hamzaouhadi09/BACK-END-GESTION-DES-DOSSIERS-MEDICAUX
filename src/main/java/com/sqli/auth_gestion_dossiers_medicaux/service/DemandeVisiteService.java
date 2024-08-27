package com.sqli.auth_gestion_dossiers_medicaux.service;

import com.sqli.auth_gestion_dossiers_medicaux.dao.DemandeVisiteDao;
import com.sqli.auth_gestion_dossiers_medicaux.model.DemandeVisite;
import com.sqli.auth_gestion_dossiers_medicaux.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class DemandeVisiteService {

    private static final String UPLOAD_DIRECTORY = "uploads/"; // Répertoire où stocker les fichiers

    @Autowired
    private DemandeVisiteDao demandeVisiteDao;

    public DemandeVisite createDemandeVisite(User user, String message, MultipartFile file) throws IOException {
        DemandeVisite demande = new DemandeVisite();
        demande.setUser(user);
        demande.setMessage(message);

        if (file != null && !file.isEmpty()) {
            String fileName = saveFile(file);
            demande.setFichierJoint(fileName); // Enregistrer le chemin du fichier dans la base de données
        }

        return demandeVisiteDao.save(demande);
    }

    private String saveFile(MultipartFile file) throws IOException {
        Path uploadPath = Paths.get(UPLOAD_DIRECTORY);
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath); // Créer le répertoire s'il n'existe pas
        }

        String fileName = file.getOriginalFilename();
        Path filePath = uploadPath.resolve(fileName);
        Files.write(filePath, file.getBytes()); // Sauvegarder le fichier sur le serveur

        return fileName;
    }
}
