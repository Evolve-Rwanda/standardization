package com.example.springauth.models.jpa;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;


@Entity
public class PreBidMeeting {

    @Id
    @Column(length = 50)
    private String id;

    @Column(columnDefinition = "TIMESTAMP")
    private String dateAndTime;

    @Column(length = 50)
    private String meetingType;

    @Column(length = 255)
    private String venueOrLink;

    @Column(length = 10)
    private String isAttendanceMandatory;

}
