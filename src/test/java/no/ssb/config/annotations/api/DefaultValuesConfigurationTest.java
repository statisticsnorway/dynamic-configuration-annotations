package no.ssb.config.annotations.api;

import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DefaultValuesConfigurationTest {

    @Name("simple-test")
    @Namespace("test")
    @RequiredKeys({"hello"})
    interface BasicConfiguration extends BaseConfiguration {

        @Property("hello")
        String hello();

        default Map<String, String> defaultValues() {
            return Map.of("hello", "world");
        }
    }

    @Test
    public void simpleHello() {
        BasicConfiguration testConfig = ConfigurationFactory.createOrGet(BasicConfiguration.class);
        assertEquals("world", testConfig.hello());
    }
}
