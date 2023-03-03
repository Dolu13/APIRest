package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import dto.Ingredient;

public class IngredientDAO {

    // Cette commande retourne un ingredient qui est stocké dans la base de données via son id
    public static Ingredient findById(int id) {
        Ingredient ingredient = new Ingredient();
        try {
            String query = "Select * from ingredients where id = ?";
            DS.getConnection();
            PreparedStatement ps = DS.connection.prepareStatement(query);
            ps.setInt(1, id);
            ResultSet rs = DS.executeQuery(ps);
            if(rs.next()){
                ingredient = new Ingredient(rs.getInt("id"), rs.getString("name"),rs.getDouble("price"));
            }
            DS.closeConnection();
            System.out.println("All is ok! 2");
        } catch (Exception e) {
            System.out.println("ERREUR 2\n" + e.getMessage());
        }
        return ingredient;
    }
    
    
    public static List<Ingredient> findAll() {
        List<Ingredient> ingredient = new ArrayList<>();
        try {
            String query = "Select * from ingredients";
            DS.getConnection();
            PreparedStatement ps = DS.connection.prepareStatement(query);
            ResultSet rs = DS.executeQuery(ps);
            while(rs.next()){
                ingredient.add(new Ingredient(rs.getInt("id"), rs.getString("name"),rs.getDouble("price")));
            }
            DS.closeConnection();
            System.out.println("All is ok!");
        } catch (Exception e) {
            System.out.println("ERREUR \n" + e.getMessage());
        }
        return ingredient;
    }

    public static void save(Ingredient ingredient){
		try{
			String query = "Insert into ingredients values(?,?,?)";
            
			DS.getConnection();
            PreparedStatement ps = DS.connection.prepareStatement(query);
            ps.setInt(1, ingredient.getId());
            ps.setString(2, ingredient.getName());
            ps.setDouble(3, ingredient.getPrice());

            DS.executeUpdate(ps);
			DS.closeConnection();            
            System.out.println("All is ok! 1");
		} catch(Exception e) {
			System.out.println("ERREUR 1\n" + e.getMessage());
		}
	}
    
    public static void delete(int id){
		try{
			String query = "DELETE from ingredients where id=?";
			DS.getConnection();
            PreparedStatement ps = DS.connection.prepareStatement(query);
            ps.setInt(1, id);
			DS.executeUpdate(ps);
			DS.closeConnection();
		} catch(Exception e) {
			System.out.println("ERREUR \n" + e.getMessage());
		}
	}
}