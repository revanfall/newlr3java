import org.eclipse.jetty.server.Authentication;

import java.sql.SQLException;

public interface IDAOUser {
    UserDataSet get(String name) throws SQLException;
}
