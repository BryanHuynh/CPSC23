import java.util.ArrayList;

/**
 * can print to a console or a JTextArea.
 * prints whatever string the user desires
 */
public class DialogBox {
	private TextWindow tw;
	private String str = "text";

	/**
	 *
	 *
	 * @param tw the location that the gui will print to
	 */
	public DialogBox(TextWindow tw){
		this.tw = tw;
	}


	/**
	 * renders the str field to the textwindow, inside a box.
	 * function auto wraps str
	 */
	public void render(){
		String[] words = str.split(" ");
		ArrayList<String> lines = new ArrayList<String>();
		String bar = "-----------------------------------------------------";
		//System.out.println(bar.length());
		String sentence = "";


		tw.append(bar + "\n");
		for(String word: words){
			if((sentence +" " + word).length() < bar.length()){
				sentence += " "+  word;
				tw.append(" "+word);
			}else{
				tw.append("\n");
				lines.add(sentence);
				sentence = word;
			}
		}
		tw.append("\n");

		tw.append(bar + "\n");
	}

	/**
	 * prints to the str field to the console, inside a box.
	 * function auto wraps
	 */
	public void renderToConsole(){
		String[] words = str.split(" ");
		ArrayList<String> lines = new ArrayList<String>();
		String bar = "------------------------------------------------------------";
		String sentence = "";

		System.out.println(bar);
		for(String word: words){
			if((sentence +" " + word).length() < bar.length()){
				sentence += " "+  word;
//				System.out.print(" "+word);
			}else{
//				System.out.println();
				lines.add(sentence);
				sentence = word;
			}
		}
		for(String line: lines){
			System.out.println(line);
		}
		System.out.print(sentence);
		System.out.println();
		System.out.println(bar);
	}



	public TextWindow getTw() {
		return tw;
	}

	public void setTw(TextWindow tw) {
		this.tw = tw;
	}

	public String getStr() {
		return str;
	}

	public void setStr(String str) {
		this.str = str;
	}
}
