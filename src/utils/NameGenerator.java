package utils;

import java.io.File;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class NameGenerator {
    private Random rand = new Random();
    private ArrayList<String> names;

    Scanner scanner;
    public NameGenerator(String filePath){
        File file = new File(filePath); //get the file
        try{
            scanner = new Scanner(file); //get the file in the scanner to be read
        }catch(Exception e){
            System.out.println(e);
        }
        names = new ArrayList<>(); //place to store the names from the file
        while(scanner.hasNext()){       //loop through the file lines
            names.add(scanner.nextLine());  //add the name to the list
        }
        // randomly get a name from the list


    }

    public String getAName(){
        String name = names.get(rand.nextInt(names.size()));
        // return name
        return name;
    }



    public static void main(String[] args){
        System.out.println(new NameGenerator("src/Names.txt").getAName());

    }
}
