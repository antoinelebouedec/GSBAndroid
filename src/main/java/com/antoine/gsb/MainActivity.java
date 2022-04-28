package com.antoine.gsb;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.antoine.gsb.R;
import com.antoine.gsb.database.BdAdapter;
import com.antoine.gsb.modele.Echantillon;

public class MainActivity extends AppCompatActivity {

    protected void onCreate(Bundle savedInstanceState) {

        //operationsBd();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*
        //test de la BD
        testBd(); */

        Button buttonAjoutEchant = (Button)findViewById(R.id.menuButtonAjoutEchantillon);
        Button buttonListeEchant = (Button)findViewById(R.id.menuButtonListeEchantillons);
        Button buttonMajEchant = (Button)findViewById(R.id.menuButtonMajEchantillons);

        //On crée un écouteur d'évènement commun au trois Button
        View.OnClickListener onClickLister = new View.OnClickListener() {

            @Override

            public void onClick(View v){

                switch(v.getId()){
                    //si on a cliqué sur le button Ajout
                    case R.id.menuButtonAjoutEchantillon:
                        Toast.makeText(getApplicationContext(), "ouverture fenêtre Ajout !", Toast.LENGTH_LONG).show();
                        Intent intentAjout = new Intent(MainActivity.this, AjoutEchant.class);
                        startActivity(intentAjout);
                        break;

                    case R.id.menuButtonListeEchantillons:
                        Toast.makeText(getApplicationContext(), "ouverture fenêtre Liste !", Toast.LENGTH_LONG).show();
                        Intent intentListe = new Intent(MainActivity.this, ListeEchant.class);
                        startActivity(intentListe);
                        break;

                    case R.id.menuButtonMajEchantillons:
                        Toast.makeText(getApplicationContext(), "ouverture fenêtre Maj !", Toast.LENGTH_LONG).show();
                        Intent intentMaj = new Intent(MainActivity.this, MajEchant.class);
                        startActivity(intentMaj);
                        break;
                }
            }
        };

        buttonAjoutEchant.setOnClickListener(onClickLister); //déclaration d’un objet source d’évênement
        buttonListeEchant.setOnClickListener(onClickLister);
        buttonMajEchant.setOnClickListener(onClickLister);

    }

    public boolean onCreateOptionsMenu (Menu menu){
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.option_menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected (MenuItem item){
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        switch (item.getItemId()) {

            case R.id.ajout:
                Toast.makeText(getApplicationContext(), "ouverture fenêtre Ajout !", Toast.LENGTH_LONG).show();
                Intent intentAjout = new Intent(MainActivity.this, AjoutEchant.class);
                startActivity(intentAjout);
                return true;

            case R.id.liste:
                Toast.makeText(getApplicationContext(), "ouverture fenêtre Liste !", Toast.LENGTH_LONG).show();
                Intent intentListe = new Intent(MainActivity.this, ListeEchant.class);
                startActivity(intentListe);
                return true;

            case R.id.maj:
                Toast.makeText(getApplicationContext(), "ouverture fenêtre Maj !", Toast.LENGTH_LONG).show();
                Intent intentMaj = new Intent(MainActivity.this, MajEchant.class);
                startActivity(intentMaj);
                return true;

            case R.id.quitter:
                finish();
        }

        return false;

    }

    public void testBd(){

        //Création d'une instance de la classe echantBDD
        BdAdapter echantBdd = new BdAdapter(this);
        //Création d'un Echantillon
        Echantillon unEchant = new Echantillon("code1", "lib1", "3");
        //On ouvre la base de données pour écrire dedans
        echantBdd.open();
        //On insère l'echantillon que l'on vient de créer
        echantBdd.insererEchantillon(unEchant);
        //System.out.println("insertion echantillon");
        //Pour vérifier que l'on a bien créé un echantillon dans la BDD
        //on extrait l’echantillon de la BDD grâce au libelle de l'echantillon que l'on a créé précédemment
        Echantillon unEchantFromBdd = echantBdd.getEchantillonWithLib("lib1");

        //Si un unArticle est retourné (donc si le unEchant à bien été ajouté à la BDD)
        if(unEchantFromBdd != null){
            //On affiche les infos de l’echantillon dans un Toast
            Toast.makeText(this, unEchantFromBdd.getLibelle(), Toast.LENGTH_LONG).show();
            System.out.println("echantillon trouve : "+unEchantFromBdd.getLibelle());
            //On modifie le titre de l’echantillon
            unEchantFromBdd.setLibelle("lib2");
            //Puis on met à jour la BDD
            echantBdd.updateEchantillon(unEchantFromBdd.getCode(), unEchantFromBdd);
        }

        else {
            Toast.makeText(this, "echantillon non trouvé", Toast.LENGTH_LONG).show();
            System.out.println("echantillon non trouvé");
        }

        //On extrait l’Article de la BDD grâce à son nouveau lib
        unEchantFromBdd = echantBdd.getEchantillonWithLib("Lib2");

        //S'il existe un Article possédant cette désignation dans la BDD
        if(unEchantFromBdd != null){
            //On affiche les nouvelles info de l’echantillon pour vérifier que le lib de l’echantillon a bien été maj
            Toast.makeText(this, unEchantFromBdd.getLibelle(), Toast.LENGTH_LONG).show();
            //on supprime le unEchant de la BDD grâce à son ID
            echantBdd.removeEchantillonWithCode(unEchantFromBdd.getCode());
        }

        //On essait d'extraire de nouveau l’echantillon de la BDD toujours grâce à son nouveau libelle
        unEchantFromBdd = echantBdd.getEchantillonWithLib("lib2");

        //Si aucun unEchant n'est retourné
        if(unEchantFromBdd == null){
            //On affiche un message indiquant que l’echantillon n'existe pas dans la BDD
            Toast.makeText(this, "Cet echantillon n'existe pas dans la BDD", Toast.LENGTH_LONG).show();
        }

        else{
            //Si l'echantillon existe (mais normalement il ne devrait pas)
            //on affiche un message indiquant que l’echantillon existe dans la BDD
            Toast.makeText(this, "Cet echantillon existe dans la BDD", Toast.LENGTH_LONG).show();
        }
        echantBdd.close();
    }
}