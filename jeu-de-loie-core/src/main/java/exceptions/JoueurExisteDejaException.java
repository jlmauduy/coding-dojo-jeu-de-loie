package exceptions;

public class JoueurExisteDejaException extends Exception {
    public JoueurExisteDejaException(String nomJoueur) {
        super("Le joueur existe déjà : " + nomJoueur);
    }

}
