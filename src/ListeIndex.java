/**
 * Cette classe cree une liste simplement chainée qui contient des instances de ListeMilieu
 * @author Jonathan Labelle
 * LABJ28039003
 * Groupe 10
 *
 */

package inf2120.tp3;


import java.util.function.Consumer;

public class ListeIndex<E extends Comparable< E > >  {

    int nbrListe = 0;
    ListeMilieu listeMilieu;
    ChainonIndex chainonTete;

    /**
     * Methode permettant de verifier si une valeur speficique est presente dans une des chaines. Utilise
     * la methode elementDeJaPresent de listeMilieu pour chercher dans la liste milieu.
     *
     * @param valeur la valeur a verifier
     * @return true si la valeur est presente
     */
    public boolean contient( E valeur ) {

        ChainonIndex courant = chainonTete;

        boolean contientValeur = false;

        if (courant != null) {

            contientValeur = courant.listeMilieu.elementDejaPresent(valeur);
            courant.listeMilieu.minima();

            while (courant.suivant != null  && !contientValeur) {

                courant = courant.suivant;

                if (courant.listeMilieu.minima().hashCode() <= valeur.hashCode()

                        && (courant.listeMilieu.maxima().hashCode() >= valeur.hashCode()
                        || courant.listeMilieu.maxima() == null)) {

                    contientValeur = courant.listeMilieu.elementDejaPresent(valeur);

                }
            }
        }

        return contientValeur;
    }

    /**
     * Permet d'acceder a une listeMilieu a un endroit precis d'une liste chainee
     *
     * @param i positon dans la liste chainee
     * @return listeMilieu
     */
    public ListeMilieu<E> get( int i ) {

        ChainonIndex courant = chainonTete;
        listeMilieu = courant.listeMilieu;

        while (i != 0) {
            courant = courant.suivant;
            listeMilieu = courant.listeMilieu;
            --i;
        }

        return listeMilieu;
    }

    /**'
     * Insere la valeur dans une liste milieu. La methode fait aussi appelle a verifieSiDivision pour s'assurer que
     * l'invariant de la grosseur des ListeMilieu de depasse par du double+1 le nombre de liste indexee
     * @param valeur valeur a ajouter
     */
    public void inserer( E valeur ) {

        ChainonIndex courant = chainonTete;

        boolean insere = false;

        if (nbrListe == 0) {

            ListeMilieu listeMilieu = new ListeMilieu();
            chainonTete = new ChainonIndex(listeMilieu);
            chainonTete.listeMilieu.inserer(valeur);
            nbrListe++;

        } else if (nbrListe == 1) {

            chainonTete.listeMilieu.inserer(valeur);
            verifierSiDivision(chainonTete);

        } else if (nbrListe > 1) {

            if (courant.listeMilieu.maxima().hashCode() >= valeur.hashCode()) {
                courant.listeMilieu.inserer(valeur);
                insere = true;

            } else {

                while (courant.suivant != null) {

                    if (courant.suivant.listeMilieu.maxima().hashCode() >= valeur.hashCode()
                            || courant.suivant.listeMilieu.maxima() == null) {
                        courant.suivant.listeMilieu.inserer(valeur);
                        insere = true;
                    }

                    courant = courant.suivant;
                }
            }

            if (!insere) {
                courant.listeMilieu.inserer(valeur);
            }

        }
        verifierSiDivision(courant);
    }

    /**
     * Verifie si listeMilieu qui s'est fait ajouter un element ne depasse pas l'invariant double+1 d'element dans un liste
     * maximum par rapport au nombre de liste indexe. Si depasse, creation d'une chaine à partir de la liste superieure
     * @param courant chainon passee apres avoir eu valeur ajouter
     */
    public void verifierSiDivision(ChainonIndex courant) {

        if (courant != null) {

            if (courant.listeMilieu.taille() > nbrListe() * 2) {

                ListeMilieu nouvelleListe = courant.listeMilieu.diviser();
                ChainonIndex nouveauChainon = new ChainonIndex(nouvelleListe);

                ChainonIndex temp = courant.suivant;
                courant.suivant = nouveauChainon;
                nouveauChainon.suivant = temp;
                nbrListe++;

            }
        }
    }

    /**
     * retourne le nombre de liste dnas la chaine indexe
      * @return
     */
    public int nbrListe() {
        return nbrListe;
    }

    /**
     * Recherche dans les listes indexes a savoir si la valeur a supprimer est plus grande que le minima et plus petite
     * que le maxima. Si oui, cherche dans la listeMilieu la valeur a supprimer
     * @param valeur a supprimer
     */
    public void supprimer( E valeur ) {
        ChainonIndex courant = chainonTete;

        if (courant.listeMilieu.minima().hashCode() <= valeur.hashCode() &&
                ((courant.listeMilieu.maxima().hashCode() >= valeur.hashCode()) || courant.listeMilieu.maxima() == null)) {
            courant.listeMilieu.supprimer(valeur);

        } else {

            while (courant.suivant != null) {

                courant = courant.suivant;

                if (courant.listeMilieu.minima().hashCode() <= valeur.hashCode() &&
                        ((courant.listeMilieu.maxima().hashCode() >= valeur.hashCode()) || courant.listeMilieu.maxima() == null)) {

                    courant.listeMilieu.supprimer(valeur);
            }
        }}
    }

    /**
     * calcule le nombre d'element present dans tout les listes milieux indexées
     * @return nombre d'element total
     */
    public int taille() {

        int taille = 0;

        ChainonIndex courant = chainonTete;

        if (courant != null) { {

                taille = taille + courant.listeMilieu.taille();

                while (courant.suivant != null) {
                    courant = courant.suivant;
                    taille = taille + courant.listeMilieu.taille();

                }
            }
        }

        return taille;
    }

}