package utility;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.List;
import java.util.Set;


public class Utility {
    private WebDriver driver;
    private WebDriverWait wait;
    private JavascriptExecutor jsExecutor;
    Actions actions;


    public Utility(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        this.jsExecutor = (JavascriptExecutor) driver;
        actions = new Actions(driver);
    }

    public void visit(String url) {
        driver.get(url);
    }


    public void close() {
        driver.close();
    }

    public void quit() {
        driver.quit();
    }

    public List<WebElement> findElements(By locator) {
        return driver.findElements(locator);
    }

    public String getCurrentUrl() {
        return driver.getCurrentUrl();
    }

    public String getPageTitle() {
        return driver.getTitle();
    }

    public String getCurrentWindowHandle() {
        return driver.getWindowHandle();
    }

    public Set<String> getAllWindowHandles() {
        return driver.getWindowHandles();
    }

    public int getWindowHandleCount() {
        return driver.getWindowHandles().size();
    }

    public Set<Cookie> getAllCookies() {
        return driver.manage().getCookies();
    }


    public Cookie getCookie(String name) {
        return driver.manage().getCookieNamed(name);
    }

    public void addCookie(String name, String value) {
        Cookie cookie = new Cookie(name, value);
        driver.manage().addCookie(cookie);
    }

    public void deleteCookie(String name) {
        driver.manage().deleteCookieNamed(name);
    }


    public void deleteAllCookies() {
        driver.manage().deleteAllCookies();
    }


    public void maximizeWindow() {
        driver.manage().window().maximize();
    }


    public void minimizeWindow() {
        driver.manage().window().minimize();
    }

    public void fullScreenWindow() {
        driver.manage().window().fullscreen();
    }

    public void getWindowSize() {
        driver.manage().window().getSize();
    }

    public void setWindowSize(int width, int height) {
        Dimension size = new Dimension(width, height);
        driver.manage().window().setSize(size);
    }

    public void getWindowPosition() {
        driver.manage().window().getPosition();
    }

    public void setWindowPosition(int x, int y) {
        Point position = new Point(x, y);
        driver.manage().window().setPosition(position);
    }

    public void refreshPage() {
        driver.navigate().refresh();
    }

    public void navigateTo(String url) {
        driver.navigate().to(url);
    }

    public void navigateBack() {
        driver.navigate().back();
    }

    public void navigateForward() {
        driver.navigate().forward();
    }

    public void switchToWindow(String windowHandle) {
        driver.switchTo().window(windowHandle);
    }

    public void switchToNewWindow() {
        driver.switchTo().newWindow(WindowType.WINDOW);
    }

    public void switchToNewTab() {
        driver.switchTo().newWindow(WindowType.TAB);
    }

    public void switchToAlert() {
        driver.switchTo().alert();
    }

    public void acceptAlert() {
        driver.switchTo().alert().accept();
    }

    public void dismissAlert() {
        driver.switchTo().alert().dismiss();
    }

    public String getAlertText() {
        return driver.switchTo().alert().getText();
    }

    public void switchToFrameByElement(WebElement frameElement) {
        driver.switchTo().frame(frameElement);
    }

    public void switchToFrameByName(String frameName) {
        driver.switchTo().frame(frameName);
    }

    public void switchToFrameByIndex(int index) {
        driver.switchTo().frame(index);
    }

    public void switchToDefaultContent() {
        driver.switchTo().defaultContent();
    }

    public void switchToParentFrame() {
        driver.switchTo().parentFrame();
    }

    public void highlightElement(WebElement element) {
        try {
            String originalStyle = element.getAttribute("style");
            executeJavaScript("arguments[0].setAttribute('style', 'border: 2px solid red;');", element);
            sleep(500);
            executeJavaScript("arguments[0].setAttribute('style', '" + originalStyle + "');", element);
        } catch (WebDriverException e) {
            System.out.println("Element not found");
            e.printStackTrace();
        }
    }

    public WebElement switchToActiveElement() {
        highlightElement(driver.switchTo().activeElement());
        return driver.switchTo().activeElement();
    }

    public void click(WebElement element) {
        scrollElementIntoView(element);
        highlightElement(element);
        element.click();
    }

    public void clear(WebElement element) {
        element.clear();
    }

    public String getText(WebElement element) {
        return element.getText();
    }

    public String getAttribute(WebElement element, String attribute) {
        return element.getAttribute(attribute);
    }

    public void type(WebElement element, String text) {
        highlightElement(element);
        element.sendKeys(text);
    }

    public void sendKeys(WebElement element, Keys keys) {
        highlightElement(element);
        element.sendKeys(keys);
    }

    public void clearOldTextAndType(WebDriver driver, WebElement element, String text) {
        highlightElement(element);
        element.clear();
        element.sendKeys(text);
    }

    public boolean isElementDisplayed(WebElement element) {
        return element.isDisplayed();
    }

    public boolean isElementEnabled(WebElement element) {
        return element.isEnabled();
    }

