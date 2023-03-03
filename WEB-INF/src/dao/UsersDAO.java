package dao;

import connexion.JwtManager;
import io.jsonwebtoken.Claims;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UsersDAO {
    // Méthode pour vérifier si l'utilisateur existe dans la base de données
    public static boolean verifierUtilisateur(String token) {
        try {
            String login = JwtManager.getlogin(token);
            DS.getConnection();
            PreparedStatement ps = DS.connection.prepareStatement("select pwd from users where login=?");
            ps.setString(1, login);
            ResultSet rs = DS.executeQuery(ps);
            if(rs.next()){
                Claims test = JwtManager.decodeJWT(token, rs.getString("pwd"));
            }
            DS.closeConnection();
            System.out.println("All is ok ! Authentification");
            return true;
        } catch (Exception e) {
            System.out.println("ERREUR 3\n" + e.getMessage());
            return false;
        }
    }

    public static boolean isPresent(String login, String password) {
        try {
            DS.getConnection();
            PreparedStatement ps = DS.connection.prepareStatement("select * from users where login=? AND pwd=?");
            ps.setString(1, login);
            ps.setString(2, password);
            ResultSet rs = DS.executeQuery(ps);
            DS.closeConnection();
            System.out.println("All is ok !");
            return rs.next();
        } catch (SQLException e) {
            System.out.println("ERREUR 3\n" + e.getMessage());
            throw new RuntimeException(e);
        }
    }
}
