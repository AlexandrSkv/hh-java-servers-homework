import javax.ws.rs.core.Application;
import java.util.Set;

public class ApplicationConfig extends Application {

    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> resources = new java.util.HashSet<>();
        resources.add(JerseyCounter.class);
        return resources;
    }
}
