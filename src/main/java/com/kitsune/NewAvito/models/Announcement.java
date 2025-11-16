package com.kitsune.NewAvito.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.text.SimpleDateFormat;
import java.util.Date;

@Entity
public class Announcement {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private Integer idUsers;

    private String title;

    private String name;

    private String address;

    private String characteristics;

    private String description;

    private Double price;

    private String telephone;

    private String otherContacts;

    private String type;

    private String tags;

    private Boolean verifiedByAdmin;

    private Boolean presenceOfComplaints;

    private Integer numberOfComplaints;

    private Date dat;

    public Integer getNumberOfComplaints() {
        return numberOfComplaints;
    }

    public void setNumberOfComplaints(Integer numberOfComplaints) {
        this.numberOfComplaints = numberOfComplaints;
    }

    public Boolean getPresenceOfComplaints() {
        return presenceOfComplaints;
    }

    public void setPresenceOfComplaints(Boolean presenceOfComplaints) {
        this.presenceOfComplaints = presenceOfComplaints;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Boolean getVerifiedByAdmin() {
        return verifiedByAdmin;
    }

    public void setVerifiedByAdmin(Boolean verifiedByAdmin) {
        this.verifiedByAdmin = verifiedByAdmin;
    }

    public void setDat(Date dat) {
        this.dat = dat;
    }

    public Integer getId() {
        return id;
    }

    public String getDat() {
        SimpleDateFormat formatForDate = new SimpleDateFormat("E dd MMMM yyyy H:mm");
        return formatForDate.format(dat);
    }

    public Integer getIdUsers() {
        return idUsers;
    }

    public void setIdUsers(Integer idUsers) {
        this.idUsers = idUsers;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCharacteristics() {
        return characteristics;
    }

    public void setCharacteristics(String characteristics) {
        this.characteristics = characteristics;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getOtherContacts() {
        return otherContacts;
    }

    public void setOtherContacts(String otherContacts) {
        this.otherContacts = otherContacts;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }
}
