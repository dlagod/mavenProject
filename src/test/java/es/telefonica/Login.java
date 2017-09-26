package es.telefonica;

import java.net.URL;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.Assert;
import org.testng.AssertJUnit;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class Login {
	
	private static final Logger LOGGER = Logger.getLogger(Login.class.getName());
	
	protected WebDriver driver;
	protected StringBuffer verificationErrors = new StringBuffer();
	
	/**
	 * Método que devuelve el Driver
	 * @return Devuelve el driver
	 */
    public WebDriver getDriver() {
        return driver;
    }
 
    /**
     * Método que establece el driver
     * @param driver Driver
     */
    public void setWebDriver(RemoteWebDriver driver) {
        this.driver = driver;
    }

	@BeforeMethod(alwaysRun = true)
	public void setUp() throws Exception {


		try {
			// Se fuerza el perfil de Firefox para indicar la
			// Opción 0 - Sin proxy
			FirefoxProfile profile = new FirefoxProfile();
			profile.setPreference("network.proxy.type", 0);

			DesiredCapabilities cap = DesiredCapabilities.firefox();
			cap.setCapability(FirefoxDriver.PROFILE, profile);
			cap.setJavascriptEnabled(true);

			// Acceso Remoto
			driver = new RemoteWebDriver(new URL("http://10.40.61.12:4444/wd/hub"), cap);
		} catch (Exception e) {
			LOGGER.error("Error al conectar con el nodo de Selenium", e);
			throw e;
		}
		
		// Maximizar la ventana
		driver.manage().window().maximize();

		// Tiempo de timeo
		//driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

	}
	
	@AfterMethod(alwaysRun = true)
	public void tearDown() throws Exception {
		
		if (LOGGER.isInfoEnabled()) {
			LOGGER.info("Login.tearDown");
		}
		
		driver.quit();
		String verificationErrorString = verificationErrors.toString();
		if (!"".equals(verificationErrorString)) {
			Assert.fail(verificationErrorString);
		}
	}
	
	/**
	 * JIMEIV-718: Identificación con credenciales usuario/contraseña
	 * 
	 * @throws Exception
	 */
	@Test(groups = { "grupo.login.ok" })
	public void loginOK() throws Exception {
		
		if (LOGGER.isInfoEnabled()) {
			LOGGER.info("Login.loginOK");
		}

		getDriver().get("http://weblogic2.ds.sacyl.es:8401/jimena");
		
		// Completamos el usuario
		By user = By.id("username");
		getDriver().findElement(user).clear();
		getDriver().findElement(user).sendKeys("2");
		
		
		//Completamos la password
		By pass = By.id("password");
		getDriver().findElement(pass).clear();
		getDriver().findElement(pass).sendKeys("2");
		
		getDriver().findElement(By.name("submit")).click();

		// Verificación del Login OK (Administrador)
		By userLocator = By.xpath("//li[@class='datos_usuario_conectado']");
		AssertJUnit.assertTrue(getDriver().findElement(userLocator).isDisplayed());

		// Verificar que aparecen todas las opciones
		// Urgencias
		By urgency = By.xpath("//*[local-name()='a'][div/div/h3/text() [contains(.,'Urgencias')]]");
		AssertJUnit.assertTrue("Se muestra 'Urgencias'", getDriver().findElement(urgency).isDisplayed());

		// Consultas
		By consultas = By.xpath("//*[local-name()='a'][div/div/h3/text() [contains(.,'Consultas')]]");
		AssertJUnit.assertTrue("Se muestra 'Consultas'", getDriver().findElement(consultas).isDisplayed());

		// Hospitalización
		By hospitalization = By.xpath("//*[local-name()='a'][div/div/h3/text() [contains(.,'Hospitalización')]]");
		AssertJUnit.assertTrue("Se muestra 'Hospitalización'", getDriver().findElement(hospitalization).isDisplayed());

		// Búsqueda de Pacientes
		By patients = By.xpath("//*[local-name()='a'][div/div/h3/text() [contains(.,'Búsqueda pacientes')]]");
		AssertJUnit.assertTrue("Se muestra 'Búsqueda pacientes'", getDriver().findElement(patients).isDisplayed());

		// Búsqueda de Episodios
		By episodes = By.xpath("//*[local-name()='a'][div/div/h3/text() [contains(.,'Búsqueda episodios')]]");
		AssertJUnit.assertTrue("Se muestra 'Búsqueda episodios'", getDriver().findElement(episodes).isDisplayed());

		// Perfil
		By profile = By.xpath("//*[local-name()='a'][div/div/h3/text() [contains(.,'Perfil')]]");
		AssertJUnit.assertTrue("Se muestra 'Perfil'", getDriver().findElement(profile).isDisplayed());
		
		// Sugerencias
		By suggestions = By.xpath("//*[local-name()='a'][div/div/h3/text() [contains(.,'Sugerencias')]]");
		AssertJUnit.assertTrue("Se muestra 'Sugerencias'", getDriver().findElement(suggestions).isDisplayed());
	}

}
