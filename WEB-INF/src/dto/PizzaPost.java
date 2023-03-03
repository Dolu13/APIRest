package dto;


public class PizzaPost {
	private int id;
	private String name;
	private String pastatype;
	private int prixbase;

	private String listIngredients;


	public PizzaPost() {
	}

	public PizzaPost(int id, String name, String pastatype, int prixbase, String listIngredients) {
		this.id = id;
		this.name = name;
		this.pastatype = pastatype;
		this.prixbase = prixbase;
		this.listIngredients = listIngredients;
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPastatype() {
		return this.pastatype;
	}

	public void setPastatype(String pastatype) {
		this.pastatype = pastatype;
	}

	public int getPrixbase() {
		return this.prixbase;
	}

	public void setPrixbase(int prixbase) {
		this.prixbase = prixbase;
	}

	public String getListIngredients() {
		return this.listIngredients;
	}

	public void setListIngredients(String listIngredients) {
		this.listIngredients = listIngredients;
	}


	@Override
	public String toString() {
		return "{" +
			" id='" + getId() + "'" +
			", name='" + getName() + "'" +
			", pastatype='" + getPastatype() + "'" +
			", prixbase='" + getPrixbase() + "'" +
			", listIngredients='" + getListIngredients() + "'" +
			"}";
	}
	
}
