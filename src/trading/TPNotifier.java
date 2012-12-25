package trading;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Set;
import java.util.Timer;

import engine.Trader;

import sun.audio.AudioPlayer;
import sun.audio.AudioStream;


public class TPNotifier implements Runnable {

	int sleepTime;
	
	public TPNotifier(int sleepTime) {
		this.sleepTime = sleepTime;
	}
	
	@Override
	public void run() {		
		playAlert();
		while (true) {
			Trader trader = new Trader();
			
			/* Major Rune scan */
			trader.priceCheckScan(70, "Major Rune", "&rarity=4");

			/* Major Sigil scan */
			trader.priceCheckScan(70, "Major Sigil", "&rarity=4");
			
			/* Superior Rune scan */
			trader.priceCheckScan(70, "Superior Rune", "&rarity=5");

			/* Superior Sigil scan */
			trader.priceCheckScan(70, "Superior Sigil", "&rarity=5");

//			// List items found
//			Set<String> set = trader.items.keySet();
//			for (String i : set) {
//				System.out.println(trader.items.get(i));
//			}

			/* Major Sigil Weapon Search */
			ArrayList<String> items = trader.socketedItemSearch(10000, "Major Sigil", Trader.sigilTypes, "&type=18&rarity=4");
			if (items.size() != 0) {
				playAlert();
			}
			
			/* Major Rune Weapon Search */
			items = trader.socketedItemSearch(10000, "Major Rune", Trader.runeTypes, "&type=0&rarity=4");
			if (items.size() != 0) {
				playAlert();
			}
			
			/* Superior Sigil Weapon Search */
			items = trader.socketedItemSearch(1000, "Superior Sigil", Trader.sigilTypes, "&type=18&rarity=5");
			if (items.size() != 0) {
				playAlert();
			}
			
			/* Superior Rune Armour Search */
			items = trader.socketedItemSearch(1000, "Superior Rune", Trader.runeTypes, "&type=0&rarity=5");
			if (items.size() != 0) {
				playAlert();
			}
			
			// Sleep
			try {
				Thread.sleep(sleepTime);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	private void playAlert() {
		try {
			System.out.println("Beep.");
    		InputStream in1 = Timer.class.getResource("/beep.wav").openStream();
    		AudioStream as = new AudioStream(in1);
    		AudioPlayer.player.start(as);
		} catch (Exception e) {
			e.printStackTrace();
		} 
	}

}
