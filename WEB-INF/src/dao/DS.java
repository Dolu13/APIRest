package dao;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public  class DS {

    public static Connection connection = null;

    public static void getConnection() {
        try {

            Class.forName("org.postgresql.Driver");
            String url = "jdbc:postgresql://localhost:5432/postgres";
            String login = "postgres";
            String mdp = "user";
            Connection con = DriverManager.getConnection(url,login,mdp);
            DS.connection = con;
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public static ResultSet executeQuery(String query) {
        try {
            Statement statement = DS.connection.createStatement();
            return statement.executeQuery(query);
        } catch(Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static ResultSet executeQuery(PreparedStatement ps){
        try {
            return ps.executeQuery();
        } catch(Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void closeConnection() {

        try {
            DS.connection.close();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public static void executeUpdate(String update) {
        try {
            Statement statement = DS.connection.createStatement();
            statement.execute(update);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void executeUpdate(PreparedStatement ps) {
        try {
            int i =  ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}