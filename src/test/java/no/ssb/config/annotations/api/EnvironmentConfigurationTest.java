package no.ssb.config.annotations.api;

import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class EnvironmentConfigurationTest {

    static Map<String, String> getModifiableEnvironment() throws Exception {
        Class pe = Class.forName("java.lang.ProcessEnvironment");
        Method getenv = pe.getDeclaredMethod("getenv");
        getenv.setAccessible(true);
        Object unmodifiableEnvironment = getenv.invoke(null);
        Class map = Class.forName("java.util.Collections$UnmodifiableMap");
        Field m = map.getDeclaredField("m");
        m.setAccessible(true);
        return (Map) m.get(unmodifiableEnvironment);
    }

    @Name("env-test")
    @Namespace("test")
    @EnvironmentPrefix("TEST_")
    @RequiredKeys({"test.hello", "test.foo"})
    interface BasicConfiguration extends BaseConfiguration {

        @Property("hello")
        String hello();

        @Property("foo")
        String foo();

        default Map<String, String> defaultValues() {
            return Map.of();
        }
    }

    @Test
    public void environmentConfiguration() throws Exception {
        getModifiableEnvironment().put("TEST_test.hello", "world");

        // fail on missing required keys
        assertThrows(IllegalArgumentException.class, () -> ConfigurationFactory.createOrGet(BasicConfiguration.class));

        // add missing value
        getModifiableEnvironment().put("TEST_test.foo", "bar");

        BasicConfiguration testConfig = ConfigurationFactory.createOrGet(BasicConfiguration.class);
        assertEquals("world", testConfig.hello());
        assertEquals("bar", testConfig.foo());
    }
}
