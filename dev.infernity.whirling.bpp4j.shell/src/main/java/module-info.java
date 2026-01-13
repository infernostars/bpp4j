module dev.infernity.whirling.bpp4j.shell {
    requires dev.infernity.whirling.bpp4j.lang;
    requires info.picocli;
    requires org.fusesource.jansi;

    exports dev.infernity.whirling.bpp4j.shell;
    opens dev.infernity.whirling.bpp4j.shell to info.picocli;
}