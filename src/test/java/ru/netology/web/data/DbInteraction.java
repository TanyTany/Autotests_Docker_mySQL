package ru.netology.web.data;


import lombok.Value;
import lombok.val;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;


@Value
public class DbInteraction {
    private DbInteraction() {
    }

//    public static User getFirstUser() throws SQLException {
//        val usersSQL = "SELECT * FROM users;";
//        val runner = new QueryRunner();
//        User first;
//
//        try (  val conn = DriverManager.getConnection(
//                "jdbc:mysql://localhost:3306/app", "app", "pass"
//        );
//        ) {
//            first = runner.query(conn, usersSQL, new BeanHandler<>(User.class));
//        }
//        return first;
//    }
//
//    public static User getSecondUser() throws SQLException {
//        val usersSQL = "SELECT * FROM users;";
//        val runner = new QueryRunner();
//        User second;
//
//        try (
//                val conn = DriverManager.getConnection(
//                        "jdbc:mysql://localhost:3306/app", "app", "pass"
//                );
//        ) {
//            List<User> all = runner.query(conn, usersSQL, new BeanListHandler<>(User.class));
//            second = all.get(1);
//        }
//        return second;
//    }

//    public static User getUserFromId(String id) throws SQLException {
//        val usersSQL = "SELECT * FROM users WHERE id=?;";
//        val runner = new QueryRunner();
//        User user;
//
//        try (
//                val conn = DriverManager.getConnection(
//                        "jdbc:mysql://localhost:3306/app", "app", "pass"
//                );
//        ) {
//            user = runner.query(conn, usersSQL, new BeanHandler<>(User.class), id);
//        }
//        return user;
//    }

    public static void setUp() {
        var runner = new QueryRunner();
        String dataSQL = "INSERT INTO users(id,login, password,status) VALUES (?, ?, ?, ?);";

        try (
                Connection conn = DriverManager.getConnection(
                        "jdbc:mysql://localhost:3306/app", "app", "pass"
                )

        ) {

            runner.update(conn, dataSQL, DataHelper.getAuthInfo().getId(), DataHelper.getAuthInfo().getLogin(), DataHelper.getAuthInfo().getPassCode(), "active");
            runner.update(conn, dataSQL, DataHelper.getOtherAuthInfo().getId(), DataHelper.getOtherAuthInfo().getLogin(), DataHelper.getOtherAuthInfo().getPassCode(), "active");
        } catch (SQLException e) {
            System.out.println("Не удалось загрузить данные в таблищы базы данных");
        }
    }

    public static AuthCode getAuthCodeFromUser() throws SQLException {
        AuthCode code;
        val usersSQL = "SELECT * FROM auth_codes WHERE created = (SELECT MAX(created) FROM auth_codes);";
        val runner = new QueryRunner();
        try (
                val conn = DriverManager.getConnection(
                        "jdbc:mysql://localhost:3306/app", "app", "pass"
                );
        ) {
            code = runner.query(conn, usersSQL, new BeanHandler<>(AuthCode.class));
        }
        return code;
    }

    public static void deleteTables() throws SQLException {

        val usersSQL = "DELETE FROM users;";
        val cardsSQL = "DELETE FROM cards;";
        val authSQL = "DELETE FROM auth_codes;";
        val transSQL = "DELETE FROM card_transactions;";
        val runner = new QueryRunner();
        try (
                val conn = DriverManager.getConnection(
                        "jdbc:mysql://localhost:3306/app", "app", "pass"
                );
        ) {
            runner.update(conn, cardsSQL);
            runner.update(conn, authSQL);
            runner.update(conn, transSQL);
            runner.update(conn, usersSQL);
        }
    }

}
