package dto;

import java.sql.Date;

public class CommandePost {
    private int commande_id;
    private int idUtilisateur;
    private String listPizzas;


    public CommandePost() {
    }

    public CommandePost(int commande_id, int idUtilisateur, String listPizzas) {
        this.commande_id = commande_id;
        this.idUtilisateur = idUtilisateur;
        this.listPizzas = listPizzas;
    }

    public int getCommande_id() {
        return this.commande_id;
    }

    public void setCommande_id(int commande_id) {
        this.commande_id = commande_id;
    }

    public int getIdUtilisateur() {
        return this.idUtilisateur;
    }

    public void setIdUtilisateur(int idUtilisateur) {
        this.idUtilisateur = idUtilisateur;
    }


    public String getListPizzas() {
        return this.listPizzas;
    }

    public void setListPizzas(String listPizzas) {
        this.listPizzas = listPizzas;
    }


    @Override
    public String toString() {
        return "{" +
            " commande_id='" + getCommande_id() + "'" +
            ", idUtilisateur='" + getIdUtilisateur() + "'" +
            ", listPizzas='" + getListPizzas() + "'" +
            "}";
    }

}
