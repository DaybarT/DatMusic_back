package com.example.music.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.music.Entity.Albums;

@Repository("AlbumsRepo")
public interface AlbumRepository  extends JpaRepository<Albums, String>{
    Albums findByType(String type);
    // List<Albums> findByArtist(Artists artist);
    Albums findByName(String name);
}

