package fr.enssat.identifiantbinome.monapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import static fr.enssat.identifiantbinome.monapplication.DisplayActivity.EXTRA_KEY_MESSAGE;

public class MainActivity extends AppCompatActivity {

    private static String KEY_BUNDLE = "message";

    private String _message;

    //A la creation de l'activité
    //le bundle savedInstanceState est null à la 1ere création.
    //le bundle savedInstanceState n'est pas null a la nieme creation
    //et ou si onSavedInstanceState() a été invoqué
    // exemple activé dans vos paramètres le mode rotation automatique
    // et tourner votre ecran pour passer de portait à paysage et vice versa ...
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        if (savedInstanceState != null) {
            _message = savedInstanceState.getString(KEY_BUNDLE);
        }
        else {
            _message = "1ere création";
        }


        setContentView(R.layout.activity_main);
        final EditText edit = this.findViewById(R.id.editText);
        Button bt = this.findViewById(R.id.button);
        bt.setOnClickListener(new View.OnClickListener()
                              {
                                  @Override
                                  public void onClick(View v)
                                  {   Intent intent = new Intent(MainActivity.this, DisplayActivity.class);
                                      if (edit.getText()!=null){intent.putExtra(EXTRA_KEY_MESSAGE, edit.getText().toString());}
                                      startActivity(intent);
                                  }
                              }

        );
    }

    //lorsque l'activité est affiché à l'ecran
    @Override
    protected void onStart() {
        super.onStart();
    }

    //A chaque fois que l'activité passe du background en foreground - l'activité est affiché à l'ecran AVEC le focus, prêt pour une interaction avec l'utilisateur.
    @Override
    protected void onResume() {
        super.onResume();
        Toast.makeText(this, _message, Toast.LENGTH_LONG).show();
    }

    //appelé avant onPause
    //on peut sauvegarder des infos utiles a une prochaine création
    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putString(KEY_BUNDLE, " ce n'est pas la 1ere création de cette activité");
        super.onSaveInstanceState(savedInstanceState);
    }

    //A chaque fois que l'activité passe du foreground en background - PERTE de focus  bouton retour, bouton récents, mode split screen - appui long sur bouton  récents
    //l'activité reste visible mais sans focus par exemple un media player en cours de lecture d'une video...
    @Override
    protected void onPause() {
        super.onPause();
    }

    //l'activité n'est plus affichée à l'écran
    @Override
    protected void onStop() {
        super.onStop();
    }

    // appelé lorsque le systeme Android tue l'app
    // pour s'assurer de la destruction:
    // dans paramètre du terminal Android  ... > options pour développeurs > Applications > Ne pas conserver activités
    //rappel pour activer le menu caché options pour développeur cliquer 5 fois de suite sur Version du Noyau : .. A propos du téléphone >
    // attention non appelé en cas de destruction direct par l'utilisateur, bouton récents > presentation des activités > slide vers la droite de l'app pour suppression.
    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    //plus ici: https://developer.android.com/guide/components/activities/intro-activities.html
}
