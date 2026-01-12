module dev.infernity.whirling.bpp4j.shell {
    requires dev.infernity.whirling.bpp4j.lang;
    requires info.picocli;

    exports dev.infernity.whirling.bpp4j.shell;
    opens dev.infernity.whirling.bpp4j.shell to info.picocli;
}