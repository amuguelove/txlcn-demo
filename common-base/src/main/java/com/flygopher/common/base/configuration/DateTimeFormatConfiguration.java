package com.flygopher.common.base.configuration;

import com.flygopher.common.base.utils.DateUtils;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.format.datetime.standard.DateTimeFormatterRegistrar;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import static java.time.format.DateTimeFormatter.ISO_LOCAL_DATE;

@Configuration
public class DateTimeFormatConfiguration implements WebMvcConfigurer {

    @Override
    public void addFormatters(FormatterRegistry registry) {
        DateTimeFormatterRegistrar registrar = new DateTimeFormatterRegistrar();
        registrar.setDateTimeFormatter(DateUtils.CUSTOM_ISO_LOCAL_DATE_TIME);
        registrar.setDateFormatter(ISO_LOCAL_DATE);
        registrar.registerFormatters(registry);
    }
}

