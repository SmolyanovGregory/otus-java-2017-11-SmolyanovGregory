package ru.otus.smolyanov.servlets;

/**
 * Created by Gregory Smolyanov.
 * <p>
 * Home work 13 - web archive
 */

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Map;

public class TemplateProcessor {
  private static final TemplateProcessor instance = new TemplateProcessor();

  private final Configuration configuration;

  private TemplateProcessor() {
    configuration = new Configuration();
    configuration.setClassForTemplateLoading(this.getClass(), "/tml/");
  }

  static TemplateProcessor instance() {
    return instance;
  }

  String getPage(String filename, Map<String, Object> data) throws IOException {
    try (Writer stream = new StringWriter();) {
      Template template = configuration.getTemplate(filename);
      template.process(data, stream);
      return stream.toString();
    } catch (TemplateException e) {
      throw new IOException(e);
    }
  }
}
