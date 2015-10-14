package exceptions;

public class PartieDemarreException extends Exception {

    public PartieDemarreException() {
        super("La partie a déjà demarré ");
    }

    public PartieDemarreException(String pseudo) {
        super("La partie a déjà démarré, impossible d'ajouter le joueur " + pseudo);
    }
}
