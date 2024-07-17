package com.opolos.mannschaft.services;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.opolos.mannschaft.model.ERole;
import com.opolos.mannschaft.model.InspectionReports;
import com.opolos.mannschaft.model.Role;
import com.opolos.mannschaft.model.User;
import com.opolos.mannschaft.repository.InspectReportsRepository;
import com.opolos.mannschaft.repository.RoleRepository;
import com.opolos.mannschaft.repository.UserRepository;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class PdfService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private InspectReportsRepository inspectReportsRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    PasswordEncoder encoder;


    public String readPdf(File file) throws IOException {
        try (PDDocument document = PDDocument.load(file)) {
            PDFTextStripper pdfStripper = new PDFTextStripper();
            return pdfStripper.getText(document);
        }
    }

    

    public Boolean checkClientExistence(String client,String mail){
            List<User> users= userRepository.findAll();
            // Loop through the list of users and perform some action
            for (User user : users) {
                // For demonstration, we'll just print the user details
                if(user.getUsername().equals(client)&&user.getEmail().equals(mail)){
                    return true;

                }
                
            }


            return false;

    }


    

public Boolean checkEmailExistence(String email){
    List<User> users= userRepository.findAll();
    // Loop through the list of users and perform some action
    for (User user : users) {
        // For demonstration, we'll just print the user details
        if(user.getEmail().equals(email)){
            return true;

        }
        
    }


    return false;

}
    public Boolean checkReportExistence(String report){
        
        List<InspectionReports> inspectionReports= inspectReportsRepository.findAll();
        // Loop through the list of users and perform some action
        for (InspectionReports inspectionReport : inspectionReports) {
            // For demonstration, we'll just print the user details
            if(inspectionReport.getReport_namr().equals(report)){
                return true;
            }     
        }
        return false;

    }

    public String insertNewUser(
    String client_name,
    String username,
    String email,
    String password,
    String client_id,
    String street,
    String address,
    String website,
    String contact_one,
    String contact_two,
    Set<String> roless,
    String instance_name
    ){
        
        User user =new User(
            client_name,
            username,
            email,
            encoder.encode(password),
            client_id,
            street,
            address,
            website,
            contact_one,
            contact_two,
            instance_name
              
            );

        Set<String> strRoles = roless;
        Set<Role> roles = new HashSet<>();

        if (strRoles == null) {
        Role userRole = roleRepository.findByName(ERole.ROLE_USER)
            .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
        roles.add(userRole);
        } else {
        strRoles.forEach(role -> {
            switch (role) {
            case "admin":
            Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            roles.add(adminRole);

            break;
            case "mod":
            Role modRole = roleRepository.findByName(ERole.ROLE_MODERATOR)
                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            roles.add(modRole);

            break;
            default:
            Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            roles.add(userRole);
            }
        });
        }

        user.setRoles(roles);
        userRepository.save(user);
        return "user inserted sucessfully";

    }

    public String insertNewReport(
            String cal_year,
            String client,
            String client_id,
            String application_type,
            String identification_number,
            String test_according_to,
            String manufucturer,
            String department,
            String measuring_length,
            String profile,
            String report_url,
            String report_namr,
            String type_number,
            String serial_number,
            String device_type,
            String cross_section,
            String test_date,
            String next_test_date,
            String examiner,
            boolean published             
    ){
        
        // Create new user's account
        InspectionReports inspectionReports = new InspectionReports(
            cal_year,
            client,
            client_id,
            application_type,
            identification_number,
            test_according_to,
            manufucturer,
            department,
            measuring_length,
            profile,
            report_url,
            report_namr,
            type_number,
            serial_number,
            device_type,
            cross_section,
            test_date,
            next_test_date,
            examiner,
            true);


        inspectReportsRepository.save(inspectionReports);
        return "Report inserted sucessfully";

    }
}
