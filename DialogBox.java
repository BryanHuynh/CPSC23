import java.util.ArrayList;

public class DialogBox {
	TextWindow tw;

	public DialogBox(TextWindow tw){
		this.tw = tw;
	}

	public void render(String str){

		String[] words = str.split(" ");
		ArrayList<String> lines = new ArrayList<String>();
		String bar = "-----------------------------------------------------";
		System.out.println(bar.length());
		String sentence = "";


		tw.append(bar + "\n-");
		for(String word: words){
			if((sentence +" " + word).length() < bar.length()){
				sentence += " "+  word;
				tw.append(" "+word);
			}else{
				tw.append("\n-");
				lines.add(sentence);
				sentence = word;
			}
		}
		tw.append("\n");

		tw.append(bar + "\n");

		/*
		tw.append(bar + "\n");

		for(String line: lines){
			System.out.println(line);
			tw.append(line + "\n");
		}
		tw.append(bar + "\n");
			*/
	}
}
