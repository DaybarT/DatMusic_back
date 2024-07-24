package com.example.music.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.music.Entity.Artists;

@Repository("ArtistsRepo")
public interface ArtistRepository extends JpaRepository<Artists, String>{
@Query(value="SELECT * FROM artists WHERE artist_id = :artistId", nativeQuery = true)
    Artists findByartistId(@Param("artistId") String artistId);
    Artists findByEmail(String email);
    Artists findByUsername(String username);
    Artists findBylicense(String license);
    Artists deleteArtistsByUsername(String username);
}
