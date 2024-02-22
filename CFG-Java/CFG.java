/*
  Course: CS 41000
  Project: CFG Project
  Name:Romulo Gutierrez
  Email:gutie136@pnw.edu
  
  Java 8
  Compiler Used: Amazon Correto JDK 8.0
  IDE Used: DrJava 
  
*/
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import static java.lang.Math.toIntExact;
import java.util.Scanner;
import java.io.File;
import java.io.FileWriter;

public class CFG {
    
    public static String readFileAtLine(int n) { // used to read file
        String line = "";
        try {
            line = Files.readAllLines(Paths.get("CFG.txt")).get(n-1);
        } 
        catch(IOException e){
            System.out.println(e);
            System.exit(0);
        }
        return line;
    }
    
    public static void main (String[]args) {
        // Track the length of the lines in the file
        long count = 0;
        try {
        Path file = Paths.get("CFG.txt");
        count = Files.lines(file).count(); 
        System.out.println("Total Lines: " + count);
        } 
        catch (Exception e) {
            e.getStackTrace();
            
        }
        int countVal = toIntExact(count);
        int ruleCount = countVal-4;
        String p[] = new String[countVal];
        
        // V chunk - all nonterminals and terminals
        String v = readFileAtLine(1).substring(3);
        v = (v.replaceAll("," , "")).replaceAll(" " , "");
       // System.out.println (v);
        // T chunk - terminals
        String t = readFileAtLine(2).substring(3);
        t = (t.replaceAll("," , "")).replaceAll(" " , "");
        v = v.replaceAll(t , "");
        
    //    System.out.println (t);
        // S chunk - starting
        String s = readFileAtLine(3).substring(3);
        String vMarked = v;
        v = v.replaceAll(s , "");
        //System.out.println (s);
        // P chunk - rules
        for (int i = 0 ; i < ruleCount; i++){
            p[i] = readFileAtLine(i+5);
          //  System.out.println (p[i]);
        }
        
        //marked production grammar array
        int mark[] = new int[ruleCount];
        Arrays.fill(mark, 0);
        // 0 is unmarked, 1 is marked, -1 is removed
        
        String nonTerminals = (v.replaceAll(t, "")).replaceAll(s, "");
        
        //removeunproductive
            // mark all starting transitions and rules with terminals
            for (int i = 0 ; i < ruleCount; i++) {
                
                String testString = (p[i].substring(3));
                if ( p[i].indexOf(s) == 0 || testString.equals(testString.toLowerCase()) ){
                    mark[i] = 1;
                   v = v.replaceAll(p[i].substring(0,1) ,"");
                } 
            }
            
            //Loop through the nonterminals and rules to mark those productive
            int check;
            for (int k = 0; k < ruleCount; k++) {
                for (int j = 0; j < ruleCount; j++) {
                    String tempString = p[j].substring(4);
                    tempString = tempString.replaceAll("([a-z])" , "");
                    for (int i = 0; i < tempString.length(); i++) {
                        check = v.indexOf(tempString.substring(i,i+1));
                        //System.out.println (check);
                        if (check == -1) {
                            mark[j] = 1;
                            v = v.replaceAll(p[j].substring(0,1) ,"");
                        }
                    }
                }
            }
            
            //turn all 0 to -1 
        for (int i = 0; i < ruleCount; i++) {
            if (mark[i] == 0) {mark[i] = -1;}
        }   // turn all 1 to 0
        for (int i = 0; i < ruleCount; i++) {
            if (mark[i] == 1) {mark[i] = 0;}
        }
            //removerules from the list
        for (int i = 0; i < v.length(); i++) {
            vMarked = vMarked.replaceAll(v.substring(i,i+1) ,"");
        }
            //reset the v chunk
        v = vMarked.substring(1);
        
        //removeunreachable
            //run through the both side to see if accessible
            //mark the start states first
        for (int j = 0; j < ruleCount; j++) {
            for (int i = 0; i < ruleCount; i++) {
                if (mark[i] == -1) {} // passover if removed
                else if ( (p[i].substring(0,1)).equals(s)) {mark[i] = 1;} // mark if starting state
                else {
                    for (int k = 0; k < ruleCount;k++) {
                        String compareStr = p[k].substring(4);
                        compareStr = compareStr.replaceAll("([a-z])" , "");
                        for (int u = 0; u < compareStr.length(); u++ ){
                            if ((p[i].substring(0,1)).equals(compareStr.substring(u,u+1)) && mark[k] == 1) {
                                mark[i]=1;
                                v = v.replaceAll(p[i].substring(0,1) ,"");
                                }
                        }
                    }
                }
                    
            }
               
        }
            //removerules from the list
        for (int i = 0; i < v.length(); i++) {
            vMarked = vMarked.replaceAll(v.substring(i,i+1) ,"");
        }
            //turn all 0 to -1 
        for (int i = 0; i < ruleCount; i++) {
            if (mark[i] == 0) {mark[i] = -1;}
        }
          
      //print the label
        try {
          FileWriter myWriter = new FileWriter("CFG-simplified.txt");
          
          myWriter.write("V: ");
          for (int x = 0; x < vMarked.length(); x++) {
            if (x == vMarked.length()-1) {
               myWriter.write(vMarked.substring(x,x+1)+", "+ readFileAtLine(2).substring(3) + "\n");}   
            else myWriter.write(vMarked.substring(x,x+1)+", ");
          }                                        //v
          myWriter.write(readFileAtLine(2)+"\n"); // t
          myWriter.write(readFileAtLine(3)+"\n"); // s
          myWriter.write("P: "+"\n"); // p
          for (int x = 0; x < ruleCount; x++){
              if (mark[x]!=-1) {
                 myWriter.write(p[x]+"\n");
              }
          }
          
          
          myWriter.close();
          System.out.println("Successfully wrote to the file.");
    } catch (IOException e) {
          System.out.println("An error occurred.");
          e.printStackTrace();
    }
      
  }
}

