package test.java.com.upwork.test;

import java.util.List;
import java.util.concurrent.TimeUnit;


import com.upwork.utils.Candidato;
import org.junit.After;
import org.junit.Before;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;



public class SearchCandidateParent {

    // private static final String CHROME_DRIVER = "C:/Users/kzgk290/chromedriver/chromedriver.exe";
    protected WebDriver driver;
    protected WebDriverWait wait;
    public static final int SHORT_WAIT = 5;
    public static final int MEDIUM_WAIT = 10;
    public static final int LONG_WAIT = 30;
    private WebElement campoBusqueda = null;
    protected List<WebElement> candidatos  = driver.findElements(By.cssSelector("#oContractorResults section.air-card-hover"));

    @Before
    public void before() {
        // navegacion
        navegar("https://upwork.com");
        // verificar pagina principal
        verificarPaginaPrincipal();
    }



    protected void navegar(String url) {
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(SHORT_WAIT, TimeUnit.SECONDS);
        wait = new WebDriverWait(driver, LONG_WAIT);
        driver.manage().window().maximize();
        driver.navigate().to(url);
    }

    protected void verificarPaginaPrincipal() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#layout .container-visitor .navbar-brand")));
        WebElement botonBusqueda = driver.findElement(By.cssSelector(".navbar-collapse.d-lg-flex button[type='submit']"));
        campoBusqueda = driver.findElement(By.cssSelector(".d-lg-flex [name='q']"));
        if(botonBusqueda.isEnabled() && campoBusqueda.isEnabled()){
            System.out.println("Se cargó la página.");
        }else{
            System.err.println("No se cargó bien");
        }
    }



    protected void buscarCandidatosPorHabilidad(String habilidad) {
        campoBusqueda.sendKeys(habilidad);
        campoBusqueda.submit();
    }



    public void verificarListaFreelancers() {
        List<WebElement> candidatos  = driver.findElements(By.cssSelector("#oContractorResults section.air-card-hover"));
        if (candidatos.size()!= 0){
            System.out.println("La lista se encuentra con candidatos" + candidatos.size());
        }else{
            System.out.println("La lista se encuentra vacia"+ candidatos.size());
        }
    }


    public Candidato buscarMejorCandidato() {
        Candidato ideal = null;
        for(WebElement we: candidatos) {

            Candidato c = getCandidateData(we);

            //voy a meter al primer candidato.
            if(ideal == null) {
                ideal = c;
                System.out.println("Se introdujo el primero candidato exitosamente");
            }
            // si encuentro candidato con menos salario, saco al candidato anterior, y meto al nuevo
            else if(c.getRate() < ideal.getRate()) {
                ideal = c;
                System.out.println("Se encontro un candidato con menor sueldo");
            }
            // si encuentro un candidato con el mismo salario, pero menor success rate, lo ignoro.  Si el success rate es mayor,
            // saco al candidato anterior, y meto el nuevo.

            else if(c.getRate() == ideal.getRate()) {
                if(c.getSuccess() > ideal.getSuccess()) {
                    ideal = c;
                    System.out.println("Se tiene el candidato ideal");
                }
            }
        }
        return ideal;
    }

    private Candidato getCandidateData(WebElement we){

        WebElement nombreElement = we.findElement(By.cssSelector("#oContractorResults section.air-card-hover [data-qa='tile_name']"));
        String nombreCandidato = nombreElement.getText();

        WebElement tituloElement = we.findElement(By.cssSelector("#oContractorResults section.air-card-hover .d-md-block [data-qa='tile_title']"));
        String titulo = tituloElement.getText();

        WebElement rateElement = we.findElement(By.cssSelector("#oContractorResults section.air-card-hover [data-rate] strong"));
        String rateText = rateElement.getText().substring(1);
        double rate = Double.parseDouble(rateText);

        WebElement successElement = we.findElement(By.cssSelector("#oContractorResults section.air-card-hover .d-lg-block .progress-bar > span"));
        String successText = successElement.getText().split("%")[0];
        int success = Integer.parseInt(successText);

        WebElement countryElement = we.findElement(By.cssSelector("#oContractorResults section.air-card-hover .freelancer-tile-location"));
        String countryText = countryElement.getText();

        return Candidato(nombreCandidato, titulo, rate, success, countryText);

    }


    protected void seleccionarCandidato(Candidato ideal) {
        String nombre = ideal.getNombreCandidato();
        double rate = ideal.getRate();
//    String xpath = "//section[contains(@class, 'air-card-hover') and contains( . , '"+nombre+"')   and contains( . , '"+rate+"')]";
//    driver.findElement(By.xpath(xpath)).click();
        for(WebElement we: candidatos) {
            Candidato actual = getCandidateData(we);
            if(actual.getNombreCandidato().equals(ideal.getNombreCandidato()) && actual.getRate() == ideal.getRate())
                we.click();
            break;
        }
    }

    protected void verificarInformacionCandidato(Candidato ideal) throws Exception {
        Candidato candidatoPagina = getInformacionCandidatoPagina();
        if( candidatoPagina.getNombreCandidato().contains(ideal.getNombreCandidato()) &&
                candidatoPagina.getTitulo().contains(ideal.getTitulo()) &&
                candidatoPagina.getCountryText().contains(ideal.getCountryText()) &&
                candidatoPagina.getSuccess() == ideal.getSuccess() &&
                candidatoPagina.getRate() == ideal.getRate() )
            System.out.println("La informacion coincide");
        else {
            System.out.println("La informacion no coincide");
            throw new Exception("La informacion del candidato no coincide");
        }
    }

    private Candidato getInformacionCandidatoPagina() {
        return null;
    }


    @After
    public void after() {
        driver.quit();
    }
}