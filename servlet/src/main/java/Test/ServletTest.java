package Test;

import org.eclipse.jetty.server.Server;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertEquals;

public class ServletTest {

    private static Server server;

    @BeforeClass
    public static void beforeAllTests() throws Exception {
        int port = 8081;
        server = App.ServletApplication.createServer(port);
        server.start();
    }

    @AfterClass
    public static void AfterTests() throws Exception {
        server.stop();
    }

    @Test
    public void LoadTest() throws InterruptedException, IOException {
        Integer count = 1000;
        String url = "http://localhost:8081/counter";
        RestTemplate restTemplate = new RestTemplate();
        ExecutorService executor = Executors.newFixedThreadPool(4);

        for (int i = 0; i < count; i++) {
            executor.submit(() -> restTemplate.postForObject(url, null, String.class));
        }

        executor.shutdown();
        executor.awaitTermination(10, TimeUnit.SECONDS);

        String response = restTemplate.getForObject(url, String.class);

        assert(response.contains(count.toString()));
    }
}
