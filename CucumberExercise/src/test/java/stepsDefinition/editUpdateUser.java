package stepsDefinition;

import Helpers.constants;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.GherkinKeyword;
import commons.ElementEvents;
import commons.pageObjects;
import io.cucumber.java.en.*;
import org.openqa.selenium.*;
import org.testng.Assert;


public class editUpdateUser {
     WebDriver driver = hooks.driver;
    ElementEvents eL = new ElementEvents();
    pageObjects pO = new pageObjects();
    WebElement element;
    constants con = new constants();
    ExtentTest scenarioDef;

    public editUpdateUser(){
        this.scenarioDef = hooks.scenarioDef;
    }

    @Given("open browser in Developer Guide")
    public void openBrowserInDeveloperGuide() throws InterruptedException, ClassNotFoundException {
               //use element variable to store the web element
        element = eL.getElementByXpath(pO.devGuideTitle);
        //call the method isDisplayed to verify if it's true or false
        boolean pageSelected = eL.elementIsDisplayed(element);
        //validate if element is displayed
        Assert.assertTrue(pageSelected);
        eL.sleeper(1);

        scenarioDef.createNode(new GherkinKeyword("Given"), "open browser in Developer Guide");
    }

    @And("user Clicks on Component Reference tab")
    public void userClicksOnComponentReferenceTab() throws InterruptedException, ClassNotFoundException {
        scenarioDef.createNode(new GherkinKeyword("And"), "user Clicks on Component Reference tab");

        //Locate the element
        element = eL.getElementByXpath(pO.tabComponentRefer);

        //click the element using actions
        eL.actionsClick(element);

        //wait for 3 seconds
        eL.sleeper(3);

        //clear element variable
        element = null;
        //set new element by xpath
        element = eL.getElementByXpath(pO.componentTitle);

        //validate if the element is displayed
        boolean componentPage = eL.elementIsDisplayed(element);
        Assert.assertTrue(componentPage);

        element = null;
    }

    @And("search in Quick Find for {string}")
    public void searchInQuickFindFor(String value) throws InterruptedException, ClassNotFoundException {
        scenarioDef.createNode(new GherkinKeyword("And"), "search in Quick Find for");

        element = eL.getElementByXpath(pO.txtQuickSearch);
        element.sendKeys("datatable");

        element = eL.getElementByXpath(pO.linkLightningWComp);
        eL.actionsClick(element);
        eL.sleeper(3);

        element = eL.getElementByXpath(pO.lblDataTableComp);
        String lblDataTableComp = eL.getText(element);
        Assert.assertEquals("Datatable", lblDataTableComp, "Text is not equal, expecting Datatable");
        element=null;
    }

    @And("click on {string} option in ddl")
    public void clickOnOptionInDdl(String value) throws InterruptedException, ClassNotFoundException {
        scenarioDef.createNode(new GherkinKeyword("And"), "click on option in ddl");

        element = eL.getElementsByXpath(pO.ddlExample);
        for(int x = 1; x <= 4; x++){
            element.sendKeys(Keys.DOWN);
        }
        element.sendKeys(Keys.ENTER);
        eL.sleeper(3);
        eL.actionsMoteToElement(element);
        eL.sleeper(2);
    }

    @And("click on Open in Playground button")
    public void clickOnOpenInPlaygroundButton() throws ClassNotFoundException {
        scenarioDef.createNode(new GherkinKeyword("And"), "click on Open in Playground button");

        eL.getElementByXpath(pO.btnPlayInBackground).click();
    }

    @When("third row information is updated")
    public void thirdRowInformationIsUpdated() throws InterruptedException, ClassNotFoundException {
        scenarioDef.createNode(new GherkinKeyword("When"), "third row information is updated");

        eL.sleeper(20);
        eL.switchToFrameByNumber(0);
        //element = eL.getElementByXpath(pO.iFrame);
        eL.switchToFrameByNumber(1);
        element = eL.getElementByXpath(pO.tblPreview);
        Assert.assertTrue(eL.elementIsDisplayed(element));

        //edit Name
        eL.editTableRow(pO.btnEditR3, pO.txtR3, con.Name);

        //edit website
        eL.editTableRow(pO.btnEditWebsiteR3, pO.txtR3, con.Website);

        //edit Phone
        eL.editTableRow(pO.btnPhone, pO.txtR3, con.Phone);

        //edit DateTime
        eL.editTableRow(pO.btnDateTime, pO.txtDate, pO.txtTime, con.Date, con.Time);

        //edit Balance
        eL.editTableRow(pO.btnBalance, pO.txtR3, con.Balance);

    }

    @Then("verify that information has been saved correctly")
    public void verifyThatInformationHasBeenSavedCorrectly() throws ClassNotFoundException {
        scenarioDef.createNode(new GherkinKeyword("And"), "verify that information has been saved correctly");

        Assert.assertEquals(eL.getElementByXpath(pO.lblLabelText).getText(), con.Name);
        Assert.assertTrue(con.Website.equals(eL.getElementByXpath(pO.lblWebsite).getText()));
        Assert.assertEquals(eL.getElementByXpath(pO.lblPhoneText).getText(), con.Phone);
        Assert.assertEquals(eL.getElementByXpath(pO.lblDateText).getText(), con.Date);
        Assert.assertEquals(eL.getElementByXpath(pO.lblBalanceText).getText(), "$" + con.Balance);
    }
}
