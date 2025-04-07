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
        @DisplayName("Database connection should be valid")
        public void testDatabaseConnection() {
            try (Connection connection = dataSource.getConnection()) {
                assertTrue(connection.isValid(1), "Connection to the test database should be valid");
            } catch (Exception e) {
                fail("Exception should not be thrown");
            }
        }

        @Test
        @DisplayName("Database should include table 'users'")
        public void testDatabaseIncludesUsersTable() {
            try (Connection connection = dataSource.getConnection()) {
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(
                    "SELECT TABLE_NAME FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_SCHEMA = 'PUBLIC' AND TABLE_NAME = 'USERS'"
                );
                assertTrue(resultSet.next(), "The 'users' table should exist in the test database");
            } catch (Exception e) {
                fail("Exception should not be thrown");
            }
        }
    }