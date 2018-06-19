package gov.dot.nhtsa.odi.ewr.aws.rds;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

/*
 * @author Bienvenido Ortiz
 */
public class RDSConnectionDemo implements RequestHandler<String, String> {
	Statement statement;
	ResultSet resultSet;

	@Override
	public String handleRequest(String input, Context context) {
		StringBuilder stBuilder = new StringBuilder();
		try {
			Class.forName("org.postgresql.Driver");
			String url = "jdbc:postgresql://artemispostgresql.cmjb9lee3x9u.us-east-1.rds.amazonaws.com:5432/apostgre01";
			String username = "artemispsystem";
			String password = "postartsystem2018";

			Connection conn = DriverManager.getConnection(url, username, password);

			context.getLogger().log("\nATTEMPTING TO QUERY DB");
			String query = "select * from ewr.ewr_f_death_injury where ewr_id=" + input;
			context.getLogger().log("\nQUERY: " + query.toUpperCase());
		      
			Statement statement = conn.createStatement();
			resultSet = statement.executeQuery(query);

			
			if(resultSet.next()) {	//while(resultSet.next()) {
			
				stBuilder.append("EWR REPORT ID: " + resultSet.getString("ewr_report_id"));

			}
			context.getLogger().log("\nOUTPUT: " + stBuilder.toString().toUpperCase() + "\n\n");
			
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		return stBuilder.toString().toUpperCase();
	}

}
