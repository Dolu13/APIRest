package dto;

import java.util.List;

public class Pizza {
	private int id;
	private String name;
	private String pastatype;
	private int prixbase;

	private List<Ingredient> listIngredients;

	
	public Pizza(int id,String name, String pastatype, int prix, List<Ingredient> listIngredients) {
		this.id = id;
		this.name = name;
		this.pastatype = pastatype;
		this.prixbase = prix;
		this.listIngredients = listIngredients;
	}

	public Pizza(){
		
	}


	public Pizza(List<Pizza> findById) {
    }

    public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPastatype() {
		return pastatype;
	}

	public void setpastatype(String pastatype) {
		this.pastatype = pastatype;
	}

	public int getPrix() {
		return prixbase;
	}

	public void setPrix(int prix) {
		this.prixbase = prix;
	}

	public List<Ingredient> getListIngredients() {
		return listIngredients;
	}

	public Ingredient getIngredient(List<Ingredient> list, int id){
		return list.get(id);
	}

	public void setListIngredients(List<Ingredient> listIngredients) {
		this.listIngredients = listIngredients;
	}


	@Override
	public String toString() {
		return "{" +
			" id='" + getId() + "'" +
			", name='" + getName() + "'" +
			", pastatype='" + getPastatype() + "'" +
			", prixbase='" + getPrix() + "'" +
			", listIngredients='" + getListIngredients() + "'" +
			"}";
	}

}
