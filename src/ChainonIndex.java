/**
 * Chainon utiliser dans ListeIndex pour creer une chaine de ListeMilieu
 */

package inf2120.tp3;

public class ChainonIndex {

    ListeMilieu listeMilieu;
    ChainonIndex suivant;

    public ChainonIndex(ListeMilieu listeMilieu) {
        this.listeMilieu = listeMilieu;
    }

}
