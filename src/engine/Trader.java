package engine;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLEncoder;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.Set;
import java.util.SortedMap;
import java.util.Timer;
import java.util.TreeMap;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.KeyManager;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import sun.audio.AudioPlayer;
import sun.audio.AudioStream;

public class Trader {
	
	public HashMap<String, Item> items = new HashMap<String, Item>();
	
	private Random r = new Random();
	
	private final static String sessionKey = ""; 
	private final static String email = "";
	private final static String password = "";
	
	String normalCookie;
	String listingsCookie;
	
	public static final String[] runeTypes = { "of Grenth", "of the Grove", "of Dwayna",  "of the Thief",  "of Strength",  "of the Elementalist",  "of the Earth", 
		"of the Ranger",  "of the Air",  "of the Soldier",  "of Balthazar",  "of the Ogre",  "of Vampirism",  "of Speed",  
		"of the Rata Sum",  "of the Guardian",  "of the Pirate",  "of Hoelbrak",  "of Divinity",  "of the Traveler",  "of the Flock",  
		"of the Krait",  "of the Eagle",  "of the Fighter",  "of the Fire",  "of Flame Legion",  "of Infiltration",  "of Lyssa",  
		"of the Pack",  "of Scavenging",  "of Melandru",  "of the Undead",  "of the Centaur",  "of Mercy",  "of the Afflicted",  
		"of Rage",  "of the Engineer",  "of the Dolyak",  "of the Wurm",  "of the Adventurer",  "of Holding",  "of the Ice",  
		"of the Mesmer",  "of the Scholar",  "of the Citadel",  "of the Warrior",  "of the Lich",  "of Svanir",  "of the Necromancer",  
		"of the Water" };
	
	public static final String[] sigilTypes = { "of Energy", "of Destroyer Slayer", "of Hobbling", "of Chilling", "of Sorrow", "of Blood", "of Paralyzation", 
		"of Intelligence", "of Water", "of Ice", "of Undead Slaying", "of Strength", "of Ghost Slaying", "of Rage", "of Frailty", 
		"of Centar Slaying", "of Smoldering", "of Conjuration", "of Justice", "of Dreams", "of Perception", "of Sanctuary", "of Life", 
		"of Leeching", "of Demon Slaying", "of Grawl Slaying", "of Nullification", "of Ogre Slaying", "of Air", "of Peril", 
		"of Icebrood Slaying", "of Seeking", "of Impact", "of Geomancy", "of Venom", "of Hydromancy", "of Serpent Slaying", "of Agony", 
		"of Bloodlust", "of Stamina", "of Speed", "of Purity", "of Battle", "of Fire", "of Smothering", "of Demon Summoning", 
		"of Accuracy", "of Force", "of Elemental Slaying", "of Restoration", "of Corruption", "of Earth", "of Doom", "of Wrath", 
		"of Debility", "of Luck" };
	
	public static final String[] jewelTypes = { "Beryl", "Emerald", "Sapphire", "Coral", "Ruby", "Chrysocola", "Opal" };
	public static final String[] longJewelTypes = { "Ornate Ruby", "Gilded Amethyst", "Intricate Sunstone", "Gilded Spinel", "Intricate Lapis", "Ornate Beryl",
		"Brilliant Coral", "Gilded Topaz", "Intricate Peridot", "Gilded Peridot", "Brilliant Ruby", "Ornate Coral", "Brilliant Emerald",
		"Brilliant Chrysocola", "Brilliant Sapphire", "Brilliant Opal", "Brilliant Beryl", "Intricate Topaz", "Gilded Sunstone",
		"Ornate Opal", "Intricate Carnelian", "Ornate Sapphire", "Gilded Carnelian", "Intricate Amethyst", "Ornate Chrysocola",
		"Gilded Lapis", "Intricate Spinel", "Ornate Emerald" };
	
