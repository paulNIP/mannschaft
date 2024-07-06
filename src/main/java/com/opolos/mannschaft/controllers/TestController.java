package com.opolos.mannschaft.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


import com.opolos.mannschaft.model.InspectionReports;
import com.opolos.mannschaft.model.User;
import com.opolos.mannschaft.repository.InspectReportsRepository;
import com.opolos.mannschaft.repository.UserRepository;

@CrossOrigin(origins = "https://opolostechnologies.com", maxAge = 3600)
@RestController
@RequestMapping("/api/test")
public class TestController {

  @Autowired
	InspectReportsRepository inspectReportsRepository;

  @Autowired
  UserRepository userRepository;

  
  @GetMapping("/all")
  public String allAccess() {
    return "Public Content.";
  }

  @GetMapping("/reports")
	public ResponseEntity<List<InspectionReports>> getAllTutorials(@RequestParam(required = false) String name) {
		try {
			List<InspectionReports> reports = new ArrayList<InspectionReports>();

			if (name == null)
				inspectReportsRepository.findAll().forEach(reports::add);


			if (reports.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}

			return new ResponseEntity<>(reports, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

  @GetMapping("/clients")
	public ResponseEntity<List<User>> getAllUsers(@RequestParam(required = false) String name) {
		try {
			List<User> client = new ArrayList<User>();

			if (name == null)
        userRepository.findAll().forEach(client::add);


			if (client.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}

			return new ResponseEntity<>(client, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

  @GetMapping("/user")
  @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
  public String userAccess() {
    return "User Content.";
  }

  @GetMapping("/mod")
  @PreAuthorize("hasRole('MODERATOR')")
  public String moderatorAccess() {
    return "Moderator Board.";
  }

  @GetMapping("/admin")
  @PreAuthorize("hasRole('ADMIN')")
  public String adminAccess() {
    return "Admin Board.";
  }
}
