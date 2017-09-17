package com.example.meiyain.hangman;

import java.util.ArrayList;


public class MainHandler {

    private final int MAX_GUESS = 10;
    private int wrongGuess;
    private final int totalWords;
    private int gameWon;
    private int gameLost;

    private ArrayList<String> words;
    private String[] letters;
    private String correctWord;


    public MainHandler(String language) {
        createWords(language);
        gameWon = 0;
        gameLost = 0;
        totalWords = words.size();
    }

    public int getWins() {

        return gameWon;
    }

    public int getLoss() {

        return gameLost;
    }

    public int getTotalWords() {

        return totalWords;
    }

    public int getWordsLeft() {

        return words.size() + 1;
    }

    public int getWrongGuess() {

        return wrongGuess;
    }

    /**
     * Oppretter en liste med ord til spilleren for å gjette på.
     * To lister med ord, men bare en blir tatt i bruk av gangen avhengig av hvilket spåk som er valgt.
     */
    private void createWords(String language) {
        if (language.equals("no")) {
            ArrayList no = new ArrayList();
            no.add("brød");
            no.add("ost");
            no.add("spekeskinke");
            no.add("syltetøy");
            no.add("agurk");
            no.add("appelsin");
            no.add("eple");
            no.add("melk");
            no.add("peanøttsmør");
            no.add("makrell i tomat");
            words = no;

        } else {
            ArrayList en = new ArrayList();
            en.add("bread");
            en.add("cheese");
            en.add("ham");
            en.add("jam");
            en.add("cucumber");
            en.add("Orange");
            en.add("apple");
            en.add("juice");
            en.add("peanut butter");
            en.add("mackerel in tomato");
            words = en;
        }
    }

    /**
     * Finner inn et nytt ord ved hjelp av getWord().
     */
    public int newWord() {
        wrongGuess = 0;
        letters = getWord();
        return letters.length;
    }

    /**
     * Finner et nytt ord og sletter det fra listen med ordene.
     */
    private String[] getWord() {
        String[] temp = new String[0];
        if (words.size() != 0) {
            int num = randomNr();
            correctWord = words.get(num);
            words.remove(num);
            temp = correctWord.split("");
        }
        return temp;
    }

    /**
     * Finner en bokstav til den gitte posisjonen.
     */
    public String getLetter(int pos) {
        return letters[pos];
    }


    /**
     * Genererer et tilfeldig nummer fra 0 til words.size().
     */
    private int randomNr() {
        java.util.Random r = new java.util.Random();
        int rand = r.nextInt(words.size());
        return rand;
    }


    /**
     * Finner hver posisjon av en gitt bokstav.
     */
    private ArrayList<Integer> getLetterWordPos(String letter) {
        ArrayList<Integer> temp = new ArrayList<Integer>();
        for (int i = 0; i < letters.length; i++) {
            if (letters[i].toUpperCase().equals(letter)) {
                temp.add(i);
            }
        }
        return temp;
    }

    /**
     * Sjekker om en bokstav eksiterer i det ordet man skal gjette seg fram til.
     * Hvis ikke bokstanven fins i ordet vil den bli lagt til i wrongGuesses.
     */
    public ArrayList<Integer> guessLetter(String letter) {
        ArrayList pos = getLetterWordPos(letter);
        if (pos.size() == 0) {
            wrongGuess++;
        }
        return pos;
    }


    /**
     * Sjekker spill status.
     * OVER - taper om man har nådd maks antall wrongGuesses.
     * WON - vinner om man har gjettet fram til ordet med enn maks antall wrongGuesses.
     * CONTINUE - om man ikke har vunnet eller tapt.
     */
    public int gameStatus(String word) {
        if (MAX_GUESS <= wrongGuess) {
            gameLost++;
            return MainActivity.OVER;
        } else if (word.replaceAll(" ", "").equals(correctWord.replaceAll(" ", ""))) {
            gameWon++;
            return MainActivity.WON;
        } else {
            return MainActivity.CONTINUE;
        }
    }
}
