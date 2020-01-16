package sample;

import java.sql.*;

public class jdbc implements DatabaseConnectable{

    private static Connection con;
    private static Statement stmt;
    private static ResultSet rs;


    public ResultSet runQuery(String query) throws SQLException {
        try {
            // database parameters
            String url = "jdbc:mysql://localhost:3306/cr6_tamas_piski_schooldatabase?useSSL=false";
            String user = "root";
            String password = "admin";

            // create a connection to the database
            con = DriverManager.getConnection(url, user, password);

            // create statement
            stmt = con.createStatement();

            // creating result object or updating
            if (query.contains("UPDATE")) stmt.executeUpdate(query);
            else rs = stmt.executeQuery(query);
            return rs;


        } catch (SQLException e) {
            System.out.println(e.getMessage());

        } finally {
            con.close();
            stmt.close();
        }
        return rs;
    };
}
