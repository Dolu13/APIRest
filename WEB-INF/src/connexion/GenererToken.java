package connexion;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.io.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;



import dao.UsersDAO;
import io.jsonwebtoken.Claims;


@WebServlet("/users/token")
public class GenererToken extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
         // Récupération des paramètres login et pwd
        String login = request.getParameter("login");
        String password = request.getParameter("pwd");
        
         // Vérification si l'utilisateur existe dans la base de données et creation du token
         if(UsersDAO.isPresent(login,password)) {
             String token  = JwtManager.createJWT(login,password);
             response.getWriter().println("Succes de l'identification");
             response.getWriter().println("Votre token :Bearer "+ token);

 
             return;
         } else {
             // Affichage d'un message d'erreur si l'utilisateur n'est pas trouvé
             response.getWriter().println("Utilisateur inconnu.");
             response.getWriter().println(login);
             response.getWriter().println(password);

             return;
         }
        
    }
}
