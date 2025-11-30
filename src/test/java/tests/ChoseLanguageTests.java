package tests;

import base.BaseTestSetup;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class ChoseLanguageTests extends BaseTestSetup {

    @BeforeAll
    public static void setCookiesAndLogin(){
        setLoggedIn(false);
        setCookiesHandled(false);
    }

    @Test
    public void changeLanguageToGermanAndValidateChanges() {
        home.openLanguageDropdown();
        home.selectLanguage("Deutsch");
        home.validateUrlEndsWith("de");
        home.validateAccountAndLoginButtons("Verbinden", "Anmeldung");
    }

    @Test
    public void changeLanguageToSpanishAndValidateChanges() {
        home.openLanguageDropdown();
        home.selectLanguage("Español");
        home.validateUrlEndsWith("es");
        home.validateAccountAndLoginButtons("Unir", "Acceso");
    }

    @Test
    public void changeLanguageToFrenchAndValidateChanges() {
        home.openLanguageDropdown();
        home.selectLanguage("Française");
        home.validateUrlEndsWith("fr");
        home.validateAccountAndLoginButtons("Rejoindre", "Se connecter");
    }

    @Test
    public void changeLanguageToBahasaIndonesiaAndValidateChanges() {
        home.openLanguageDropdown();
        home.selectLanguage("bahasa Indonesia");
        home.validateUrlEndsWith("id");
        home.validateAccountAndLoginButtons("Bergabung", "Gabung");
    }

    @Test
    public void changeLanguageToItalianAndValidateChanges() {
        home.openLanguageDropdown();
        home.selectLanguage("Italiano");
        home.validateUrlEndsWith("it");
        home.validateAccountAndLoginButtons("Giuntura", "Accesso");
    }

    @Test
    public void changeLanguageToJapaneseAndValidateChanges() {
        home.openLanguageDropdown();
        home.selectLanguage("日本語");
        home.validateUrlEndsWith("ja");
        home.validateAccountAndLoginButtons("参加する", "ログイン");
    }

    @Test
    public void changeLanguageToPortugueseAndValidateChanges() {
        home.openLanguageDropdown();
        home.selectLanguage("Português");
        home.validateUrlEndsWith("pt");
        home.validateAccountAndLoginButtons("Juntar", "Acesso");
    }

    @Test
    public void changeLanguageToThaiAndValidateChanges() {
        home.openLanguageDropdown();
        home.selectLanguage("แบบไทย");
        home.validateUrlEndsWith("th");
        home.validateAccountAndLoginButtons("เข้าร่วม", "เข้าสู่ระบบ");
    }
}
