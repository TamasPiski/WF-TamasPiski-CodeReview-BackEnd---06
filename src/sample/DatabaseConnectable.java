package sample;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface DatabaseConnectable {
    ResultSet runQuery(String query) throws SQLException;
}

