package com.my_app.entities.product;

public class Product {
    private Integer id;
    private String name;
    private int price;
    private int quantity;
    private String description;
    private String imageURL;
    private String category;

    public Product() {
    }

    public Product( String name, int price, int quantity, String description, String imageURL, String category) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.description = description;
        this.imageURL = imageURL;
        this.category = category;
    }

    

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
   
    public int calculateTotalPrice() {
        return price * quantity;
    }

    public void increaseQuantity(int count) {
        this.quantity += count;
    }
    
    public void decreaseQuantity(int count) {
        if (count <= quantity) {
            this.quantity -= count;
        }
    }

    public boolean hasProduct() {
        return this.quantity > 0;
    }

    public boolean hasEnoughtProduct(int count) {
        return count <= quantity;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
}
