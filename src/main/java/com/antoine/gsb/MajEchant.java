package com.antoine.gsb;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.antoine.gsb.database.BdAdapter;
import com.antoine.gsb.modele.Echantillon;
import com.antoine.gsb.R;

public class MajEchant extends AppCompatActivity {

    private EditText etCode;
    private EditText etQuantite;
    private Button bSupprimer;
    private Button bAjouter;
    private Button bQuitter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.maj_echant);

        etCode = findViewById(R.id.majEditTextCode);
        etQuantite = findViewById(R.id.majEditTextQte);
        bSupprimer = findViewById(R.id.majButtonSupprimer);
        bAjouter = findViewById(R.id.majButtonAjouter);
        bQuitter = findViewById(R.id.majButtonQuitter);

        bQuitter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        bSupprimer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                majSupprimerQteEchantillon();
            }
        });

        bAjouter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                majAjoutQteEchantillon();
            }
        });
    }

    public void majAjoutQteEchantillon() {
        BdAdapter adapter = new BdAdapter(this);
        adapter.open();

        Echantillon echantillon = adapter.getEchantillonWithLib(etCode.getText().toString());

        if (etQuantite.getText().toString().matches("")) {
            Toast.makeText(this, "Veuillez remplir tous les champs", Toast.LENGTH_LONG).show();
            return;
        }

        int stock = Integer.parseInt(echantillon.getQuantiteStock());
        int stockToAdd = Integer.parseInt(etQuantite.getText().toString());
        int newQuantite = stock + stockToAdd;

        if (stockToAdd <= 0) {
            Toast.makeText(this, "La quantité à ajouter doit être supérieur à 0", Toast.LENGTH_LONG).show();
            return;
        }

        Toast.makeText(this, "Le stock concernant le code saisi est mis à jour", Toast.LENGTH_LONG).show();
        echantillon.setQuantiteStock(String.valueOf(newQuantite));
        adapter.updateEchantillon(echantillon.getCode(),echantillon);
    }

    public void majSupprimerQteEchantillon() {
        BdAdapter adapter = new BdAdapter(this);
        adapter.open();

        Echantillon echantillon = adapter.getEchantillonWithLib(etCode.getText().toString());

        if (etQuantite.getText().toString().matches("")) {
            Toast.makeText(this, "Veuillez remplir tous les champs", Toast.LENGTH_LONG).show();
            return;
        }

        int stock = Integer.parseInt(echantillon.getQuantiteStock());
        int stockToAdd = Integer.parseInt(etQuantite.getText().toString());
        int newQuantite = stock - stockToAdd;

        if (newQuantite <= 0) {
            Toast.makeText(this, "Il n'y a pas assez de stock", Toast.LENGTH_LONG).show();
            return;
        }

        Toast.makeText(this, "Le stock concernant le code saisi est mis à jour", Toast.LENGTH_LONG).show();
        echantillon.setQuantiteStock(String.valueOf(newQuantite));
        adapter.updateEchantillon(echantillon.getCode(),echantillon);
    }
}