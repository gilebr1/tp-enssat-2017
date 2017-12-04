package fr.enssat.identifiantbinome.monapplication;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

// Ensemble des parametres échangés entre le thread d'ui et le thread de background
// String en parametre de la methode doInBackground
// String passé en parametre à onProgressUpdate
// Object en retour de la méthode doInBackground
public class HttpAsyncTask extends AsyncTask<String, String, Object>
{   private static final String TAG = "HttpAsyncTask";
    private static final String JSON_KEY_NOM = "nom";
    private static final String JSON_KEY_DATE= "date";
    private static final String JSON_KEY_ARRAY= "personnes";

    private ProgressDialog          _progress;
    private AsyncResponseListener   _responseListener;

    public HttpAsyncTask(AsyncResponseListener responseListener)
    {   _responseListener = responseListener;}

    // méthode invoquée en 1er.
    // en avant plan -foreground- avant la méthode doInbackground
    // dans le fil d'execution -thread- de l'ui.
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        _progress = new ProgressDialog(_responseListener.getContext());
        _progress.setTitle(_responseListener.getContext().getString(R.string.app_download));
        _progress.setMessage(_responseListener.getContext().getString(R.string.app_json_get));
        _progress.show();
    }

    //méthode invoquée en second, en background dans un nouveau fil d'éxecution différent de celui de l'ui
    //On effectue des requetes http sans pb
    @Override
    protected Object doInBackground(String... url) {
        String jsonString = getJsonString(url[0]);
        JSONObject jsonPersonne;
        JSONArray  arrayJsonPersonnes;
        Personne   personne;
        ArrayList<Personne> liste = new ArrayList<>();

        if (jsonString != null)
        {   try {   arrayJsonPersonnes = new JSONObject(jsonString).getJSONArray(JSON_KEY_ARRAY);
                    for (int i = 0; i < arrayJsonPersonnes.length(); i++)
                    {   jsonPersonne = arrayJsonPersonnes.getJSONObject(i);
                        personne = new Personne(jsonPersonne.getString(JSON_KEY_NOM),jsonPersonne.getString(JSON_KEY_DATE));
                        liste.add(personne);
                    }
            }
            catch (Exception e)
            {  publishProgress(_responseListener.getContext().getString(R.string.app_error_2));}
        }
        else
        {  publishProgress(_responseListener.getContext().getString(R.string.app_error_3));}

        _progress.dismiss();

        return (Object)liste;
    }

        private String getJsonString(String urlString) {
            StringBuilder       result = new StringBuilder();
            URL                 url;
            HttpURLConnection   connection = null;
            BufferedReader      reader;
            String              line;
            InputStream         inputstream;

            try
            {   url = new URL(urlString);
                connection = (HttpURLConnection) url.openConnection();
                inputstream = connection.getInputStream();
                reader = new BufferedReader(new InputStreamReader(new BufferedInputStream(inputstream)));
                while ((line = reader.readLine()) != null)
                {   result.append(line);}
                publishProgress(_responseListener.getContext().getString(R.string.app_parsing_ok));
            }
            catch( Exception e)
            {   publishProgress(_responseListener.getContext().getString(R.string.app_error_2));}
            finally
            {   if (connection !=null) {connection.disconnect();}}

            return result.toString();
        }

    //invoqué par publishProgress en cours d'execution de doInBackground
    protected void onProgressUpdate(String... msg) {
        Toast.makeText(_responseListener.getContext(), msg[0], Toast.LENGTH_LONG).show();
    }

    //invoqué en dernier, le résultat de doInBackground devient le parametre en entrée de cette méthode
    @Override
    protected void onPostExecute(Object liste) {
        _responseListener.onAsyncTaskResponse((ArrayList<Personne>)liste);
    }
}
