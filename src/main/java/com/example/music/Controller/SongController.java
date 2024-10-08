package com.example.music.Controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.music.Entity.Songs;
import com.example.music.Repository.SongRepository;
import com.example.music.Service.FtpService;
import com.example.music.Service.SongService;

@RestController
public class SongController {
    
    @Autowired
    private SongService songservice;

    @Autowired
    private SongRepository songrepository;
    
    @Autowired
    private FtpService ftpService;


    @PostMapping("/uploadSong")
    public Songs connectFTP(@RequestParam("file") MultipartFile localFilePath,@ModelAttribute Songs song) throws IOException {
        boolean prueba = ftpService.connectToFTP();
        if (prueba == true){
            Songs data = songservice.uploadSong(song);
            if (data != null){
                ftpService.uploadFile(data.getSongId(),localFilePath);
                song.setSongId(data.getSongId());
                
            }
            
        }
        return song;
    }
    // 7edc1367-04ab-4a87-a7d2-40c7597e7b51
    @DeleteMapping("/deleteSong")
    public String deleteSong(@RequestBody String id) throws IOException{ //cuando tengas el frontend, ajustalo para pasarle el objeto entero 
        boolean prueba = ftpService.connectToFTP();
        if (prueba == true){
            //no hace falta buscarla, este metodo estara disponible solo cuando ya hayas encontrado la cancion
           
            ftpService.deleteSong(id);
        }
        return null;
    }
}


