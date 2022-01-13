import com.codeborne.selenide.*;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.Test;

import java.time.Duration;

import static com.codeborne.selenide.CollectionCondition.texts;
import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Configuration.*;
import static com.codeborne.selenide.Selectors.*;
import static com.codeborne.selenide.Selenide.*;

public class SelenideTests {

    public SelenideTests() {
        baseUrl = "http://the-internet.herokuapp.com";
        ChromeOptions options = new ChromeOptions();
        options.addArguments("start-maximized");
        Configuration.browserCapabilities = options;
        Configuration.browserSize = null;
    }

    @Test
    public void chainableSelectors(){
        open("/tables");
        System.out.println($(byTagName("tr"),2).$(byTagName("td"),4).getText());
       //same as $(byTagName("tr"),2).$(byTagName("td")
        $(byXpath("//tr[2]/td[3]")).getText();

    }

    @Test
    public void basicCommands() {
        open("/dynamic_controls");
       // $(byAttribute("href","http://elementalselenium.com/")).click();
        SelenideElement enableButton= $(By.cssSelector("#input-example button"));
        SelenideElement message =  $("#message");
        enableButton.click();
        System.out.println(message.getText());
        enableButton.shouldHave(or("Check text and state",text("Disabled"), enabled));
        $(byXpath("//form[@id='input-example']/input")).should(disabled, Duration.ofSeconds(1));

    }
    @Test
    public void keyPressesExample() {
        open("/key_presses");
        actions().sendKeys(Keys.ESCAPE).perform();

        sleep(5000);

    }
    @Test
    public void elementsCollectionExample(){
        open("/add_remove_elements/");
        for (int i = 0; i <3 ; i++) {
            $(byText("Add Element")).click();
        }
         $$(By.cssSelector("#elements button")).shouldHave(texts("Delete","Delete","Delete"));
        $$(byText("Delete")).stream().forEach(el -> el.click());
        // $(byText("Delete")).click();
        //  System.out.println($("body").find("#elements").findAll(".added-manually").get(0).getText());
        sleep(4000);
    }
    @Test
    public void handleDropDowns(){
        open("/dropdown");
        $("#dropdown").selectOptionContainingText("1");
        $("#dropdown").getSelectedOption().shouldHave(matchText("Option 1"),value("1"));
        $("#dropdown").selectOptionContainingText("Option 1");
        $("#dropdown").getSelectedOption().shouldHave(matchText("ption 1"),value("1"));
    }
}
