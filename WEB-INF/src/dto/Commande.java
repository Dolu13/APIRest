package dto;
//Chaque commande doit contenir l’identifiant de l’utilisateur, la date de commande, et les pizzas commandées

import java.sql.Date;
import java.util.List;

public class Commande {
    private int commande_id;
    private int idUtilisateur;
    private Date date;
    private List<Pizza> lPizzas;

  

    public Commande(int commande_id, int idUtilisateur, Date date, List<Pizza> lPizzas) {
        this.commande_id = commande_id;
        this.idUtilisateur = idUtilisateur;
        this.date = date;
        this.lPizzas = lPizzas;
    }

    public Commande() {
    }


    public Commande(int commande_id2, int idUtilisateur2, List<Pizza> pizza) {
        this.commande_id = commande_id2;
        this.idUtilisateur = idUtilisateur2;
        this.lPizzas = pizza;
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

    public Date getDate() {
        return this.date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public List<Pizza> getLPizzas() {
        return this.lPizzas;
    }

    public void setLPizzas(List<Pizza> lPizzas) {
        this.lPizzas = lPizzas;
    }


    @Override
    public String toString() {
        return "{" +
            ", idUtilisateur='" + getIdUtilisateur() + "'" +
            ", date='" + getDate() + "'" +
            ", lPizzas='" + getLPizzas().toString() + "'" +
            "}";
    }


}
