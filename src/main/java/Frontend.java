import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.handler.AbstractHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

public class Frontend extends AbstractHandler implements Runnable {

    public AtomicInteger HandleCount = new AtomicInteger();
    private DAOUser dao;
    public Frontend() throws Exception {
    dao=new DAOUser("jdbc:mysql://localhost/javausers","root","1234");
    }



    public void handle(String urlPath,
                       Request request,
                       HttpServletRequest httpServletRequest,
                       HttpServletResponse httpServletResponse)throws IOException, ServletException {
        httpServletResponse.setContentType("text/html;charset=utf-8");
        Integer userId;
        String username;
        PrintWriter pageWriter = httpServletResponse.getWriter();

        httpServletResponse.setStatus(HttpServletResponse.SC_OK);
        request.setHandled(true);
        HttpSession session = request.getSession();
        if (session.getAttribute("state") == null) {
            session.setAttribute("state", "start");
        }

        if (urlPath.equals("/") && session.getAttribute("state").equals("start")){
            String html =
                    "<form action=\"/login\">" +
                            " <label>Login: <input type=\"text\" name=\"username\" required></label>" +
                            "<input type=\"submit\" value=\"Send\">" +
                            "   <input type=\"reset\" value=\"Clean\">"+
                            "</form>";
            pageWriter.println(html);
        }
        if (urlPath.equals("/login") && session.getAttribute("state").equals("start")){

            username = request.getParameter("username");
            Optional<UserDataSet> user = null;
            try {
                user = Optional.ofNullable(dao.get(username));
            } catch (SQLException e) {
                e.printStackTrace();
            }

            userId = user.map(UserDataSet::getId).orElse(-1);
            if (userId == -1){
                String html = "<div>"+ "No user is found" + "</div>" +
                        "<a href=\"/\">Autorization</a>";
                pageWriter.println(html);
            } else {
                session.setAttribute("state", "authorized");
                session.setAttribute("userId", userId);
                session.setAttribute("username", username);
                httpServletResponse.sendRedirect("/");
            }
        }
        if (urlPath.equals("/") && session.getAttribute("state").equals("authorized")) {
            String html = "<div>Hello, " + session.getAttribute("username") +
                    " ! Твой user id: " +
                    session.getAttribute("userId") +
                    " </div>" +
                    "<a href=\"/logout\">Logout</a>";
            pageWriter.println(html);
        }
        if (urlPath.equals("/logout") && session.getAttribute("state").equals("authorized")){
            session.setAttribute("state", "start");
            httpServletResponse.sendRedirect("/");
        }
        HandleCount.incrementAndGet();
    }

    public void run(){
        while(true){
            System.out.println(HandleCount);
            try{
                Thread.sleep(10000);
            } catch(InterruptedException e){
                e.printStackTrace();
            }
        }
    }
}

