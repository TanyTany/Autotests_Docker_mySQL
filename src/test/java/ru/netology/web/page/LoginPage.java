package ru.netology.web.page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import ru.netology.web.data.DataHelper;
import ru.netology.web.data.User;

import static com.codeborne.selenide.Selenide.$;

public class LoginPage {
    private SelenideElement loginField = $("[data-test-id=login] input");
    private SelenideElement passwordField = $("[data-test-id=password] input");
    private SelenideElement loginButton = $("[data-test-id=action-login]");

    private SelenideElement authError = $("[data-test-id=error-notification]");

    public VerificationPage validLogin(DataHelper.AuthInfo info) {

        loginField.setValue(info.getLogin());
        passwordField.setValue(info.getPassword());
        loginButton.click();
        return new VerificationPage();
    }

    public SelenideElement IfThreeInvalidPass(DataHelper.AuthInfo info) {
        loginField.setValue(info.getLogin());
        if (info.getLogin().equals("vasya"))passwordField.setValue("password");
        else {
            passwordField.setValue("password123");
        }
        loginButton.click();
        loginButton.click();
        loginButton.click();
        SelenideElement error = authError.shouldBe(Condition.visible);
        return error;
    }
}
