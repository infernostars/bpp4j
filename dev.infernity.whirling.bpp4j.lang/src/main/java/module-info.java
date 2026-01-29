module dev.infernity.whirling.bpp4j.lang {
    requires org.jetbrains.annotations;
    requires brigadier;
    requires java.rmi;

    exports dev.infernity.whirling.bpp4j.lang;
    exports dev.infernity.whirling.bpp4j.lang.interpreter;
    exports dev.infernity.whirling.bpp4j.lang.tokenizer;
}