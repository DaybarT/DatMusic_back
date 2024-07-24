package com.example.music.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.music.Entity.Licenses;

import jakarta.transaction.Transactional;

@Repository("LicensesRepo")
public interface LicenseRepository extends JpaRepository<Licenses, String> {

    @Query("SELECT l FROM Licenses l WHERE l.assigned = false")
    Licenses findLicenses();

    @Transactional
    @Modifying
    @Query("UPDATE Licenses l SET l.assigned = true WHERE l.licenseKey = :licenseKey")
    void licenseAssigned(@Param("licenseKey") String licenseKey);

}
