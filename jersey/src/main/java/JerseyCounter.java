import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import javax.inject.Singleton;
import javax.ws.rs.*;
import javax.ws.rs.core.*;

import java.util.Map;

@Singleton
@Path("counter")
public class JerseyCounter {

    private int counter = 0;
    private static final ObjectMapper mapper = new ObjectMapper();

    @GET
    @Produces("application/json")
    public Response getCounter() throws JsonProcessingException {

        ObjectNode json = mapper.createObjectNode();

        json.put("date", java.time.Instant.now().toString());
        json.put("value", counter);

        String response = mapper.writeValueAsString(json);

        return Response.status(200).entity(response).build();
    }

    @POST
    @Produces("text/html; charset=UTF-8")
    public Response postCounter() {
        counter += 1;
        String response = "<h1>Значение счетчика увеличено</h1>";
        return Response.status(200).entity(response).build();
    }

    @DELETE
    @Produces("text/html; charset=UTF-8")
    public Response deleteCounter(@Context HttpHeaders headers) {

        String value = headers.getHeaderString("Subtraction-Value");

        if (value != null){
            try {
                counter = counter - Integer.parseInt(value);
                String response = "<h1>Значение счетчика уменьшено</h1>";
                return Response.status(200).entity(response).build();
            }
            catch (NumberFormatException e) {
                String response = "<h1>Значение заголовка Subtraction-Value должно быть числом</h1>";
                return Response.status(400).entity(response).build();
            }
        }
        else {
            String response = "<h1>Нет заголовка Subtraction-Value</h1>";
            return Response.status(400).entity(response).build();
        }
    }

    @Path("clear")
    @POST
    @Produces("text/html; charset=UTF-8")
    public Response postClear(@Context HttpHeaders headers) {

        String cookieName = "hh-auth";
        int cookieLen = 10;
        boolean cookieFlag = false;

        Map<String, Cookie> cookies = headers.getCookies();

        for (Map.Entry<String, Cookie> cookie : cookies.entrySet()) {
            if (cookie.getKey().equals(cookieName) && cookie.getValue().getValue().length() > cookieLen) {
                cookieFlag = true;
                break;
            }
        }

        if (cookieFlag) {
            counter = 0;
            String response = "<h1>Значение счетчика обнулено</h1>";
            return Response.status(200).entity(response).build();
        }
        else
        {
            return Response.status(403).build();
        }
    }
}
