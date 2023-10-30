import java.util.ArrayList;
import java.util.List;

public class Query {

    List<Integer> query_s;
    List<Integer> query_s_fg;
    List<Integer> query_o;
    List<Integer> query_o_fg;
    List<Integer> query_p;
    List<Integer> query_p_fg;

    public Query(){
        query_s= new ArrayList<Integer>();
        query_s_fg= new ArrayList<Integer>();
        query_o= new ArrayList<Integer>();
        query_o_fg= new ArrayList<Integer>();
        query_p= new ArrayList<Integer>();
        query_p_fg= new ArrayList<Integer>();
    }


    public void s_addChild(int s, int fg) {
		query_s.add(s);
		query_s_fg.add(fg);
	}
			 // 自ノードを述語として，主語と目的語を追加
	public void p_addChild(int p, int fg) {
		query_p.add(p);
		query_p_fg.add(fg);
	}
			 // 自ノードを目的語として，主語と述語を追加
	public void o_addChild(int o, int fg) {
		query_o.add(o);
		query_o_fg.add(fg);
	}

}
