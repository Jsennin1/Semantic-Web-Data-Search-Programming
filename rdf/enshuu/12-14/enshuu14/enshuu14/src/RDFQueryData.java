import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;



public class RDFQueryData {

        public String pathToRDFQueryData= "src/QueryData.txt";
	    public ArrayList<String> RDFtriplePattern;     
	    public ArrayList<String> var;
	    public ArrayList<String> varsWithStar;
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
	        varsWithStar = new ArrayList<String>();
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
                            else if(tripleData[j].charAt(tripleData[j].length()-1)=='*'){
                                String deletedStar = tripleData[j].substring(0, tripleData[j].length()-1);
                                ids[j][0] = uri.indexOf(deletedStar);
                                varsWithStar.add(deletedStar);
                                ids[j][1] = 0;
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
            else if (varsWithStar.size()>0){
                subs = solvesQueryWithStar();
            }
            else if(query.query_s_fg.get(i)+query.query_p_fg.get(i)+query.query_o_fg.get(i)==1){
                
                if(query.query_s_fg.get(i)==1){
                    // i searched on o and gave the objects ID because predicates is generally rdf:type or another rdf thing and used alot which means more time to search
                    subs = FindOneUnknown(query.query_s.get(i),query.query_p.get(i),nodeList.nodes[query.query_o.get(i)].o_predicates,
                    nodeList.nodes[query.query_o.get(i)].o_subjects);

                }
                else if(query.query_p_fg.get(i)==1){
                    subs = FindOneUnknown(query.query_p.get(i),query.query_o.get(i),nodeList.nodes[query.query_s.get(i)].s_objects,
                    nodeList.nodes[query.query_s.get(i)].s_predicates);
                    
                }
                else{
                    subs = FindOneUnknown(query.query_o.get(i),query.query_p.get(i),nodeList.nodes[query.query_s.get(i)].s_predicates,
                    nodeList.nodes[query.query_s.get(i)].s_objects);
                }

            }
            else if(query.query_s_fg.get(i)+query.query_p_fg.get(i)+query.query_o_fg.get(i)==2){


                if(query.query_s_fg.get(i)==0){

                    if(query.query_p.get(i) == query.query_o.get(i)){
                        subs = FindSameTwo(query.query_o.get(i),nodeList.nodes[query.query_s.get(i)].s_poequals);

                    }
                    else{
                        subs = FindTwoUnknown(query.query_p.get(i),query.query_o.get(i),nodeList.nodes[query.query_s.get(i)].s_predicates,
                        nodeList.nodes[query.query_s.get(i)].s_objects);
                    }

                }
                else if(query.query_p_fg.get(i)==0){

                    if(query.query_s.get(i) == query.query_o.get(i)){
                        subs = FindSameTwo(query.query_s.get(i),nodeList.nodes[query.query_p.get(i)].p_soequals);

                    }
                    else{
                        subs = FindTwoUnknown(query.query_s.get(i),query.query_o.get(i),nodeList.nodes[query.query_p.get(i)].p_subjects,
                        nodeList.nodes[query.query_p.get(i)].p_objects);
                    }
                    
                
                }
                else{

                    if(query.query_s.get(i) == query.query_p.get(i)){
                        subs = FindSameTwo(query.query_s.get(i),nodeList.nodes[query.query_o.get(i)].o_spequals);

                    }
                    else{
                        subs = FindTwoUnknown(query.query_s.get(i),query.query_p.get(i),nodeList.nodes[query.query_o.get(i)].o_subjects,
                        nodeList.nodes[query.query_o.get(i)].o_predicates);
                    }
  
                }
        

            }
            else{



                if(query.query_s.get(i) == query.query_p.get(i) &&  query.query_s.get(i) == query.query_o.get(i) ){
                    subs = FindSameThree(query.query_s.get(i),nodeList.equaltriples);
                }
                else if(query.query_p.get(i) == query.query_o.get(i)){
                    subs = FindSameTwoWithUnkown(query.query_o.get(i),nodeList.poequals,query.query_s.get(i),nodeList.nodes,"po");

                }
                else if(query.query_s.get(i) == query.query_o.get(i)){
                    subs = FindSameTwoWithUnkown(query.query_o.get(i),nodeList.spequals,query.query_p.get(i),nodeList.nodes,"so");


                }
                else if(query.query_s.get(i) == query.query_p.get(i)){
                    subs = FindSameTwoWithUnkown(query.query_s.get(i),nodeList.spequals,query.query_o.get(i),nodeList.nodes,"sp");
                }
                else{
                    subs = FindDifferentThree(query.query_s.get(i),query.query_p.get(i),query.query_o.get(i),idTriple);
                }
            }


            
        }
        return subs;
    }
    public Set<List<Integer>> solvesQueryWithStar(){
        Set<List<Integer>> subs = new HashSet<List<Integer>>();
        ArrayList<Integer> openList = new ArrayList<Integer>();
        ArrayList<Integer> closeList= new ArrayList<Integer>();
        //subclassof or type
        if(query.query_p.get(0)==1 || query.query_p.get(0)==0){
            if(query.query_s_fg.get(0)==1){
                openList.add(query.query_o.get(0));  
                while(openList.size()>0){
                    ArrayList<Integer> ListToMerge = new ArrayList<Integer>();

                    for (Integer integer : nodeList.nodes[openList.get(0)].subclasses) {
                        if(!closeList.contains(integer))
                            ListToMerge.add(integer);
                    }
                    openList.addAll(ListToMerge);
                    closeList.add(openList.get(0));
                    openList.remove(0);
                }
                //typeof
                if(query.query_p.get(0)==0){
                    System.out.println("closeList.size()= " + closeList.size());
                    
                    for (int i = 0; i < closeList.size(); i++) {
                        for(int j = 0; j <  nodeList.nodes[closeList.get(i)].instances.size(); j++){
                            subs.add(new ArrayList<Integer>(Arrays.asList(query.query_s.get(0),nodeList.nodes[closeList.get(i)].instances.get(j))));
                        }
                    }
                }
                //subclass of
                else{
                    for (int i = 0; i < closeList.size(); i++) {
                        subs.add(new ArrayList<Integer>(Arrays.asList(query.query_s.get(0),closeList.get(i))));
                    }
                }
                
                
            }
            else{
                //typeof
                if(query.query_p.get(0)==0){
                    openList.addAll(nodeList.nodes[query.query_s.get(0)].types);
                }
                //subclass of
                else{
                    openList.add(query.query_s.get(0));                
                }
                while(openList.size()>0){
                    ArrayList<Integer> ListToMerge = new ArrayList<Integer>();

                    for (Integer integer : nodeList.nodes[openList.get(0)].superclasses) {
                        if(!closeList.contains(integer))
                            ListToMerge.add(integer);
                    }
                    openList.addAll(ListToMerge);
                    closeList.add(openList.get(0));
                    openList.remove(0);
                }
                for (int i = 0; i < closeList.size(); i++) {
                    subs.add(new ArrayList<Integer>(Arrays.asList(query.query_o.get(0),closeList.get(i))));
                }
            }
            
        }
        return subs;
    }
    Set<List<Integer>> FindOneUnknown(int unknownID, int knownID, List<Integer> known1list, List<Integer> unknownlist){
        Set<List<Integer>> subs = new HashSet<List<Integer>>();

        for (int i = 0; i < known1list.size(); i++) {

            if(known1list.get(i) == knownID){
                subs.add(new ArrayList<Integer>(Arrays.asList(unknownID,unknownlist.get(i))));
            }
        }
        return subs;
    }





    Set<List<Integer>> FindTwoUnknown(int unknownID1, int unknownID2,  List<Integer> known1list, List<Integer> known2list){
        Set<List<Integer>> subs = new HashSet<List<Integer>>();

        if(known1list.size()>0){
            for (int i = 0; i < known1list.size(); i++) {
                subs.add(new ArrayList<Integer>(Arrays.asList(unknownID1,known1list.get(i),unknownID2,known2list.get(i))));
            }

        }
        return subs;
    }




    Set<List<Integer>> FindSameTwo(int unknownID1,List<Integer> equalList){

        Set<List<Integer>> subs = new HashSet<List<Integer>>();
        if(equalList.size()>0){
            for (int i = 0; i < equalList.size(); i++) {
                subs.add(new ArrayList<Integer>(Arrays.asList(unknownID1,equalList.get(i))));
            }
        }
        return subs;
    }

    Set<List<Integer>> FindSameThree(int unknownID1,List<Integer> equalList){
        
        return FindSameTwo(unknownID1,equalList);
    }

    Set<List<Integer>> FindSameTwoWithUnkown(int unknownID1,List<Integer> equalList , int unknownID2 , Node[] nods, String sameOnes ){

        Set<List<Integer>> subs = new HashSet<List<Integer>>();
        if(equalList.size()>0){
            for (int i = 0; i < equalList.size(); i++) {
                
                if(sameOnes=="sp"){
                    for (int j = 0; j < nods[equalList.get(i)].o_spequals.size(); j++) {



                        

                        subs.add(new ArrayList<Integer>(Arrays.asList(unknownID1,nods[equalList.get(i)].o_spequals.get(j),unknownID2,equalList.get(i))));
                    }
                }
                else if(sameOnes=="so"){
                    for (int j = 0; j < nods[equalList.get(i)].p_soequals.size(); j++) {

                        subs.add(new ArrayList<Integer>(Arrays.asList(unknownID1,nods[equalList.get(i)].p_soequals.get(j),unknownID2,equalList.get(i))));
                    }

                }
                else{
                    for (int j = 0; j < nods[equalList.get(i)].s_poequals.size(); j++) {

                        subs.add(new ArrayList<Integer>(Arrays.asList(unknownID1,nods[equalList.get(i)].s_poequals.get(j),unknownID2,equalList.get(i))));
                    }

                }
                
            
            }
        }
        return subs;
    }

    Set<List<Integer>> FindDifferentThree(int unknownID1, int unknownID2 , int unknownID3 , Set<List<Integer>> idtriples){
        Set<List<Integer>> subs = new HashSet<List<Integer>>();

        if(idtriples.size()>0){
            for (List<Integer> list : idtriples) {
                subs.add(new ArrayList<Integer>(Arrays.asList(unknownID1,list.get(0),unknownID2,list.get(1)
                ,unknownID3,list.get(2))));
            }

        }

        return subs;
    }

    public void printSingleSubsWithStrings(Set<List<Integer>> subs){
    	if(query.query_s_fg.get(0)+query.query_p_fg.get(0)+query.query_o_fg.get(0)>1){
            //it prints only one unknown, didnt implement all of them so needed to check from subs array ids :(
            return;
        }
        System.out.println("Result");
        if(query.query_s_fg.get(0)==1){
            for (List<Integer> list : subs) {
                System.out.println(uri.get(list.get(1))+","+ uri.get(query.query_p.get(0))+","+uri.get(query.query_o.get(0))+".");
            }
        }
        else if(query.query_p_fg.get(0)==1){
            for (List<Integer> list : subs) {
                System.out.println(uri.get(query.query_s.get(0)) +","+uri.get(list.get(1)) +","+uri.get(query.query_o.get(0))+".");
            }
        }
        else{
            for (List<Integer> list : subs) {
                System.out.println(uri.get(query.query_s.get(0)) +","+ uri.get(query.query_p.get(0))  +","+ uri.get(list.get(1)) +".");
            }
        }

    }



    public Query subQuery(Integer[] sub){




        return query;
    }


}
