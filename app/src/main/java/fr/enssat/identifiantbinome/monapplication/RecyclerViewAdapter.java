package fr.enssat.identifiantbinome.monapplication;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.PersonneRecyclerViewHolder>
{
    private RecyclerViewActivity _activity;

    public RecyclerViewAdapter(RecyclerViewActivity activity)
    {   _activity = activity;}

    //création de viewHolder et layout de cellule utilisé
    @Override
    public PersonneRecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {   View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.linear_item_personne, parent, false);
        PersonneRecyclerViewHolder holder = new PersonneRecyclerViewHolder(view);
        return new PersonneRecyclerViewHolder(view);
    }

    //remplir la cellule de chaque Personne
    @Override
    public void onBindViewHolder(PersonneRecyclerViewHolder holder, int position)
    {Personne personne = _activity.getListePersonnes().get(position);
        holder.bind(personne);
    }

    @Override
    public int getItemCount()
    {
        return _activity.getListePersonnes().size();
    }

    //view holder specifique au recyclerview
    protected class PersonneRecyclerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        TextView _nom;
        TextView _naissance;

        public PersonneRecyclerViewHolder(View itemView)
        {
            super(itemView);
            _nom = itemView.findViewById(R.id.nom);
            _naissance = itemView.findViewById(R.id.naissance);
            itemView.setOnClickListener(this);
        }

        public void bind(Personne personne)
        {
            _nom.setText(personne.getNom());
            _naissance.setText(personne.getNaissance());
        }

        //suppression de l'item de la liste
        @Override
        public void onClick(View v)
        {   final int pos = getAdapterPosition();
            AlertDialog dialog;
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(_activity);
            String button_ok  = _activity.getString(android.R.string.ok);
            String message    = _activity.getString(R.string.app_delete,_activity.getListePersonnes().get(pos).getNom());
            String button_cancel = _activity.getString(android.R.string.cancel);

            alertDialogBuilder.setMessage(message);
            alertDialogBuilder.setCancelable(true);
            alertDialogBuilder.setNegativeButton(button_cancel, null);
            alertDialogBuilder.setPositiveButton(button_ok, new DialogInterface.OnClickListener()
            {   @Override
                public void onClick(DialogInterface dialog, int which)
                {  _activity.getListePersonnes().remove(pos);
                notifyDataSetChanged();
                }
            });

            dialog = alertDialogBuilder.create();
            dialog.show();
        }
    }
}
