package fr.enssat.identifiantbinome.monapplication;

public class Personne {
    private String _naissance;
    private String _nom;

    public Personne(String nom, String naissance) {
        _naissance = naissance;
        _nom = nom;
    }

    protected String getNom() {
        return _nom;
    }

    protected String getNaissance() {
        return _naissance;
    }

    protected void setNom(String nom) {
        _nom = nom;
    }

    protected void setNaissance(String naissance) {
        _naissance = naissance;
    }
 }
