package no.ssb.config.annotations.api;

import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PropertiesResourceTest {

    @Name("properties-test")
    @Namespace("test")
    @PropertiesResource("application.properties")
    @RequiredKeys({"hello"})
    interface ResourceConfiguration extends BaseConfiguration {

        @Property("hello")
        String hello();

        default Map<String, String> defaultValues() {
            return Map.of();
        }
    }


    @Test
    public void propertyResourceTest() {
        ResourceConfiguration testConfig = ConfigurationFactory.createOrGet(ResourceConfiguration.class);
        assertEquals("world", testConfig.hello());
    }
}
