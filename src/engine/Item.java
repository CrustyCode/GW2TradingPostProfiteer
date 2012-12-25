package engine;

public class Item {

	public int type_id = 0;
	public int data_id = 0;
	public int level = 0;
	public int rarity = 0;
	
	public String name = "";
	public int price = 0;
	
	public int vendorPrice = 0;
	
	public Item(int type_id, int data_id, int level, int rarity, 
			String name, int minSaleOffer, int vendorPrice) {
		this.type_id = type_id;
		this.data_id = data_id;
		this.level = level;
		this.rarity = rarity;
		this.name = name;
		this.price = minSaleOffer;
		this.vendorPrice = vendorPrice;
	}
	
	public String toString() {
		return "Type: " + type_id + ", ID: " + data_id + ", Level: " + level + ", Rarity: " + 
				rarity + ", Name: " + name + ", Price: " + price + "c.";
	}
	
}
