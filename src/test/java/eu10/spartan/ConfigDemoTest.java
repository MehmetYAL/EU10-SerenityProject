package eu10.spartan;

import org.junit.jupiter.api.Test;
import utilities.ConfigReader;

public class ConfigDemoTest {
    @Test
    public void test1(){
        System.out.println("ConfigReader.getProperty(\"serenity.project.name\") = " + ConfigReader.getProperty("serenity.project.name"));
        System.out.println("ConfigReader.getProperty(\"spartan.editor.username\") = " + ConfigReader.getProperty("spartan.editor.username"));

    }
}
