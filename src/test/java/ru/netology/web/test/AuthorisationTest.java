package ru.netology.web.test;

import lombok.val;
import org.junit.jupiter.api.*;
import ru.netology.web.data.DataHelper;
import ru.netology.web.data.DbInteraction;
import ru.netology.web.page.LoginPage;

import java.sql.SQLException;

import static com.codeborne.selenide.Selenide.*;

public class AuthorisationTest {
    @BeforeEach
    void setUp() throws SQLException {
        DbInteraction.deleteTables();
        DbInteraction.setUp(); }

    @Test
    void shouldAuthorisationValid1User() throws SQLException {
    open("http://0.0.0.0:9999/");
        val loginPage = new LoginPage();
        val authInfo = DataHelper.getAuthInfo();
        val verificationPage = loginPage.validLogin(authInfo);
        val verificationCode = DbInteraction.getAuthCodeFromUser();
        verificationPage.validVerify(verificationCode);
    }

    @Test
    void shouldAuthorisationInvalidUser() throws SQLException {
        open("http://0.0.0.0:9999/");
        val loginPage = new LoginPage();
        val authInfo = DataHelper.getOtherAuthInfo();
        val verificationPage = loginPage.validLogin(authInfo);
        val verificationCode = DbInteraction.getAuthCodeFromUser();
        verificationPage.validVerify(verificationCode);
    }



    @Test
    void shouldAuthorisationInvalidPass() throws SQLException {
        open("http://0.0.0.0:9999/");
        val loginPage = new LoginPage();
        val authInfo = DataHelper.getInvalidPass();
        loginPage.invalidPass(authInfo);

    }

    @Test
    void shouldAuthorisationIfInvalidPassThreeTime() throws SQLException {
        open("http://0.0.0.0:9999/");
        val loginPage = new LoginPage();
        val authInfo = DataHelper.getInvalidPass();
        loginPage.invalidPassThreeTimes(authInfo);

    }

    @AfterEach
     void deleteTabs() throws SQLException {
        DbInteraction.deleteTables();
    }

}

