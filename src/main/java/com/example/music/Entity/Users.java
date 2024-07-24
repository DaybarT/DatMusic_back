package com.example.music.Entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Users {
    @Id
    @Column(nullable = false, unique=true)
    private String id;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String nombre;

    @Column(nullable = false)
    private String apellido;
    
    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String pass;
}
