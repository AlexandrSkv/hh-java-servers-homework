import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletHandler;

public class ServletApplication {

  private static Server createServer(int port) {
    Server server = new Server(port);
    ServletHandler servletHandler = new ServletHandler();
    servletHandler.addServletWithMapping(ServletCounter.class, "/counter/*");
    servletHandler.addFilterWithMapping(ServletCoockieFilter.class, "/counter/clear", 0);
    server.setHandler(servletHandler);
    return server;
  }

  public static void main(String[] args) throws Exception {
    int port = 8081;
    Server server = createServer(port);
    server.start();
    server.join();
    // run, Jetty, run!
  }
}
