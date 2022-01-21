/**
 * Classe qui cree une liste simplement chainee et trie au fur et a mesure des entrees une liste contenant la moitie
 * des elements les plus gros et l'autre des plus petits
 */

package inf2120.tp3;

import java.util.Iterator;
import java.util.Spliterator;
import java.util.function.Consumer;

public class ListeMilieu< E extends Comparable< E >> {

    Chainon chainonTete;
    Chainon listeSuperieur;
    Chainon listeInferieur;
    int taille = 0;

    public ListeMilieu() {

        }

    /**
     * Separe la liste en deux en prenant les elements de la liste superieure et en les associant à un nouveau chainon.
     * Les elements restants sont rearange pour reequilibrer la liste du haut et du bas
      * @return nouvelle liste a partir des elements superieurs
     */
    public ListeMilieu<E> diviser() {

        ListeMilieu nouvelleListe = new ListeMilieu();
        int compteur = 0;

        if (listeSuperieur != null) {

            Chainon courant = listeSuperieur;
            nouvelleListe.inserer(courant.element.hashCode());
            taille--;
            compteur++;

            while (courant.suivant != null) {

                courant = courant.suivant;
                nouvelleListe.inserer(courant.element.hashCode());
                taille--;
                compteur++;

            }

            listeSuperieur.suivant = null;
            listeSuperieur = null;

            for (int i = 0; i < compteur; i++) {

                verifierEquilibre();
            }

        }


        return nouvelleListe;
    }

    /**
     * Insere un nouvel element a la liste. Si le chianon inferieur est vide, le cree. S'il existe deja, procede a un tri
     * pour mettre les elements les plus gros avants les plus petits. Verifie egalemnt a la fin si les deux listes sont
     * equilibres
     * @param valeur
     */
    public void inserer(E valeur) {

        Chainon<E> nouveau = new Chainon<>(valeur);

        if (!elementDejaPresent(valeur)){

            if (listeInferieur == null) {
                listeInferieur = nouveau;

            } else if (taille == 1) {

                   if (listeInferieur.getElement().hashCode() < valeur.hashCode()) {
                       listeSuperieur = nouveau;
                   }

                       else {
                           Chainon temp = listeInferieur;
                           listeSuperieur = temp;
                           listeInferieur = nouveau;
                       }

            } else if (valeur.hashCode() > listeInferieur.getElement().hashCode()) {
                listeSuperieur = insererPositionSuperieur(verifierPositionSuperieur(valeur), nouveau, listeSuperieur);

            } else {
                listeInferieur = insererPositionInferieur(verifierPositionInferieur(valeur), nouveau, listeInferieur);

            }
            taille++;
        }

        verifierEquilibre();
    }

    /**
     * Insere un element a un endroit specifique de la liste superieure en rearrangeant les liens entre chainons
     * @param positon posiiton a insere
     * @param nouveau le nouvel element
     * @param listeChainon la liste superieur
     * @return
     */
    public Chainon insererPositionSuperieur(int positon, Chainon nouveau, Chainon listeChainon) {

        Chainon <E> courant = getChainonSuperieur(positon);

        if (positon == 0) {

            nouveau.suivant = listeChainon;
            listeChainon = nouveau;

        } else {

            nouveau.suivant = courant;
            getChainonSuperieur(positon-1).suivant = nouveau;

        }

        return listeChainon;
    }

    /**
     * Insere un element a un endroit specifique de la liste inferieure en rearrangeant les liens entre chainons
     * @param positon posiiton a insere
     * @param nouveau le nouvel element
     * @param listeChainon la liste inferieure
     * @return
     */
    public Chainon insererPositionInferieur(int positon, Chainon nouveau, Chainon listeChainon) {

        Chainon <E> courant = getChainonInferieur(positon);

        if (positon == 0) {
            nouveau.suivant = listeChainon;
            listeChainon = nouveau;

        } else {

            nouveau.suivant = courant;
            getChainonInferieur(positon-1).suivant = nouveau;

        }
        return listeChainon;
    }

