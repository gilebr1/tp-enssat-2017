package fr.enssat.identifiantbinome.monapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

public class DisplayActivity extends AppCompatActivity {
    protected static final String EXTRA_KEY_MESSAGE = "message";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display);
        String text = this.getIntent().getStringExtra(EXTRA_KEY_MESSAGE);
        TextView txt = this.findViewById(R.id.textview);
        txt.setText(text);
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
    }

    //appelé avant onPause
    //on peut sauvegarder des infos utiles a une prochaine création
    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
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
    // rappel pour activer le menu caché options pour développeur cliquer 5 fois de suite sur Version du Noyau : .. A propos du téléphone >
    // attention non appelé en cas de destruction direct par l'utilisateur, bouton récents > presentation des activités > slide vers la droite de l'app pour suppression.
    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

}



