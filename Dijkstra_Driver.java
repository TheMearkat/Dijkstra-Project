import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class Dijkstra_Driver {

	   @SuppressWarnings("resource")
	   public static void main(String args[]) throws IOException
	      {
	          
	          String pathCostTime[][],requestedPathList[][];
	          BufferedReader flightData, requestedFlightData;
	          List<String> nodesList;
	          PrintWriter out = new PrintWriter("Output.txt");
	           
	              flightData = new BufferedReader(new FileReader("FlightData.txt"));
	              requestedFlightData = new BufferedReader(new FileReader("FlightPlans.txt"));
	              String str;
	              nodesList = new ArrayList<String>();
	              pathCostTime = new String[Integer.parseInt(flightData.readLine())][4];
	              requestedPathList = new String[Integer.parseInt(requestedFlightData.readLine())][3];
	              int i=0, j;
	              String _node;
	              
	              while((str = flightData.readLine()) != null)
	              {
	                  j=0;
	                  StringTokenizer data = new StringTokenizer(str,"|");
	                  int k = 1;
	                  while(k<=2)
	                  {
	                      if(!nodesList.contains(_node = data.nextToken()))
	                      {
	                          pathCostTime[i][j++] = _node;
	                          nodesList.add(_node);
	                      }
	                      else
	                          pathCostTime[i][j++] = _node;
	                      k++;
	                  }
	                  while(data.hasMoreTokens())
	                  {
	                      pathCostTime[i][j++] = data.nextToken();
	                  }
	                  i++;
	              }
	              i=0;
	              
	              while((str = requestedFlightData.readLine()) != null)
	              {
	                  j=0;
	                  StringTokenizer data = new StringTokenizer(str,"|");
	                  while(data.hasMoreTokens())
	                      requestedPathList[i][j++] = data.nextToken();
	                  i++;
	              }
	              i=1;
	           
	              for(String requestedPath[] : requestedPathList)
	              {
	                  if(!(nodesList.contains(requestedPath[0])&& nodesList.contains(requestedPath[1])))
	                  {
	                      System.out.println("Path not found.");
	                      continue;
	                  }
	                  String _type , _otherType;
	                  if(requestedPath[2].equals("T"))
	                  {
	                      _type = "Time";
	                      _otherType = "Cost";
	                  }
	                  else
	                  {
	                      _type = "Cost";
	                      _otherType = "Time";
	                  }
	                 
	                  Dijkstra dijkstras = new Dijkstra(nodesList);
	                  
	                  dijkstras.dijkstra_algorithm(pathCostTime, requestedPath);
	                  out.println("Flight " + i + ": "+ dijkstras.source + ", " +
	                          dijkstras.destination + " (" + _type + ")");
	                  for (String node:nodesList)
	                  {
	                      if(!node.equals(dijkstras.destination))
	                          continue;
	                      List<String> completePath = findPath(dijkstras.visitedNodesList,
	                              dijkstras.destination);
	                      
	                      for (int k = 0; k < completePath.size(); k++)
	                      {
	                          if(k == completePath.size()-1 )
	                              out.print(completePath.get(k)+". ");
	                          else
	                              out.print(completePath.get(k)+" --> ");
	                      }
	                      
	                      out.println(_type + ": " + dijkstras.minDistance.get(node) + " "
	                              + _otherType + ": " + dijkstras.distances.get(node));
	                      break;
	                  }
	                  i++;
	              }
	          
	          out.close();
	      }
	      
	     
 private static List<String> findPath(List<Path> visitedNodesList, String Destination)
	      {
	          List<String> completePath = new ArrayList<String>();
	          for( Path path : visitedNodesList)
	          {
	              if(!path.exists(Destination))
	                  continue;
	              completePath = findPath(visitedNodesList, path.getNode());
	              completePath.add(Destination);
	              return completePath;
	          }
	          completePath.add(Destination);
	          return completePath;
	      }
	   }