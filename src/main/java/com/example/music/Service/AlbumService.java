package com.example.music.Service;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;

import com.example.music.Entity.Albums;
import com.example.music.Entity.Artists;
import com.example.music.Repository.AlbumRepository;
import com.example.music.Repository.ArtistRepository;

import jakarta.transaction.Transactional;

public class AlbumService {
    @Autowired
    private ArtistRepository artistRepository;

    @Autowired
    private AlbumRepository albumRepository;

    @Transactional
    public Albums uploadAlbum(Albums album) {
        String id = UUID.randomUUID().toString();

        try {
            System.out.println("Creando el album con nombre: " + album.getName() + " con el id: " + album.getId());
            Artists artist = artistRepository.findByartistId("c89f89e9-5f1d-4ab9-9e35-c2de6740102c"); // HeaderParam

            if (artist == null) {
                throw new Exception("Artista no existente");
            } else {
                Albums newAlbum = new Albums(id, album.getName(), album.getPhoto(), album.getType(), artist);

                return albumRepository.save(newAlbum);
            }

        } catch (Exception e) {
            // Manejar la excepción adecuadamente o lanzarla si es necesario
            throw new RuntimeException("Error crear el album: " + e.getMessage());
        }

    }

    public Albums findAlbums(String name) {
        Albums findAlbums = albumRepository.findByName(name);
        return findAlbums;
    }
}
