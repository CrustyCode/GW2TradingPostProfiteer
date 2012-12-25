package engine;
import java.util.ArrayList;


public class Wrap {

	private ArrayList<Wrap> inside;
	
	private String s;
	
	public Wrap(String s) {
		this.s = s;
		inside = new ArrayList<Wrap>();
	}

	/** Unwrap the string into segments contained within ArrayList inside */
	public void unwrap(char c, char d) {
		int i = 0;
		boolean firstChar = true;
		int pos1 = 0, pos2 = 0;
		boolean ignoreNext = false;
		while (i < s.length()) {
			if (!ignoreNext)
				if (firstChar) {
					if (s.charAt(i) == c) {
						pos1 = i + 1;
						firstChar = false;
					}
				} else {
					if (s.charAt(i) == d) {
						pos2 = i;
						firstChar = true;
						// Store segment
						inside.add(new Wrap(s.substring(pos1, pos2)));
					}
				}
			if (ignoreNext)
				ignoreNext = false;
			if (s.charAt(i) == '\\')
				ignoreNext = true;
			i++;
		}
//		for (Wrap w : inside)
//			System.out.println(w.getString());
	}

	public String getString() {
		return s;
	}
	
	public ArrayList<Wrap> getInside() {
		return inside;
	}
	
}
