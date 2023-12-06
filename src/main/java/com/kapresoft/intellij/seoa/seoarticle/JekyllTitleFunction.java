package com.kapresoft.intellij.seoa.seoarticle;

import com.intellij.codeInsight.template.EverywhereContextType;
import com.intellij.codeInsight.template.Expression;
import com.intellij.codeInsight.template.ExpressionContext;
import com.intellij.codeInsight.template.Result;
import com.intellij.codeInsight.template.TemplateContextType;
import com.intellij.codeInsight.template.TextResult;
import com.intellij.codeInsight.template.macro.MacroBase;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.util.text.StringUtil;
import com.intellij.openapi.util.text.Strings;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.Collections;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

import static java.util.Optional.ofNullable;

@SuppressWarnings("unused")
public class JekyllTitleFunction extends MacroBase {

    private static final Logger LOG = Logger.getInstance(JekyllTitleFunction.class);

    private static final String NAME = "articleTitle";

    private static final String[] KEYWORDS = {
            "springboot = Spring Boot •",
            "spring = Spring •",
            "java = Java •",
            "health = Health •"
    };
    private final Map<String, String> keywordMap;


    public JekyllTitleFunction() {
        this(NAME, "%s()".formatted(NAME));
    }

    private JekyllTitleFunction(String name, String description) {
        super(name, description);
        keywordMap = initKeywords();
    }

    private Map<String, String> initKeywords() {
        final Map<String, String> map = Arrays.stream(KEYWORDS).map(pair -> pair.split("="))
                .filter(kvPair -> kvPair.length == 2)
                .collect(Collectors.toMap(kvPair -> kvPair[0].trim(), kvPair -> kvPair[1].trim()));
        return Collections.unmodifiableMap(map);
    }

    private String handleKeywords(String value) {
        if (Strings.isEmpty(value)) {
            return value;
        }
        String[] parts = value.split("\\s+");
        final StringBuilder sb = new StringBuilder();
        Arrays.stream(parts).forEach(w -> {
            var item = w;
            var itemLc = item.toLowerCase();
            if (keywordMap.containsKey(itemLc)) {
                item = keywordMap.get(itemLc);
            }
            sb.append(" ").append(item);
        });
        return sb.toString().trim();
    }

    private String toTitleCase(String name) {
        String title = name.replaceAll("(\\d)+(-)+", "")
                .replaceFirst(".md$", "")
                .replaceAll("-", " ");
        title = StringUtil.capitalizeWords(title, true);
        title = handleKeywords(title);
        return title;
    }

    @Override
    protected @Nullable Result calculateResult(Expression @NotNull [] params, ExpressionContext context, boolean quick) {
        final AtomicReference<String> title = new AtomicReference<>("ERROR");
        ofNullable(context.getPsiElementAtStartOffset()).ifPresent(p -> {
            var file = p.getContainingFile();
            String name = file.getName();
            title.set(toTitleCase(name));
        });
        return new TextResult(title.get());
    }

    @Override
    public boolean isAcceptableInContext(TemplateContextType context) {
        LOG.warn("isAcceptableInContext(): Entering...");
        // Might want to be less restrictive in future
        return (context instanceof EverywhereContextType);
    }

}
