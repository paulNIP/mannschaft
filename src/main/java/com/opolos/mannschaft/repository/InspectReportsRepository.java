package com.opolos.mannschaft.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.opolos.mannschaft.model.InspectionReports;



public interface InspectReportsRepository extends JpaRepository<InspectionReports, Long>  {

    // Iterable<InspectionReports> findByReport_namrContaining(String report_namr);
    @Query( 
        nativeQuery = true, 
        value 
        = "SELECT * FROM mannschaft_inspection_reports where clientid=:clientId GROUPBY test_date DESC") 
       Optional<InspectionReports> findReportByBarCode(@Param("clientId") String clientId); 

}
