package App;

import javax.servlet.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ServletCoockieFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig){
    }
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        String cookieName = "hh-auth";
        int cookieLen = 10;
        boolean cookieFlag = false;

        HttpServletResponse httpResponse = (HttpServletResponse) response;
        HttpServletRequest httpRequest = (HttpServletRequest) request;

        final Cookie[] cookies = httpRequest.getCookies();

        if(cookies !=null) {
            for(Cookie c: cookies) {
                if(cookieName.equals(c.getName()) && c.getValue().length() > cookieLen) {
                    cookieFlag = true;
                    break;
                }
            }
        }

        if(cookieFlag) {
            chain.doFilter(request,response);
        }
        else {
            httpResponse.sendError(HttpServletResponse.SC_FORBIDDEN);
        }

    }

    @Override
    public void destroy() {

    }
}
