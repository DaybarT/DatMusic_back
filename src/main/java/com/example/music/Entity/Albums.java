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

public class Albums {
    @Id
    @Column(nullable = false, unique=true)
    private String id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String photo;
    
    @Column(nullable = false)
    private String type;

    @ManyToOne
    @JoinColumn(name = "artist_id", nullable = false)
    private Artists artist;

}
