package com.antoine.gsb;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.antoine.gsb.database.BdAdapter;
import com.antoine.gsb.modele.Echantillon;
import com.antoine.gsb.R;

public class AjoutEchant extends AppCompatActivity {

    private EditText etCode;
    private EditText etLibelle;
    private EditText etStock;
    private Button bAjouter;
    private Button bQuitter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ajout_echant);

        etCode = findViewById(R.id.ajoutEditTextCode);
        etLibelle = findViewById(R.id.ajoutEditTextLib);
        etStock = findViewById(R.id.ajoutEditTextStock);
        bAjouter = findViewById(R.id.ajoutButtonAjouter);
        bQuitter = findViewById(R.id.ajoutButtonQuitter);

        bAjouter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ajoutEchantillon();
            }
        });

        bQuitter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    public void ajoutEchantillon() {
        BdAdapter adapter = new BdAdapter(this);

        if (etStock.getText().toString().matches("") ||
                etLibelle.getText().toString().matches("") ||
                etCode.getText().toString().matches("")) {
            Toast.makeText(this, "Veuillez remplir tous les champs", Toast.LENGTH_LONG).show();
            return;
        }

        int stock = Integer.parseInt(etStock.getText().toString());
        if (stock <= 0) {
            Toast.makeText(this, "La quantité doit être supérieur à 0", Toast.LENGTH_LONG).show();
            return;
        }
        adapter.open();
        adapter.insererEchantillon(new Echantillon(
                etCode.getText().toString(),
                etLibelle.getText().toString(),
                etStock.getText().toString()
        ));
        Toast.makeText(this, "Un échantillon a été ajouté", Toast.LENGTH_LONG).show();
        adapter.close();
    }
}