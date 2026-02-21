module dev.infernity.whirling.bpp4j.lang {
    requires org.jetbrains.annotations;
    requires brigadier;
    requires java.sql;

    exports dev.infernity.whirling.bpp4j.lang;
    exports dev.infernity.whirling.bpp4j.lang.interpreter;
    exports dev.infernity.whirling.bpp4j.lang.parsing;
    exports dev.infernity.whirling.bpp4j.lang.tokenizer;
}