
import java.util.InputMismatchException;
import java.util.Scanner;
import java.io.*; 
import static java.lang.System.exit;
import java.util.ArrayList;
import java.util.LinkedList;


public class find_route {
    
    static LinkedList source=new LinkedList();
    static LinkedList destination=new LinkedList();
    static LinkedList distance=new LinkedList();
    static String[] splitp=new String[3];
    static LinkedList<String> visited=new LinkedList<String>();   
    static LinkedList fringequeue=new LinkedList();
    static String destination1;
    static String distance1;
    static int dis;
    static int statereached=0;
     static int depth=0;
     static class Node
    {
       Node next;
       String name;
       int depth;
       double  pathcost;
       Node parentnode;
       Node(String newname, int newd, double newc,Node newp){
       this.name=newname;
       this.depth=newd;
       this.pathcost=newc;
       this.parentnode=newp;
       }
       public  double getnodecost()
       {
           return pathcost;
       }
        public String getnodestate()
       {
           return name;
       }
         public  int getnodedepth()
       {
           return depth;
       }
          public  Node getnodeparent()
       {
           return parentnode;
       }
      
    }
    static int index=0;
    static int i=0;
    static int dislength;
    static int sourcelen;
    static int destinationlen;
    static int present=0;
    
    
    public static Node insertNode(Node node) {
    if (node == null)
        return null;
//reference from https://codereview.stackexchange.com/questions/8521/insert-sort-on-a-linked-list
    // Make the first node the start of the sorted list.
    Node sortedList = node;
    node = node.next;
    sortedList.next = null;

    while(node != null) {
        // Advance the nodes.
        final Node current = node;
        node = node.next;

        // Find where current belongs.
        if (current.pathcost < sortedList.pathcost) {
            // Current is new sorted head.
            current.next = sortedList;
            sortedList = current;
        } else {
            // Search list for correct position of current.
            Node search = sortedList;
            while(search.next != null && current.pathcost > search.next.pathcost)
                search = search.next;

            // current goes after search.
            current.next = search.next;
            search.next = current;
        }
    }

    return sortedList;
}
    public static void main (String[] args) throws IOException
 {

     try {
                        Scanner scanner=new Scanner(System.in);
			File file = new File(args[0]);
			FileReader fileReader = new FileReader(file);
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			StringBuffer stringBuffer = new StringBuffer();
			String line;
			while ((line = bufferedReader.readLine()) != null) 
                        {
                              if(line.contains("END OF INPUT"))
                              {
                              }
                              else{
                          
                            splitp=line.split(" ");
                            source.add(splitp[0]);
                            destination.add(splitp[1]);
                            distance.add(splitp[2]);
                            i++;
                            
                                  

			}}
			fileReader.close();
			
int a,b;
  
       
     
     
      // System.out.println("Enter the source ");
       String sourcen = args[1];
 
     //  System.out.println("Enter the destination ");
       String destinationn=args[2];
      
       int depth=0;
       int first=0;
      
     Node so=new Node(null,0,0,null);
     int n=source.size();
     a=0;
     for(a=0;a<n;a++)
     {
     
     String name= (String)source.get(a);   
    
         if(name.equals(sourcen) && !visited.contains(sourcen))
         {         
             if(first==0)
             {           
             so=new Node(sourcen,depth,0,null);
             first=1;
             fringequeue.add(index,so);
             index++;
             depth=depth+1;
             }
             String destination1=(String)destination.get(a);
             String distance1=(String)distance.get(a);
             int dis=Integer.parseInt(distance1);
             Node newn=new Node(destination1,depth,dis,so);
             fringequeue.add(index,newn);
             index++;
              }
        
     }
     depth=0;
     for(a=0;a<destination.size();a++)
     {
         String name= (String)destination.get(a);   
         if(name.equals(sourcen) && !visited.contains(sourcen))
         {if(first==0)
             {
             so=new Node(sourcen,depth,0,null);
             first=1;
             fringequeue.add(so);
             depth=depth+1;
             }
             String destination1=(String)source.get(a);
             String distance1=(String)distance.get(a);
             int dis=Integer.parseInt(distance1);
             Node newn=new Node(destination1,depth,dis,so);
             fringequeue.add(newn);
                       
         }
     }
visited.add(sourcen);
     

while(!fringequeue.isEmpty())
       {
           
           
        
        
        Node least=(Node)fringequeue.get(0);
        if((least.name).equals(destinationn))
        {  System.out.println("PATH FOUND");
           
           System.out.println( "Distance" +least.getnodecost()+"km");//entire path cost
           statereached=1;
           double totalcost=least.getnodecost();
           double prevcost=totalcost;
           Node trace=least.getnodeparent();
           System.out.print( "route:" + destinationn+" to " );
           while(trace!=null)
           {
               String name=trace.getnodestate();
               double tracecost=trace.getnodecost();
               totalcost=prevcost-tracecost;
               prevcost=tracecost;
               
               System.out.print(name+"      ");
               System.out.println(totalcost +"km");
               if(name!=sourcen)
               System.out.print(name+" to ");
               trace=trace.getnodeparent();
           }
           exit(0);
        }
        fringequeue.remove(0);
        depth=depth+1;
        if(!fringequeue.isEmpty()){
               Node newsource=(Node)fringequeue.get(0);//to get the first node
               double cost=newsource.getnodecost();//to get the first node cost to add to cumulative cost of its descendents
               String newstate=newsource.getnodestate();//to get the name of the node
               int presentfringe=0;
              if(!visited.contains(newstate)) 
              {
             for(a=0;a<source.size();a++)
           {
            String saveme=(String)source.get(a);
               if(saveme.equals(newstate))
                {
                    String des=(String)destination.get(a);
                    String distance1=(String)distance.get(a);
                    int dist1=Integer.parseInt(distance1);
                    dist1=dist1+(int)cost;//cumulative cost
                     
                     Node newnode1=new Node(des,depth,dist1,newsource);
                     
                     for(int y=0;y<fringequeue.size();y++)
                     {
                         Node node1=(Node)fringequeue.get(y);
                         String checknames=node1.getnodestate();
                         double checkcosts=node1.getnodecost();
                         if(checknames.equals(des))
                         {
                             presentfringe=1;
                          }
                     
                     }
                     
//                     //now add to the fringe
                     if(!visited.contains(des)&&(presentfringe==0))
                     fringequeue.add(newnode1);
                     else
                     {
                      for(int c=0;c<fringequeue.size();c++)
                      {
                         Node node2=(Node)fringequeue.get(c);
                         String checkname=node2.getnodestate();
                         double checkcost=node2.getnodecost();
                         if(checkname.equals(des))
                         {
                             if(dist1<checkcost)
                             {
                                 fringequeue.remove(node2);
                                 fringequeue.add(newnode1);
                             }
                         }
                         
                      }
                     }
                }
                }


         presentfringe=0;

            // to get the names of descendents traverse the destination array list
           for(a=0;a<source.size();a++)
            {
              String newstatename=(String)destination.get(a);
              if(newstatename.equals(newstate))
             {
                     String des=(String)source.get(a);
                     String dist=(String)distance.get(a);
                     int dist1=Integer.parseInt(dist);
                     dist1=dist1+(int)cost;//cumulative cost
                     Node newnode1=new Node(des,depth,dist1,newsource);
                      //now add to the fringe
                       for(int y=0;y<fringequeue.size();y++)
                     {
                         Node node1=(Node)fringequeue.get(y);
                         String checknames=node1.getnodestate();
                         double checkcosts=node1.getnodecost();
                         if(checknames.equals(des))
                         {
                             presentfringe=1;
                          }
                     
                     }
                      if(!visited.contains(des)&&(presentfringe==0))
                          fringequeue.add(newnode1);
                       else
                     {
                      for(int q=0;q<fringequeue.size();q++)
                      {
                         Node node2=(Node)fringequeue.get(q);
                         String checkname=node2.getnodestate();
                         double checkcost=node2.getnodecost();
                         if(checkname.equals(des))
                         {
                             if(dist1<checkcost)
                             {
                                 fringequeue.remove(node2);
                                 fringequeue.add(newnode1);
                             }
                         }
                         
                      }
                     }
                 }
             }
             
             
         visited.add(newstate);
       }  } 
        
     }
       if(statereached==0)
       {
           System.out.println("Distance:" +"infinity"   +"route: none");
       }
       
 
                        
		} 
                catch (IOException e) 
                {
			e.printStackTrace();
		}
 }//end of main 
    
    
    
    
    
    
    

  
}
