import java.util.ArrayList;

/**
 * can print to a console or a JTextArea.
 * prints whatever string the user desires
 */
public class DialogBox {
	private TextWindow tw;							//the location that -giving the game is running gui- will print to
	private String str = "text";				//the text that we will be printing

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
		String[] words = str.split(" ");											//split the line to be printed into an array of words
		String bar = "-----------------------------------------------------";	//arbituary size of the bar that will box the textbox
		//System.out.println(bar.length());
		String sentence = "";

		/**
		tw.append(bar + "\n");																//add the top bar to the textbox
		for(String word: words){															//start looping through the words
			if((sentence +" " + word).length() < bar.length()){	// check if the line we are creating is too long and can be wrapped to the next line
				sentence += " "+  word;														//if the line we creating isn't too long then we can appending it to the same line
				tw.append(" "+word);
			}else{
				tw.append("\n");																	//create a new line
				sentence = word;																	// since the line was too long with the word attached, we throw the word onto the next line
			}
		}
		tw.append("\n");

		tw.append(bar + "\n");																//close the box
		**/
	}

	/**
	 * prints to the str field to the console, inside a box.
	 * function auto wraps
	 */
	public void renderToConsole(){
		String[] words = str.split(" ");											//split the string we will be printing into words
		ArrayList<String> lines = new ArrayList<String>();		//lines that we will be printing to the console, that are wrapped
		String bar = "------------------------------------------------------------";//the box that wraps the text
		String sentence = "";

		System.out.println(bar);															//start the box
		for(String word: words){															//loop through all the words in order to create lines that we will be printing
			if((sentence +" " + word).length() < bar.length()){	//check if the current line with another word is to long, or can we add another word to the same line
				sentence += " "+  word;														// we can add an extra word to the line
//				System.out.print(" "+word);
			}else{																							//line was too long and need to to be wrapped
//				System.out.println();
				lines.add(sentence);															//that is the end of one sentence so we can start anew
				sentence = word;																	//since the word couldn't be added to the last line, we add it to the start of the new line
			}
		}
		lines.add(sentence);																		// add the last line to the list
		for(String line: lines){
			System.out.println(line);														//loop through all the lines and print them to the console
		}
		System.out.println();
		System.out.println(bar);														//close the box
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
