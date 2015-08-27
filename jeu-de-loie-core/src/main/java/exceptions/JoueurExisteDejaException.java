package exceptions;

public class JoueurExisteDejaException extends RuntimeException {
    public JoueurExisteDejaException(String nomJoueur) {
        super("Le joueur existe déjà : " + nomJoueur);
    }

}
