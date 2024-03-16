package com.pluralsight.courseinfo.cli;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CourseRetriever {
    private static final Logger LOG = LoggerFactory.getLogger(CourseRetriever.class);

    //Remember "args" is just a name for the String[] type
    public static void main(String[] args) {
        LOG.info("Hello World and just started");
        if (args.length == 0) {
            LOG.warn("Please provide an author name as first argument.");
            return;
        }

        //Catches errors or rare causes when normal code wouldn't run and outputs a string
        try {
            retrieveCourses(args[0]);
            //what does the e mean? (prob just a name and Exception is the type)
        } catch (Exception e) {
            LOG.error("Unexpected error", e);
        }
    }

    //here is a method with a data type string, name authorID, and prints out a statement
    private static void retrieveCourses(String authorId){
        LOG.info("Retrieving courses for author '{}'", authorId);
    }
}
