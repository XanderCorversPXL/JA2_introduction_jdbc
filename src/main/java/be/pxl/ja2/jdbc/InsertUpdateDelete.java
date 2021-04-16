package be.pxl.ja2.jdbc;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;

public class InsertUpdateDelete {

    private static final Logger LOGGER = LogManager.getLogger(InsertUpdateDelete.class);

    public static void main(String[] args) {

        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/musicdb", "user", "password");
             Statement statement = conn.createStatement()) {
            //conn.setAutoCommit(false);
            statement.execute("DELETE FROM contacts WHERE true");
            statement.execute("INSERT INTO contacts (name, phone, email) " +
                    "VALUES('Joe', 45632, 'joe@anywhere.com')");
            statement.execute("INSERT INTO contacts (name, phone, email) VALUES('Jane', 4829484, 'jane@somewhere.com')");
            statement.execute("INSERT INTO contacts (name, phone, email) VALUES('Fido', 9038, 'dog@email.com')");

            statement.execute("SELECT * FROM contacts");
            //result of last select query on statement
            ResultSet results = statement.getResultSet();
            int numberOfRows = 0;
            while (results.next()) {
                numberOfRows++;
                System.out.println(results.getString("name") + " " +
                        results.getInt("phone") + " " +
                        results.getString("email"));
            }
            LOGGER.info("Number of rows in table: " + numberOfRows);

            int updatedRows = statement.executeUpdate("UPDATE contacts set phone = '486666' WHERE name = 'Jane'");
            LOGGER.info("Rows updated: " + updatedRows);

            statement.executeUpdate("DELETE FROM contacts WHERE email = 'dog@email.com'");

            statement.execute("SELECT * FROM contacts");
            results = statement.getResultSet();
            while (results.next()) {
                numberOfRows++;
                System.out.println(results.getString("name") + " " +
                        results.getInt("phone") + " " +
                        results.getString("email"));
            }
        } catch (SQLException e) {
            LOGGER.fatal("Something went wrong.", e);
        }
    }
}
