

import java.io.BufferedReader;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.io.*; 


public class LoadCSV {
	
    static String pathToCsv= "src/enshuu5.csv";
    static ArrayList<String> uri;     
    static Set<List<Integer>> idTriple;

     static void ReadCSV(){
        uri = new ArrayList<String>();
		uri.add("rdf:type");
		uri.add("rdfs:subClassOf");
		uri.add("rdfs:subPropertyOf");
		uri.add("rdfs:domain");
		uri.add("rdfs:range");
        idTriple = new HashSet<List<Integer>>();
        try (BufferedReader csvReader = new BufferedReader(new FileReader(pathToCsv))){
            
            String row;
            while ((row = csvReader.readLine()) != null) {
                String[] data = row.split(",");
                ArrayList<Integer> id = new ArrayList<Integer>();
                for (String word : data) {
                    if(!uri.contains(word)){
                    id.add(uri.size());
                    uri.add(word);
                    }
                    else{
                        id.add(uri.indexOf(word));
                    }
                }   
                idTriple.add(id);
            }
            csvReader.close();
        }catch (Exception e) {
            System.out.println(e+" file is not found");
        }
        
    }
    
    static void printURIandIdTriple(){
        System.out.println("uri:");
        
        for (int x = 0; x<uri.size(); x++){
            System.out.println("uri["+x+"]=" + uri.get(x));
        }
        System.out.println("idTriple:");

        for (List<Integer> list : idTriple) {
            System.out.println(list);
        }
    }

	    
	public static void main(String args[]){
	    ReadCSV();
	    printURIandIdTriple();
		NodeList nodeList = new NodeList(idTriple, uri.size());
		nodeList.idTripleToNode();

	    RDFQueryData rDFQueryData = new RDFQueryData(idTriple, uri,nodeList);
	    nodeList.printNodeList();
		rDFQueryData.ReadRDFQueryData();
		rDFQueryData.printVar();
		rDFQueryData.printQuery();
        Set<List<Integer>> subs = rDFQueryData.solvesQuery();
        System.out.println("subs= " + subs);
        rDFQueryData.printSingleSubsWithStrings(subs);

		
	}
}
