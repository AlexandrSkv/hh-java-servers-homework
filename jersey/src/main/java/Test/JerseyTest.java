package Test;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.eclipse.jetty.server.Server;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;

import java.util.Map;
import java.util.concurrent.*;

import static org.junit.Assert.assertEquals;

public class JerseyTest {

    private static Server server;

    @BeforeClass
    public static void beforeAllTests() throws Exception {
        int port = 8081;
        server = App.JerseyApplication.createServer(port);
        server.start();
    }

    @AfterClass
    public static void AfterTests() throws Exception {
        server.stop();
    }

    @Test
    public void LoadTest() throws InterruptedException, IOException {
        int count = 1000;
        String url = "http://localhost:8081/counter";
        RestTemplate restTemplate = new RestTemplate();
        ExecutorService executor = Executors.newFixedThreadPool(4);

        for (int i = 0; i < count; i++) {
            executor.submit(() -> restTemplate.postForObject(url, null, String.class));
        }

        executor.shutdown();
        executor.awaitTermination(10, TimeUnit.SECONDS);

        String response = restTemplate.getForObject(url, String.class);
        
        ObjectMapper mapper = new ObjectMapper();
        Map<?, ?> map = mapper.readValue(response, Map.class);
        int value = (int) map.get("value");

        assertEquals(count, value);
    }
}
