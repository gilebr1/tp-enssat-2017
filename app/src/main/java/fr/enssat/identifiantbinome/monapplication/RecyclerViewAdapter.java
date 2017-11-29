package fr.enssat.identifiantbinome.monapplication;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.PersonneRecyclerViewHolder>
{   private List<Personne> _liste;

    public RecyclerViewAdapter(List<Personne> liste) {
        _liste = liste;
    }

    //créeation de viewHolder et layout de cellule utilisé
    @Override
    public PersonneRecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {   View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.linear_item_personne,parent,false);
        return new PersonneRecyclerViewHolder(view);
    }

    //remplir la cellule de chaque Personne
    @Override
    public void onBindViewHolder(PersonneRecyclerViewHolder holder, int position)
    {   Personne personne = _liste.get(position);
        holder.bind(personne);
    }

    @Override
    public int getItemCount() {
        return _liste.size();
    }

    //view holder specifique au recyclerview
    protected class PersonneRecyclerViewHolder extends RecyclerView.ViewHolder{
        TextView _nom;
        TextView _naissance;

        public PersonneRecyclerViewHolder(View itemView) {
            super(itemView);
            _nom = itemView.findViewById(R.id.nom);
            _naissance = itemView.findViewById(R.id.naissance);
        }

        public void bind(Personne personne){
            _nom.setText(personne.getNom());
            _naissance.setText(personne.getNaissance());
        }
    }
}
