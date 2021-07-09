package basicmethods;

import core.Base;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.List;
import java.util.NoSuchElementException;

public class GeneralExamples extends Base {
    private WebDriver driver;

    @BeforeMethod
    public void beforeMethos(){
        driver = initializeDriver();
    }

    @Test
    public void ejercicio1() throws InterruptedException {
        /* Ejercicio 1
        ir a https://phptravels.com/demo/
        hacer clic en product (menú superior)
        hacer clic en Providers (submenu de product)
        verificar que el título sea Travel XML API Integrations Providers
        hacer clic en demo (menú superior)
        verificar que el título sea Application Test Drive
        */
        String expectedTitleProvidersPage="Travel XML API Integrations Providers";
        driver.get("https://phptravels.com/demo/");
        WebElement optionProviders = driver.findElement(By.linkText("Providers"));
        optionProviders.click();
        Thread.sleep(2000);
        WebElement actualTitleOfProvidersPage= driver.findElement(By.xpath("//*[contains(text(),'Travel XML API Integrations Providers')]"));
        System.out.println(actualTitleOfProvidersPage.getText());
        Assert.assertEquals(expectedTitleProvidersPage,actualTitleOfProvidersPage.getText());

        String expectedTitleDemoPage="Application Test Drive";
        WebElement DemoPage= driver.findElement(By.xpath("//*[@href='https://phptravels.com/demo']"));
        DemoPage.click();
        WebElement actualTitleOfDemoPage= driver.findElement(By.xpath("//*[contains(text(),'Application Test Drive')]"));
        Thread.sleep(2000);
        System.out.println(actualTitleOfDemoPage.getText());
        Assert.assertEquals(expectedTitleDemoPage,actualTitleOfDemoPage.getText());

    }

    @Test
    public void ejercicio2(){
        /* Ejercicio 2
        ir a http://the-internet.herokuapp.com/
        seleccionar la opción dropdown
        seleccionar la opción 2 (por value)
        verificar que la opción 1 NO esté seleccionada
        verificar que la opción 2 esté seleccionada
        seleccionar la opción 1 (por visible text)
        verificar que la opción 1 esté seleccionada
        verificar que la opción 2 NO esté seleccionada
        seleccionar la opción 2 (por index)
        verificar que la opción 1 NO esté seleccionada
        verificar que la opción 2 esté seleccionada
        */
        boolean selectedAttribute = true;
        boolean notSelectedAttribute = false;

        driver.get("http://the-internet.herokuapp.com/");
        WebElement optionDropdown= driver.findElement(By.xpath("//*[text()='Dropdown']"));
        optionDropdown.click();
        WebElement option2= driver.findElement(By.xpath("//*[@value='2']"));
        option2.click();
        WebElement option1= driver.findElement(By.xpath("//*[@value='1']"));
        option1.isSelected();
        Assert.assertEquals(notSelectedAttribute,option1.isSelected());
        Assert.assertEquals(selectedAttribute,option2.isSelected());
        WebElement option1ByVisibleText= driver.findElement(By.xpath("//*[text()='Option 1']"));
        option1ByVisibleText.click();
        Assert.assertEquals(selectedAttribute,option1.isSelected());
        Assert.assertEquals(notSelectedAttribute,option2.isSelected());
        WebElement optionByIndex = driver.findElement(By.xpath("//*[@id='dropdown']/option[3]"));
        optionByIndex.click();
        Assert.assertEquals(notSelectedAttribute,option1.isSelected());
        Assert.assertEquals(selectedAttribute,option2.isSelected());
    }
    @Test
    public void ejercicio3() throws Exception {
        /* Ejercicio 2
        ir a http://the-internet.herokuapp.com/
        seleccionar la opción add/remove elements
        verificar que el títlo diga Add/Remove Elements (assert)
        verificar que add element es visible
        verificar que el botón delete no es visible
        hacer clic en add element
        verificar que el botón delete es visible
        hacer clic en delete
        verificar que el botón delete NO es visible
        */
        String expectedTitleAddRemovePage = "Add/Remove Elements";
        boolean visible = true;
        boolean noVisible = false;
        driver.get("http://the-internet.herokuapp.com/");
        WebElement optionAddRemove= driver.findElement(By.xpath("//*[text()='Add/Remove Elements']"));
        optionAddRemove.click();
        Assert.assertEquals(expectedTitleAddRemovePage,driver.findElement(By.xpath("//*[text()='Add/Remove Elements']")).getText());
        WebElement addElementBtn= driver.findElement(By.xpath("//*[text()='Add Element']"));
        Assert.assertEquals(visible,addElementBtn.isDisplayed());
        Assert.assertEquals(noVisible,existsElement());
        addElementBtn.click();
        WebElement deleteBtn= driver.findElement(By.xpath("//*[text()='Delete']"));
        Assert.assertEquals(visible,deleteBtn.isDisplayed());
        deleteBtn.click();
        Assert.assertEquals(noVisible,deleteBtn.isDisplayed());
    }
    private boolean existsElement() throws Exception {
        boolean test= driver.findElement(By.xpath("//*[text()='Delete']")).isDisplayed();
        if(test!=true){
            return false;
        }
        return true;
    }
    @Test
    public void ejercicio4() throws InterruptedException {
        /* Ejercicio 2
        ir a http://the-internet.herokuapp.com/
        seleccionar la opción Form Authentication
        verificar que el título sea login page (assert)
        hacer clic en el botón login (sin poner usuari ni contaseña)
        verificar que el mensaje de error se muestre
        llenar el usuario y contraseña que indica la página
        escribir el usuario y contraseña que dicen el página
        verificar que el título sea "Secure Area" (assert)
        verificar que el mensaje de éxito salga arriba (assert)
        hacer clic en logout
        verificar que el título sea nuevamente login page (assert)
        */
        String expectedTitleFormAuthentication = "Login Page";
        boolean errorMessage = true;
        String secureMessage = " Secure Area";
        driver.get("http://the-internet.herokuapp.com/");
        WebElement optionFormAuthentication= driver.findElement(By.xpath("//*[text()='Form Authentication']"));
        optionFormAuthentication.click();
        Assert.assertEquals(expectedTitleFormAuthentication,driver.findElement(By.xpath("//*[text()='Login Page']")).getText());
        WebElement loginBtn= driver.findElement(By.xpath("//i[@class='fa fa-2x fa-sign-in']"));
        loginBtn.click();
        Assert.assertEquals(errorMessage,driver.findElement(By.xpath("//div[@id='flash']")).isDisplayed());
        driver.findElement(By.xpath("//*[@id='username']")).sendKeys("tomsmith ");
        driver.findElement(By.xpath("//*[@id='password']")).sendKeys("SuperSecretPassword!");
        loginBtn.click();
        Assert.assertEquals(secureMessage,driver.findElement(By.xpath("//div[@id='flash']")).getText());
        Assert.assertEquals(errorMessage,driver.findElement(By.xpath("//*[@class='flash success']")).isDisplayed());
        driver.findElement(By.xpath("//*[@class='icon-2x icon-signout']")).click();
        Assert.assertEquals(expectedTitleFormAuthentication,driver.findElement(By.xpath("//*[text()='Login Page']")).getText());
    }

    @AfterMethod
    public void afterMethod(){
        driver.quit();
    }
}
