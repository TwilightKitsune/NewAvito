package com.kitsune.NewAvito.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.text.SimpleDateFormat;
import java.util.Date;

@Entity
public class Complaint {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private Date date;

    private String text;

    private Integer idAnnouncement;

    private Integer idUser;

    public Integer getId() {
        return id;
    }

    public String getDate() {
        SimpleDateFormat formatForDate = new SimpleDateFormat("E dd MMMM yyyy H:mm");
        return formatForDate.format(date);
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Integer getIdAnnouncement() {
        return idAnnouncement;
    }

    public void setIdAnnouncement(Integer idAnnouncement) {
        this.idAnnouncement = idAnnouncement;
    }

    public Integer getIdUser() {
        return idUser;
    }

    public void setIdUser(Integer idUser) {
        this.idUser = idUser;
    }
}
