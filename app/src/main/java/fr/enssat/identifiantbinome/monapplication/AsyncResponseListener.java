package fr.enssat.identifiantbinome.monapplication;

import android.content.Context;

import java.util.List;

interface AsyncResponseListener
{   Context getContext();
    void onAsyncTaskResponse(List<Personne> liste);
}
