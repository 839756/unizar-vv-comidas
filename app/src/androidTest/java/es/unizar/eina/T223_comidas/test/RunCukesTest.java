package es.unizar.eina.T223_comidas.cucumber;

import cucumber.api.CucumberOptions;

@CucumberOptions(
        glue = { "es.unizar.eina.T223_comidas.test" }, tags = "~@wip" , features = { "features" }
)

public class RunCukesTest {
}
