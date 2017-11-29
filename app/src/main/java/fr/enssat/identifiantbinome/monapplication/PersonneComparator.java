package fr.enssat.identifiantbinome.monapplication;

import java.util.Comparator;

public class PersonneComparator implements Comparator<Personne>
{
    public int compare(Personne left, Personne right) {
        return left.getNom().compareToIgnoreCase(right.getNom());
    }
}
