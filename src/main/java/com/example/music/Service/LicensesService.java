package com.example.music.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.music.Entity.Licenses;
import com.example.music.Repository.LicenseRepository;
@Service
public class LicensesService {

    @Autowired
    private LicenseRepository licenseRepository;

    public String createLicense() {

        String licenseKey = String.valueOf((int) (Math.random() * 1000000000));

            // Crear una nueva instancia de Licenses
        Licenses license = new Licenses();
        license.setLicenseKey(licenseKey);
        license.setAssigned(false);

        // Guardar la instancia en la base de datos
        licenseRepository.save(license);
        return licenseKey;
    }

}
