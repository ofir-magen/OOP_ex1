# Node_Info

#setup and data structure:
--------
 using int "key" for the key, double "tag" for tag and String "Info" for Info
# Node_Info class methods:


getKey() - return the key of the node
      
String getInfo() - return a string of his info 
    
setInfo(String s) - set his string info 
    
double getTag() - return a int of his tag 
    
setTag(double t) - set his int tag 


#WGraph_DS
--------
#setup and data structure:
--------
useing HashMap "graph" to keep the nodes in the graph, HashMap "Ni" to keep the Neighbors of this nodes, HashMap "Niw" to keep the weight between this node and his Neighbors, graph int "edge" to count the edge of the graph and int "MC" to count the Changes on the graph

#WGraph_DS class methods:
--------
getNode(int key) - get a node from the graph

hasEdge(int node1, int node2) - check if there is a edge between node1 and node2........... return boolean

getEdge(int node1, int node2) - check if there is a edge between node1 and node2 and return the weight between them

addNode(node_data n) - add node to the graph

connect(int node1, int node2, double w) - create a edge between node1 and node2, and add weight to they adge

Collection<node_Info> getV() - return the Collection of the nodes in the graph

Collection<node_Info> getV(int node_id) - return the Collection of the nodes of this node

removeNode(int key) - remove the node and disconnect the edges that he have

removeEdge(int node1, int node2) - remove the edge between node1 and node2

nodeSize() - get the amount of all the node in the graph

edgeSize() - get the amount of all the edges in the graph

getMC() -  get the number of the changes in the graph

#WGraph_Algo
--------
#setup and data structure:
--------
useing weighted_graph "graph"

#WGraph_Algo class methods:
--------

init(weighted_graph g) - init the graph on which this set of algorithms operates on.

getGraph() - add a weighted_graph to WGraph_Algo for useing his Functions 


graph copy() - make a deep copy of the graph


boolean isConnected() - check if all the node of the graph is connected, return boolean

shortestPathDist(int src, int dest) - check the shortest path between two nodes by weight in the graph

shortestPath(int src, int dest) - get a list of the nodes we pass on shortest path by weight between two nodes

save(String file) - save a file of this node

load(String file) - load a graph file 
