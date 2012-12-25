package trading;
import java.util.Random;


public class TPAutoBot {

	public static void main(String[] args) {
		
		Random r = new Random();
		new TPNotifier(15*60*1000 + r.nextInt(1000*60)).run();
		
	}

}
