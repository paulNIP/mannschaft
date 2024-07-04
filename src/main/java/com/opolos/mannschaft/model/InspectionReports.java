package com.opolos.mannschaft.model;

import jakarta.persistence.*;

@Entity
@Table(name = "mannschaft_inspection_reports")
public class InspectionReports {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE)
  private long id;


  @Column(name = "calYear")
  private String cal_year;

  @Column(name = "client")
  private String client;

  @Column(name = "clientID")
  private String client_id;

  
  @Column(name = "applicationType")
  private String application_type;

  @Column(name = "identificationNumber")
  private String identification_number;

  @Column(name = "testAccordingTo")
  private String test_according_to;

  @Column(name = "manufucturer")
  private String manufucturer;

  @Column(name = "department")
  private String department;

  @Column(name = "measuringLength")
  private String measuring_length;

  @Column(name = "profile")
  private String profile;

  @Column(name = "reportUrl")
  private String report_url;

  @Column(name = "reportName")
  private String report_namr;

  @Column(name = "typeNumber")
  private String type_number;

  @Column(name = "serialNumber")
  private String serial_number;

  @Column(name = "deviceType")
  private String device_type;

  @Column(name = "crossSection")
  private String cross_section;

  @Column(name = "test_date")
  private String test_date;

  @Column(name = "next_test_date")
  private String next_test_date;

  @Column(name = "examiner")
  private String examiner;

  @Column(name = "published")
  private boolean published;


  public InspectionReports() {
  }


  public InspectionReports(String cal_year, String client, String client_id, String application_type, String identification_number, String test_according_to, String manufucturer, String department, String measuring_length, String profile, String report_url, String report_namr, String type_number, String serial_number, String device_type, String cross_section, String test_date, String next_test_date, String examiner, boolean published) {

    this.cal_year = cal_year;
    this.client = client;
    this.client_id = client_id;
    this.application_type = application_type;
    this.identification_number = identification_number;
    this.test_according_to = test_according_to;
    this.manufucturer = manufucturer;
    this.department = department;
    this.measuring_length = measuring_length;
    this.profile = profile;
    this.report_url = report_url;
    this.report_namr = report_namr;
    this.type_number = type_number;
    this.serial_number = serial_number;
    this.device_type = device_type;
    this.cross_section = cross_section;
    this.test_date = test_date;
    this.next_test_date = next_test_date;
    this.examiner = examiner;
    this.published = published;
  }


  public long getId() {
    return this.id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public String getCal_year() {
    return this.cal_year;
  }

  public void setCal_year(String cal_year) {
    this.cal_year = cal_year;
  }

  public String getClient() {
    return this.client;
  }

  public void setClient(String client) {
    this.client = client;
  }

  public String getClient_id() {
    return this.client_id;
  }

  public void setClient_id(String client_id) {
    this.client_id = client_id;
  }

  public String getApplication_type() {
    return this.application_type;
  }

  public void setApplication_type(String application_type) {
    this.application_type = application_type;
  }

  public String getIdentification_number() {
    return this.identification_number;
  }

  public void setIdentification_number(String identification_number) {
    this.identification_number = identification_number;
  }

  public String getTest_according_to() {
    return this.test_according_to;
  }

  public void setTest_according_to(String test_according_to) {
    this.test_according_to = test_according_to;
  }

  public String getManufucturer() {
    return this.manufucturer;
  }

  public void setManufucturer(String manufucturer) {
    this.manufucturer = manufucturer;
  }

  public String getDepartment() {
    return this.department;
  }

  public void setDepartment(String department) {
    this.department = department;
  }

  public String getMeasuring_length() {
    return this.measuring_length;
  }

  public void setMeasuring_length(String measuring_length) {
    this.measuring_length = measuring_length;
  }

  public String getProfile() {
    return this.profile;
  }

  public void setProfile(String profile) {
    this.profile = profile;
  }

  public String getReport_url() {
    return this.report_url;
  }

  public void setReport_url(String report_url) {
    this.report_url = report_url;
  }

  public String getReport_namr() {
    return this.report_namr;
  }

  public void setReport_namr(String report_namr) {
    this.report_namr = report_namr;
  }

  public String getType_number() {
    return this.type_number;
  }

  public void setType_number(String type_number) {
    this.type_number = type_number;
  }

  public String getSerial_number() {
    return this.serial_number;
  }

  public void setSerial_number(String serial_number) {
    this.serial_number = serial_number;
  }

  public String getDevice_type() {
    return this.device_type;
  }

  public void setDevice_type(String device_type) {
    this.device_type = device_type;
  }

  public String getCross_section() {
    return this.cross_section;
  }

  public void setCross_section(String cross_section) {
    this.cross_section = cross_section;
  }

  public String getTest_date() {
    return this.test_date;
  }

  public void setTest_date(String test_date) {
    this.test_date = test_date;
  }

  public String getNext_test_date() {
    return this.next_test_date;
  }

  public void setNext_test_date(String next_test_date) {
    this.next_test_date = next_test_date;
  }

  public String getExaminer() {
    return this.examiner;
  }

  public void setExaminer(String examiner) {
    this.examiner = examiner;
  }

  public boolean isPublished() {
    return this.published;
  }

  public boolean getPublished() {
    return this.published;
  }

  public void setPublished(boolean published) {
    this.published = published;
  }

  

    
}


