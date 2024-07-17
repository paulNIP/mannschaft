package com.opolos.mannschaft.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.opolos.mannschaft.model.InspectionReports;

public interface InspectReportsRepository extends JpaRepository<InspectionReports, Long>  {

    // Iterable<InspectionReports> findByReport_namrContaining(String report_namr);

    @Query("SELECT * FROM InspectionReports r WHERE r.clientid = :clientid GROUP BY r.test_date DESC")
    List<Object[]> findGroupedByField(@Param("clientid") String clientid);
    
}
