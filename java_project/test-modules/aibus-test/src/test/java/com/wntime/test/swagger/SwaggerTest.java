package com.wntime.test.swagger;

import io.github.swagger2markup.GroupBy;
import io.github.swagger2markup.Language;
import io.github.swagger2markup.Swagger2MarkupConfig;
import io.github.swagger2markup.Swagger2MarkupConverter;
import io.github.swagger2markup.builder.Swagger2MarkupConfigBuilder;
import io.github.swagger2markup.markup.builder.MarkupLanguage;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Paths;


public class SwaggerTest {

    @Test
    public void generateDocsMD(){
        Swagger2MarkupConfig config = new Swagger2MarkupConfigBuilder()
                .withMarkupLanguage(MarkupLanguage.MARKDOWN)
                .withOutputLanguage(Language.ZH)
                .withPathsGroupedBy(GroupBy.TAGS)
                .withGeneratedExamples()
                .withoutInlineSchema()
                .build();
        try {
            Swagger2MarkupConverter.from(new URL("http://localhost:5300/aibus/v2/api-docs"))
                    .withConfig(config)
                    .build()
                    .toFile(Paths.get("src/test/resources/docs/markdown"));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }
    @Test
    public void generateDocsASC(){
        Swagger2MarkupConfig config = new Swagger2MarkupConfigBuilder()
                .withMarkupLanguage(MarkupLanguage.ASCIIDOC)
                .withOutputLanguage(Language.ZH)
                .withPathsGroupedBy(GroupBy.TAGS)
                .withGeneratedExamples()
                .withoutInlineSchema()
                .build();
        try {
            Swagger2MarkupConverter.from(new URL("http://localhost:5300/aibus/v2/api-docs"))
                    .withConfig(config)
                    .build()
                    .toFile(Paths.get("src/test/resources/docs/asciidoc"));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }
}
