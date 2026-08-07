package com.thanjoz.jobportal_backend.service;

import com.thanjoz.jobportal_backend.dto.CompanyRequest;
import com.thanjoz.jobportal_backend.dto.CompanyResponse;
import com.thanjoz.jobportal_backend.entity.Company;
import com.thanjoz.jobportal_backend.entity.User;
import com.thanjoz.jobportal_backend.enums.Role;
import com.thanjoz.jobportal_backend.repository.CompanyRepository;
import com.thanjoz.jobportal_backend.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CompanyService {

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthService authService;

    public CompanyResponse registerCompany(
            CompanyRequest request
    ) {

        User owner = authService.getLoggedInUser();

        Company company = new Company();

        company.setName(request.getName());

        company.setDescription(
                request.getDescription()
        );

        company.setWebsite(
                request.getWebsite()
        );

        company.setOwner(owner);

        Company savedCompany =
                companyRepository.save(company);

        // Make the company owner an ADMIN
        owner.setRole(Role.ROLE_ADMIN);
        userRepository.save(owner);

        CompanyResponse response =
                new CompanyResponse();

        response.setId(savedCompany.getId());

        response.setName(
                savedCompany.getName()
        );

        response.setDescription(
                savedCompany.getDescription()
        );

        response.setWebsite(
                savedCompany.getWebsite()
        );

        return response;

    }

    // ================= GET ALL COMPANIES =================

    public List<CompanyResponse> getAllCompanies() {

        List<Company> companies = companyRepository.findAll();

        return companies.stream().map(company -> {

            CompanyResponse response = new CompanyResponse();

            response.setId(company.getId());
            response.setName(company.getName());
            response.setDescription(company.getDescription());
            response.setWebsite(company.getWebsite());

            if (company.getOwner() != null) {
                response.setOwnerId(company.getOwner().getId());
            }

            return response;

        }).toList();

    }
// ================= GET COMPANY BY ID =================

    public CompanyResponse getCompanyById(Long id) {

        Company company = companyRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Company not found"));

        CompanyResponse response = new CompanyResponse();

        response.setId(company.getId());
        response.setName(company.getName());
        response.setDescription(company.getDescription());
        response.setWebsite(company.getWebsite());

        if (company.getOwner() != null) {
            response.setOwnerId(company.getOwner().getId());
        }

        return response;
    }
    // ================= UPDATE COMPANY =================

    public CompanyResponse updateCompany(
            Long id,
            CompanyRequest request
    ) {

        Company company = companyRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Company not found"));

        company.setName(request.getName());
        company.setDescription(request.getDescription());
        company.setWebsite(request.getWebsite());

        Company updatedCompany = companyRepository.save(company);

        CompanyResponse response = new CompanyResponse();

        response.setId(updatedCompany.getId());
        response.setName(updatedCompany.getName());
        response.setDescription(updatedCompany.getDescription());
        response.setWebsite(updatedCompany.getWebsite());

        if (updatedCompany.getOwner() != null) {
            response.setOwnerId(updatedCompany.getOwner().getId());
        }

        return response;

    }



// ================= DELETE COMPANY =================

    public void deleteCompany(Long id) {

        Company company = companyRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Company not found"));

        companyRepository.delete(company);

    }
}