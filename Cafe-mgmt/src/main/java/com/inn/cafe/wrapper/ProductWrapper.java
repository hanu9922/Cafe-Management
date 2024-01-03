package com.inn.cafe.wrapper;




public class ProductWrapper {
	
	int id;
	String name;
	String description;
	int price;
	String status;
	int categoryId;
	String categoryName;
	public ProductWrapper() {
		
	}
	
public ProductWrapper(int id,String name) {
	super();
		this.id=id;
		this.name=name;
	}

    public ProductWrapper(int id,String name,String description,int price) {
	super();
		this.id=id;
		this.name=name;
		this.description=description;
		this.price=price;
	}
	public ProductWrapper(int id, String name, String description, int price, String status, int categoryId,
			String categoryName) {
		super();
		this.id = id;
		this.name = name;
		this.description = description;
		this.price = price;
		this.status = status;
		this.categoryId = categoryId;
		this.categoryName = categoryName;
	}
	public int getId() {
		return id;
	}
	public String getName() {
		return name;
	}
	public String getDescription() {
		return description;
	}
	public int getPrice() {
		return price;
	}
	public String getStatus() {
		return status;
	}
	public int getCategoryId() {
		return categoryId;
	}
	public String getCategoryName() {
		return categoryName;
	}

}
