package hr.pishe.config;

import hr.pishe.interceptor.GeneralInterceptor;
import hr.pishe.interceptor.SubscriptionInterceptor;
import io.github.jhipster.config.locale.AngularCookieLocaleResolver;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;

@Configuration
public class LocaleConfiguration extends WebMvcConfigurerAdapter implements EnvironmentAware {

    @Override
    public void setEnvironment(Environment environment) {
        // unused
    }

    @Bean
    public SubscriptionInterceptor subscriptionInterceptor() {
        return new SubscriptionInterceptor();
    }

    @Bean
    public GeneralInterceptor generalInterceptor() {
        return new GeneralInterceptor();
    }

    @Bean(name = "localeResolver")
    public LocaleResolver localeResolver() {
        AngularCookieLocaleResolver cookieLocaleResolver = new AngularCookieLocaleResolver();
        cookieLocaleResolver.setCookieName("NG_TRANSLATE_LANG_KEY");
        return cookieLocaleResolver;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        LocaleChangeInterceptor localeChangeInterceptor = new LocaleChangeInterceptor();
        localeChangeInterceptor.setParamName("language");
        registry.addInterceptor(localeChangeInterceptor);

        registry.addInterceptor(generalInterceptor()).addPathPatterns("/api/**");

        registry.addInterceptor(subscriptionInterceptor()).addPathPatterns("/api/candidate-message");
        registry.addInterceptor(subscriptionInterceptor()).addPathPatterns("/api/candidate-message/**");
        registry.addInterceptor(subscriptionInterceptor()).addPathPatterns("/api/candidate");
        registry.addInterceptor(subscriptionInterceptor()).addPathPatterns("/api/candidate/**");
        registry.addInterceptor(subscriptionInterceptor()).addPathPatterns("/api/candidate-schedule");
        registry.addInterceptor(subscriptionInterceptor()).addPathPatterns("/api/candidate-schedule/**");
        registry.addInterceptor(subscriptionInterceptor()).addPathPatterns("/api/comment");
        registry.addInterceptor(subscriptionInterceptor()).addPathPatterns("/api/comment/**");
        registry.addInterceptor(subscriptionInterceptor()).addPathPatterns("/api/company-member");
        registry.addInterceptor(subscriptionInterceptor()).addPathPatterns("/api/company-member/**");
        registry.addInterceptor(subscriptionInterceptor()).addPathPatterns("/api/company-pipeline");
        registry.addInterceptor(subscriptionInterceptor()).addPathPatterns("/api/company-pipeline/**");
//        registry.addInterceptor(subscriptionInterceptor()).addPathPatterns("/api/company");
//        registry.addInterceptor(subscriptionInterceptor()).addPathPatterns("/api/company/**");
        registry.addInterceptor(subscriptionInterceptor()).addPathPatterns("/api/feedback");
        registry.addInterceptor(subscriptionInterceptor()).addPathPatterns("/api/feedback/**");
        registry.addInterceptor(subscriptionInterceptor()).addPathPatterns("/api/job");
        registry.addInterceptor(subscriptionInterceptor()).addPathPatterns("/api/job/**");
    }
}
