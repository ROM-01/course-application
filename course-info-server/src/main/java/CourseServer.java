import com.pluralsight.courseinfo.repository.CourseRepository;
import com.pluralsight.courseinfo.server.CourseResource;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.util.Properties;

public class CourseServer {

//    static {
//        LogManager.getLogManager().reset();
//        SLF4JBridgeHandler.install();
//    }
    //not working


    private static final Logger LOG = LoggerFactory.getLogger(CourseServer.class);
    private static final String BASE_URI = "http://localhost:8080/";

    public static void main(String... args) {
        String databaseFilename = loadDatabaseFilename();
        LOG.info("Starting HTTP server with database {}", databaseFilename);
        CourseRepository courseRepository = CourseRepository.openCourseRepository(databaseFilename);
        ResourceConfig config = new ResourceConfig().register(new CourseResource(courseRepository));

        GrizzlyHttpServerFactory.createHttpServer(URI.create(BASE_URI), config);
    }

    private static String loadDatabaseFilename() {

        try (InputStream propertiesStream =
                CourseServer.class.getResourceAsStream("/server.properties")) {
            Properties properties = new Properties();
            properties.load(propertiesStream);
            return properties.getProperty("course-info.database");
        } catch (IOException e) {
            throw new IllegalStateException("Could not load database filename");
        }
    }
}