    public boolean isSelected(WebElement element) {
        return element.isSelected();
    }

    public void submit(WebElement element) {
        highlightElement(element);
        element.submit();
    }

    public String getCssValue(WebElement element, String propertyName) {
        return element.getCssValue(propertyName);
    }

    public String getTagName(WebElement element) {
        return element.getTagName();
    }

    public void selectDropdownByValue(WebElement dropdown, String value) {
        Select select = new Select(dropdown);
//		waitForElementVisibility(driver, dropdown);
        highlightElement(dropdown);
        highlightElement(driver.findElement(By.xpath("//option[@value=\"" + value + "\"]")));
        select.selectByValue(value);
    }


    public void selectDropdownByIndex(WebElement dropdown, int index) {
        Select select = new Select(dropdown);
        select.selectByIndex(index);
    }


    public void selectDropdownByVisibleText(WebElement dropdown, String visibleText) {
        Select select = new Select(dropdown);
        select.selectByVisibleText(visibleText);
    }

    public void hoverOverElement(WebElement element) {
        actions.moveToElement(element).perform();
    }


    public void dragAndDrop(WebElement sourceElement, WebElement targetElement) {
        actions.dragAndDrop(sourceElement, targetElement).perform();
    }

    public void doubleClick(WebElement element) {
        actions.doubleClick(element).perform();
    }

    public void rightClick(WebElement element) {
        actions.contextClick(element).perform();
    }

    public void performKeyDown(Keys key) {
        actions.keyDown(key).perform();
    }

    public void performKeyUp(Keys key) {
        actions.keyUp(key).perform();
    }

    public void executeJavaScript(String script) {
        jsExecutor.executeScript(script);
    }

    public void executeJavaScript(String script, WebElement element) {
        jsExecutor.executeScript(script, element);
    }

    public void scrollElementIntoView(WebElement element) {
        executeJavaScript("arguments[0].scrollIntoView(true);", element);
    }

