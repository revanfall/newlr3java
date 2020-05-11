import java.sql.*;
import java.util.function.Function;

public class Executor {
    private Connection dbConnection;


    public Executor(String url, String user, String password)
            throws Exception
    {
        Driver driver = (Driver) Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();
        DriverManager.registerDriver(driver);
        this.dbConnection = DriverManager.getConnection(url, user, password);
    }
    public <T> T execQuery(
                           String query,
                           Function<ResultSet,T> handler)
            throws SQLException {
        Statement stmt = dbConnection.createStatement();
        stmt.execute(query);
        ResultSet result = stmt.getResultSet();
        T value = handler.apply(result);
        result.close();
        stmt.close();

        return value;
    }
}
