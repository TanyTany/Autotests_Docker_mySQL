package ru.netology.web.page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import ru.netology.web.data.DataHelper;
import ru.netology.web.data.User;

import java.time.Duration;

import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.$;
import static java.time.Duration.*;

public class LoginPage {
    private SelenideElement loginField = $("[data-test-id=login] input");
    private SelenideElement passwordField = $("[data-test-id=password] input");
    private SelenideElement loginButton = $("[data-test-id=action-login]");

    private SelenideElement authError = $("[data-test-id=error-notification]");
    private SelenideElement blockError = $(withText("Доступ заблокирован"));

    public VerificationPage validLogin(DataHelper.AuthInfo info) {

        loginField.setValue(info.getLogin());
        passwordField.setValue(info.getPassword());
        loginButton.click();
        return new VerificationPage();
    }

    public void invalidPass(DataHelper.AuthInfo info) {
        loginField.setValue(info.getLogin());
        passwordField.setValue(info.getPassword());
        loginButton.click();
        authError.shouldBe(Condition.visible);
    }

    public void invalidPassThreeTimes (DataHelper.AuthInfo info) {
        loginField.setValue(info.getLogin());
        passwordField.setValue(info.getPassword());
        loginButton.click();
        loginButton.click();
        loginButton.click();
        blockError.shouldBe(Condition.visible);
    }
}