    public void sleep(long milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void takeScreenshot(String filePath) {
        File screenshotFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        try {
            FileUtils.copyFile(screenshotFile, new File(filePath));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void scrollToTop() {
        executeJavaScript("window.scrollTo(0, 0);");
    }

    public void scrollToBottom() {
        executeJavaScript("window.scrollTo(0, document.body.scrollHeight);");
    }

    public void executeAsyncScript(String script, Object... args) {
        jsExecutor.executeAsyncScript(script, args);
    }

    public void uploadFile(WebElement fileInput, String filePath) {
        fileInput.sendKeys(filePath);
    }

    public void scrollHorizontally(int xOffset) {
        String script = "window.scrollBy(arguments[0], 0);";
        jsExecutor.executeScript(script, xOffset);
    }

    public void assertElementPresent(WebElement element) {
        Assert.assertNotNull(element, "Element is null");
    }

    public void assertElementNotPresent(WebElement element) {
        Assert.assertNull(element, "Element is present");
    }

    public void assertTextEquals(WebElement element, String expectedText) {
        String actualText = element.getText().trim();
        highlightElement(element);
        Assert.assertEquals(actualText, expectedText, "Text mismatch for element");
    }

    public void assertTextContains(WebElement element, String expectedText) {
        String actualText = element.getText().trim();
        highlightElement(element);
        Assert.assertTrue(actualText.contains(expectedText), "Text does not contain the expected value for element");
    }

    public void assertAttributeEquals(WebElement element, String attributeName, String expectedValue) {
        String actualValue = element.getAttribute(attributeName);
        Assert.assertEquals(actualValue, expectedValue, "Attribute value mismatch for element");
    }

    public void assertElementDisplayed(WebElement element) {
        Assert.assertTrue(element.isDisplayed(), "Element is not displayed");
    }

    public void assertElementNotDisplayed(WebElement element) {
        Assert.assertFalse(element.isDisplayed(), "Element is displayed");
    }

    public void assertElementEnabled(WebElement element) {
        Assert.assertTrue(element.isEnabled(), "Element is not enabled");
    }

    public void assertElementNotEnabled(WebElement element) {
        Assert.assertFalse(element.isEnabled(), "Element is enabled");
    }

    public void assertElementSelected(WebElement element) {
        Assert.assertTrue(element.isSelected(), "Element is not selected");
    }

    public void assertElementNotSelected(WebElement element) {
        Assert.assertFalse(element.isSelected(), "Element is selected");
    }

    public void assertElementAttributeEquals(WebElement element, String attributeName, String expectedValue) {
        String actualValue = element.getAttribute(attributeName);
        Assert.assertEquals(actualValue, expectedValue, "Attribute value mismatch for element");
    }

    public void assertElementCssValueEquals(WebElement element, String propertyName, String expectedValue) {
        String actualValue = element.getCssValue(propertyName);
        Assert.assertEquals(actualValue, expectedValue, "CSS value mismatch for element");
    }

    public void assertElementTextNotEmpty(WebElement element) {
        String actualText = element.getText().trim();
        Assert.assertFalse(actualText.isEmpty(), "Element text is empty");
    }

    public void assertElementTextEmpty(WebElement element) {
        String actualText = element.getText().trim();
        Assert.assertTrue(actualText.isEmpty(), "Element text is not empty");
    }

    public WebElement findShadowElementByCssSelector(WebElement hostElement, String cssSelector) {
        return (WebElement) jsExecutor.executeScript("return arguments[0].shadowRoot.querySelector(arguments[1]);", hostElement, cssSelector);
    }

    public WebElement findShadowElementByText(WebElement shadowElement, String text) {
        return (WebElement) jsExecutor.executeScript("return Array.from(arguments[0].shadowRoot.querySelectorAll('*')).find(element => element.innerText.includes(arguments[1]))", shadowElement, text);
    }

    public WebElement findShadowElementByTagName(WebElement hostElement, String tagName) {
        return (WebElement) jsExecutor.executeScript("return arguments[0].shadowRoot.getElementsByTagName(arguments[1])[0];", hostElement, tagName);
    }

    public WebElement findShadowElementById(WebElement hostElement, String id) {
        return (WebElement) jsExecutor.executeScript("return arguments[0].shadowRoot.getElementById(arguments[1]);", hostElement, id);
    }

    public WebElement findShadowElementByClassName(WebElement hostElement, String className) {
        return (WebElement) jsExecutor.executeScript("return arguments[0].shadowRoot.getElementsByClassName(arguments[1])[0];", hostElement, className);
    }

    public WebElement findShadowElementByAttributeValue(WebElement hostElement, String attributeName, String attributeValue) {
        return (WebElement) jsExecutor.executeScript("return arguments[0].shadowRoot.querySelector('[' + arguments[1] + '=\"' + arguments[2] + '\"]');", hostElement, attributeName, attributeValue);
    }

    public void clickShadowElement(WebElement shadowElement) {
        jsExecutor.executeScript("arguments[0].click();", shadowElement);
    }

    public void typeIntoShadowElement(WebElement shadowElement, String text) {
        jsExecutor.executeScript("arguments[0].value = arguments[1];", shadowElement, text);
    }

    public String getShadowElementText(WebElement shadowElement) {
        return (String) jsExecutor.executeScript("return arguments[0].textContent;", shadowElement);
    }

    public void scrollToShadowElement(WebElement shadowElement) {
        jsExecutor.executeScript("arguments[0].scrollIntoView();", shadowElement);
    }

    public boolean isShadowElementDisplayed(WebElement shadowElement) {
        Object result = jsExecutor.executeScript("const style = window.getComputedStyle(arguments[0]); return style && style.display !== 'none' && style.visibility !== 'hidden'", shadowElement);
        return result instanceof Boolean && (Boolean) result;
    }

    public boolean isShadowElementEnabled(WebElement shadowElement) {
        return shadowElement.isEnabled();
    }


    public void hoverOverShadowElement(WebElement shadowElement) {
        Actions actions = new Actions(driver);
        actions.moveToElement(shadowElement).perform();
    }

    public void selectOptionFromShadowElementDropdown(WebElement shadowElement, String optionText) {
        clickShadowElement(shadowElement);
        WebElement option = findShadowElementByText(shadowElement, optionText);
        if (option != null) {
            clickShadowElement(option);
        }
    }

    public void scrollIntoViewShadowElement(WebElement shadowElement) {
        jsExecutor.executeScript("arguments[0].scrollIntoView({behavior: 'auto', block: 'center', inline: 'center'})", shadowElement);
    }

    public void scrollToShadowElement(WebElement shadowElement, int xOffset, int yOffset) {
        jsExecutor.executeScript("arguments[0].scrollBy(arguments[1], arguments[2])", shadowElement, xOffset, yOffset);
    }

    public void highlightShadowElement(WebElement shadowElement) {
        String originalStyle = (String) jsExecutor.executeScript("return arguments[0].getAttribute('style')", shadowElement);

        jsExecutor.executeScript("arguments[0].setAttribute('style', arguments[1])", shadowElement, "border: 2px solid red; " + originalStyle);
    }

    public void unhighlightShadowElement(WebElement shadowElement) {
        jsExecutor.executeScript("arguments[0].removeAttribute('style')", shadowElement);
    }

    public void takeScreenshotOfShadowElement(WebElement shadowElement, String filePath) throws IOException {
        File screenshotFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        BufferedImage fullImage = ImageIO.read(screenshotFile);
        Point elementLocation = shadowElement.getLocation();
        int elementWidth = shadowElement.getSize().getWidth();
        int elementHeight = shadowElement.getSize().getHeight();
        BufferedImage shadowElementImage = fullImage.getSubimage(elementLocation.getX(), elementLocation.getY(), elementWidth, elementHeight);
        ImageIO.write(shadowElementImage, "png", new File(filePath));
    }

    public void waitForElementVisibility(WebElement element) {
        wait.until(ExpectedConditions.visibilityOf(element));
    }


}
