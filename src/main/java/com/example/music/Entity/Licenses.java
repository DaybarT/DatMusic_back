package com.example.music.Entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor

@Table(name = "licenses")
public class Licenses {

    @Id
    @Column(nullable = false, unique=true)
    private String licenseKey;

    @Column(nullable = false, name = "assigned")
    private boolean assigned;

}
