package util;

import java.util.Date;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;

import io.quarkus.runtime.LaunchMode;
import io.quarkus.runtime.StartupEvent;
import model.EccEntity;

@ApplicationScoped
public class Startup {
    /**
     * This method is executed at the start of your application
     */
    public void start(@Observes StartupEvent evt) {
        // in DEV mode we seed some data
        if(LaunchMode.current() == LaunchMode.DEVELOPMENT) {
            EccEntity a = new EccEntity();
            a.task = "First item";
            a.persist();

            EccEntity b = new EccEntity();
            b.task = "Second item";
            b.completed = new Date();
            b.persist();
        }
    }
}
