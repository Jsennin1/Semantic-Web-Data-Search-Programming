
import java.util.ArrayList;
import java.util.List;


public class Node {
	public List<Integer> s_predicates;
	public List<Integer> s_objects;
	public List<Integer> s_poequals;
	public List<Integer> p_subjects;
	public List<Integer> p_objects;
	public List<Integer> p_soequals;
	public List<Integer> o_subjects;
	public List<Integer> o_predicates;
	public List<Integer> o_spequals;
	public List<Integer> superclasses;
	public List<Integer> types;
	public List<Integer> domains;
	public List<Integer> ranges;
	public List<Integer> domain_s;
	public List<Integer> range_s;
	public List<Integer> subclasses;
	public List<Integer> instances;
	public List<Integer> superproperties;
	public List<Integer> subproperties;
	
	public Node(){
		s_predicates = new ArrayList<Integer>();
		s_objects = new ArrayList<Integer>();
		s_poequals = new ArrayList<Integer>();
		p_subjects = new ArrayList<Integer>();
		p_objects = new ArrayList<Integer>();
		p_soequals = new ArrayList<Integer>();
		o_subjects = new ArrayList<Integer>();
		o_predicates = new ArrayList<Integer>();
		o_spequals = new ArrayList<Integer>();
		superclasses = new ArrayList<Integer>();
		types = new ArrayList<Integer>();
		domains = new ArrayList<Integer>();
		ranges = new ArrayList<Integer>();
		domain_s = new ArrayList<Integer>();
		range_s = new ArrayList<Integer>();
		subclasses = new ArrayList<Integer>();
		instances = new ArrayList<Integer>();
		superproperties = new ArrayList<Integer>();
		subproperties = new ArrayList<Integer>();
	}


	public void adds_poequals(int x) {
		s_poequals.add(x);
	}
	public void addp_soequals(int x) {
		p_soequals.add(x);
	}
	public void addo_spequals(int x) {
		o_spequals.add(x);
	}

	public void addtypes(int s, int o ,Node nodeO) {
		types.add(o);
		nodeO.instances.add(s);
	}
	public void addsubclasses(int s, int o ,Node nodeO) {
		subclasses.add(o);
		nodeO.superclasses.add(s);
	}
	public void addsubproperties(int s, int o ,Node nodeO) {
		subproperties.add(o);
		nodeO.superproperties.add(s);
	}
	public void adddomain(int s, int o ,Node nodeO) {
		domains.add(o);
		nodeO.domain_s.add(s);
	}
	public void addrange(int s, int o , Node nodeO) {
		ranges.add(o);
		nodeO.range_s.add(s);
	}
	
	/*uri[0] = ‘‘rdf:type’’
	uri[1] = ‘‘rdfs:subClassOf’’
	uri[2] = ‘‘rdfs:subPropertyOf’’
	uri[3] = ‘‘rdfs:domain’’
	uri[4] = ‘‘rdfs:range’’*/

	public void s_addChild(int p, int o) {
		s_predicates.add(p);
		s_objects.add(o);
	}
			 // 自ノードを述語として，主語と目的語を追加
	public void p_addChild(int s, int o) {
		p_subjects.add(s);
		p_objects.add(o);
	}
			 // 自ノードを目的語として，主語と述語を追加
	public void o_addChild(int s, int p) {
		o_subjects.add(s);
		o_predicates.add(p);
	}
	
}

