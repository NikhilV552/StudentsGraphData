import java.util.*;
import java.sql.*;

class FirstConnection{
    public static void main(String args[]){
        try{
            Class.forName("org.apache.jena.jdbc.remote.RemoteEndpointDriver");
            //Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con=DriverManager.getConnection("jdbc:jena:remote:query=http://localhost:3030/demo/query&update=http://locakhost:3030/demo/update");
            Statement stmt=con.createStatement();
            String query="PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>"+
            "PREFIX : <http://localhost:3030/demo>"+
            "SELECT   ?s ?p ?o "+
            "where {"+
            "    ?s ?p ?o"+
            "}" ;
            ResultSet rs=stmt.executeQuery(query);
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
                System.out.println(rs.getString("s")+":"+rs.getString("p")+":"+rs.getString("o"));
            }
            System.out.println(count);
            con.close();
        }catch(Exception e){
            e.printStackTrace();
        }
       System.out.println("Hello world"); 
    }
}
/*
java -cp .:mysql-connector-java-8.0.18.jar FirstConnection

java -cp .:jena-jdbc-driver-bundle-4.0.0.jar FirstConnection
*/