package com.thanjoz.jobportal_backend.controller;

import com.thanjoz.jobportal_backend.entity.Company;
import com.thanjoz.jobportal_backend.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/companies")
@CrossOrigin
public class CompanyController {

    @Autowired
    private CompanyService companyService;

    @PostMapping("/create/{ownerId}")
    public Company createCompany(
            @PathVariable Long ownerId,
            @RequestBody Company company) {

        return companyService.createCompany(company, ownerId);
    }

    @GetMapping
    public List<Company> getAllCompanies() {
        return companyService.getAllCompanies();
    }

    @GetMapping("/{id}")
    public Company getCompanyById(@PathVariable Long id) {
        return companyService.getCompanyById(id);
    }

    @GetMapping("/owner/{ownerId}")
    public List<Company> getCompaniesByOwner(
            @PathVariable Long ownerId) {

        return companyService.getCompaniesByOwner(ownerId);
    }
    @PutMapping("/{id}")
    public Company updateCompany(
            @PathVariable Long id,
            @RequestBody Company company) {

        return companyService.updateCompany(id, company);
    }

    @DeleteMapping("/{id}")
    public String deleteCompany(@PathVariable Long id) {

        companyService.deleteCompany(id);

        return "Company deleted successfully";
    }
}