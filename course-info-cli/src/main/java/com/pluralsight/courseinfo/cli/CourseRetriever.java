package com.pluralsight.courseinfo.cli;

import com.pluralsight.courseinfo.cli.service.CourseRetrieverService;
import com.pluralsight.courseinfo.cli.service.CourseStorageService;
import com.pluralsight.courseinfo.cli.service.PluralsightCourse;
import com.pluralsight.courseinfo.repository.CourseRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

import static java.util.function.Predicate.not;

public class CourseRetriever {
    private static final Logger LOG = LoggerFactory.getLogger(CourseRetriever.class);

    //Remember "args" is just a name for the String[] type
    public static void main(String[] args) {
        LOG.info("CourseRetriever starting");
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
        CourseRetrieverService courseRetrieverService = new CourseRetrieverService();
        CourseRepository courseRepository = CourseRepository.openCourseRepository("./courses.db");
        CourseStorageService courseStorageService = new CourseStorageService(courseRepository);

        List<PluralsightCourse> coursesToStore = courseRetrieverService.getCoursesFor(authorId)
                .stream()
                .filter(not(PluralsightCourse::isRetired))
                //.filter(course -> !course.isRetired()) < this is a lambda expression
                .toList();

        LOG.info("Retrieved the following {} courses {}", coursesToStore.size(), coursesToStore);
        courseStorageService.storePluralsightCourses(coursesToStore);
        LOG.info("Courses successfully stored");
    }
}
