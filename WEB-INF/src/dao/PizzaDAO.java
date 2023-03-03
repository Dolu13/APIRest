package dao;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.*;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import dto.Ingredient;
import dto.Pizza;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class PizzaDAO {

    public static Pizza findById(int id) {
        Pizza pizza = new Pizza();
        try {
            String query = "Select distinct pizza.id,pizza.name,pizza.pastatype,pizza.prixbase from pizza,composition,ingredients where pizza.id = composition.idpizza AND composition.idingredient = ingredients.id AND pizza.id =?";
            DS.getConnection();
            PreparedStatement ps = DS.connection.prepareStatement(query);

            ps.setInt(1, id);
            System.out.println(ps.toString());
            ResultSet rs = DS.executeQuery(ps);
            if(rs.next()){
                pizza =  new Pizza(rs.getInt("id"), rs.getString("name"), rs.getString("pastatype"),
                        rs.getInt("prixbase"), getIngredientsById(rs.getInt("id")));
                DS.closeConnection();
                System.out.println("All is ok! 3");
            }

        } catch (Exception e) {
            System.out.println("ERREUR 3\n" + e.getMessage());
        }
        
        return pizza;
    }

    public static List<Ingredient> getIngredientsById(int index) {
        List<Ingredient> list = new ArrayList<>();

        try {
            String query = "Select ingredients.name,ingredients.id,ingredients.price from pizza,composition,ingredients where pizza.id = composition.idpizza AND composition.idingredient = ingredients.id AND composition.idpizza = ?";
            DS.getConnection();
            PreparedStatement ps = DS.connection.prepareStatement(query);
            ps.setInt(1, index);
            ResultSet rs = DS.executeQuery(ps);
            while (rs.next()) {
                list.add(new Ingredient(rs.getInt("id"), rs.getString("name"), rs.getDouble("price")));
            }
            DS.closeConnection();
            System.out.println("All is ok! 2");
        } catch (Exception e) {
            System.out.println("ERREUR 2\n" + e.getMessage());
        }
        return list;
    }

    public static List<Pizza> findAll() {
        List<Pizza> pizza = new ArrayList<>();
        try {
            DS.getConnection();
            String query = "Select distinct pizza.id,pizza.name,pizza.pastatype,pizza.prixbase from pizza,composition,ingredients where pizza.id = composition.idpizza AND composition.idingredient = ingredients.id ";
            PreparedStatement ps = DS.connection.prepareStatement(query);
            ResultSet rs = DS.executeQuery(ps);
            while (rs.next()) {
                pizza.add(new Pizza(rs.getInt("id"), rs.getString("name"), rs.getString("pastatype"),
                        rs.getInt("prixbase"), getIngredientsById(rs.getInt("id"))));
            }
            DS.closeConnection();
            System.out.println("All is ok! ");
        } catch (Exception e) {
            System.out.println("ERREUR 1\n" + e.getMessage());
        }
        return pizza;
    }

    public static void save(Pizza pizza) {
        try {
            String query = "Insert into pizza values(?,?,?,?)";

            DS.getConnection();
            PreparedStatement ps = DS.connection.prepareStatement(query);
            ps.setInt(1, pizza.getId());
            ps.setString(2, pizza.getName() );
            ps.setString(3, pizza.getPastatype());
            ps.setDouble(4, pizza.getPrix());
            DS.executeUpdate(ps);
            for(int i =0; i<pizza.getListIngredients().size();i++){
                PizzaDAO.addIngredient(pizza.getIngredient(pizza.getListIngredients(), i).getId(), pizza.getId());
            }
            DS.closeConnection();
            System.out.println("All is ok! ");
        } catch (Exception e) {
            System.out.println("ERREUR 8\n" + e.getMessage());
        }
    }

    public static void remove(int id) {
        try {
            String query = "DELETE from pizza where id=?";
            DS.getConnection();
            PreparedStatement ps = DS.connection.prepareStatement(query);
            ps.setInt(1, id);
            DS.executeUpdate(ps);
            DS.closeConnection();
            System.out.println("All is ok! ");
        } catch (Exception e) {
            System.out.println("ERREUR \n" + e.getMessage());
        }
    }

    public static void removeIngredient(int idIngredient, int idPizza) {
        try {
            String query = "DELETE from composition where idingredient=? AND idpizza=?";
            DS.getConnection();
            PreparedStatement ps = DS.connection.prepareStatement(query);
            ps.setInt(1, idIngredient);
            ps.setInt(2, idPizza);
            DS.executeUpdate(ps);
            DS.closeConnection();
            System.out.println("All is ok! ");

        } catch (Exception e) {
            System.out.println("ERREUR \n" + e.getMessage());
        }
    }

    public static void addIngredient(int idIngredient, int idPizza) {
        try {
            DS.getConnection();
            String query = "insert into composition values(?,?)";
            PreparedStatement ps = DS.connection.prepareStatement(query);
            ps.setInt(1, idPizza);
            ps.setInt(2, idIngredient);
            DS.executeUpdate(ps);
            DS.closeConnection();
            System.out.println("All is ok! ");
        } catch (Exception e) {
            System.out.println("ERREUR \n" + e.getMessage());
        }
    }

    public static double getPrice(int idPizza) {
        double price = 0.0;
        try {
            String query = "Select ingredients.price from pizza,composition,ingredients where pizza.id = composition.idpizza AND composition.idingredient = ingredients.id AND composition.idpizza =?";
            DS.getConnection();
            PreparedStatement ps = DS.connection.prepareStatement(query);
            ps.setInt(1, idPizza);
            ResultSet rs = DS.executeQuery(ps);
            while (rs.next()) {
                price += rs.getDouble("price");
            }
            String query2 = "Select prixbase from pizza where id = ?";
            PreparedStatement ps2 = DS.connection.prepareStatement(query2);
            ps2.setInt(1, idPizza);
            ResultSet rs2 = DS.executeQuery(ps2);
            rs2.next();
            price += rs2.getDouble("prixbase");
            DS.closeConnection();
            System.out.println("All is ok !");
        } catch (Exception e) {
            System.out.println("ERREUR \n" + e.getMessage());
        }
        return price;
    }

   public static void doPatch(int id, String type, String value) {
    try {
        DS.getConnection();
        String query = "";
        query = "Update pizza set "+type.replaceAll("\"","")+"=? where id="+id+"";
        PreparedStatement ps = DS.connection.prepareStatement(query);
        if(type.equals("prixbase")){
            ps.setInt(1, Integer.valueOf(value.replaceAll("\"", "")));
        }else{
            ps.setString(1, value.replaceAll("\"", ""));
        }
        DS.executeUpdate(ps);
        DS.closeConnection();
        System.out.println("All is ok! ");
    } catch (Exception e) {
        System.out.println("ERREUR \n" + e.getMessage());
    }


    }
}
