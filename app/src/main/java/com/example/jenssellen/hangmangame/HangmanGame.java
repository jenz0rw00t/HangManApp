package com.example.jenssellen.hangmangame;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class HangmanGame {

    private Random random = new Random();
    private String theWord;
    private int triesLeft = 10;
    private ArrayList<String> allWords;
    private String wrongGuesses = "";
    private boolean[] visible;
    private Set<String> guessesList = new HashSet<>();
    private boolean activeGame;

    public HangmanGame(Set<String> allWordsSet){
        allWords = new ArrayList<>(allWordsSet);
        activeGame = true;
        newWord();
    }

    public Set<String> getGuessesList() {
        return guessesList;
    }

    public HangmanGame (Set<String> allWordsSet, String theWord, Set<String> guessesList){
        allWords = new ArrayList<>(allWordsSet);
        this.theWord = theWord;
        visible = new boolean[theWord.length()];
        activeGame = true;
        String[] guesses = guessesList.toArray(new String[0]);
        for(String s: guesses){
            guess(s.charAt(0));
        }
    }

    public boolean isActiveGame() {
        return activeGame;
    }

    public Set<String> getAllWordsSet() {
        return new HashSet<>(allWords);
    }

    public void guess(char guess){
        guessesList.add(Character.toString(guess));
        int indexOfGuess = theWord.indexOf(guess);
        if (indexOfGuess<0) {
            triesLeft--;
            wrongGuesses+=guess+", ";
        } else {
            while (indexOfGuess>=0){
                visible[indexOfGuess] = true;
                indexOfGuess=theWord.indexOf(guess, indexOfGuess+1);
            }
        }
    }

    public boolean hasLost(){
        return triesLeft <= 0;
    }

    public boolean hasWon() {
        for (boolean b : visible) {
            if (!b)
                return false;
        }
        return true;
    }

    public void clearGuesses(){
        guessesList.clear();
    }

    public boolean hasUsedLetter(char guess){
        if(guessesList != null){
            return guessesList.contains(Character.toString(guess));
        } else {
            return false;
        }
    }

    public void newWord(){
        theWord = allWords.get(random.nextInt(allWords.size()));
        allWords.remove(theWord);
        visible = new boolean[theWord.length()];
        activeGame = true;
    }

    public String getHiddenWord() {
        String text = "";
        for (int i = 0; i < theWord.length() ; i++) {
            if(visible[i]){
                text+=theWord.charAt(i);
            } else {
                text+="-";
            }
        }
        return text;
    }

    public void setActiveGame(boolean activeGame) {
        this.activeGame = activeGame;
    }

    public String getTheWord() {
        return theWord;
    }

    public int getTriesLeft() {
        return triesLeft;
    }

    public String getWrongGuesses() {
        return wrongGuesses;
    }

}
