package com.zzh.eureka.config;

import feign.MethodMetadata;
import org.springframework.cloud.openfeign.AnnotatedParameterProcessor;
import org.springframework.cloud.openfeign.CollectionFormat;
import org.springframework.cloud.openfeign.support.SpringMvcContract;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.ResourceLoader;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

import static feign.Util.emptyToNull;
import static org.springframework.core.annotation.AnnotatedElementUtils.findMergedAnnotation;

/**
 * @Description:
 * @Author: zzh
 * @Crete 2024/12/27 11:01
 */
public class CustomSpringMvcContract extends SpringMvcContract {

    private final boolean decodeSlashCustom;

    private ResourceLoader resourceLoaderCustom = new DefaultResourceLoader();

    public CustomSpringMvcContract(List<AnnotatedParameterProcessor> annotatedParameterProcessors,
                                   ConversionService conversionService, boolean decodeSlash) {
        super(annotatedParameterProcessors, conversionService, decodeSlash);
        this.decodeSlashCustom = decodeSlash;
    }

    @Override
    protected void processAnnotationOnClass(MethodMetadata data, Class<?> clz) {
        RequestMapping classAnnotation = findMergedAnnotation(clz, RequestMapping.class);
        if (classAnnotation != null) {
            // Prepend path from class annotation if specified
            if (classAnnotation.value().length > 0) {
                String pathValue = emptyToNull(classAnnotation.value()[0]);
                pathValue = resolve(pathValue);
                if (!pathValue.startsWith("/")) {
                    pathValue = "/" + pathValue;
                }
                data.template().uri(pathValue);
                if (data.template().decodeSlash() != decodeSlashCustom) {
                    data.template().decodeSlash(decodeSlashCustom);
                }
            }
        }
        CollectionFormat collectionFormat = findMergedAnnotation(clz, CollectionFormat.class);
        if (collectionFormat != null) {
            data.template().collectionFormat(collectionFormat.value());
        }
    }

    private String resolve(String value) {
        if (StringUtils.hasText(value) && resourceLoaderCustom instanceof ConfigurableApplicationContext) {
            return ((ConfigurableApplicationContext) resourceLoaderCustom).getEnvironment().resolvePlaceholders(value);
        }
        return value;
    }

    @Override
    public void setResourceLoader(ResourceLoader resourceLoader) {
        super.setResourceLoader(resourceLoader);
        this.resourceLoaderCustom = resourceLoader;
    }
}
