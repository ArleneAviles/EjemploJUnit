package test.java.com.upwork.tests;

//import utils.Candidato;
import com.upwork.utils.Candidato;
import org.junit.After;
i ñmport org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

public class SearchCandidate extends test.java.com.upwork.test.SearchCandidateParent {
    WebDriver driver;
    WebDriverWait wait;

    @Before
    public void before() {
        //navegacion
        navegar("https://upwork.com");
        //verificar pagina principal
        verificarPaginaPrincipal();
    }

    @Test
    public void searchBestCandidate(){
        buscarCandidatosPorHabilidad("Selenium");
        verificarListaFreelancers();
        Candidato morris=buscarMejorCandidato();
        seleccionarCandidato(morris);
        verificarInformacionCandidato(morris);
    }


    @After
    public void after() {
    }
}