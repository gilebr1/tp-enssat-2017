package fr.enssat.identifiantbinome.monapplication;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import static fr.enssat.identifiantbinome.monapplication.R.id.buttonDefinir;

public class NewPersonActivity extends AppCompatActivity
{
    protected static String NB_PERSONNE = "nombre_de_personne_a_ajouter";
    protected static String NOM_PERSONNE = "nom_de_personne_a_ajouter";
    protected static String DATE_PERSONNE = "naissance_de_personne_a_ajouter";

    private static String DATE_FORMAT = "dd/MM/yyyy";

    private List<Personne> _listePersonnesAjoutees;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_person);

        _listePersonnesAjoutees = new ArrayList<>();

        final EditText editNaissance= findViewById(R.id.editNaissance);
        final Calendar cal = Calendar.getInstance();
        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                cal.set(Calendar.YEAR, year);
                cal.set(Calendar.MONTH, monthOfYear);
                cal.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                editNaissance.setText(new SimpleDateFormat(DATE_FORMAT, Locale.FRANCE).format(cal.getTime()));
            }
        };

       editNaissance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(NewPersonActivity.this, date, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH),
                        cal.get(Calendar.DAY_OF_MONTH)).show();
            }
        });


       final EditText editNom= findViewById(R.id.editNom);


       final NewPersonActivity me = this;
       Button bt = findViewById(buttonDefinir);
       bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Personne aux = new Personne(editNom.getText().toString(),editNaissance.getText().toString());
                _listePersonnesAjoutees.add(aux);
                Toast.makeText(me, editNom.getText().toString() + " pris en compte pour l'ajout", Toast.LENGTH_LONG).show();
                editNom.setText(null);
                editNaissance.setText(null);
            }
        });
    }

    @Override
    public void onBackPressed()
    {   Bundle bundle = new Bundle();
        bundle.putInt(NB_PERSONNE,_listePersonnesAjoutees.size());
        int i = 0;
        for (Personne aux : _listePersonnesAjoutees) {
            bundle.putString(NOM_PERSONNE+i, aux.getNom());
            bundle.putString(DATE_PERSONNE+i, aux.getNaissance());
            i++;
        }
        Intent resultIntent = new Intent();
        resultIntent.putExtras(bundle);
        //donner le résultat avant d'appeler la super classe.
        setResult(RESULT_OK, resultIntent);

        super.onBackPressed();
    }
}
