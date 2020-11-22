package Ex1;

import Ex1.node_info;
import ex0.node_data;
import org.w3c.dom.ls.LSOutput;

import java.io.*;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Stack;

import static java.lang.Integer.MAX_VALUE;

public class WGraph_Algo implements weighted_graph_algorithms {
    private weighted_graph graph;



    public WGraph_Algo(){
        this.graph= new WGraph_DS();
    }

    /**
     * reset the tag of the node to a.
     * @param a
     */
    private void reset(int a){
        for (node_info v:this.graph.getV()) {
            v.setTag(a);
        }
    }
    /**
     * reset the info of the node to be his "key"
     */
    private void reset(){

        for (node_info v:this.graph.getV()) {
            String x="";
            x+=(int)v.getKey();
            v.setInfo(x);
        }
    }


    /**
     *init the graph to the graph algo for to ues the algo metods
     * @param g
     */
    @Override
    public void init(weighted_graph g) {
        this.graph=g;
    }

    /**
     * Return the underlying graph of which this class works.
     *
     * @return
     */
    @Override
    public weighted_graph getGraph() {
        return this.graph;
    }

    /**
     * make a deep copy of this weighted graph.
     * run on all the nodes in the hashmap "graph" and keep they "key" ,"tag", "Info" to make new node with the same parameters
     * add to the new graph the new node

     * run on all the nodes in the hashmap "Ni" and "Niw", and connect on the new graph the nodes with the same weight like in the old graph

     *
     *
     * @return
     */
    @Override
    public weighted_graph copy() {
        WGraph_DS f= new WGraph_DS();
        int key;
        double tag;
        String Info;
        for (node_info n:graph.getV()) { // run on the graph, get all the nodes and create them in to the new graph
            key=n.getKey();
            tag=n.getTag();
            Info=n.getInfo();
            f.addNode(key);
            f.getNode(key).setTag(tag);
            f.getNode(key).setInfo(Info);
        }
        for (node_info n:graph.getV()){ // geting the Neighbors of the node and connect them with they weight
            for(node_info i:  graph.getV(n.getKey())){
                f.connect(n.getKey(),i.getKey(), graph.getEdge(n.getKey(),i.getKey()));
            }
        }
        return f;
    }

    /**
     * check if the graph hane nodes
     * keep the size of the graph
     * make Queue for use BFS
     * reset the tag to -1
     * run by BFS on all the Neighbors of the node and if the tag is !=0 add him to the Queue and count him
     * if the counter == the size of the graph, all the nodes are connected
     * @return boolean
     */
    @Override
    public boolean isConnected() {
        Queue<node_info> bfsq = new LinkedList<>();
        int sizeG = graph.getV().size();

        int counter=0;
        node_info first=null;
        reset(-1);
        for (node_info n:graph.getV()) {
            first=n;
            if(first!=null) {
                break;
            }
        }
        if(first==null){
            return true;
        }
        bfsq.add(first);
        first.setTag(0);
        counter++;
        while(!bfsq.isEmpty()){
            node_info tmp = bfsq.poll();
            for (node_info neighbor_node : graph.getV(tmp.getKey())) { // run on the neighbors node and tag them
                if (neighbor_node.getTag() != 0) {
                    counter++;
                    neighbor_node.setTag(0);
                    bfsq.add(neighbor_node);
                }
            }
        }
        reset(-1);
        if(sizeG==counter)return true;
        return false;
    }

    /**
     * returns the length of the shortest path between src to dest
     * run on the graph by Dykstra
     * keep the Info of the perent for save the weight and add to the node
     * return the info of the "dest" and calculate all the Info of this node
     * Note: if no such path --> returns -1
     *
     * @param src  - start node
     * @param dest - end (target) node
     * @return
     */
    @Override
    public double shortestPathDist(int src, int dest) {
        if(src==dest|| graph.getNode(src)==null ||graph.getNode(dest)==null){  // if its the same or not in the graph
            return -1;
        }

        Queue<Integer> need_to_pass = new LinkedList<>(); // add the key of the
        reset(); // reset the node info
        reset(MAX_VALUE); // set the tag of the node to MAX_VALUE
        need_to_pass.add(src);
        double perenttag=0; // keeping the tag of the "perent" of the node

        int start=src;

        graph.getNode(start).setTag(0);

        while(!need_to_pass.isEmpty()) { // run on the Queue
            perenttag =graph.getNode(need_to_pass.peek()).getTag();
            String perentinfo="";
            perentinfo+=graph.getNode(need_to_pass.peek()).getInfo();
            int perent_key=need_to_pass.poll(); // keep the key of the node that we working on

            for (node_info n : graph.getV(perent_key)) { // run on the Neighbors "checker"
                if (n.getTag() == MAX_VALUE) { // add the node to the queue
                    need_to_pass.add(n.getKey());
                    n.setInfo(n.getInfo()+","+perentinfo);
                }


                if ((graph.getEdge(perent_key, n.getKey())+perenttag) < n.getTag()) {
                    n.setTag(graph.getEdge(perent_key, n.getKey())+perenttag);
                    n.setInfo(perentinfo+","+n.getKey());



                }

            }


        }
        if(graph.getNode(dest).getTag()==MAX_VALUE){
            return  -1;
        }
        return graph.getNode(dest).getTag();
    }



    /**
     * returns the the shortest path between src to dest - as an ordered List of nodes:
     * run by Dykstra and keep the "key"
     * add to the list
     * Note if no such path --> returns null;
     *
     * @param src  - start node
     * @param dest - end (target) node
     * @return
     */
    @Override
    public List<node_info> shortestPath(int src, int dest) {
        if (graph.nodeSize()==0 || shortestPathDist(src,dest)==-1){
            return null;

        }


        LinkedList<node_info> nodePath = new LinkedList<node_info>();
        shortestPathDist(src,dest); // make  Info from the shortestPathDist()
        String info =graph.getNode(dest).getInfo();
        int keys;
        String[] Snode =info.split(",");
        for(int i=0;i<Snode.length;i++){
            keys=Integer.parseInt(Snode[i]);
            nodePath.add(graph.getNode(keys));
      //      System.out.println(keys+"key");
        }
        return nodePath;
    }

    /**
     * Saves this weighted (undirected) graph to the given
     * file name
     *
     * @param file - the file name (may include a relative path).
     * @return true - iff the file was successfully saved
     */
    @Override
    public boolean save(String file) {

        try {
            FileOutputStream fileOut = new FileOutputStream(file);
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(graph);
            out.close();
            fileOut.close();
            System.out.printf("Serialized data is saved in /tmp/employee.ser");
        } catch (IOException i) {
            i.printStackTrace();
        }
        return false;
    }

    /**
     * This method load a graph to this graph algorithm.
     * if the file was successfully loaded - the underlying graph
     * of this class will be changed (to the loaded one), in case the
     * graph was not loaded the original graph should remain "as is".
     *
     * @param file - file name
     * @return true - iff the graph was successfully loaded.
     */
    @Override
    public boolean load(String file) {
        try {
            FileInputStream fileIn = new FileInputStream(file);
            ObjectInputStream in = new ObjectInputStream(fileIn);
            graph = (weighted_graph) in.readObject();
            in.close();
            fileIn.close();
        } catch (IOException i) {
            i.printStackTrace();
            return false;
        }
        catch (ClassNotFoundException c) {
            System.out.println("graph is class not found");
            c.printStackTrace();
            return false;
        }
        return false;
    }
}
