package com.example.meiyain.hangman;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.util.Locale;


public class MainMenu extends Activity {

    /**
     * Velger engelsk som språk om man trykker på UK-flagget.
     */
    public void onClickUK(View view) {
        Locale locale = new Locale("en", "en_GB");
        Configuration config = new Configuration();
        config.locale = locale;
        Resources res = getBaseContext().getResources();
        res.updateConfiguration(config, res.getDisplayMetrics());
        goToPlayMenu();
    }

    /**
     * Velger norsk som språk om man trykker på NO-flagget.
     */
    public void onClickNO(View view) {
        Locale locale = new Locale("no", "NO");
        Configuration config = new Configuration();
        config.locale = locale;
        Resources res = getBaseContext().getResources();
        res.updateConfiguration(config, res.getDisplayMetrics());
        goToPlayMenu();
    }

    /**
     * Starter spillmneyen når man har valgt et språk.
     */
    private void goToPlayMenu() {
        setContentView(R.layout.play_activity);
    }

    /**
     * Starter selve spillet.
     */
    public void onClickStartGame(View view) {
        startActivity(new Intent(this, com.example.meiyain.hangman.MainActivity.class));
        finish();
    }

    /**
     * Deaktiverer tilbake knappen som er på telefonen.
     */
    @Override
    public void onBackPressed() {
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
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
        if(id == R.id.exit){
            goOutOfApp();
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

    /**
     * Metode for å gå ut av hangman appen.
     */
    public void goOutOfApp() {

        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
    }
}
