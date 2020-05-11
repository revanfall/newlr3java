import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.function.Function;

public class DAOUser implements IDAOUser {
    Executor db;
    public DAOUser(String url,String log,String pass) throws Exception {
        db=new Executor(url,log,pass);
    }
    @Override
    public UserDataSet get(String name) throws SQLException {
        String query="select * from usertable where Name="+"'"+name+"'";
        Function<ResultSet,UserDataSet> queryMapped=(ResultSet)->{
            try {
                ResultSet.first();
                return new UserDataSet(ResultSet.getInt("id"),ResultSet.getString("Name"));
            } catch (SQLException e) {
                e.printStackTrace();
                return null;
            }
        };
        return  db.execQuery(query,queryMapped);
    }
}
