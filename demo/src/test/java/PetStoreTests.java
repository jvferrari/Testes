import org.junit.jupiter.api.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class PetStoreTests {

    private WebDriver driver;
    private WebDriverWait wait;

    @BeforeEach
    public void setUp() {
        System.setProperty("webdriver.chrome.driver", "caminho/do/chromedriver");
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, 10);
        driver.get("https://petstore.swagger.io/");
    }

    @AfterEach
    public void tearDown() {
        driver.quit();
    }

    @Test
    public void testFindElement() {
        WebElement searchField = driver.findElement(By.cssSelector("input[type='search']"));
        Assertions.assertNotNull(searchField);
    }

    @Test
    public void testSendKeys() {
        WebElement searchField = driver.findElement(By.cssSelector("input[type='search']"));
        searchField.sendKeys("dog");
        Assertions.assertEquals("dog", searchField.getAttribute("value"));
    }

    @Test
    public void testMoveToElement() {
        WebElement petCategory = driver.findElement(By.xpath("//span[contains(text(),'pet')]"));
        Actions action = new Actions(driver);
        action.moveToElement(petCategory).perform();
        WebElement petsLink = wait
                .until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[contains(text(),'pets')]")));
        Assertions.assertTrue(petsLink.isDisplayed());
    }

    @Test
    public void testSubmit() {
        WebElement searchField = driver.findElement(By.cssSelector("input[type='search']"));
        searchField.sendKeys("cat");
        searchField.sendKeys(Keys.ENTER);
        WebElement searchResults = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".result")));
        Assertions.assertTrue(searchResults.isDisplayed());
    }

    @Test
    public void testById() {
        WebElement categoryDropdown = driver.findElement(By.id("operations-pet-findPetsByStatus"));
        Assertions.assertNotNull(categoryDropdown);
    }

    @Test
    public void testSelectByIndex() {
        WebElement statusDropdown = driver.findElement(By.cssSelector("select[name='status']"));
        Select select = new Select(statusDropdown);
        select.selectByIndex(1);
        WebElement firstOption = select.getFirstSelectedOption();
        Assertions.assertEquals("pending", firstOption.getText());
    }

    @Test
    public void testAssertTrue() {
        WebElement searchField = driver.findElement(By.cssSelector("input[type='search']"));
        Assertions.assertTrue(searchField.isEnabled());
    }

    @Test
    public void testAssertFalse() {
        WebElement searchButton = driver.findElement(By.cssSelector("button[type='submit']"));
        Assertions.assertFalse(searchButton.isSelected());
    }

    @Test
    public void testElementExistsById() {
        WebElement element = driver.findElement(By.id("input-search"));
        Assertions.assertNotNull(element);
    }

    @Test
    public void testPageTitle() {
        String expectedTitle = "Swagger Petstore";
        Assertions.assertEquals(expectedTitle, driver.getTitle());
    }

    @Test
    public void testLinkNavigation() {
        WebElement link = driver.findElement(By.linkText("API"));
        link.click();
        String expectedUrl = "swagger.io";
        Assertions.assertTrue(driver.getCurrentUrl().contains(expectedUrl));
    }

    @Test
    public void testElementNotPresent() {
        Assertions.assertThrows(
                NoSuchElementException.class,
                () -> driver.findElement(By.id("nonexistent-element")));
    }

    @Test
    public void testElementVisibleAndClickable() {
        WebElement signInButton = driver.findElement(By.xpath("//button[contains(text(),'Sign in')]"));
        wait.until(ExpectedConditions.visibilityOf(signInButton));
        Assertions.assertTrue(signInButton.isDisplayed() && signInButton.isEnabled());
    }

    @Test
    public void testElementTextEquals() {
        WebElement heading = driver.findElement(By.cssSelector("h1"));
        Assertions.assertEquals("Swagger Petstore", heading.getText());
    }

}