	public Trader() {
		// Turning off certificate checking
		SSLContext ctx = null;
		try {
			ctx = SSLContext.getInstance("TLS");
		} catch (NoSuchAlgorithmException e2) {
			e2.printStackTrace();
		}
		try {
			ctx.init(new KeyManager[0],
					new TrustManager[] { new DefaultTrustManager() },
					new SecureRandom());
		} catch (KeyManagementException e1) {
			e1.printStackTrace();
		}
		SSLContext.setDefault(ctx);
		
		// Authenticate
		String args1 = null;
		try {
			args1 = "email="
					+ URLEncoder.encode(email, "UTF-8")
					+ "&password="
					+ URLEncoder.encode(password, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		System.out.println("Args: " + args1);
		normalCookie = logIn("https://account.guildwars2.com/login?redirect_uri=http%3A%2F%2Ftradingpost-live"
				+ ".ncplatform.net%2Fauthenticate%3Fsource%3D%252F&game_code=gw2", args1);
		
		listingsCookie = "";
		try {
			listingsCookie = "s=" + URLEncoder.encode(sessionKey, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
		System.out.println("Cookie: " + normalCookie);
		System.out.println("Listings Cookie: " + listingsCookie);
	}
	
	public HashMap<String, Item> getItems() {
		return items;
	}

	private class DefaultTrustManager implements X509TrustManager {
		public java.security.cert.X509Certificate[] getAcceptedIssuers() {
			return null;
		}

		public void checkClientTrusted(
				java.security.cert.X509Certificate[] arg0, String arg1)
				throws java.security.cert.CertificateException {
		}

		public void checkServerTrusted(
				java.security.cert.X509Certificate[] arg0, String arg1)
				throws java.security.cert.CertificateException {
		}
	}

	public void playAlert() {
		try {
			System.out.println("Beep.");
    		InputStream in1 = Timer.class.getResource("/beep.wav").openStream();
    		AudioStream as = new AudioStream(in1);
    		AudioPlayer.player.start(as);
		} catch (Exception e) {
			e.printStackTrace();
		} 
	}
	
	private StringBuffer connectionRequest(String sUrl, String cookie, String method) {
		URL url;
		HttpsURLConnection connection = null;
		try {
			url = new URL(sUrl);
			connection = (HttpsURLConnection) url.openConnection();
			connection.setHostnameVerifier(new HostnameVerifier() {
				public boolean verify(String arg0, SSLSession arg1) {
					return true;
				}
			});
			connection.setRequestMethod(method);
			connection.setRequestProperty("Cookie", cookie);
			connection.setRequestProperty("Host", "tradingpost-live.ncplatform.net");
			connection.setUseCaches(false);
			connection.setDoInput(true);
			
			// Get Response
			DataInputStream is = new DataInputStream(connection.getInputStream());
			BufferedReader rd = new BufferedReader(new InputStreamReader(is));
			String line;
			StringBuffer response = new StringBuffer();
			while ((line = rd.readLine()) != null) {
				response.append(line);
				response.append('\r');
			}
			rd.close();
			is.close();
			return response;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (connection != null) {
				connection.disconnect();
			}
		}
		return null;
	}
	
	public int getLivePrice(int data_id) {
//		sleep(5 + r.nextInt(10)); // Sleep for a bit, act like a human, decrease load on server

		String sUrl = null;
		try {
			sUrl = "https://tradingpost-live.ncplatform.net/ws/listings.json?id=" + URLEncoder.encode("" + data_id, "UTF-8") + "&type=sells";
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}
		
		StringBuffer response = connectionRequest(sUrl, listingsCookie, "GET");
//		System.out.println("=" + response);
		
		// Get square bracketed section
		int pos = -1;
		for (int i = 0; i < response.length(); i++) {
			if (response.charAt(i) == '[') {
				pos = i;
				break;
			}
		}
		if (pos == -1) {
			System.err.println("Messed up brackets: " + response);
			return -1;
		}
		String items1 = response.substring(pos, response.length() - 3);
		
//		System.out.println(">" + items1);
		
		String liveSellPrice = null;
		Wrap w = new Wrap(items1);
		w.unwrap('[', ']');
		ArrayList<Wrap> inside = w.getInside();
		for (Wrap w2 : inside) {
			w2.unwrap('{', '}');
			ArrayList<Wrap> inside2 = w2.getInside();
			if (inside2.size() == 0) // No sell orders
				break;
			Wrap w3 = inside2.get(0); // First sell order (lowest)
			w3.unwrap('"', '"');
			ArrayList<Wrap> inside3 = w3.getInside();
			liveSellPrice = inside3.get(3).getString();
		}
		
		if (liveSellPrice == null) {
			liveSellPrice = "-1";
		}
		return Integer.parseInt(liveSellPrice);
	}
	
	private void sleep(long time) {
		try {
			Thread.sleep(time);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public int getHighestBuyOrder(int data_id) {
		sleep(30 + r.nextInt(50)); // Sleep for a bit, act like a human, decrease load on server

		String sUrl = null;
		try {
			sUrl = "https://tradingpost-live.ncplatform.net/ws/listings.json?id=" + URLEncoder.encode("" + data_id, "UTF-8") + "&type=buys";
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}

		StringBuffer response = connectionRequest(sUrl, listingsCookie, "GET");
		
		// Get square bracketed section
		int pos = -1;
		for (int i = 0; i < response.length(); i++) {
			if (response.charAt(i) == '[') {
				pos = i;
				break;
			}
		}
		if (pos == -1) {
			System.err.println("Messed up brackets: " + response);
			return -1;
		}
		String items1 = response.substring(pos, response.length() - 3);
		String highestBuyOrder = null;
		Wrap w = new Wrap(items1);
		w.unwrap('[', ']');
		ArrayList<Wrap> inside = w.getInside();
		for (Wrap w2 : inside) {
			w2.unwrap('{', '}');
			ArrayList<Wrap> inside2 = w2.getInside();
			if (inside2.size() == 0) // No buy orders
				break;
			Wrap w3 = inside2.get(0); // First buy order (highest)
			w3.unwrap('"', '"');
			ArrayList<Wrap> inside3 = w3.getInside();
			highestBuyOrder = inside3.get(3).getString();
		}
		
		if (highestBuyOrder == null) {
			highestBuyOrder = "-1";
		}

		return Integer.parseInt(highestBuyOrder);
	}
	
	/** Rarity:<br>
	 * 	1 - Basic<br>
	 *  2 - Fine<br>
	 *  3 - Masterwork<br>
	 *  4 - Rare<br>
	 *  5 - Exotic */
	public ArrayList<Item> requestPage(String search, int offset, String addedString) {
		System.out.println("Page scan - Search: " + search + " Offset: " + offset + " Options: " + addedString);
		sleep(30 + r.nextInt(50)); // Sleep for a bit, act like a human
		ArrayList<Item> items = new ArrayList<Item>();
		String sUrl = null;
		try {
			sUrl = "https://tradingpost-live.ncplatform.net/ws/search.json?text=" + URLEncoder.encode(search, "UTF-8") + "&levelmin=0&levelmax=80&offset=" + offset + addedString;
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}
		
		StringBuffer response = connectionRequest(sUrl, normalCookie, "GET");

		// Get square bracketed section
		int pos = -1;
		for (int i = 0; i < response.length(); i++) {
			if (response.charAt(i) == '[') {
				pos = i;
				break;
			}
		}
		if (pos == -1)
			return null;
		String items1 = response.substring(pos, response.length() - 2);
		
		Wrap w = new Wrap(items1);
		w.unwrap('[', ']');
		ArrayList<Wrap> inside = w.getInside();
		for (Wrap w2 : inside) {
			w2.unwrap('{', '}');
			ArrayList<Wrap> inside2 = w2.getInside();
			for (Wrap w3 : inside2) {
				// One item
				w3.unwrap('"', '"');
				ArrayList<Wrap> inside3 = w3.getInside();
				Item item;
				// type_id
				int type_id = Integer.parseInt(inside3.get(1).getString());
				int data_id = Integer.parseInt(inside3.get(3).getString());
				int level = Integer.parseInt(inside3.get(9).getString());
				int rarity1 = Integer.parseInt(inside3.get(11).getString());
				int cachedPrice = Integer.parseInt(inside3.get(13).getString()); // Prices are cached and not reliable
				int price = getLivePrice(data_id);
				int vendorPrice = Integer.parseInt(inside3.get(15).getString());
				String name = inside3.get(5).getString();
//				System.out.println("Item: " + name + " Cached Price: " + cachedPrice + " Live Price: " + price);
				item = new Item(type_id, data_id, level, rarity1, name, price, vendorPrice);
				items.add(item);
			}
		}
		return items;
	}
	
	private String logIn(String targetURL, String urlParameters) {
		URL url;
		HttpsURLConnection connection = null;
		try {
			// Create connection
			url = new URL(targetURL);
			connection = (HttpsURLConnection) url.openConnection();

			connection.setHostnameVerifier(new HostnameVerifier() {
				public boolean verify(String arg0, SSLSession arg1) {
					return true;
				}
			});
			
			connection.setRequestMethod("POST");
			connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
			connection.setRequestProperty("Content-Length", Integer.toString(urlParameters.getBytes().length));
			connection.setRequestProperty("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
			connection.setRequestProperty("Accept-Language", "en-us,en;q=0.5");
			connection.setRequestProperty("Connection", "keep-alive");
			connection.setRequestProperty("Host", "account.guildwars2.com");
			connection.setRequestProperty("Referer", "account.guildwars2.com");
			
			connection.setUseCaches(false);
			connection.setDoInput(true);
			connection.setDoOutput(true);

			// Send request
			DataOutputStream wr = new DataOutputStream(connection.getOutputStream());
			wr.writeBytes(urlParameters);
			wr.flush();
			wr.close();

			// Get Response
			InputStream is = connection.getInputStream();
			BufferedReader rd = new BufferedReader(new InputStreamReader(is));
			String line;
			StringBuffer response = new StringBuffer();
			while ((line = rd.readLine()) != null) {
				response.append(line);
				response.append('\r');
			}
			rd.close();
			
			// Get cookie
			String cookie = connection.getHeaderField("Set-Cookie");
//			System.out.println("Cookie: " + cookie);
			
			return cookie;
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (connection != null) {
				connection.disconnect();
			}
		}
		return null;
	}
	
	public void priceCheckScan(int pageDepth, String search, String extraString) {
		for (int i = 1; i < pageDepth; i += 10) {
			ArrayList<Item> tempItems = requestPage(search, i, extraString);
			if (tempItems.size() == 0) {
				System.out.println("No more pages, reached end.");
				break;
			}
			for (Item it : tempItems) {
				items.put(it.name, it);
			}
		}
	}
	
	/** int pageDepth, String socketType, String[] socketSuffixes, String cookie, String extraString */
	public ArrayList<String> socketedBuyOrderItemSearch(int pageDepth, String socketType, String[] socketSuffixes, String extraString) {
		SortedMap<Integer, String> map = new TreeMap<Integer, String>();
		int totalCost = 0;
		int totalProfit = 0;
		for (int i = 1; i < pageDepth; i += 10) {
//			System.out.println(" " + i);
			ArrayList<Item> tempItems = requestPage("", i, extraString);
			if (tempItems.size() == 0) {
				System.out.println("No more pages, reached end.");
				break;
			}
			for (Item auctionItem : tempItems) {
//				if (auctionItem.price == -1)
//					continue;
//				items.put(it.name, it);
				for (String type : socketSuffixes)
					if (auctionItem.name.endsWith(type)) {
						int socketPrice = items.get(socketType + " " + type).price;
						// Deduct 20% first for lost sockets due to 80% salvage rate, then deduct 15% for TP deduction
						int deductedSocketPrice = (int) ((socketPrice - socketPrice*0.20f) - (socketPrice*0.15f));
						// Get highest buy order price
						int highestBuyOrder = getHighestBuyOrder(auctionItem.data_id);
						if (highestBuyOrder < auctionItem.vendorPrice)
							highestBuyOrder = auctionItem.vendorPrice;
						if (highestBuyOrder < deductedSocketPrice) { 
							// Alert
							int profit = deductedSocketPrice - highestBuyOrder;
							int percentageProfit = (int) ((((float)profit)/highestBuyOrder)*100);
							String s = profit + "c profit (" + percentageProfit + "%): Make order " + auctionItem.name + " for " + highestBuyOrder + "c up to a maximum of " + 
									deductedSocketPrice + "c and sell salvage for " + socketPrice + "c (" + deductedSocketPrice + ").";
							System.out.println(s);
							map.put(percentageProfit, s);
							totalCost += highestBuyOrder*20;
							totalProfit += profit*20;
						}
					}
			}
		}
		Set<Integer> keySet = ((TreeMap) map).descendingKeySet();
		System.out.println("");
		System.out.println("Results for " + socketType + " with " + extraString + ":");
		ArrayList<String> returnValues = new ArrayList<String>();
		for (Integer i : keySet) {
			System.out.println(map.get(i));
			returnValues.add(map.get(i));
		}
		System.out.println("Assuming 20 of each, Total cost: " + totalCost + "c and Total Profit: " + totalProfit + "c.");
		return returnValues;
	}
	
	/** int pageDepth, String socketType, String[] socketSuffixes, String cookie, String extraString */
	public ArrayList<String> socketedItemSearch(int pageDepth, String socketType, String[] socketSuffixes, String extraString) {
		SortedMap<Integer, String> map = new TreeMap<Integer, String>();
		for (int i = 1; i < pageDepth; i += 10) {
//			System.out.println(" " + i);
			ArrayList<Item> tempItems = requestPage("", i, extraString);
			if (tempItems.size() == 0) {
				System.out.println("No more pages, reached end.");
				break;
			}
			for (Item auctionItem : tempItems) {
				if (auctionItem.price == -1)
					continue;
//				items.put(it.name, it);
				for (String type : socketSuffixes)
					if (auctionItem.name.endsWith(type)) {
						int socketPrice = items.get(socketType + " " + type).price;
						// Deduct 20% first for lost sockets due to 80% salvage rate, then deduct 15% for TP deduction
						int deductedSocketPrice = (int) ((socketPrice - socketPrice*0.20f) - (socketPrice*0.15f));
						if (auctionItem.price < deductedSocketPrice) { 
							// Alert
							int profit = deductedSocketPrice - auctionItem.price;
							int percentageProfit = (int) ((((float)profit)/auctionItem.price)*100);
							String s = profit + "c profit (" + percentageProfit + "%): Buy " + auctionItem.name + " for " + auctionItem.price + "c up to a maximum of " + 
									deductedSocketPrice + "c and sell salvage for " + socketPrice + "c (" + deductedSocketPrice + ").";
							System.out.println(s);
							map.put(percentageProfit, s);
						}
					}
			}
		}
		Set<Integer> keySet = ((TreeMap) map).descendingKeySet();
		System.out.println("");
		System.out.println("Results for " + socketType + " with " + extraString + ":");
		ArrayList<String> returnValues = new ArrayList<String>();
		for (Integer i : keySet) {
			System.out.println(map.get(i));
			returnValues.add(map.get(i));
		}
		return returnValues;
	}
	
}