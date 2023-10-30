
import java.util.ArrayList;
import java.util.List;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Set;

public class NodeList {

        Set<List<Integer>> idTriple;
		public Node[] nodes;
	    public List<Integer> equaltriples;
	    public List<Integer> spequals;
	    public List<Integer> soequals;
	    public List<Integer> poequals;


        


        public NodeList(Set<List<Integer>>  triple, int nodesize){
            idTriple= triple;
            nodes = new Node[nodesize];
            equaltriples = new ArrayList<Integer>();
            spequals = new ArrayList<Integer>();
            soequals = new ArrayList<Integer>();
            poequals = new ArrayList<Integer>();
        }

    public void idTripleToNode(){
        for (int i = 0; i < nodes.length; i++) {
        nodes[i] = new Node();
        }

        for (List<Integer> list : idTriple) {
            int s = list.get(0);
            int p = list.get(1);
            int o = list.get(2);
            nodes[s].s_addChild(p,o);
            nodes[p].p_addChild(s,o);
            nodes[o].o_addChild(s,p);
            if(o==p && p==s){
                equaltriples.add(s);
            }
            else if(o==p){
                nodes[s].adds_poequals(o);
                poequals.add(s);
            }
            else if(s==o){
                nodes[p].addp_soequals(o);
                soequals.add(p);

                
            }
            else if(p==s){
                nodes[o].addo_spequals(s);
                spequals.add(o);
            }
            SeperateByRDFtype(s,p,o);
        }
    }

    void SeperateByRDFtype(int s , int type , int o){
        /*uri[0] = ‘‘rdf:type’’
        uri[1] = ‘‘rdfs:subClassOf’’
        uri[2] = ‘‘rdfs:subPropertyOf’’
        uri[3] = ‘‘rdfs:domain’’
        uri[4] = ‘‘rdfs:range’’*/
        if(type==0){
            nodes[s].addtypes(s, o, nodes[o]);
        }
        else if(type==1){
            nodes[s].addsubclasses(s, o, nodes[o]);
        }
        else if(type==2){
            nodes[s].addsubproperties(s, o, nodes[o]);
        }
        else if(type==3){
            nodes[s].adddomain(s, o, nodes[o]);
        }
        else if(type==4){
            nodes[s].addrange(s, o, nodes[o]);
        }
    }
    public void printNodeList(){
	        System.out.println("NodeList:");
	        
	        for (int x = 0; x< nodes.length; x++) {
	            System.out.println("Node["+x+"]: s_predicates: " + nodes[x].s_predicates 
                + " s_objects: " + nodes[x].s_objects
                + " p_subjects: " + nodes[x].p_subjects
                + " p_objects: " + nodes[x].p_objects
                + " o_subjects: " + nodes[x].o_subjects
                + " o_predicates: " + nodes[x].o_predicates
                + " s_poequals: " + nodes[x].s_poequals
                + " p_soequals: " + nodes[x].p_soequals
                + " o_spequals: " + nodes[x].o_spequals
                + " types: " + nodes[x].types
                + " instances: " + nodes[x].instances
                + " subclasses: " + nodes[x].subclasses
                + " superclasses: " + nodes[x].superclasses
                + " subproperties: " + nodes[x].subproperties
                + " superproperties: " + nodes[x].superproperties
                + " domains: " + nodes[x].domains
                + " domain_s: " + nodes[x].domain_s
                + " ranges: " + nodes[x].ranges
                + " range_s: " + nodes[x].range_s);
                
            }
            System.out.println("equaltriples: " + equaltriples);
	    }
}

	