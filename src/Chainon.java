/**
 * Chainon utilise par la liste milieu pour liee les elements
 */

package inf2120.tp3;


public class Chainon <E> {

    public E element;
    protected Chainon <E> suivant = null;

    public Chainon(E element) {
        this.element = element;
    }

    public Chainon() {}


    /*

    CODE DE LISTE
     */

    public E getElement() {
        return element;
    }

    public void setElement(E element) {
        this.element = element;
    }

    public Chainon<E> getSuivant() {
        return suivant;
    }

    public void setSuivant(Chainon<E> suivant) {
        this.suivant = suivant;
    }


}
