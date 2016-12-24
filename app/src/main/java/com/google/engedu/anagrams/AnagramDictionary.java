package com.google.engedu.anagrams;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Random;


public class AnagramDictionary {

    private static final int MIN_NUM_ANAGRAMS = 5;
    private static final int DEFAULT_WORD_LENGTH = 3;
    private static final int MAX_WORD_LENGTH = 7;
    private Random random = new Random();
    private HashSet <String> wordSet=new HashSet<String>();
    private ArrayList <String> wordList=new ArrayList<String>();
    private HashMap <String,ArrayList> lettersToWord=new HashMap<String,ArrayList>();

    public  String alphabeticalorder(String word_to_sort)
     {
        char[] chars=word_to_sort.toCharArray();
         Arrays.sort(chars);
         String sorted=new String(chars);
         return sorted;

     }
    public AnagramDictionary(InputStream wordListStream) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(wordListStream));
        String line;
        String key;
        while((line = in.readLine()) != null) {
            String word = line.trim();
            wordList.add(word);
            wordSet.add(word);
            key=alphabeticalorder(word);
            if(lettersToWord.containsKey(key)) {
                lettersToWord.get(key).add(word);
            }
            else{
                ArrayList<String>  str_list=new ArrayList <String>();
                str_list.add(word);
                lettersToWord.put(key,str_list);

            }

        }


    }

    public boolean isGoodWord(String word, String base) {

        {
            if(wordSet.contains(word))
            {
                if(!word.contains(base)){
                    return true;
                }

            }
        }

        return false;
    }


    public ArrayList<String> getAnagrams(String targetWord) {
        String temp=targetWord;
        char[] chars;
        ArrayList <String> result = new ArrayList<String>();
        chars=temp.toCharArray();
        Arrays.sort(chars);
        temp= String.valueOf(chars);

        if(lettersToWord.containsKey(temp)){

            result=(ArrayList)lettersToWord.get(temp);

        }


        return result;
    }

    public ArrayList<String> getAnagramsWithOneMoreLetter(String word) {
        char c;
        String temp=word;

        ArrayList<String> result = new ArrayList<String>();
        ArrayList<String> temp_list=new ArrayList<String>();
        for(c='a';c>='a'&&c<='z';c++)
        {
            temp=temp+c;

            temp_list=getAnagrams(temp);
            for(int i=0;i<temp_list.size();i++){
                if(!(isGoodWord(temp_list.get(i),word))){
                    temp_list.remove(i);
                }

            }

            result.addAll(temp_list);
            temp=word;
        }

        return result;
    }

    public String pickGoodStarterWord() {
        Random r = new Random();
        int index=r.nextInt(wordList.size());
        ArrayList  <String> ana=new ArrayList <String>();
        while(true){
            ana=getAnagramsWithOneMoreLetter(wordList.get(index));
            if(ana.size()!=0){
                return wordList.get(index);
            }
            else
                index=r.nextInt(wordList.size());
        }
    }
}
