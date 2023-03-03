package controleurs;


import com.fasterxml.jackson.databind.ObjectMapper;
import dao.CommandeDAO;
import dao.PizzaDAO;
import dao.UsersDAO;
import dto.Commande;
import dto.CommandePost;
import dto.Pizza;
import io.jsonwebtoken.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


@WebServlet("/commandes/*")
public class CommandeRestAPI extends HttpServlet{
    public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, java.io.IOException {
        res.setContentType("application/json;charset=UTF-8");
        PrintWriter out = res.getWriter();
        ObjectMapper objectMapper = new ObjectMapper();
        String info = req.getPathInfo();
        
        if (info == null || info.equals("/")) {
            Collection<Commande> models = CommandeDAO.findAll();
            String jsonstring = objectMapper.writeValueAsString(models);
            out.print(jsonstring);
            return;
        }
       String[] split = info.split("/");

        if (split.length != 2 && split.length != 3 ) {
            res.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }
        if(split.length == 2){
            Commande models = CommandeDAO.findbyId(Integer.parseInt(split[1]));
            String jsonstring = objectMapper.writeValueAsString(models);
            out.print(jsonstring);
            return;
        }

        if(split.length == 3){
            if (split[2].equals("prixfinal")) {
        		Commande models = CommandeDAO.findbyId(Integer.parseInt(split[1]));
                double price = 0;
                for(int i = 0; i < models.getLPizzas().size();i++){
                    price += PizzaDAO.getPrice(models.getLPizzas().get(i).getId());
                }
                out.print(price);
        		return;
            }
        }   
    }

    public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException, java.io.IOException {
        String authorization = req.getHeader("Authorization");
        if (authorization == null || !authorization.startsWith("Bearer ") || !UsersDAO.verifierUtilisateur(authorization)){
            res.sendError(403);
            return;
        }
        ObjectMapper objectMapper = new ObjectMapper();
        res.setContentType("application/json;charset=UTF-8");
        PrintWriter out = res.getWriter();
        StringBuilder data = new StringBuilder();
        String info = req.getPathInfo();
        BufferedReader reader = req.getReader();
        String line;
       
        while ((line = reader.readLine()) != null) {
        	data.append(line); 
        } 
        if (info == null || info.equals("/")) {
            CommandePost commande = objectMapper.readValue(data.toString(), CommandePost.class);
            String[] idpizza = commande.getListPizzas().split(",");
            System.out.println("TAYUAIAYAAY" + idpizza[0] +idpizza[1] +idpizza[2] +"");
            List<Pizza> pizza = new ArrayList<>();
            for(int i =0; i< idpizza.length; i++){
                pizza.add(PizzaDAO.findById(Integer.parseInt(idpizza[i])));
            }
            Commande commande2 = new Commande(commande.getCommande_id(), commande.getIdUtilisateur(), pizza);
            CommandeDAO.save(commande2);
        }else{
            res.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }
    }
}

