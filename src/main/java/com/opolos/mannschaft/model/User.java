package com.opolos.mannschaft.model; 

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "users", 
    uniqueConstraints = { 
      @UniqueConstraint(columnNames = "username"),
      @UniqueConstraint(columnNames = "email") 
    })
public class User {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @NotBlank
  @Size(max = 50)
  private String client_name;

  @NotBlank
  @Size(max = 20)
  private String username;

  @NotBlank
  @Size(max = 50)
  @Email
  private String email;

  @NotBlank
  @Size(max = 120)
  private String password;

  @NotBlank
  @Size(max = 120)
  private String client_id;

  @NotBlank
  @Size(max = 120)
  private String street;

  @NotBlank
  @Size(max = 120)
  private String address;

  @NotBlank
  @Size(max = 120)
  private String website;

  @NotBlank
  @Size(max = 120)
  private String contact_one;

  @NotBlank
  @Size(max = 120)
  private String contact_two;

  @NotBlank
  @Size(max = 120)
  private String instance_name;



  @ManyToMany(fetch = FetchType.LAZY)
  @JoinTable(  name = "user_roles", 
        joinColumns = @JoinColumn(name = "user_id"), 
        inverseJoinColumns = @JoinColumn(name = "role_id"))
  private Set<Role> roles = new HashSet<>();

  public User(String username2, String email2, String string, String client_id2, String street2, String address2, String website2, String contact_one2, String contact_two2, Set<String> roless, String instance_name2) {
  }



  public User(String client_name, String username, String email, String password, String client_id, 
  String street, String address, String website, String contact_one, String contact_two,String instance_name, Set<Role> roles) {
    this.client_name = client_name;
    this.username = username;
    this.email = email;
    this.password = password;
    this.client_id = client_id;
    this.street = street;
    this.address = address;
    this.website = website;
    this.contact_one = contact_one;
    this.contact_two = contact_two;
    this.roles = roles;
    this.instance_name = instance_name;
  }


  public String getInstance_name() {
    return this.instance_name;
  }

  public void setInstance_name(String instance_name) {
    this.instance_name = instance_name;
  }


  public String getClient_id() {
    return this.client_id;
  }

  public void setClient_id(String client_id) {
    this.client_id = client_id;
  }

  public String getStreet() {
    return this.street;
  }

  public void setStreet(String street) {
    this.street = street;
  }

  public String getAddress() {
    return this.address;
  }

  public void setAddress(String address) {
    this.address = address;
  }

  public String getWebsite() {
    return this.website;
  }

  public void setWebsite(String website) {
    this.website = website;
  }

  public String getContact_one() {
    return this.contact_one;
  }

  public void setContact_one(String contact_one) {
    this.contact_one = contact_one;
  }

  public String getContact_two() {
    return this.contact_two;
  }

  public void setContact_two(String contact_two) {
    this.contact_two = contact_two;
  }



  public String getClient_name() {
    return this.client_name;
  }

  public void setClient_name(String client_name) {
    this.client_name = client_name;
  }


  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public Set<Role> getRoles() {
    return roles;
  }

  public void setRoles(Set<Role> roles) {
    this.roles = roles;
  }
}