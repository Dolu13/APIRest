package controleurs;

import java.io.*;

import java.util.Collection;


import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;

import dao.IngredientDAO;
import dao.UsersDAO;
import dto.Ingredient;




@WebServlet("/ingredients/*")
public class IngredientRestAPI extends HttpServlet {

	
	public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        res.setContentType("application/json;charset=UTF-8");
        PrintWriter out = res.getWriter();
        ObjectMapper objectMapper = new ObjectMapper();
        String info = req.getPathInfo();
        if (info == null || info.equals("/")) {
            Collection<Ingredient> models = IngredientDAO.findAll();
            String jsonstring = objectMapper.writeValueAsString(models);
            out.print(jsonstring);
            return;
        }
        String[] splits = info.split("/");
        if (splits.length != 2 && splits.length != 3 ) {
            res.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }
        String id = splits[1];
        if (splits.length==3) {
        	if (splits[2].equals("name")) {
        		out.print(objectMapper.writeValueAsString(IngredientDAO.findById(Integer.parseInt(id)).getName()));
        		return;
        	}
        	else {
        		res.sendError(HttpServletResponse.SC_BAD_REQUEST);
                return;
        	}
        }
        if (IngredientDAO.findById(Integer.parseInt(id)) == null  ) {
            System.out.println("Ingredient not found");
        	res.getWriter().println("Ingredient not found");
            return;
        }
        out.print(objectMapper.writeValueAsString(IngredientDAO.findById(Integer.parseInt(id))));
        return;
    }


	
	public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        String authorization = req.getHeader("Authorization");
        if (authorization == null || !authorization.startsWith("Bearer ") || !UsersDAO.verifierUtilisateur(authorization)){
            res.sendError(403);
            return;
        }
        ObjectMapper objectMapper = new ObjectMapper();
        res.setContentType("application/json;charset=UTF-8");
        PrintWriter out = res.getWriter();
        StringBuilder data = new StringBuilder();
        BufferedReader reader = req.getReader();
        String line;
        while ((line = reader.readLine()) != null) {
        	data.append(line);
        }
        String info = req.getPathInfo();
        if (!(info == null) && !info.equals("/")) {
            res.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }
        Ingredient i = objectMapper.readValue(data.toString(), Ingredient.class);
        if(IngredientDAO.findById(i.getId()) == null){
            System.out.println("Ingredient not found");
            res.getWriter().println("Ingredient not found");
            return;
        }
        
        IngredientDAO.save(i);
        return;
    }
	
	public void doDelete(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        String authorization = req.getHeader("Authorization");
        if (authorization == null || !authorization.startsWith("Bearer ") || !UsersDAO.verifierUtilisateur(authorization)){
            res.sendError(403);
            return;
        }
		res.setContentType("application/json;charset=UTF-8");
        String info = req.getPathInfo();
        if (info == null || info.equals("/")) {
        	res.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }
        String[] splits = info.split("/");
        if (splits.length != 2) {
            res.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }
        String id = splits[1];
        if (IngredientDAO.findById(Integer.parseInt(id)) == null) {
            res.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }
        IngredientDAO.delete(Integer.parseInt(id));

        return;
    }
}