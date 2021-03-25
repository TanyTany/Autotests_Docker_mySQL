package ru.netology.web.test;

import com.codeborne.selenide.Condition;
import lombok.val;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.web.data.DataHelper;
import ru.netology.web.data.DbInteraction;
import ru.netology.web.page.LoginPage;

import java.sql.SQLException;

import static com.codeborne.selenide.Selenide.*;

public class AuthorisationTest {
    @BeforeEach
    void setUp() {
        DbInteraction.setUp();
    }


    @Test
    void shouldAuthorisationValidUser() throws SQLException {
    open("http://0.0.0.0:9999/");
        val loginPage = new LoginPage();
        val authInfo = DataHelper.getAuthInfo();
        val verificationPage = loginPage.validLogin(authInfo);
        val verificationCode = DbInteraction.getAuthCodeFromUser();
        verificationPage.validVerify(verificationCode);
    }

    @Test
    void shouldAuthorisationInvalidPass() throws SQLException {
        open("http://0.0.0.0:9999/");
        val loginPage = new LoginPage();
        val authInfo = DataHelper.getAuthInfo();
        loginPage.invalidPass(authInfo);

    }

    @Test
    void shouldAuthorisationIfInvalidPassThreeTime() throws SQLException {
        open("http://0.0.0.0:9999/");
        val loginPage = new LoginPage();
        val authInfo = DataHelper.getAuthInfo();
        loginPage.invalidPassThreeTimes(authInfo);

    }


    @AfterEach
    void deleteTables() throws SQLException {
        DbInteraction.deleteTables();
    }
}

