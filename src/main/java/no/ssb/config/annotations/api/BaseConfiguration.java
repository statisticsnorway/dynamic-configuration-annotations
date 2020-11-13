package no.ssb.config.annotations.api;

import java.util.Map;

public interface BaseConfiguration {

    Map<String, String> defaultValues();

    @Property
    Map<String, String> asMap();

}
