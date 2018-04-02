package ch.epfl.imhof.painting;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

import ch.epfl.imhof.Attributed;
import ch.epfl.imhof.painting.LineStyle.LineCap;
import ch.epfl.imhof.painting.LineStyle.LineJoin;

public final class RoadPainterGenerator {
    
    private RoadPainterGenerator(){}
    
    public static Painter painterForRoads(RoadSpec... specs) {
        
        List<RoadSpec> rSList = Arrays.asList(specs);
        List<Painter> painterList = new ArrayList<>();
        
        rSList.forEach(x -> painterList.add(x.innerBridge));
        rSList.forEach(x -> painterList.add(x.outerBridge));
        rSList.forEach(x -> painterList.add(x.innerRoad));
        rSList.forEach(x -> painterList.add(x.outerRoad));
        rSList.forEach(x -> painterList.add(x.tunnel));
        
        return painterList.stream().reduce(Painter::above).get();
    }
    
    public static final class RoadSpec {
        
        private static final Predicate<Attributed<?>> isBridge = Filters.tagged("bridge");
        private static final Predicate<Attributed<?>> isTunnel = Filters.tagged("tunnel");
        private static final Predicate<Attributed<?>> isRoad = (isBridge.or(isTunnel)).negate();
        
        private static final float[] empty = new float[0];
        
        private final LineStyle innerStyle, outerStyle, tunnelStyle;
        public final Painter innerBridge, outerBridge, innerRoad, outerRoad, tunnel;
        
        public RoadSpec(Predicate<Attributed<?>> filter, float wI, Color cI, float wC, Color cC) {
            
            this.innerStyle = new LineStyle(wI, cI, LineCap.ROUND, LineJoin.ROUND, empty);
            this.outerStyle = new LineStyle(wI + 2 * wC, cC, LineCap.BUTT, LineJoin.ROUND, empty);
            this.tunnelStyle = new LineStyle(wI / 2, cC, LineCap.BUTT, LineJoin.ROUND, new float[]{2 * wI, 2 * wI});
            
            this.innerBridge = Painter.line(innerStyle).when(isBridge.and(filter));
            this.outerBridge = Painter.line(outerStyle).when(isBridge.and(filter));
            this.innerRoad = Painter.line(innerStyle).when(isRoad.and(filter));
            this.outerRoad = Painter.line(outerStyle.withLineCap(LineCap.ROUND)).when(isRoad.and(filter));
            this.tunnel = Painter.line(tunnelStyle).when(isTunnel.and(filter));
        }
    }
}
