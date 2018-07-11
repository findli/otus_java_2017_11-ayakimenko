package ru.otus.spring.server;

import freemarker.template.Configuration;
import freemarker.template.TemplateException;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Map;

public class TemplateProcessor {

    private static final String TMPL_DIR = "/tmpl/";
    private static TemplateProcessor instance = new TemplateProcessor();

    private final Configuration configuration;

    private TemplateProcessor() {
        configuration = new Configuration();
        configuration.setClassForTemplateLoading(this.getClass(), TMPL_DIR);
    }

    public static TemplateProcessor instance() {
        return instance;
    }

    public String getPage(String filename, Map<String, Object> data) throws IOException {
        try (Writer stream = new StringWriter()) {
            configuration.getTemplate(filename).process(data, stream);
            return stream.toString();
        } catch (TemplateException e) {
            throw new IOException(e);
        }
    }
}
