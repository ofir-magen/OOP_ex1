package Ex1;

import java.util.Iterator;
import java.util.Random;

public class testfunction {
    private static Random _rnd = null;
    private static int _errors = 0, _tests = 0,_number_of_exception=0;
    private static String _log = "";

    static weighted_graph graph_creator(int v_size, int e_size, int seed) {
        int max =v_size*(v_size-1)/2;
        _rnd = new Random(seed);
        weighted_graph g = new WGraph_DS();
        for (int i = 0; i < v_size; i++) {
            g.addNode(i);
        }
        Iterator<node_info> itr = g.getV().iterator(); // Iterator is a more elegant and generic way, but KIS is more important
        int[] nodes = new int[g.nodeSize()];
        int i = 0;
        while (itr.hasNext()) {
            node_info tmp = itr.next();
            nodes[i] = tmp.getKey();
            i++;
        }
if(e_size<=max) {
    while (g.edgeSize() < e_size) {
        int a = nextRnd(0, v_size);
        int b = nextRnd(0, v_size);
        int y = nodes[a];
        int x = nodes[b];
        int w1 = nextRnd(0, 1000);
        double w = ((double) w1) / 10;
        g.connect(y, x, w);
    }
}
        else {
            while (g.edgeSize() < max) {
                int a = nextRnd(0, v_size);
                int b = nextRnd(0, v_size);
                int y = nodes[a];
                int x = nodes[b];
                int w1 = nextRnd(0, 1000);
                double w = ((double) w1) / 10;
                g.connect(y, x, w);
            }
        }
        return g;
    }

    private static int nextRnd(int min, int max) {
        double v = nextRnd(0.0 + min, (double) max);
        int ans = (int) v;
        return ans;
    }

    private static double nextRnd(double min, double max) {
        double d = _rnd.nextDouble();
        double dx = max - min;
        double ans = d * dx + min;
        return ans;
    }
}



