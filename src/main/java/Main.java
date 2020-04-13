import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.ContextHandler;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.server.session.SessionHandler;

public class Main {
    public static void main(String[] args) throws Exception {
        Server server = new Server(9090);

        ContextHandler staticFilesHandler = new ContextHandler();
        staticFilesHandler.setContextPath("/resources");

        ResourceHandler resourceHandler = new ResourceHandler();
        resourceHandler.setDirectoriesListed(true);
        resourceHandler.setResourceBase("C:\\Users\\Ilya\\Downloads\\springboot\\newlr3java\\src\\main\\resources");

        staticFilesHandler.setHandler(resourceHandler);

        SessionHandler sessionHandler = new SessionHandler();
        HelloServer sessionServer = new HelloServer();
        sessionHandler.setHandler(sessionServer);

        Frontend frontend = new Frontend();
        Thread thread = new Thread(frontend);
        thread.start();

        HandlerList handlers = new HandlerList();
        handlers.setHandlers(new Handler[] {staticFilesHandler, sessionHandler,  frontend});
        server.setHandler(handlers);

        server.start();
        server.join();
    }
}

