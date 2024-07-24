package com.example.music.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.music.Service.LicensesService;


@RestController
public class AdminController {
    
    @Autowired
    private LicensesService licensesService;

    @GetMapping("/createLicense")
    public String createLicense() {
      
        return licensesService.createLicense();
        
    }
}
