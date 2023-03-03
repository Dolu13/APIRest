package dao;

import dto.Commande;
import dto.Pizza;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class CommandeDAO {

    // Cette commande retourne toutes les commandes stockées dans la base de données
    public static List<Commande> findAll() {

        List<Commande> commandes = new ArrayList<>();
        try {
            String query = "select distinct commande_id,user_id,datecommande from pizza,consommation,commandes where pizza.id = consommation.idpizza AND commandes.commande_id = consommation.idcommande ";
            DS.getConnection();
            PreparedStatement ps = DS.connection.prepareStatement(query);
            ResultSet rs = DS.executeQuery(ps);
            while (rs.next()) {
                commandes.add(new Commande(rs.getInt("commande_id"),rs.getInt("user_id"), rs.getDate("datecommande"), findById(rs.getInt("commande_id"))));
            }
            DS.closeConnection();
            System.out.println("All is ok! 1");
        } catch (Exception e) {
            System.out.println("ERREUR \n" + e.getMessage());
        }
        return commandes;
    }

    // Cette commande retourne toutes les pizzas d'une commande via son id qui sont stockées dans la base de données
    public static List<Pizza> findById(int id) {
        List<Pizza> pizzas = new ArrayList<>();
        try {
            String query = "Select * from consommation where idcommande = ?";
            DS.getConnection();
            PreparedStatement ps = DS.connection.prepareStatement(query);
            ps.setInt(1, id);
            ResultSet rs = DS.executeQuery(ps);
            while(rs.next()){
                pizzas.add(PizzaDAO.findById(rs.getInt("idpizza")));
            }
            DS.closeConnection();
            System.out.println("All is ok! 2");
        } catch (Exception e) {
            System.out.println("ERREUR \n" + e.getMessage());
        }
        return pizzas;
    }

    // Cette commande retourne les details d'une commande via son id stockées dans la base de données
    public static Commande findbyId(int id) {
        Commande commandes = new Commande();
        try {
            String query = "select distinct commande_id,user_id,datecommande from pizza,consommation,commandes where pizza.id = consommation.idpizza AND commandes.commande_id = consommation.idcommande AND commandes.commande_id = ?";
            DS.getConnection();
            PreparedStatement ps = DS.connection.prepareStatement(query);
            ps.setInt(1, id);
            ResultSet rs = DS.executeQuery(ps);
            while (rs.next()) {
                commandes = new Commande(rs.getInt("commande_id"),rs.getInt("user_id"), rs.getDate("datecommande"), findById(rs.getInt("commande_id")));
            }
            DS.closeConnection();
            System.out.println("All is ok! 3");
        }catch(Exception e){
            System.out.println("ERREUR \n" + e.getMessage());
        }
        return commandes;
    }

    // Cette commande ajouter une nouvelle commande dans la base de données avec tout ses attributs
    public static void save(Commande commande) {
        try {
            String query = "Insert into commandes values(?,?,Date(now()))";
            DS.getConnection();
            PreparedStatement ps = DS.connection.prepareStatement(query);
            ps.setInt(1, commande.getCommande_id());
            ps.setInt(2, commande.getIdUtilisateur() );
            DS.executeUpdate(ps);
            for(int i =0; i<commande.getLPizzas().size();i++){
                CommandeDAO.addPizza(commande.getLPizzas().get(i).getId(),commande.getCommande_id());
            }
            DS.closeConnection();
            System.out.println("All is ok! 4");
        } catch (Exception e) {
            System.out.println("ERREUR 4\n" + e.getMessage());
        }
    }

    // Cette commande ajoute les pizzas d'une commande associé dans la base de données
    private static void addPizza(int idpizza, int idcommande) {
        try {
            String query = "Insert into consommation values(?,?)";
            DS.getConnection();
            PreparedStatement ps = DS.connection.prepareStatement(query);
            ps.setInt(1, idcommande);
            ps.setInt(2, idpizza);
            DS.executeUpdate(ps);
            DS.closeConnection();
            System.out.println("All is ok! 5");
        } catch (Exception e) {
            System.out.println("ERREUR \n" + e.getMessage());
        }
    }

}
