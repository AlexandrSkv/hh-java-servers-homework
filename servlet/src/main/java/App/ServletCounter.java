package App;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

import static App.ServletApplication.counter;

public class ServletCounter extends HttpServlet {

    //private int counter = 0;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setHeader("Content-Type", "text/html; charset=UTF-8");
        PrintWriter writer = response.getWriter();
        switch (request.getRequestURI()) {
            case ("/counter"): {
                response.setStatus(HttpServletResponse.SC_OK);
                writer.print("<h1>Текущие состояние счетчика: " + counter.getCounter() + "</h1>");
                writer.flush();
                break;
            }
            case ("/counter/clear"):
            {
                response.setHeader("Allow" , "POST");
                response.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
                writer.flush();
                break;
            }
            default: {
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setHeader("Content-Type", "text/html; charset=UTF-8");
        PrintWriter writer = response.getWriter();
        switch (request.getRequestURI()) {
            case ("/counter"):
            {
                counter.increaseCounter();

                response.setStatus(HttpServletResponse.SC_OK);
                writer.print("<h1>Значение счетчика увеличено</h1>");
                writer.flush();
                break;
            }
            case ("/counter/clear"):
            {
                counter.deleteCounter();

                response.setStatus(HttpServletResponse.SC_OK);
                writer.print("<h1>Значение счетчика обнулено</h1>");
                writer.flush();
                break;
            }
            default:
            {
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
            }
        }
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setHeader("Content-Type", "text/html; charset=UTF-8");
        PrintWriter writer = response.getWriter();

        switch (request.getRequestURI()) {
            case ("/counter"):
            {
                String value = request.getHeader("Subtraction-Value");
                if (value != null) {
                    try {
                        counter.decreaseCounter(Integer.parseInt(value));
                        response.setStatus(HttpServletResponse.SC_OK);
                        writer.print("<h1>Значение счетчика уменьшено</h1>");
                        writer.flush();
                    } catch (NumberFormatException e){
                        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                        writer.print("<h1>Значение заголовка Subtraction-Value должно быть числом</h1>");
                        writer.flush();
                    }
                }
                else {
                    response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                    writer.print("<h1>Нет заголовка Subtraction-Value</h1>");
                    writer.flush();
                }
                break;
            }
            case ("/counter/clear"):
            {
                response.setHeader("Allow" , "POST");
                response.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
                break;
            }
            default:
            {
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
            }
        }
    }
}
