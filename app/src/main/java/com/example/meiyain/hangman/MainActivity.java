package com.example.meiyain.hangman;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.ArrayList;


public class MainActivity extends Activity {

    public final static int OVER = -1;
    public final static int CONTINUE = 0;
    public final static int WON = 1;
    public final static int END = 2;
    public final static int RULE = 3;

    private MainHandler handler;
    private String[] guessedWords;
    private String statusWonLost;
    private String statusTotalLeft;


    /**
     * Metode som legger en streng til TextView med id = guessed_letters.
     * For hver feil gjettet bokstav vil hangman bilde endre seg og være lik antall mislykkede forsøk.
     */
    private void wrongLetters(String letter) {
        TextView textView = (TextView) findViewById(R.id.guessedLetters);
        if (letter.isEmpty()) {
            textView.setText("");
        }
        textView.append(letter + "  ");
        updateView();
    }


    /**
     * Funksjon som gjør en bokstav usynlig dersom den blir trykket og sjekker om det ordet man
     * skal gjette seg fram til inneholder den valgte bosktaven.
     */
    public void onClickButtonLetter(View view) {
        Button button = (Button) view;
        button.setVisibility(view.INVISIBLE);
        String letter = button.getText().toString();
        ArrayList<Integer> pos = handler.guessLetter(letter);
        if (pos.size() == 0) {
            wrongLetters(letter);
        } else {
            updateGuessedWord(pos);
        }
        checkStatus();
    }

    /**
     * Funksjon for oppdatering av hangman image view
     */
    private void updateView() {
        ImageView imageView = (ImageView) findViewById(R.id.hangman);
        int error = handler.getWrongGuess();
        switch (error) {
            case 0:
                imageView.setImageResource(R.drawable.hang_0);
                break;
            case 1:
                imageView.setImageResource(R.drawable.hang_1);
                break;
            case 2:
                imageView.setImageResource(R.drawable.hang_2);
                break;
            case 3:
                imageView.setImageResource(R.drawable.hang_3);
                break;
            case 4:
                imageView.setImageResource(R.drawable.hang_4);
                break;
            case 5:
                imageView.setImageResource(R.drawable.hang_5);
                break;
            case 6:
                imageView.setImageResource(R.drawable.hang_6);
                break;
            case 7:
                imageView.setImageResource(R.drawable.hang_7);
                break;
            case 8:
                imageView.setImageResource(R.drawable.hang_8);
                break;
            case 9:
                imageView.setImageResource(R.drawable.hang_9);
                break;
            case 10:
                imageView.setImageResource(R.drawable.hang_complete);
            default:
                break;
        }
    }

    /**
     * Funksjon som fyller bostaver og tomme plasser med '_' i feltet med gjettede ord.
     */
    private void updateGuessedWord(ArrayList<Integer> pos) {
        if (pos == null) {
            for (int i = 1; i < guessedWords.length; i++) {
                if (handler.getLetter(i).equals(" ")) {
                    guessedWords[i] = "  ";
                } else {
                    guessedWords[i] = "_";
                }
            }
        } else {
            for (int i = 0; i < pos.size(); i++) {
                guessedWords[pos.get(i)] = handler.getLetter(pos.get(i));
            }
        }
        updateGuessedWordView();
    }

    /**
     * Oppdaterer GuessedWordView for å se hvilken bokstav som er riktig.
     */
    private void updateGuessedWordView() {
        TextView textView = (TextView) findViewById(R.id.guessedWord);
        textView.setText(getWordGuessedWord());
    }

    /**
     * Hjelpemetode for å gjøre om guessedWord array til en streng med to mellomrom på slutten av hvert
     * bokstav, for å gjøre det lettere å hvert bokstav.
     */
    private String getWordGuessedWord() {
        String word = "";
        for (int i = 1; i < guessedWords.length; i++) {
            word += guessedWords[i] + "  ";
        }
        return word;
    }

    /**
     * Funksjon som oppdaterer antall seier, tap og ord igjen.
     */
    private void updateWonLostView() {
        TextView textView = (TextView) findViewById(R.id.viewWonLost);
        statusWonLost =
                getResources().getString(R.string.won) + ": " + handler.getWins() + " " +
                "\n" + getResources().getString(R.string.lost) + ": " + handler.getLoss() + " ";
        textView.setText(statusWonLost);
    }