    /**
     * Verifie la taille de la liste inferieure et superieure et rearrange si besoin. Les doivent etre égales en terme
     * d'élément, la liste du bas ayant un élement de plus si chiffre impair. Si un desiquilibre, les chainons sont
     * reassocies et envoyer dans l'Autre liste au besion
     */
    public void verifierEquilibre() {

        int tailleInferieur;
        int tailleSuperieur;

        if (listeInferieur == null) {
            tailleInferieur = 0;

        } else {

            tailleInferieur = verificationTailleChainon(listeInferieur);
        }

        if (listeSuperieur == null) {
            tailleSuperieur = 0;

        } else {

            tailleSuperieur = verificationTailleChainon(listeSuperieur);
        }

        if (tailleSuperieur > tailleInferieur) {

            Chainon temp = listeSuperieur.suivant;
            listeSuperieur.suivant = listeInferieur;
            listeInferieur = listeSuperieur;
            listeSuperieur = temp;
        }

        if (tailleInferieur -1 > tailleSuperieur) {

            Chainon temp = listeInferieur.suivant;
            listeInferieur.suivant = listeSuperieur;
            listeSuperieur = listeInferieur;
            listeInferieur = temp;
        }
    }

    /**
     * Verifie le nombre d'element dans le chainon
     * @param chainon a compter
     * @return
     */
    public int verificationTailleChainon(Chainon chainon) {

        Chainon courant = chainon;
        int tailleChainon = 0;

        if (courant.getElement() != null) {
            tailleChainon++;
        }

        while (courant.suivant != null) {
            tailleChainon++;
            courant = courant.suivant;
        }

        return tailleChainon;
    }

    /**
     * Verifie si un element est deja present dans une des deux listes
     * @param element a vefrifer
     * @return
     */

    public boolean elementDejaPresent (E element) {

        boolean elementPresent = verifierElementListeInferieur(element);

        if (!elementPresent) {
            elementPresent = verifierElementListeSuperieur(element);
        }

        return elementPresent;
    }

    /**
     * Verifie la presence de l'element dans la liste inferieure
     * @param element a verifier
     * @return
     */
    public boolean verifierElementListeInferieur(E element) {
        boolean elementPresent = false;

        if (listeInferieur != null){

            Chainon courant = listeInferieur;

            if (courant.getElement().hashCode() == element.hashCode()) {
                elementPresent = true;
            }

            while (!elementPresent && courant.suivant != null) {

                if (courant.suivant.getElement().hashCode() == element.hashCode()) {
                    elementPresent = true;
                }

                courant = courant.suivant;
            }
        }
        return elementPresent;
    }


    /**
     * Verifie la presence de l'element dans la liste superieure
     * @param element a verifier
     * @return
     */
    public boolean verifierElementListeSuperieur(E element) {
        boolean elementPresent = false;
        if (listeSuperieur != null && !elementPresent) {
            Chainon courant = listeSuperieur;

            if (courant.getElement().hashCode() == element.hashCode()) {
                elementPresent = true;
            }

            while (!elementPresent && courant.suivant != null) {

                if (courant.suivant.getElement().hashCode() == element.hashCode()) {
                    elementPresent = true;
                }

                courant = courant.suivant;
            }
        }
        return elementPresent;
    }

    /**
     * Verifie ou inserer un chainon en particulier dans la liste superieure
     * @param valeur a inserer
     * @return
     */
    public int verifierPositionSuperieur(E valeur) {

        int position = 0;
        Chainon courant = listeSuperieur;
        if (valeur.hashCode() > courant.element.hashCode()) {
            position++;
        }
        while (courant.suivant != null){
            if (valeur.hashCode() > courant.element.hashCode()) {
                position++;
            }
            courant = courant.suivant;
        }

        return position;
    }

    /**
     * Verifie ou inserer un chainon en particulier dans la liste inferieure
     * @param valeur a inserer
     * @return
     */
    public int verifierPositionInferieur(E valeur) {

        int position = 0;
        Chainon courant = listeInferieur;

        if (valeur.hashCode() < courant.element.hashCode()) {
            position++;
        }

        while (courant.suivant != null){
            if (valeur.hashCode() < courant.suivant.element.hashCode()) {
                position++;
            }

            courant = courant.suivant;

        }
        return position;
    }

