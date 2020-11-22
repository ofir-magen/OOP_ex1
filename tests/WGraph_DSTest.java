package Ex1;

import org.junit.jupiter.api.Test;

import java.util.Date;
import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.assertEquals;

import static org.junit.jupiter.api.Assertions.*;

class WGraph_DSTest {


    @Test
    void connect() {
        weighted_graph test1 = new WGraph_DS();
        weighted_graph test2 = new WGraph_DS();
        weighted_graph test3 = new WGraph_DS();
        weighted_graph test4 = new WGraph_DS();


        try {
            test1 = testfunction.graph_creator(12, 0, 1);
            test2 = testfunction.graph_creator(0, 0, 1);
            test3 = testfunction.graph_creator(12, 66, 1);
            test4 = testfunction.graph_creator(12, 96, 1);

            assertEquals(0, test1.edgeSize(), "need to be " + 0 + " node(connect)");
            assertEquals(0, test2.edgeSize(), "need to be " + 0 + " node(connect)");
            assertEquals(66, test3.edgeSize(), "need to be " + 66 + " node(connect)");
            assertEquals(66, test4.edgeSize(), "need to be " + 66 + " node(connect)");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Test
    void my_test() {
        weighted_graph test1 = new WGraph_DS();
        for(int i=2; i<20;i++) {
            try {
                int edge = (i * (i - 1) / 2);
                test1 = testfunction.graph_creator(i, edge, 1);
                for (node_info n : test1.getV()) {
                    for (node_info e : test1.getV(n.getKey())) {

                    }
                }
                Iterator<node_info> itr = test1.getV().iterator();
                node_info temp = itr.next();
                assertEquals((edge), test1.edgeSize(), "need to be " + (edge) + "node(my_test[edgeSize])");

                test1.removeNode(temp.getKey());
                assertEquals((edge - (i - 1)), test1.edgeSize(), "need to be " + (edge - (i - 1)) + "node(my_test[edgeSize])");

            }
            catch (Exception e) {
                e.printStackTrace();
            }

        }
        weighted_graph test2 = new WGraph_DS();
        test2.addNode(1);
        test2.addNode(3);
        test2.addNode(2);
        test2.connect(1,3,110);
        test2.connect(1,3,20);
        assertEquals(20, test2.getEdge(3,1), "need to be " + 20+ "node(my_test[change_getEdge])");

        weighted_graph test3 = new WGraph_DS();
        test3.addNode(1);
        test3.addNode(2);
        test3.addNode(3);
        test3.addNode(4);
        test3.addNode(5);
        test3.addNode(100);
        test3.connect(100,1,11);
        test3.connect(100,2,22);
        test3.connect(100,3,33);
        test3.connect(100,4,44);
        test3.connect(100,5,55);
        test3.removeNode(100);
        assertEquals(0, test3.edgeSize(), "need to be 0 node(my_test[connect])");


    }
    // WGraph_AlgoTest
    @Test
    void copy() {



        try {

            weighted_graph_algorithms test1 = new WGraph_Algo();

            weighted_graph copy21 = new WGraph_DS();
            weighted_graph copy2 ;

            copy21 = testfunction.graph_creator(10, 90, 1);



            test1.init(copy21);
            copy2 = test1.copy();

            for (node_info n : copy2.getV()) {

                copy21.getNode(n.getKey());
                // check the nodes if they Exists
                assertEquals(n.getKey(), copy21.getNode(n.getKey()).getKey(), "need to be 0 node(my_test[get key from copy])");
                for (node_info ni : copy2.getV(n.getKey())) {

                    copy2.getEdge(n.getKey(), ni.getKey());
                    copy21.hasEdge(n.getKey(), ni.getKey());
                    // check the Neighbors of the nodes if they are connected
                    assertEquals(true, copy21.hasEdge(n.getKey(), ni.getKey()), "need to be 0 node(my_test[copy, check if all connected])");
                    // check the Neighbors of the nodes have the same weight
                    assertEquals(copy2.getEdge(n.getKey(), ni.getKey()), copy21.getEdge(n.getKey(), ni.getKey()), "need to be 0 node(my_test[copy, check the weight])");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    @Test
    void isConnected() {
        weighted_graph_algorithms test1 = new WGraph_Algo();
        weighted_graph g = new WGraph_DS();
        g.addNode(1);
        g.addNode(2);
        g.addNode(3);
        g.addNode(4);
        g.addNode(5);
        g.addNode(100);
        g.connect(100,1,11);
        g.connect(100,2,22);
        g.connect(100,3,33);
        g.connect(100,4,44);
        g.connect(100,5,55);
        test1.init(g);
// shape of star, remove the midle node.
        assertEquals(true, test1.isConnected(), "need to be 0 node(my_test[isconnect])");
        g.removeNode(100);
        assertEquals(false, test1.isConnected(), "need to be 0 node(my_test[connect])");
// no node at all
        weighted_graph_algorithms test2 = new WGraph_Algo();
        weighted_graph g2 = new WGraph_DS();
        test2.init(g2);
        assertEquals(true, test2.isConnected(), "need to be 0 node(my_test[isconnect])");

    }

    @Test
    void shortestPathDist() {
        weighted_graph_algorithms test1 = new WGraph_Algo();
        weighted_graph g1 = new WGraph_DS();
        g1.addNode(1);
        g1.addNode(2);
        test1.init(g1);

        assertEquals(-1, test1.shortestPathDist(1,2), "need to be -1 (my_test[shortestPathDist])");
        g1.connect(1,2,10);
        assertEquals(10, test1.shortestPathDist(1,2), "need to be 1 (my_test[shortestPathDist])");

    }

    @Test
    void shortestPath() {

        weighted_graph_algorithms test1 = new WGraph_Algo();
        weighted_graph g1 = new WGraph_DS();
        g1.addNode(11);
        g1.addNode(12);
        g1.addNode(13);
        g1.addNode(14);
        g1.connect(11,12,1);
        g1.connect(11,13,10);
        g1.connect(14,12,9);
        g1.connect(13,14,1);
        test1.init(g1);
int[] ni1={11,12,14};
int i=0;
boolean flag=true;
        for (node_info n:test1.shortestPath(11,14)) {
         if(n.getKey()!=ni1[i]){
             flag=false;
             break;
         }
         i++;
        }
        assertEquals(true, flag, "need to be true (my_test[shortestPath1])");

    g1.connect(11,12,1);
        g1.connect(11,13,10);
        g1.connect(14,12,19);
        g1.connect(13,14,1);
        int[] ni2={11,13,14};
        i=0;
        flag=true;
        for (node_info n:test1.shortestPath(11,14)) {
            if(n.getKey()!=ni2[i]){
                flag=false;
                break;
            }
            i++;
        }
        assertEquals(true, flag, "need to be true (my_test[shortestPath2)");

        g1.connect(13,12,1);
        int[] ni3={11,12,13,14};
        i=0;
        flag=true;
        for (node_info n:test1.shortestPath(11,14)) {
            if(n.getKey()!=ni3[i]){
                flag=false;
                break;
            }
            i++;
        }
        assertEquals(true, flag, "need to be true (my_test[shortestPath3])");
        g1.addNode(1);
        g1.addNode(2);
        assertEquals(null, test1.shortestPath(1,2), "need to be null (my_test[shortestPath3])");


    }
    @Test
    void save() {
        weighted_graph_algorithms test1 = new WGraph_Algo();
        weighted_graph g1 = new WGraph_DS();
        g1.addNode(11);
        g1.addNode(12);
        g1.addNode(13);
        g1.addNode(14);
        g1.connect(11,12,1);
        g1.connect(11,13,10);
        g1.connect(14,12,9);
        g1.connect(13,14,1);
        test1.init(g1);
        try {
            test1.save("/Users/ofirmagen/Desktop/ofir.txt");
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    @Test
    void load() {
        weighted_graph_algorithms test1 = new WGraph_Algo();
        weighted_graph g1 = new WGraph_DS();
        test1.init(g1);
        test1.load("/Users/ofirmagen/Desktop/ofir.txt");
        test1.getGraph();



        weighted_graph g11 = new WGraph_DS();
        g11.addNode(11);
        g11.addNode(12);
        g11.addNode(13);
        g11.addNode(14);
        g11.connect(11,12,1);
        g11.connect(11,13,10);
        g11.connect(14,12,9);
        g11.connect(13,14,1);





        for (node_info n : test1.getGraph().getV()) {

            g11.getNode(n.getKey());
            // check the nodes if they Exists
            assertEquals(n.getKey(), g11.getNode(n.getKey()).getKey(), "need to be 0 node(my_test[heck the nodes if they Exists])");
            for (node_info ni : test1.getGraph().getV(n.getKey())) {

                test1.getGraph().getEdge(n.getKey(), ni.getKey());
                g11.hasEdge(n.getKey(), ni.getKey());
                // check the Neighbors of the nodes if they are connected
                assertEquals(true, g11.hasEdge(n.getKey(), ni.getKey()), "need to be 0 node(my_test[ check if all connected])");
                // check the Neighbors of the nodes have the same weight
                assertEquals(test1.getGraph().getEdge(n.getKey(), ni.getKey()), g11.getEdge(n.getKey(), ni.getKey()), "need to be 0 node(my_test[ check the weight])");
            }
        }
    }
    @Test
    void End(){
    double end = new Date().getTime();
    }
}







