package com.thanjoz.jobportal_backend.controller;

import com.thanjoz.jobportal_backend.dto.CompanyRequest;
import com.thanjoz.jobportal_backend.dto.CompanyResponse;
import com.thanjoz.jobportal_backend.service.CompanyService;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/companies")
@CrossOrigin
public class CompanyController {

    @Autowired
    private CompanyService companyService;

    // ================= CREATE COMPANY =================

    @PostMapping("/register")
    public ResponseEntity<CompanyResponse> registerCompany(

            @RequestBody CompanyRequest request

    ) {

        CompanyResponse response =
                companyService.registerCompany(
                        request
                );

        return ResponseEntity.ok(response);

    }
// ================= GET ALL COMPANIES =================

    @GetMapping
    public ResponseEntity<List<CompanyResponse>> getAllCompanies() {

        return ResponseEntity.ok(
                companyService.getAllCompanies()
        );

    }
    // ================= GET COMPANY BY ID =================

    @GetMapping("/{id}")
    public ResponseEntity<CompanyResponse> getCompanyById(
            @PathVariable Long id
    ) {

        return ResponseEntity.ok(
                companyService.getCompanyById(id)
        );

    }
    // ================= UPDATE COMPANY =================

    @PutMapping("/{id}")
    public ResponseEntity<CompanyResponse> updateCompany(
            @PathVariable Long id,
            @RequestBody CompanyRequest request
    ) {

        return ResponseEntity.ok(
                companyService.updateCompany(id, request)
        );

    }



// ================= DELETE COMPANY =================

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCompany(
            @PathVariable Long id
    ) {

        companyService.deleteCompany(id);

        return ResponseEntity.ok("Company deleted successfully");

    }
}