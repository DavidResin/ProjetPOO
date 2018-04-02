package ch.epfl.imhof.painting;

import static org.junit.Assert.*;

import java.util.function.Predicate;

import org.junit.Test;

import ch.epfl.imhof.Attributed;
import ch.epfl.imhof.Attributes;

public class FiltersTest {

    private static Predicate<Attributed<?>> isLake = Filters.tagged("natural", "water", "pond");
    private static Predicate<Attributed<?>> isMountain = Filters.tagged("rocky", "slope");
    
    private static Predicate<Attributed<?>> isBuilding = Filters.tagged("building");
    private static Predicate<Attributed<?>> isForest = Filters.tagged("forest");
    
    private static Predicate<Attributed<?>> onLayer0 = Filters.onLayer(0);
    private static Predicate<Attributed<?>> onLayer1 = Filters.onLayer(1);

    @Test
    public void taggedChecksCorrectly() {
        Attributes.Builder builder = new Attributes.Builder();
        builder.put("building", "1");
        Attributes attr = builder.build();
        Attributed<Integer> test = new Attributed<Integer>(3, attr);
        assertTrue(isBuilding.test(test));
        assertFalse(isForest.test(test));
    }
    
    @Test
    public void taggedMultipleChecksCorrectly() {
        Attributes.Builder builder = new Attributes.Builder();
        builder.put("natural", "water");
        builder.put("rocky", "flat");
        Attributes attr = builder.build();
        Attributed<Integer> test = new Attributed<Integer>(3, attr);
        assertTrue(isLake.test(test));
        assertFalse(isMountain.test(test));
    }
    
    @Test
    public void onLayerChecksCorrectly() {
        Attributes.Builder builder = new Attributes.Builder();
        builder.put("layer", "0");
        Attributes attr = builder.build();
        Attributed<Integer> test = new Attributed<Integer>(3, attr);
        assertTrue(onLayer0.test(test));
        assertFalse(onLayer1.test(test));
    }
}
