package Utils;

import com.aventstack.extentreports.ExtentTest;
import org.apache.logging.log4j.core.*;
import org.apache.logging.log4j.core.appender.AbstractAppender;
import org.apache.logging.log4j.core.config.plugins.Plugin;
import org.apache.logging.log4j.core.config.plugins.PluginAttribute;
import org.apache.logging.log4j.core.config.plugins.PluginFactory;

import java.io.Serializable;

@Plugin(name = "ExtentReportsAppender", category = "Core", elementType = "appender")
public class ExtentReportsAppender extends AbstractAppender {

    protected ExtentReportsAppender(String name, Filter filter, Layout<? extends Serializable> layout) {
        super(name, filter, layout);
    }

    @Override
    public void append(LogEvent event) {
        try {
            ExtentTest test = ExtentManager.getTest();
            String message = event.getMessage().getFormattedMessage();
            System.out.println("ExtentReportsAppender called: " + message + ", test: " + (test != null ? "not null" : "null"));
            if (test != null) {
                org.apache.logging.log4j.Level level = event.getLevel();
                
                if (level.isMoreSpecificThan(org.apache.logging.log4j.Level.ERROR)) {
                    test.fail(message);
                } else if (level.isMoreSpecificThan(org.apache.logging.log4j.Level.WARN)) {
                    test.warning(message);
                } else if (level.isMoreSpecificThan(org.apache.logging.log4j.Level.INFO)) {
                    test.info(message);
                } else {
                    test.info(message);
                }
            }
        } catch (Exception e) {
            // Silent catch to prevent logging errors from breaking the tests
            System.err.println("Error appending to ExtentReports: " + e.getMessage());
        }
    }

    @PluginFactory
    public static ExtentReportsAppender createAppender(@PluginAttribute("name") String name) {
        return new ExtentReportsAppender(name, null, null);
    }
}
