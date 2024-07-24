package com.example.music.Service;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.music.Entity.Artists;
import com.example.music.Entity.Songs;
import com.example.music.Repository.ArtistRepository;
import com.example.music.Repository.SongRepository;

import jakarta.transaction.Transactional;
@Service
public class SongService {

    @Autowired
    private ArtistRepository artistRepository;

    @Autowired
    private SongRepository songRepository;

    @Transactional
    public Songs uploadSong(Songs song) {
    String id = UUID.randomUUID().toString();
    
    try {
        System.out.println(id);
        
        Artists artist = artistRepository.findByartistId("c89f89e9-5f1d-4ab9-9e35-c2de6740102c"); //HeaderParam
        
        Songs newSong = new Songs(
                id,
                song.getSongName(),
                song.getType(),
                song.getPhoto(),
                null,
                artist);
                // song.setArtist(artist.getArtistId()));
                
        return songRepository.save(newSong);
        
    } catch (Exception e) {
        e.printStackTrace();  // Imprimir la traza de la excepción
        // Manejar la excepción adecuadamente o lanzarla si es necesario
        throw new RuntimeException("Error al subir la canción: " + e.getMessage());
    }
    
}
    public Songs findSongs(String name){
        Songs findSong = songRepository.findBySongName(name);       
        return findSong;
    }
    
    
}