    /**
     * Retourne le plus grand element de la liste inferieure
     * @return
     */
    public E milieu() {
        return (E)listeInferieur.getElement();
    }

    /**
     * Retourne le plus petit element de la liste inferieure
     * @return
     */
    public E minima() {

        Chainon courant = listeInferieur;

        while (courant.suivant != null) {
            courant = courant.suivant;
        }

        return (E)courant.getElement();
    }

    /**
     * retourne le plus grand element de la liste superieure
     * @return
     */
    public E maxima() {

        Chainon courant = new Chainon();

        if (listeSuperieur != null){
            courant = listeSuperieur;

            while (courant.suivant != null) {
                courant = courant.suivant;
            }

        } else if (listeInferieur != null){
            courant = listeInferieur;

        }

        return (E)courant.getElement();
    }

    /**
     * Supprime en element en particulier d'une des deux listes et reajuste la grandeur des listes si besoin
     * @param valeur a suppriemr
     */
    public void supprimer(E valeur) {

        int position;

        if (listeInferieur.getElement().hashCode() >= valeur.hashCode() && listeInferieur != null) {

            position = chainonASupprimer(valeur, listeInferieur);

            if (position != -1) {
            Chainon courant = getChainonInferieur(position);

            if (courant.equals(listeInferieur)) {
                listeInferieur = courant.suivant;

            } else {
                getChainonInferieur(position-1).suivant = courant.suivant;
                }

                taille--;
            }

        } else if (listeSuperieur != null) {

            position = chainonASupprimer(valeur, listeSuperieur);

            if (position != -1) {

                Chainon courant = getChainonSuperieur(position);

                if (courant.equals(listeSuperieur)) {
                    listeSuperieur = courant.suivant;

                } else {
                    getChainonSuperieur(position-1).suivant = courant.suivant;
                }

                taille--;
            }
        }

        verifierEquilibre();
    }

    /**
     * Supprime le chainon contenant l'element a supprimer et reassocie les chainons entre eux
     * @param valeur a supprime
     * @param liste liste inferieur ou superieure
     * @return
     */
    public int chainonASupprimer(E valeur, Chainon liste) {

        boolean chainonTrouve = false;
        int position = -1;

        while (liste.suivant != null && !chainonTrouve) {

            if (liste.suivant.element.hashCode() == valeur.hashCode()) {

                chainonTrouve = true;
                position++;
            }

            liste = liste.suivant;
            position++;
        }

        if (!chainonTrouve) {
            position = 0;
        }

        return position;
    }

    public int taille() {
        return taille;
    }

    /**
     * Donne access a un chainon specifique de la liste superieure
     * @param position
     * @return
     * @throws IndexOutOfBoundsException
     */
    public Chainon<E> getChainonSuperieur(int position)
            throws IndexOutOfBoundsException {
        if (position < 0 || taille <= position) {
            throw new IndexOutOfBoundsException();
        }

        Chainon<E> courant = listeSuperieur;

        while (position != 0) {
            courant = courant.suivant;
            --position;
        }

        return courant;
    }

    /**
     * Donne access a un chainon specifique de la liste inferieure
     * @param position
     * @return
     * @throws IndexOutOfBoundsException
     */
    public Chainon<E> getChainonInferieur(int position)
            throws IndexOutOfBoundsException {
        if (position < 0 || taille <= position) {
            throw new IndexOutOfBoundsException();
        }

        Chainon<E> courant = listeInferieur;

        while (position != 0) {
            courant = courant.suivant;
            --position;
        }

        return courant;
    }

    public Chainon<E> getChainon(int position)
            throws IndexOutOfBoundsException {
        if (position < 0 || taille <= position) {
            throw new IndexOutOfBoundsException();
        }

        Chainon<E> courant = chainonTete;

        while (position != 0) {
            courant = courant.suivant;
            --position;
        }

        return courant;
    }




}