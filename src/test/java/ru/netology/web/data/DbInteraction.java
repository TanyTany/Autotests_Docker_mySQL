package ru.netology.web.data;


import lombok.val;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;



import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;



public final class DbInteraction {


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
        val cardsSQL = "DELETE FROM cards;";
        val authSQL = "DELETE FROM auth_codes;";
        val usersSQL = "DELETE FROM users;";
        val transSQL = "DELETE FROM card_transactions;";
        val runner = new QueryRunner();
        try (
                Connection conn = DriverManager.getConnection(
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
