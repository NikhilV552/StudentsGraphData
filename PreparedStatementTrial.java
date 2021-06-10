/*
PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>

PREFIX : <http://localhost:3030/student#>

SELECT ?testName ?subject ?marks 
WHERE{
	?student rdf:type :STUDENT.
  	?student :name "NIKHIL V".
  	?student :tests ?testNode.
  	?testNode rdf:label ?testName.
  	?testNode :score ?testSubjects.
  	?testSubjects :marks ?marks.
  	?testSubjects :subject ?subject.
}

*/

import java.sql.*;
class PreparedStatementTrial{
    public static void main(String args[]){
        try{
            Class.forName("org.apache.jena.jdbc.remote.RemoteEndpointDriver");
            //Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con=DriverManager.getConnection("jdbc:jena:remote:query=http://localhost:3030/student/query&update=http://locakhost:3030/student/update");
            String query="PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> "+
            "PREFIX : <http://localhost:3030/student#>"+
            "SELECT ?testName ?subject ?marks "+
            "WHERE{ "+
                " ?student rdf:type :STUDENT . "+
                "  ?student :name ? ."+
                "  ?student :tests ?testNode."+
                "  ?testNode rdf:label ?testName."+
                "  ?testNode :score ?testSubjects."+
                "  ?testSubjects :marks ?marks."+
                "  ?testSubjects :subject ?subject."+
            "}";
            PreparedStatement psmt=con.prepareStatement(query);
            //psmt.setString(1, ":STUDENT");
            psmt.setString(1, "NIKHIL V");
            ResultSet rs=psmt.executeQuery();
            ResultSetMetaData meta=rs.getMetaData();
            int c=meta.getColumnCount();
            System.out.println("count="+c);
            for(int i=1;i<=c;i++){
                System.out.println(meta.getColumnName(i)+":"+meta.getColumnClassName(i));
            }
            int count=0;
            while(rs.next()){
                count++;
                //System.out.println(rs.getString("s")+":"+rs.getString("p"));
                System.out.println(rs.getString("testName")+":"+rs.getString("subject")+":"+rs.getString("marks"));
            }
            System.out.println(count);
            con.close();
        }catch(Exception e){
            e.printStackTrace();
        }
       System.out.println("Hello world"); 
    }
}