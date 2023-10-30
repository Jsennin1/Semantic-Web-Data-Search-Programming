import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;



public class RDFQueryData {

        public String pathToRDFQueryData= "src/QueryData.txt";
	    public ArrayList<String> RDFtriplePattern;     
	    public ArrayList<String> var;
        public Query  query;  
        Set<List<Integer>> idTriple;
	    ArrayList<String> uri;
        NodeList nodeList;    
        
        public RDFQueryData(Set<List<Integer>>  triple, ArrayList<String> uriData, NodeList nodeL){
            idTriple= triple;
            uri = uriData;
            query = new Query();
            nodeList= nodeL;
        }
        

        public void ReadRDFQueryData(){
	        var = new ArrayList<String>();
	        RDFtriplePattern = new ArrayList<String>();

	        try (BufferedReader queryDataReader = new BufferedReader(new FileReader(pathToRDFQueryData))){
	            
	            String row0;
	            String row="";
	            while ((row0=queryDataReader.readLine()) != null) {
					
					row += row0;
	            }


                String[] data = row.split("\\.");

	                for (int i = 0; i < data.length; i++) {


                        RDFtriplePattern.add(data[i]);
                        String[] tripleData = data[i].split(",");

                        Integer[][] ids = new Integer[3][2];
	                    for (int j = 0; j < tripleData.length; j++) {

                            if( tripleData[j].charAt(0)=='?'){
                                if(!var.contains(tripleData[j])){
                                    ids[j][0] = var.size();
                                    var.add(tripleData[j]);
                                }
                                else{
                                    ids[j][0] = var.indexOf(tripleData[j]);
                                }
                                ids[j][1] = 1;
                                }
                            else{
                                ids[j][0] = uri.indexOf(tripleData[j]);
                                ids[j][1] = 0;

                            }
                        }
                        query.s_addChild(ids[0][0],ids[0][1]);
                        query.p_addChild(ids[1][0],ids[1][1]);
                        query.o_addChild(ids[2][0],ids[2][1]);
	                }   
	            queryDataReader.close();
	        }catch (Exception e) {
	            System.out.println(e+" file is not found");
	        }
	        
	    }
	    
	    public void printVar(){
	        System.out.println("var:");
	        
	        for (int x = 0; x<var.size(); x++){
	            System.out.println("var["+x+"]=" + var.get(x));
	        }
	    }

        public void printQuery(){
	        System.out.println("Query:");
	        System.out.println("query_s = " + query.query_s);
            System.out.println("query_s_fg = " + query.query_s_fg);
            System.out.println("query_p =" + query.query_p);
            System.out.println("query_p_fg = " + query.query_p_fg);
            System.out.println("query_o = " + query.query_o);
            System.out.println("query_o_fg = " + query.query_o_fg);
	    }

        
    public Set<List<Integer>> solvesQuery(){
        Set<List<Integer>> subs = new HashSet<List<Integer>>();

        for (int i = 0; i < query.query_s.size(); i++) {
            if(query.query_s_fg.get(i)+query.query_p_fg.get(i)+query.query_o_fg.get(i)==0){

                if(nodeList.nodes[query.query_s.get(i)]==null){
                    return subs;
                }

                if(nodeList.nodes[query.query_s.get(i)].s_predicates.contains(query.query_p.get(i)) && 
                    nodeList.nodes[query.query_s.get(i)].s_objects.contains(query.query_o.get(i))){

                        subs.add(new ArrayList<Integer>());
                        
                }
                return subs;
            }
            
        }
        return subs;
    }

    public Query subQuery(Integer[] sub){




        return query;
    }


}
