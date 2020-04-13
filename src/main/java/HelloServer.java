import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.handler.AbstractHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class HelloServer extends AbstractHandler {
    public void handle(String s, Request request, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws IOException, ServletException {
        httpServletResponse.setContentType("text/html;chatset=utf-8");
        httpServletResponse.setStatus(HttpServletResponse.SC_OK);
        //request.setHandled(true);

        String html="<!DOCTYPE html>\n"+
                "<html>\n" +
                "<head>\n" +
                "<link rel=\"stylesheet\" type=\"text/css\" href=\"C:\\Users\\Ilya\\Desktop\\main.css\"/>\n" +
                "</head>\n" +
                "<body>\n" +
                "<h1>Hello Server</h1>\n"+
                "<h2>Files</h2>\n" +
                "<p>Pictures</p>\n" +
                "</body>\n" +
                "</html>";
        httpServletResponse.getWriter().println(html);
        httpServletResponse.getWriter().println("<img src=\"resources/day-2-grivni-2-768x578.jpg\"/>\n" );
    }
}
