package fr.enssat.identifiantbinome.monapplication;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.StringTokenizer;

public class RecyclerViewActivity extends AppCompatActivity implements AdapterView.OnItemClickListener
{
    protected int REQUEST_CODE_TO_MATCH = 1234;

    private List<Personne> _listePersonnes;
    private RecyclerViewAdapter _recyclerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_recycler_view);

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        // cellules comme une ListView
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        //cellules avec un gridlayout, 3 cellues par ligne
        //recyclerView.setLayoutManager(new GridLayoutManager(this,3));

        final RecyclerViewActivity me = this;
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {   launchAddPersonne(me);}
        });

        //parsing du .csv
        InputStream inputStream = this.getResources().openRawResource(R.raw.csv_items);
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, Charset.forName("UTF-8")));
        _listePersonnes = new ArrayList<>();
        try {   StringTokenizer st;
                String line;
                while ( (line = reader.readLine() )!= null) {
                    st = new StringTokenizer(line, ",");
                    Personne p= new Personne (st.nextToken(),st.nextToken());
                    _listePersonnes.add(p);
                }
        } catch (IOException e) {
                e.printStackTrace();
        }

        //creation du list adapter avec la liste initiale de personnes
        _recyclerAdapter = new RecyclerViewAdapter(_listePersonnes);
        recyclerView.setAdapter(_recyclerAdapter);
    }

    //creation du menu avec le bouton ajouter
    public boolean onCreateOptionsMenu(Menu menu) {
        this.getMenuInflater().inflate(R.menu.menu_button, menu);
        return super.onCreateOptionsMenu(menu);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.bouton_add:
                launchAddPersonne(this);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

        private void launchAddPersonne(Context ctx )
        {   Intent myIntent = new Intent(ctx, NewPersonActivity.class);
            startActivityForResult(myIntent, REQUEST_CODE_TO_MATCH);
        }

    //ajout des nouvelles personnes à la liste
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == REQUEST_CODE_TO_MATCH) {

            if(resultCode == AppCompatActivity.RESULT_OK){
                //on met à jour la liste de personne avec le bundle reçu
                //on demande à l'adapter de redessiner la listview
                int nb_to_add=data.getIntExtra(NewPersonActivity.NB_PERSONNE,0);
                for (int i=0; i<nb_to_add;i++)
                {   _listePersonnes.add(
                        new Personne(data.getStringExtra(NewPersonActivity.NOM_PERSONNE+i),
                        data.getStringExtra(NewPersonActivity.DATE_PERSONNE+i))
                    );
                }
                Collections.sort(_listePersonnes, new PersonneComparator());

                _recyclerAdapter.notifyDataSetChanged();
            }

            if (resultCode == AppCompatActivity.RESULT_CANCELED) {
                Toast.makeText(this, this.getString(R.string.app_error_1), Toast.LENGTH_LONG).show();
            }
        }
    }

    //suppression de personne sur click d'un element de la liste
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id)
    {   AlertDialog dialog;
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        final int pos = position;

        String button_ok  = this.getString(android.R.string.ok);
        String message    = this.getString(R.string.app_delete,_listePersonnes.get(position).getNom());
        String button_cancel = this.getString(android.R.string.cancel);

        alertDialogBuilder.setMessage(message);
        alertDialogBuilder.setCancelable(true);
        alertDialogBuilder.setNegativeButton(button_cancel, null);
        alertDialogBuilder.setPositiveButton(button_ok, new DialogInterface.OnClickListener()
        {   @Override
            public void onClick(DialogInterface dialog, int which)
            {   _listePersonnes.remove(pos);
                _recyclerAdapter.notifyDataSetChanged();
            }
        });

        dialog = alertDialogBuilder.create();
        dialog.show();
    }
}