package drone;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.container.test.api.RunAsClient;
import org.jboss.arquillian.drone.api.annotation.Drone;
import org.jboss.arquillian.junit5.ArquillianExtension;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.resolver.api.maven.archive.importer.MavenImporter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.net.URL;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(ArquillianExtension.class)
@RunAsClient
public class BasicIT {
    private static final String testName = "drone-test";
    @ArquillianResource
    URL baseUrl;
    @Drone
    WebDriver driver;

    /*
        findBy() are the Graphene parts
     */
    @FindBy(id = "oneButton")
    private WebElement oneButton;
    @FindBy(id = "twoButton")
    private WebElement twoButton;

    @BeforeEach
    void setup() {
        driver.get(baseUrl + "");
    }

    @Test
    void contextPath() {
        assertEquals(String.format("/%s/", testName), baseUrl.getPath());
    }

    @Test
    void title() {
        assertEquals("My Index", driver.getTitle());
    }

    @Test
    void droneButtonPress() {
        driver.findElement(By.id("oneButton")).click();
    }

    @Test
    @Tag("graphene")
    void grapheneButtonPress() {
        oneButton.click();
    }

    @Deployment
    public static WebArchive deploy() {
        return ShrinkWrap.create(MavenImporter.class, testName + ".war")
                .loadPomFromFile("pom.xml").importBuildOutput()
                .as(WebArchive.class);
    }
}
