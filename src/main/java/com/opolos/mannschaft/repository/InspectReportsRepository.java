package com.opolos.mannschaft.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.opolos.mannschaft.model.InspectionReports;

public interface InspectReportsRepository extends JpaRepository<InspectionReports, Long>  {
    
}
