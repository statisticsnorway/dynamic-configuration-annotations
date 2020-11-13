module no.ssb.config.annotations {

    requires java.base;

    requires no.ssb.config;
    requires no.ssb.service.provider.api;
    requires dapla.secrets.client.api;

    exports no.ssb.config.annotations.api;

}
