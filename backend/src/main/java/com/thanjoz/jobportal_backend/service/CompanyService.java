package com.thanjoz.jobportal_backend.service;

import com.thanjoz.jobportal_backend.entity.Company;
import com.thanjoz.jobportal_backend.entity.User;
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

    public Company createCompany(Company company, Long ownerId) {

        User owner = userRepository.findById(ownerId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        company.setOwner(owner);

        return companyRepository.save(company);
    }

    public List<Company> getCompaniesByOwner(Long ownerId) {
        return companyRepository.findByOwnerId(ownerId);
    }

    public List<Company> getAllCompanies() {
        return companyRepository.findAll();
    }

    public Company getCompanyById(Long id) {
        return companyRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Company not found"));
    }
}