    private void updateWordsTotalLeft(){
        TextView textView = (TextView) findViewById(R.id.viewTotalLeft);
        statusTotalLeft =
                getResources().getString(R.string.word_total) + ": " + handler.getTotalWords() +
                "\n" + getResources().getString(R.string.word_left) + ": " + handler.getWordsLeft();
        textView.setText(statusTotalLeft);
    }

    /**
     * Funksjon for å sjekke hvor langt du har kommet i spillet. kaller samme metode med ulike
     * variabler.
     */
    private void checkStatus() {
        int status = handler.gameStatus(getWordGuessedWord());
        switch (status) {
            case OVER:
                updateWonLostView();
                updateWordsTotalLeft();
                gameDialog(getResources().getString(R.string.game_lost), END);
                break;
            case WON:
                updateWonLostView();
                updateWordsTotalLeft();
                gameDialog(getResources().getString(R.string.game_won), END);
                break;
            case CONTINUE:
                break;
        }
    }

    /**
     * Denne metoden viser tre forskjellige views avhengig av spill statusen og hvor langt ut i
     * spillet man har kommet.
     */
    private void gameDialog(String title, int status) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setTitle(title);
        switch (status) {
            case RULE:
                alertDialogBuilder
                        .setMessage(getResources().getString(R.string.game_rules))
                        .setCancelable(false)
                        .setPositiveButton(getResources().getString(R.string.game_continue), new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
                break;
            case END:
                if (handler.getWordsLeft() == 1) {
                    alertDialogBuilder
                            .setMessage(getResources().getString(R.string.game_end) + "\n" + statusWonLost)
                            .setCancelable(false)
                            .setPositiveButton(getResources().getString(R.string.main_menu), new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    goToMainMenu();
                                }
                            });
                } else {
                    alertDialogBuilder
                            .setMessage(statusWonLost + "\n" + statusTotalLeft + "\n\n" + getResources().getString(R.string.game_end_message))
                            .setCancelable(false)
                            .setNegativeButton(getResources().getString(R.string.main_menu), new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    goToMainMenu();
                                }
                            })
                            .setPositiveButton(getResources().getString(R.string.game_continue), new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    newGame();
                                    dialog.cancel();
                                }
                            });
                }
                break;
        }
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    /**
     * Funksjon som oppretter et nytt spill.
     * Kaller et nytt ord for å spillet og oppdaterer alle views.
     */
    private void newGame() {
        guessedWords = new String[handler.newWord()];
        updateGuessedWord(null);
        updateWonLostView();
        updateWordsTotalLeft();
        wrongLetters("");
        resetButtons();
    }

    /**
     * Hjelpemetode som går gjennom alle bokstav knapper og gjør dem synlige igjen.
     */
    private void resetButtons() {
        TableLayout buttonGroup = (TableLayout) findViewById(R.id.all_Letters);
        for (int i = 0; i < buttonGroup.getChildCount(); i++) {
            TableRow row = (TableRow) buttonGroup.getChildAt(i);
            for (int j = 0; j < row.getChildCount(); j++) {
                Button button = (Button) row.getChildAt(j);
                button.setVisibility(View.VISIBLE);
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.hangman, menu);
        return true;
    }

    /**
     *Meny valg, hvis valgt:
     * spilltips --> viser spillreglene
     * spillmeny --> går til hovedmeny.
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.main_menu) {
            goToMainMenu();
            return true;
        }
        if (id == R.id.rules_button) {
            gameDialog(getResources().getString(R.string.game_rules_button), RULE);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    /**
     * Metode for å gå tilbake til hovedmenyen og avslutter spillet.
     */
    private void goToMainMenu() {
        startActivity(new Intent(this, MainMenu.class));
        finish();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hangman_activity);
        handler = new MainHandler(getResources().getConfiguration().locale.getLanguage());
        guessedWords = new String[handler.newWord()];
        updateGuessedWord(null);
        updateWonLostView();
        updateWordsTotalLeft();
    }
}
