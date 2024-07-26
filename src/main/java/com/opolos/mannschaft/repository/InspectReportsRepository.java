package com.opolos.mannschaft.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.opolos.mannschaft.model.InspectionReports;



public interface InspectReportsRepository extends JpaRepository<InspectionReports, Long>  {

    // Iterable<InspectionReports> findByReport_namrContaining(String report_namr);

    @Query("SELECT r FROM InspectionReports r WHERE r.client_id = :clientId ORDER BY test_date DESC")
    List<InspectionReports> findByCustomCriteria(@Param("clientId") String clientId);

    @Query("SELECT u FROM InspectionReports u WHERE " +
            "LOWER(u.cal_year) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
            "LOWER(u.client) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
            "LOWER(u.client_id) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
            "LOWER(u.application_type) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
            "LOWER(u.identification_number) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
            "LOWER(u.test_according_to) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
            "LOWER(u.manufucturer) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
            "LOWER(u.department) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
            "LOWER(u.measuring_length) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
            "LOWER(u.profile) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
            "LOWER(u.report_url) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
            "LOWER(u.report_namr) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
            "LOWER(u.type_number) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
            "LOWER(u.serial_number) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
            "LOWER(u.device_type) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
            "LOWER(u.cross_section) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
            "LOWER(u.test_date) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
            "LOWER(u.next_test_date) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
            "LOWER(u.examiner) LIKE LOWER(CONCAT('%', :searchTerm, '%'))")
    List<InspectionReports> search(@Param("searchTerm") String searchTerm);


    @Query("SELECT DISTINCT y.cal_year FROM InspectionReports y ORDER BY y.cal_year DESC")
    List<String> findDistinctYear();

    @Query("SELECT DISTINCT y.client FROM InspectionReports y")
    List<String> findDistinctClient();

    @Query("SELECT DISTINCT y.department FROM InspectionReports y")
    List<String> findDistinctDepartment();

    @Query("SELECT DISTINCT y.examiner FROM InspectionReports y")
    List<String> findDistinctExaminer();


}
