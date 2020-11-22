package Ex1;
import java.io.Serializable;
import java.util.*;

public class WGraph_DS implements  weighted_graph , Serializable {

    private HashMap<Integer, node_info> graph;
    private int MC=0;
    private int Edge=0;
    private HashMap<Integer, HashMap<Integer,node_info>> Ni;
    private HashMap<Integer, HashMap<Integer,Double>> Niw;


    public WGraph_DS() {
        graph = new HashMap<Integer, node_info>();
        Niw = new HashMap<Integer, HashMap<Integer,Double>>();
        Ni = new HashMap<Integer, HashMap<Integer,node_info>>();
    }

    /**
     * return the node_info by the key, by check if the node is in the graph.
     * @param key - the node_id
     * @return the node_info with this key
     */
    @Override
    public node_info getNode(int key) {
        if (graph.containsKey(key)){
            return graph.get(key);
        }

        return null;
    }

    /**
     * check if  node1 and node2 are connected. by checking if the nodes in the graph.
     * checking if node1 have in the hashmap of the Neighbors the node2
     * checking if node2 have in the hashmap of the Neighbors the node1
     * @param node1
     * @param node2
     * @return boolean
     */
    @Override
    public boolean hasEdge(int node1, int node2) {
        if((node1 == node2)) {
            return false;
        }
        else {
            return  (graph.containsKey(node1)) &&
                    (Ni.get(node1).containsKey(node2)) &&
                    (graph.containsKey(node2)) &&
                    (Ni.get(node2).containsKey(node1));


        }
    }


    /**
     *get the edge between node1 and node2
     * checking by the function "hasEdge" if they are connected
     * take from the hashmap "Niw" the weight
     * @param node1
     * @param node2
     * @return weight of the edge
     */
    @Override
    public double getEdge(int node1, int node2) {
        if(hasEdge(node1,node2)){
            return Niw.get(node1).get(node2);
        }

        return -1;
    }

    /**
     * add a new node to the graph with the given key.
     * checking if the graph have a node with the same key
     * make new node with this key
     * adding to the hashmap "graph" and put new hashmap "NiWeight", "nodeNi" to the hashmaps "Ni", "Niw"
     * @param key
     */
    @Override
    public void addNode(int key) {
        if(!graph.containsKey(key)){
            nodeinfo n= new nodeinfo(key);
            graph.put(key,n);

            HashMap<Integer, Double>  NiWeight = new HashMap<Integer, Double>();
            Niw.put(key,NiWeight);
            HashMap<Integer, node_info>  nodeNi = new HashMap<Integer, node_info>();
            Ni.put(key,nodeNi);

            this.MC++;

        }
    }

    /**
     * Connect an edge between node1 and node2, with an edge with weight.
     * checking if is already have one, if there is, change the weight between them in the hashmap "Niw"
     * add to node1  hashmap "Ni" the node2
     * add to node1  hashmap "Niw" the node2
     * run Twice to add all to node1 and then make the same for the node 2
     *
     * @param node1
     * @param node2
     * @param w
     */
    @Override
    public void connect(int node1, int node2, double w) {
        if((node1==node2)||graph.get(node2)==null||graph.get(node1)==null){
            return;
        }

        if(hasEdge(node1,node2)){
            int save1;
            for (int i=0; i < 2; i++) {
                Niw.get(node1).put(node2, w);
                save1=node1;
                node1=node2;
                node2=save1;
            }

        }


        else if(!(hasEdge(node1,node2) )) {
            int save;
            for (int i=0; i < 2; i++) {
                Niw.get(node1).put(node2, w);
                Ni.get(node1).put(node2, graph.get(node2));
                save=node1;
                node1=node2;
                node2=save;
            }
            Edge++;
            this.MC++;
        }
    }

    /**
     * make a collection of all the node_info in the graph
     * @return Collection<node_info>
     */
    @Override
    public Collection<node_info> getV() {
        return graph.values();
    }

    /**
     *make a collection of all the node_info that are connected to this node_info
     * checking if the graph have this node
     * go to hashmap "Ni" and make Collection of his Neighbors
     * @param node_id
     * @return Collection<node_info>
     */
    @Override
    public Collection<node_info> getV(int node_id) {
        if(graph.containsKey(node_id)){
            return Ni.get(node_id).values();
        }

        return null;
    }

    /**
     * Delete the node (with the given ID) from the graph -
     * removes all edges that are connected to this node BY runing all his  Neighbors
     * go to hashmap "Ni" and remove the edges from his Neighbors to the node
     * remove the weight from the hashmap "Niw"  between his Neighbors
     * remove the node from the hashmap "graph"
     * @param key
     * @return the data of the removed node (null if none).
     */
    @Override
    public node_info removeNode(int key) {
        if (graph.containsKey(key)){
            Queue<Integer> to_delete=new LinkedList<Integer>();
            for(Map.Entry<Integer,node_info> n:Ni.get(key).entrySet()){
                to_delete.add(n.getKey());

            }
            while(!to_delete.isEmpty()){
                removeEdge(key,to_delete.poll());

            }

            graph.remove(key,graph.get(key));

            this.MC++;
        }



        return null;
    }

    /**
     * Delete the edge from the graph, by Delete from the hashmap "Ni" the edge between them
     * Delete from the hashmap "Niw" the weight
     * Note: this method should run in O(1) time.
     *
     * @param node1
     * @param node2
     */
    @Override
    public void removeEdge(int node1, int node2) {
        if (node1!=node2 && hasEdge(node1,node2)) {

            Ni.get(node1).remove(node2,graph.get(node2));
            Ni.get(node2).remove(node1,graph.get(node1));
            Niw.get(node1).remove(node2,Niw.get(node1).get(node2));
            Niw.get(node2).remove(node1,Niw.get(node2).get(node1));
            Edge--;
            this.MC++;
        }
    }

    /**
     * return the number of nodes in the graph, by counting every time that we add anode (add) or remove node (subtract)
     * Note: this method should run in O(1) time.
     *
     * @return
     */
    @Override
    public int nodeSize() {
        return graph.size();
    }

    /**
     * return the number of edges (undirectional graph).
     * counting every time that we add edge (add) or remove remove (subtract) (and more metods thet are changing the number of the edges)
     * Note: this method should run in O(1) time.
     *
     * @return
     */
    @Override
    public int edgeSize() {

        return Edge;
    }

    /**
     * return the Mode Count - for testing changes in the graph.
     * counting every time that we did changes to the graph
     * @return
     */
    @Override
    public int getMC() {
        return this.MC;
    }




    static class nodeinfo implements node_info , Serializable {
        private int key;
        private double Tag = -1;
        private String Info;


        /**
         * add key to making a nodeinfo.
         */
        public nodeinfo(int key) { // add key to making nodeinfo
            this.key = key;
            String Info= new String();
        }

        /**
         * Return the key of the node.
         * @return
         */
        @Override
        public int getKey() {

            return this.key;
        }

        /**
         * return the Info associated with this node.
         * @return
         */
        @Override
        public String getInfo() {

            return this.Info;
        }

        /**
         * make a changes to the Info associated with this node.
         * @param s
         */
        @Override
        public void setInfo(String s) {
            this.Info = s;

        }

        /**
         * geting the Tag that associated with this node
         * @return
         */
        @Override
        public double getTag() {

            return this.Tag;
        }

        /**
         * set the Tag that associated with this node
         *
         * @param t - the new value of the tag
         */
        @Override
        public void setTag(double t) {
            this.Tag = t;
        }
    }
}
