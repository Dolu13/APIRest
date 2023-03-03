package controleurs;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import dao.IngredientDAO;
import dao.PizzaDAO;
import dao.UsersDAO;
import dto.Ingredient;
import dto.Pizza;
import dto.PizzaPost;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/pizzas/*")
public class PizzaRestAPI extends HttpServlet {

    public void service(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        if (req.getMethod().equalsIgnoreCase("PATCH")) {
            String authorization = req.getHeader("Authorization");
            if (authorization == null || !authorization.startsWith("Bearer ") || !UsersDAO.verifierUtilisateur(authorization)){
                res.sendError(403);
                return;
            }
            res.setContentType("application/json;charset=UTF-8");
            PrintWriter out = res.getWriter();
            StringBuilder data = new StringBuilder();
            String info = req.getPathInfo();
            BufferedReader reader = req.getReader();
            String line;
            while ((line = reader.readLine()) != null) {
                data.append(line);
            }
            ObjectMapper mapper = new ObjectMapper();
            List<String> keys = new ArrayList<>();
            List<String> values = new ArrayList<>();
            JsonNode jsonNode = mapper.readTree(data.toString());
            JsonParser jsonParser = jsonNode.traverse();
            while (!jsonParser.isClosed()) {
                if (jsonParser.nextToken() == JsonToken.FIELD_NAME) {
                    //avoir les key
                    keys.add((jsonParser.getCurrentName()));
                    // pour les int
                    values.add(String.valueOf(jsonNode.get(jsonParser.getCurrentName())));
                    // pour les String
                    values.add(String.valueOf(jsonNode.get(jsonParser.getCurrentName())));

                }
            }
            int id = Integer.parseInt(values.get(0));
            String type = keys.get(1);
            String value = values.get(2);
            PizzaDAO.doPatch(id, type, value);
        } else {
            super.service(req, res);
        }
    }

    public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        res.setContentType("application/json;charset=UTF-8");
        PrintWriter out = res.getWriter();
        ObjectMapper objectMapper = new ObjectMapper();
        String info = req.getPathInfo();
        if (info == null || info.equals("/")) {
            Collection<Pizza> models = PizzaDAO.findAll();
            String jsonstring = objectMapper.writeValueAsString(models);
            out.print(jsonstring);
            return;
        }
        String[] splits = info.split("/");

        if (splits.length != 2) {
            res.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }
        String id = splits[1];

        if (splits.length == 3) {
            if (splits[2].equals("name")) {
                out.print(objectMapper.writeValueAsString(PizzaDAO.findById(Integer.parseInt(id)).getName()));
                return;
            } else if (splits[2].equals("prixfinal")) {
                out.print(PizzaDAO.getPrice(Integer.parseInt(id)));
                return;
            } else {
                res.sendError(HttpServletResponse.SC_BAD_REQUEST);
                return;
            }
        }

        if (PizzaDAO.findById(Integer.parseInt(id)) == null) {
            res.getWriter().println("Pizza not found");
            return;
        } else {
            out.print(objectMapper.writeValueAsString(PizzaDAO.findById(Integer.parseInt((id)))));
            return;
        }
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
        String info = req.getPathInfo();
        BufferedReader reader = req.getReader();
        String line;

        while ((line = reader.readLine()) != null) {
            data.append(line);
        }

        if (info == null || info.equals("/")) {
            PizzaPost p = objectMapper.readValue(data.toString(), PizzaPost.class);
            String[] id = p.getListIngredients().split(",");
            List<Ingredient> idingredients = new ArrayList<>();
            for (int i = 0; i < id.length; i++) {
                idingredients.add(IngredientDAO.findById(Integer.parseInt(id[i])));
            }
            Pizza p2 = new Pizza(p.getId(), p.getName(), p.getPastatype(), p.getPrixbase(), idingredients);

            PizzaDAO.save(p2);
            return;
        } else {
            String[] split = info.split("/");
            if (split.length == 2) {
                Ingredient i = objectMapper.readValue(data.toString(), Ingredient.class);
                PizzaDAO.addIngredient(i.getId(), Integer.parseInt(split[1]));
                return;
            }else {
                res.sendError(HttpServletResponse.SC_BAD_REQUEST);
                return;
            }

        }
    }

    public void doDelete(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        String authorization = req.getHeader("Authorization");
        if (authorization == null || !authorization.startsWith("Bearer ") || !UsersDAO.verifierUtilisateur(authorization)){
            res.sendError(403);
            return;
        }
        res.setContentType("application/json;charset=UTF-8");
        // PrintWriter out = res.getWriter();
        String info = req.getPathInfo();
        if (info == null || info.equals("/")) {
            res.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }
        String[] splits = info.split("/");
        if (splits.length > 3) {
            res.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        String idP = splits[1];

        if (PizzaDAO.findById(Integer.parseInt(idP)) == null) {
            res.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }
        if (splits.length == 2) {
            PizzaDAO.remove(Integer.parseInt(idP));
            return;
        }
        if (splits.length == 3) {
            String idI = splits[2];
            PizzaDAO.removeIngredient(Integer.parseInt(idI), Integer.parseInt(idP));
        }
    }
}
