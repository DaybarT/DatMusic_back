package com.example.music.Entity;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor

public class Songs {
     @Id
     @Column(nullable = false, unique=true)
    private String songId;

    @Column(nullable = false)
    private String songName;

    @Column(nullable = false)
    private String type;

    @Column(nullable = false)
    private String photo;

    @Column(nullable = true)
    private String album;

    @ManyToOne
    @JoinColumn(name = "Artist_id", nullable = false)
    private Artists artist;

}
