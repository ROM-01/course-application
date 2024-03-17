package com.pluralsight.courseinfo.cli.service;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.time.Duration;
import java.time.LocalTime;

@JsonIgnoreProperties(ignoreUnknown = true) //tells Json to ignore other properties and just focus on the listed ones
public record PluralsightCourse(String id, String title, String duration, String contentURL, boolean isRetired) {
    //duration = "00:05:57"
    public long durationInMinutes(){
        return Duration.between(
                LocalTime.MIN,
                LocalTime.parse(duration())
        ).toMinutes();

    }
}
