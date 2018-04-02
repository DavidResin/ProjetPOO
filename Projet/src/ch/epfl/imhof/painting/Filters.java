package ch.epfl.imhof.painting;

import java.util.Arrays;
import java.util.function.Predicate;

import ch.epfl.imhof.Attributed;

/**
 * Des constructeurs de filtres a attributs
 * 
 * @author Magaly Abboud (249344)
 * @author David Resin (225452)
 */
public final class Filters {
    private Filters() {}
    
    /**
     * Construit un filtre verifiant la presence de l'attribut passe en argument
     * 
     * @param attr
     *              L'attribut que le filtre verifie
     * @return
     *              Un filtre pour cet attribut, retournant true s'il trouve l'attibut et false sinon
     */
    public static Predicate<Attributed<?>> tagged(String attr) {
        return (x) -> (x.hasAttribute(attr));
    }
    
    /**
     * Construit un filtre verifiant la presence des attrinbuts passes en argument
     * 
     * @param attr
     *              Le tableau d'attributs que le filtre verifie
     * @return
     *              Un filtre pour ces attributs, retournant true, s'il trouve tous les attributs et false sinon
     */
    public static Predicate<Attributed<?>> tagged(String key, String... values) {
        return (x) -> (Arrays.asList(values).contains(x.attributeValue(key)));
    }
    
    /**
     * Construit un filtre verifiant la valeur de l'attribut layer
     * 
     * @param attr
     *              La valeur a verifier
     * @return
     *              Un filtre pour cette valeur, retournant true si l'attribut layer vaut cette valeur, et false sinon
     */
    public static Predicate<Attributed<?>> onLayer(int i) {
        return (x) -> (Integer.parseInt(x.attributeValue("layer", "0")) == i);
    }
}
