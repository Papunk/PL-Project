package API;


import java.util.ArrayList;
import java.util.HashMap;

class Edge<V> {
    private V vertex;
    private int weight;
    public Edge(V v, int w) {
        vertex = v;
        weight = w;
    }
    public V getVertex() {
        return vertex;
    }
    public void setVertex(V vertex) {
        this.vertex = vertex;
    }
    public int getWeight() {
        return weight;
    }
    public void setWeight(int weight) {
        this.weight = weight;
    }
    public String toString(){

        return "( "+ vertex + ", " + weight + " )";
    }
    interface LNGraph<V> {


    }

    public class LNGraphStruct implements LNGraph {
        private HashMap<V, ArrayList<Edge<V>>> adjacencyList;
        private boolean direct;
        private ArrayList<V> vertexList;


        public void Graph(boolean isDirected) {
            adjacencyList = new HashMap<V, ArrayList<Edge<V>>>();
            direct = isDirected;
            vertexList = new ArrayList<V>();
        }
        public void add(V vertex, ArrayList<Edge<V>> connectedVertex) {
            adjacencyList.put(vertex, connectedVertex);
            vertexList.add(vertex);

            for (Edge<V> vertexConnectedToAddedVertex : connectedVertex) {
                ArrayList<Edge<V>> correspondingConnectedList = adjacencyList.get(vertexConnectedToAddedVertex.getVertex());
                if (correspondingConnectedList == null) {
                    adjacencyList.put(vertexConnectedToAddedVertex.getVertex(),
                            new ArrayList<Edge<V>>());
                    vertexList.add(vertexConnectedToAddedVertex.getVertex());
                    correspondingConnectedList = adjacencyList
                            .get(vertexConnectedToAddedVertex.getVertex());
                }

                if (!direct) {
                    int weight = vertexConnectedToAddedVertex.getWeight();
                    correspondingConnectedList.add(new Edge<V>(vertex, weight));
                }
            }

        }
        public boolean addArc(V source, V end, int weight) {
            if (!direct) {
                return false;
            }
            if (!adjacencyList.containsKey(source)) {
                ArrayList<Edge<V>> temp = new ArrayList<Edge<V>>();
                temp.add(new Edge<V>(end, weight));
                add(source, temp);
                return true;
            }

            if (!adjacencyList.containsKey(end)) {
                ArrayList<Edge<V>> temp = new ArrayList<Edge<V>>();
                add(end, temp);
            }
            adjacencyList.get(source).add(new Edge<V>(end, weight));
            return true;
        }

        public boolean addEdge(V verOne, V verTwo, int weight) {
            if (direct) {
                return false;
            }

            if (!adjacencyList.containsKey(verOne)) {
                ArrayList<Edge<V>> temp = new ArrayList<Edge<V>>();
                temp.add(new Edge<V>(verTwo, weight));
                add(verOne, temp);
                return true;
            }

            if (!adjacencyList.containsKey(verTwo)) {
                ArrayList<Edge<V>> temp = new ArrayList<Edge<V>>();
                temp.add(new Edge<V>(verOne, weight));
                add(verTwo, temp);
                return true;
            }

            adjacencyList.get(verOne).add(new Edge<V>(verTwo, weight));
            adjacencyList.get(verTwo).add(new Edge<V>(verOne, weight));
            return true;
        }

        public ArrayList<V> getAdjacentVertices(V vertex){
            ArrayList<V> returnList = new ArrayList<V>();
            for (Edge<V> edge : adjacencyList.get(vertex)) {
                returnList.add(edge.getVertex());
            }
            return returnList;
        }
        public double getDistanceBetween(V source, V end){
            for (Edge<V> edge : adjacencyList.get(source)) {
                if (edge.getVertex() == end){
                    return edge.getWeight();
                }
            }
            return Double.POSITIVE_INFINITY;
        }




    }}