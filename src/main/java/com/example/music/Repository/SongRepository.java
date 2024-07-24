package com.example.music.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.music.Entity.Songs;


@Repository("SongRepo")
public interface SongRepository extends JpaRepository<Songs, String>{
    Songs findSongsBysongName(String songName);
    Songs findSongsByType(String type);
    // List<Songs> findByAlbum(Albums albumName);
    // List<Songs> findByArtist(Artists nombre);
    @Query(value="SELECT * FROM songs WHERE song_name = :song_name LIMIT 10", nativeQuery = true)
    Songs findBySongName(@Param("song_name") String songName);

    // @Transactional
    // void deletesongById(String songId);
}

