import com.codeborne.selenide.*;
import com.codeborne.selenide.testng.SoftAsserts;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.locators.RelativeLocator;
import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.io.FileNotFoundException;
import java.time.Duration;

import static com.codeborne.selenide.CollectionCondition.size;
import static com.codeborne.selenide.CollectionCondition.texts;
import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Configuration.*;
import static com.codeborne.selenide.Selectors.*;
import static com.codeborne.selenide.Selenide.*;
@Listeners({ SoftAsserts.class})
public class SelenideTests {

    public SelenideTests() {
        baseUrl = "http://the-internet.herokuapp.com";
        ChromeOptions options = new ChromeOptions();
        options.addArguments("start-maximized");
        Configuration.browserCapabilities = options;
        Configuration.browserSize = null;
            timeout=20000;
            holdBrowserOpen=false;
            screenshots=false;
            baseUrl = "http://the-internet.herokuapp.com";
            reopenBrowserOnFail = true;
            downloadsFolder="src/main/resources/Pictures";
            fastSetValue=true;
            assertionMode=AssertionMode.SOFT;
            fileDownload=FileDownloadMode.HTTPGET;
            reportsFolder="src/main/resources/failedScreens";
        downloadsFolder="src/main/resources/images";

    }

    @Test
    public void useSeleniumWithSelenide(){
        open("https://applitools.com/blog/category/advanced-topics/");
        WebElement post1= WebDriverRunner.getWebDriver().findElement(By.className("title"));
        String post4 = WebDriverRunner.getWebDriver().findElement(RelativeLocator.with(By.tagName("article")).below(post1)).getText();
        System.out.println(" Below post 1 is : "+ post4);
    }
    @Test
    public void chainableSelectors(){
        open("/tables");
        System.out.println($(byTagName("tr"),2).$(byTagName("td"),4).getText());

       //same as $(byTagName("tr"),2).$(byTagName("td")
        $(byXpath("//tr[2]/td[3]")).getText();
        $("#table1").find("tbody").$(byTagName("tr"),2).findAll("td")
               .forEach(el -> System.out.println(el.getText()));

        $("#table1").findAll("tbody tr").size();

    }

    @Test
    public void basicCommands() {
        assertionMode=AssertionMode.SOFT;
        open("/dynamic_controls");
       // $(byAttribute("href","http://elementalselenium.com/")).click();
        SelenideElement enableButton= $(By.cssSelector("#input-example button"));
        SelenideElement message =  $("#message");
        enableButton.click();
        System.out.println(message.getText());
        System.out.println(enableButton.getText());
        enableButton.shouldHave(text("sdsf"));
        $(byXpath("//form[@id='input-example']/input")).should(not(enabled),Duration.ofSeconds(1));

    }
    @Test
    public void keyPressesExample() {
        open("/key_presses");
        actions().sendKeys(Keys.ESCAPE).perform();

        sleep(5000);

    }
    @Test
    public void checkConditions() {
        open("/inputs");
        $(".example input").setValue("3");
        $(".example input").shouldBe(not(empty));

    }
    @Test
    public void elementsCollectionExample(){
        open("/add_remove_elements/");
        for (int i = 0; i <1 ; i++) {
            $(byText("Add Element")).click();
        }
        String text= $(By.cssSelector("#elements button")).getText();
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(text.startsWith("A"),true);
        //same as Assert.assertEquals(text.startsWith("A"),true);

      Assert.assertEquals(text,"Delete");
      softAssert.assertAll();
      //  $(By.cssSelector("#elements button")).shouldHave(attribute("class","added-manually"));
     //   $$(byText("Delete")).stream().forEach(el -> el.click());

        // $(byText("Delete")).click();
        //  System.out.println($("body").find("#elements").findAll(".added-manually").get(0).getText());

    }
    @Test
    public void handleDropDowns(){
        open("/dropdown");
        $("#dropdown").selectOptionContainingText("1");
        $("#dropdown").getSelectedOption().shouldHave(matchText("Option 1"),value("1"));
        $("#dropdown").selectOptionContainingText("Option 1");
        $("#dropdown").getSelectedOption().shouldHave(matchText("ption 1"),value("1"));
    }
    @Test
    public void fileDownload() throws FileNotFoundException {
        open("/download");
        $(byText("logo.png")).download();

    }
}
