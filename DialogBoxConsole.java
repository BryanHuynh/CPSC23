import java.util.ArrayList;

public class DialogBoxConsole extends DialogBox{
    /**
     * prints to the str field to the console, inside a box.
     * function auto wraps
     */
    public void render(){
        String[] words = getStr().split(" ");											//split the string we will be printing into words
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

}
