package application.mercadona.crud;

public class DetailProduct
{

	private int stock;
	private float price;
	
	private Product product;
	private Category category;
	
	public DetailProduct(int stock, float price) {
		super();
		this.stock = stock;
		this.price = price;
	}

	public DetailProduct(int stock, float price, Product product, Category category) {
		super();
		this.stock = stock;
		this.price = price;
		this.product = product;
		this.category = category;
	}

	public int getStock() {
		return stock;
	}

	public void setStock(int stock) {
		this.stock = stock;
	}

	public float getPrice() {
		return price;
	}

	public void setPrice(float price) {
		this.price = price;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}
	
}
