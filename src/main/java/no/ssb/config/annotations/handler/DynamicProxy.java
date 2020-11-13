package no.ssb.config.annotations.handler;

import no.ssb.config.annotations.api.BaseConfiguration;

import java.lang.reflect.Proxy;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

public class DynamicProxy<T> {

    final T instance;
    final AtomicBoolean initialized = new AtomicBoolean();

    public DynamicProxy(Class<T> clazz) {
        this(clazz, new LinkedHashMap<>());
    }

    @SuppressWarnings("unchecked")
    public DynamicProxy(Class<T> clazz, Map<String, String> overrideValues) {
        instance = (T) Proxy.newProxyInstance(
                clazz.getClassLoader(),
                new Class[]{clazz},
                new DynamicInvocationHandler(clazz, overrideValues));
        validate();
    }

    void validate() {
        if (initialized.compareAndSet(false, true)) {
            // invoke BaseConfiguration.asMap to trigger initialization
            if (instance instanceof BaseConfiguration) {
                ((BaseConfiguration) instance).asMap();
            }
        }
    }

    public T instance() {
        return instance;
    }
}
