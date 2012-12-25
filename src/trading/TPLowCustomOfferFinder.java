package trading;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

import engine.Item;
import engine.Trader;

/** 
 * Search the TP for items that have low buy order compared to sell price
 * */
public class TPLowCustomOfferFinder {

	public static void main(String[] args) {

		Trader trader = new Trader();
		
		// =========================================================
		
		// Type: Armour, Rarity: 5
		
		SortedMap<Integer, String> map = new TreeMap<Integer, String>();
		
		int pageDepth = 1000000;
		for (int rarity = 3; rarity <= 3; rarity++) { // 0 to 5
			for (int type = 3; type <= 3; type++) { // 0 to 18
				for (int i = 1; i < pageDepth; i += 10) {
					ArrayList<Item> tempItems = trader.requestPage("", i, "&type=" + type + "&rarity=" + rarity);
					if (tempItems.size() == 0) {
						System.out.println("No more pages, reached end.");
						break;
					}
					for (Item item : tempItems) {
		//				System.out.println("" + item.toString());
//						FullItem fullItem = 
						int highestBuyOrder = trader.getHighestBuyOrder(item.data_id);
						String analysisString = "";
						int cost = highestBuyOrder;
						if (cost < item.vendorPrice)
							cost = item.vendorPrice;
						int profit = (int) ((item.price - (item.price * 0.15f)) - cost);
						int percentageProfit = (int) (((float)profit/cost)*100);
						if (profit > 0) {
							analysisString += " PROFIT: " + profit + "c (" + percentageProfit + "%)";
						}
						
						if (highestBuyOrder == -1) {
							analysisString += " NO BUY ORDER";
						}
						System.out.println(item.toString() + " -" + analysisString);
						
						map.put((Integer) percentageProfit, item.toString() + " -" + analysisString);
					}
				}
			}
		}
		
		System.out.println();
		System.out.println("---");
		System.out.println();
		Set<Integer> keySet = ((TreeMap) map).descendingKeySet();
		for (Integer in : keySet) {
			System.out.println("" + map.get(in));
		}
		
		
	}

}
