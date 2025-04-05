package edu.ntnu.stud;

    import static org.junit.jupiter.api.Assertions.assertTrue;
    import static org.junit.jupiter.api.Assertions.fail;

    import java.sql.Connection;
    import java.sql.ResultSet;
    import java.sql.Statement;

    import javax.sql.DataSource;

    import org.junit.jupiter.api.DisplayName;
    import org.junit.jupiter.api.Test;
    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.boot.test.context.SpringBootTest;
    import org.springframework.test.context.ActiveProfiles;

    /**
     * This class contains tests for verifying the database setup for testing.
     */
    @SpringBootTest
    @ActiveProfiles("test")
    public class TestDatabaseSetupTest {

        @Autowired
        private DataSource dataSource;

        /**
         * Tests the database connection and prints the names of all tables in the test database.
         *
         */
        @Test
        @DisplayName("Should return the amount and names of tables in the test database")
        public void testDatabaseConnection() {
            try (Connection connection = dataSource.getConnection()) {
                assertTrue(connection.isValid(1), "Connection to the test database should be valid");

                // Check the names of the tables in the database
                try (Statement statement = connection.createStatement()) {
                    ResultSet resultSet = statement.executeQuery(
                        "SELECT TABLE_NAME FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_SCHEMA = 'PUBLIC'"
                    );
                    while (resultSet.next()) {
                        String tableName = resultSet.getString("TABLE_NAME");
                        System.out.println("Table name: " + tableName);
                    }
                }
            } catch (Exception e) {
                fail("Exception should not be thrown");
            }
        }
    }