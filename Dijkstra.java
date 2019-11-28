import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Set;


public class Dijkstra
{
	private Set<String> settled;
	private PriorityQueue<Node> pq;
	private int optType,otherType;
	private String pathCostTime[][];
	private List<String> nodeList;
	
	protected HashMap<String, Integer> minDistance;
	protected HashMap<String, Integer> distances;
	protected List<Path> visitedNodesList;
	protected String source;
	protected String destination;
	

   public Dijkstra(List<String> nodeList)
   {
       this.nodeList = nodeList;
       minDistance = new HashMap<String,Integer>();
       distances = new HashMap<String,Integer>();
       settled = new HashSet<String>();
       visitedNodesList = new ArrayList<Path>();
       pq = new PriorityQueue<Node>(new Node());
   }
   // definition of the method dijkstra_algorithm()
   public void dijkstra_algorithm(String pathCostTime[][], String requestedPath[])
   {
       String evaluationNode;
       source = requestedPath[0];
       destination = requestedPath[1];
       
       if(requestedPath[2].equalsIgnoreCase("C"))
       {
           optType = 2;
           otherType = 3;
       }
       else
       {
           optType = 3;
           otherType = 2;
       }
       this.pathCostTime = pathCostTime;
       for (String node:nodeList)
       {
    	   //set initial distances to infinity
           minDistance.put(node, Integer.MAX_VALUE);
           distances.put(node, Integer.MAX_VALUE);
       }
       pq.add(new Node(source, 0));
       minDistance.replace(source, 0);
       distances.replace(source, 0);
       
       while (!pq.isEmpty())
       {
           evaluationNode = getMinNode();
           Path evaluationNodeList = new Path();
           evaluationNodeList.setNode(evaluationNode);
           settled.add(evaluationNode);
           nextNode(evaluationNode, evaluationNodeList);
           if(!nodeExists(visitedNodesList, evaluationNode))
               visitedNodesList.add(evaluationNodeList);
       }
   }
   // definition of the method nodeExists()
   private boolean nodeExists(List<Path> visitedNodesList2, String evaluationNode)
   {
       for (Path path : visitedNodesList)
       {
           if(path.getNode().equals(evaluationNode))
               return true;
       }
       return false;
   }
  
   private String getMinNode()
   {
       String node = pq.remove().node;
       return node;
   }

   private void nextNode(String evaluationNode, Path evaluationNodeList)
   {
       int edgeDistance = -1;
       int newDistance = -1;
       for (int i = 0; i<pathCostTime.length; i++)
       {
           if(!pathCostTime[i][0].equals(evaluationNode))
               continue;
           String destinationNode;
           for (int j = 0; j < nodeList.size(); j++)
           {
               destinationNode = nodeList.get(j);
               if(!pathCostTime[i][1].equals(destinationNode))
                   continue;
               if (!settled.contains(destinationNode))
               {
                   edgeDistance = Integer.parseInt(pathCostTime[i][optType]);
                   newDistance = minDistance.get(evaluationNode) + edgeDistance;
                   if (newDistance < minDistance.get(destinationNode))
                   {
                       minDistance.replace(destinationNode,newDistance);
                       distances.replace(destinationNode,distances.get(evaluationNode) +
                               Integer.parseInt(pathCostTime[i][otherType]));
                       for (Path path : visitedNodesList)
                       {
                           if(path.exists(destinationNode))
                               path.delete(destinationNode);
                           break;
                       }
                       evaluationNodeList.add(destinationNode);
                   }
                   pq.add(new Node(destinationNode, minDistance.get(destinationNode)));
               }
           }
       }
   }
}







