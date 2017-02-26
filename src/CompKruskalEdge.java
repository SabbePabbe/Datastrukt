import java.util.Comparator;

public class CompKruskalEdge implements Comparator<Edge>{


    @Override
    public int compare(Edge o1, Edge o2) {

        return (int) Math.signum(o1.getWeight() - o2.getWeight());

    }

    
}
