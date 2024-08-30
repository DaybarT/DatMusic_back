package com.example.music.Service;

import java.io.IOException;
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

    @Autowired
    private ArtistRepository artistRepository;

    @Transactional
    public Artists register(Artists artist) {

        try {

            if (artistRepository.findByEmail(artist.getEmail()) != null) {
                throw new IOException("El email ya está registrado");
            }
            if (artistRepository.findByUsername(artist.getUsername()) != null) {
                throw new IOException("Username no disponible");
            }
            artist.setPass(BCrypt.hashpw(artist.getPass(), BCrypt.gensalt()));

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
                throw new IOException("No hay licencias disponibles para asignar");
            }
        } catch (Exception e) {
            throw new RuntimeException("Error al registrar el Artista"+ e.getMessage());
        }
    }

    @Transactional
    public Artists updateArtist(Artists artist) {
        try {
            Artists updateData = artistRepository.findByUsername(artist.getUsername());
            if (updateData == null) {
                throw new IllegalArgumentException("Artista no encontrado para actualizar");
            }

            updateData.setEmail(artist.getEmail());

            // Guardar los cambios en el repositorio
            return artistRepository.save(updateData);

        } catch (Exception e) {
            throw new RuntimeException("Error al actualizar el Artista: " + e.getMessage());
        }
    }

    @Transactional
    public Artists updatePass(Artists artist) { // mira a ver si puedes hacerlo con un email, que mande un mail con otro

        try {
            // metodo y que updatee la contraseña enviando un objeto a aqui.
            Artists updatePass = artistRepository.findByEmail(artist.getEmail());
            if (updatePass == null) {
                throw new RuntimeException("Usuario no encontrado para cambiar la contraseña");
            }

            artist.setPass(BCrypt.hashpw(artist.getPass(), BCrypt.gensalt()));

            return artistRepository.save(updatePass);
        } catch (Exception e) {
            throw new RuntimeException("Error al actualizar la contraseña: " + e.getMessage());

        }

    }

}
