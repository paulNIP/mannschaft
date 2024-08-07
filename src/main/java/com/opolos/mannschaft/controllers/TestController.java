package com.opolos.mannschaft.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.google.firebase.messaging.FirebaseMessagingException;
import com.opolos.mannschaft.dto.AllDevicesNotificationRequest;
import com.opolos.mannschaft.dto.DeviceNotificationRequest;
import com.opolos.mannschaft.dto.NotificationSubscriptionRequest;
import com.opolos.mannschaft.dto.TopicNotificationRequest;
import com.opolos.mannschaft.model.InspectionReports;
import com.opolos.mannschaft.model.User;
import com.opolos.mannschaft.repository.InspectReportsRepository;
import com.opolos.mannschaft.repository.UserRepository;
import com.opolos.mannschaft.services.NotificationService;
import com.opolos.mannschaft.services.PdfService;

import jakarta.validation.Valid;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/test")
public class TestController {

  @Autowired
	InspectReportsRepository inspectReportsRepository;

  @Autowired
  UserRepository userRepository;

  @Autowired
  PdfService pdfService;

  @Autowired
  NotificationService notificationService;

  
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


	@GetMapping("/barcodescan_results")
    public List<InspectionReports> getRecordsByCustomCriteria(@RequestParam String fieldValue) {
        return pdfService.getRecordsByCustomCriteria(fieldValue);
    }

  @GetMapping("/search_reports")
  public List<InspectionReports> search(@RequestParam String searchTerm) {
      return pdfService.searchReports(searchTerm);
  }

  @GetMapping("/distinct-report-year")
    public List<String> getDistinctYear() {
        return pdfService.getDistinctYear();
    }

    @GetMapping("/distinct-report-client")
    public List<String> getDistinctClient() {
        return pdfService.getDistinctClient();
    }

    @GetMapping("/distinct-report-examiner")
    public List<String> getDistinctExaminer() {
        return pdfService.getDistinctExaminer();
    }

    @GetMapping("/distinct-report-department")
    public List<String> getDistinctDepartment() {
        return pdfService.getDistinctDepartment();
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

  

    @PostMapping("/send-to-device")
    public ResponseEntity<String> sendNotification(@RequestBody @Valid DeviceNotificationRequest request) {
        try {
            notificationService.sendNotificationToDevice(request);
            return ResponseEntity.ok("Notification sent successfully.");
        } catch (FirebaseMessagingException | ExecutionException | InterruptedException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to send notification.");
        }
    }

    @PostMapping("/send-to-topic")
    public ResponseEntity<String> sendNotificationToTopic(@RequestBody @Valid TopicNotificationRequest request) {
        try {
            notificationService.sendPushNotificationToTopic(request);
            return ResponseEntity.ok("Notification sent successfully.");
        } catch (FirebaseMessagingException | ExecutionException | InterruptedException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to send notification.");
        }
    }

    @PostMapping("/send-to-all")
    public ResponseEntity<String> sendNotificationToAll(@RequestBody @Valid AllDevicesNotificationRequest request) {
        try {
            notificationService.sendMulticastNotification(request);
            return ResponseEntity.ok("Multicast notification sent successfully.");
        } catch (FirebaseMessagingException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to send multicast notification.");
        }
    }

    @PostMapping("/subscribe")
    public ResponseEntity<String> subscribeToTopic(@RequestBody @Valid NotificationSubscriptionRequest request) {
        try {
            notificationService.subscribeDeviceToTopic(request);
            return ResponseEntity.ok("Device subscribed to the topic successfully.");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to subscribe device to the topic.");
        }
    }

    @PostMapping("/unsubscribe")
    public ResponseEntity<String> unsubscribeFromTopic(@RequestBody @Valid NotificationSubscriptionRequest request) {
        try {
            notificationService.unsubscribeDeviceFromTopic(request);
            return ResponseEntity.ok("Device unsubscribed from the topic successfully.");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to unsubscribe device from the topic.");
        }
    }
}
