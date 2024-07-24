package com.example.music.Service;

import java.util.UUID;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.music.Entity.Artists;
import com.example.music.Entity.Licenses;
import com.example.music.Repository.ArtistRepository;
import com.example.music.Repository.LicenseRepository;

import jakarta.transaction.Transactional;
@Service
public class ArtistService {

    @Autowired
    private LicenseRepository licenseRepository;

    private final ArtistRepository artistRepository;

    @Autowired
    public ArtistService(ArtistRepository artistRepository) {
        this.artistRepository = artistRepository;
    }

    @Transactional
    public Artists register(Artists artist) {

        if (artistRepository.findByEmail(artist.getEmail()) != null) {
            throw new IllegalArgumentException("El email ya está registrado");
        }
        if (artistRepository.findByUsername(artist.getUsername()) != null){
            throw new IllegalArgumentException("Username no disponible");
        }
        artist.setPass(BCrypt.hashpw(artist.getPass(),BCrypt.gensalt()));

        try {
           
            Licenses license = licenseRepository.findLicenses();
            if (license != null) {
                // Mark the license as assigned
                licenseRepository.licenseAssigned(license.getLicenseKey());

                // Generate a unique ID for the artist
                String artistId = UUID.randomUUID().toString();
                artist.setArtistId(artistId);
                
                // Set the license key for the artist
                artist.setLicense(license.getLicenseKey());

                // Save the artist entity
                return artistRepository.save(artist);
            } else {
                throw new RuntimeException("No hay licencias disponibles para asignar");
            }
        } catch (Exception e) {
            throw new RuntimeException("Error al registrar el Artista", e);
        }
    }
}
