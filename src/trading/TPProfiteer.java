package trading;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Set;

import engine.Trader;

public class TPProfiteer {

	public static void main(String[] args) {

		Trader trader = new Trader();
		
		/* Scan all runes */
//					priceCheckScan(70, "Rune", cookie, "");
			
//					===
		
		/* Minor Sigil scan */
//		trader.priceCheckScan(70, "Minor Sigil", "&rarity=3");
					
		/* Major Sigil scan */
		trader.priceCheckScan(70, "Major Sigil", "&rarity=4");
					
		/* Superior Sigil scan */
//		trader.priceCheckScan(70, "Superior Sigil", "&rarity=5");
				
//					===
		
		/* Minor Rune scan */
//		trader.priceCheckScan(70, "Minor Rune", "&rarity=3");
					
		/* Major Rune scan */
		trader.priceCheckScan(70, "Major Rune", "&rarity=4");
					
		/* Superior Rune scan */
//		trader.priceCheckScan(70, "Superior Rune", "&rarity=5");
					
//					===
		
					/* Embellished Jewel */
//					priceCheckScan(100, "Embellished Jewel", cookie, "&rarity=4");
					
					/* Exquisite Jewel */
//					priceCheckScan(50, "Exquisite Jewel", cookie, "&rarity=5");
					
		// List items found
		Set<String> set = trader.items.keySet();
		for (String i : set) {
			System.out.println(trader.items.get(i));
		}
		
		// =========================================================
					
					/* SEARCHES */ // TODO SEARCHES
					
		/* Minor Sigil Weapon Search */
		trader.socketedBuyOrderItemSearch(10000, "Minor Sigil", Trader.sigilTypes, "&type=18&rarity=3");
					
		/* Major Sigil Weapon Search */
		trader.socketedBuyOrderItemSearch(10000, "Major Sigil", Trader.sigilTypes, "&type=18&rarity=4");
					
		/* Superior Sigil Weapon Search */
//		trader.socketedBuyOrderItemSearch(1000, "Superior Sigil", Trader.sigilTypes, "&type=18&rarity=5");
		
//					===
					
		/* Minor Rune Armour Search */
		trader.socketedBuyOrderItemSearch(10000, "Minor Rune", Trader.runeTypes, "&type=0&rarity=3");
		
		/* Major Rune Armour Search */
		trader.socketedBuyOrderItemSearch(10000, "Major Rune", Trader.runeTypes, "&type=0&rarity=4");
					
		/* Superior Rune Armour Search */
//		trader.socketedBuyOrderItemSearch(1000, "Superior Rune", Trader.runeTypes, "&type=0&rarity=5");
//		
//					===
//					
//					NOTE: Abandoned, Can't be done because socket can't be determined, could be cheap version or expensive - Can't access that data
//					/* Embellished Jewel Trinket Search */
//					socketedTrinketSearch(1000, "Embellished", longJewelTypes, cookie, "&type=15&rarity=4");
//					
//					/* Exquisite Jewel Trinket Search */
//					socketedTrinketSearch(1000, "Exquisite", jewelTypes, cookie, "&type=15&rarity=5");
		
		trader.playAlert();
		
	}

}
