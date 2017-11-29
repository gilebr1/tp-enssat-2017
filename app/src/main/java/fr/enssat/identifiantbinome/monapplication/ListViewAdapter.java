package fr.enssat.identifiantbinome.monapplication;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import java.util.List;

public class ListViewAdapter extends ArrayAdapter<Personne>
{
    //personnes est la liste des personnes à afficher
    public ListViewAdapter(Context context, List<Personne> personnes) {
        super(context, 0, personnes);
    }

    @Override
    @NonNull
    public View getView(int position, View convertView, ViewGroup parent) {

        //on cree la vue dédiée à l'affichage de la ligne n° de position si elle n'existe pas.
        if(convertView == null){
            convertView = LayoutInflater.from(this.getContext()).inflate(R.layout.item_personne, parent, false);
        }

        //on recupere le view holder s'il existe
        PersonneViewHolder viewHolder = (PersonneViewHolder) convertView.getTag();

        //on cree le view holder s'il n'existe pas et on l'affecte à la vue correspondant à la ligne n° de position
        if(viewHolder == null){
            viewHolder = new PersonneViewHolder();
            viewHolder.nom = convertView.findViewById(R.id.nom);
            viewHolder.naissance = convertView.findViewById(R.id.naissance);
            convertView.setTag(viewHolder);
        }

        //on récupére de la Liste de personnes la Personne n°position
        Personne p = getItem(position);

        //il ne reste plus qu'à remplir notre vue
        viewHolder.nom.setText(p.getNom());
        viewHolder.naissance.setText(p.getNaissance());

        return convertView;
    }

    private class PersonneViewHolder {
        TextView nom;
        TextView naissance;
    }
}
