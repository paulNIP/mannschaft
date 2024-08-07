package com.opolos.mannschaft.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.opolos.mannschaft.model.InspectionReports;
import com.opolos.mannschaft.repository.InspectReportsRepository;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api")
public class ReportsController {


    
	@Autowired
	InspectReportsRepository inspectReportsRepository;

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

	@GetMapping("/reports/{id}")
	public ResponseEntity<InspectionReports> getTutorialById(@PathVariable("id") long id) {
		Optional<InspectionReports> reportData = inspectReportsRepository.findById(id);

		if (reportData.isPresent()) {
			return new ResponseEntity<>(reportData.get(), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	// @PostMapping("/tutorials")
	// public ResponseEntity<Tutorial> createTutorial(@RequestBody Tutorial tutorial) {
	// 	try {
	// 		Tutorial _tutorial = tutorialRepository
	// 				.save(new Tutorial(tutorial.getTitle(), tutorial.getDescription(), false));
	// 		return new ResponseEntity<>(_tutorial, HttpStatus.CREATED);
	// 	} catch (Exception e) {
	// 		return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
	// 	}
	// }

	// @PutMapping("/tutorials/{id}")
	// public ResponseEntity<Tutorial> updateTutorial(@PathVariable("id") long id, @RequestBody Tutorial tutorial) {
	// 	Optional<Tutorial> tutorialData = tutorialRepository.findById(id);

	// 	if (tutorialData.isPresent()) {
	// 		Tutorial _tutorial = tutorialData.get();
	// 		_tutorial.setTitle(tutorial.getTitle());
	// 		_tutorial.setDescription(tutorial.getDescription());
	// 		_tutorial.setPublished(tutorial.isPublished());
	// 		return new ResponseEntity<>(tutorialRepository.save(_tutorial), HttpStatus.OK);
	// 	} else {
	// 		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	// 	}
	// }

	// @DeleteMapping("/tutorials/{id}")
	// public ResponseEntity<HttpStatus> deleteTutorial(@PathVariable("id") long id) {
	// 	try {
	// 		tutorialRepository.deleteById(id);
	// 		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	// 	} catch (Exception e) {
	// 		return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
	// 	}
	// }

	// @DeleteMapping("/tutorials")
	// public ResponseEntity<HttpStatus> deleteAllTutorials() {
	// 	try {
	// 		tutorialRepository.deleteAll();
	// 		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	// 	} catch (Exception e) {
	// 		return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
	// 	}

	// }

	// @GetMapping("/tutorials/published")
	// public ResponseEntity<List<Tutorial>> findByPublished() {
	// 	try {
	// 		List<Tutorial> tutorials = tutorialRepository.findByPublished(true);

	// 		if (tutorials.isEmpty()) {
	// 			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	// 		}
	// 		return new ResponseEntity<>(tutorials, HttpStatus.OK);
	// 	} catch (Exception e) {
	// 		return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
	// 	}
	// }

}
