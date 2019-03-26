package in.anandm.apps;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.web.context.request.RequestContextListener;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.*;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

import java.util.Locale;

//import com.accessgroup.ib.RequestInterceptor;

/**
 * Created by farooq
 */
@Configuration
@EnableWebMvc
public class WebConfig extends WebMvcConfigurerAdapter {





    @Bean(name = "messageSource")
    public MessageSource messageSource() {
        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.setBasename("classpath:i18n/app_msg");
        messageSource.setDefaultEncoding("UTF-8");
        return messageSource;
    }

    @Bean
    public LocaleResolver localeResolver() {
        CookieLocaleResolver resolver = new CookieLocaleResolver();
        resolver.setDefaultLocale(new Locale("en"));
        resolver.setCookieName("myLocaleCookie");
        resolver.setCookieMaxAge(4800);
        return resolver;
    }

    @Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
        configurer.enable();
    }

/*    @Bean
    public EmbeddedServletContainerCustomizer containerCustomizer() {

        return new EmbeddedServletContainerCustomizer() {
            @Override
            public void customize(ConfigurableEmbeddedServletContainer container) {
                ErrorPage error404Page = new ErrorPage(HttpStatus.NOT_FOUND, "/404");
                ErrorPage error500Page = new ErrorPage(HttpStatus.INTERNAL_SERVER_ERROR, "/500");

                container.addErrorPages(error404Page, error500Page);
            }
        };
    }*/


    @Override
    public void configureViewResolvers(ViewResolverRegistry registry) {
        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        viewResolver.setViewClass(JstlView.class);
        viewResolver.setPrefix("/WEB-INF/views/");
        viewResolver.setSuffix(".jsp");
        registry.viewResolver(viewResolver);
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/resources/**").addResourceLocations("/resources/");
        String readPath = String.format("%s/IB_LOGOS/", System.getProperty("user.dir").replace("'\\'", "'//'"));
        String formatted = readPath.replace("\\", "/");
        registry.addResourceHandler("/IB_LOGOS/**").addResourceLocations(String.format("file:///%s", formatted));

        String filePath = String.format("%s/full_statements/", System.getProperty("user.dir").replace("'\\'", "'//'"));
        String formattedSlash = filePath.replace("\\", "/");
        registry.addResourceHandler("/full_statements/**").addResourceLocations(String.format("file:///%s", formattedSlash));

        String filePathmy_qr = String.format("%s/my_qr/", System.getProperty("user.dir").replace("'\\'", "'//'"));
        String formattedSlashmy_qr = filePathmy_qr.replace("\\", "/");
        registry.addResourceHandler("/my_qr/**").addResourceLocations(String.format("file:///%s", formattedSlashmy_qr));


    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        LocaleChangeInterceptor interceptor = new LocaleChangeInterceptor();
        interceptor.setParamName("mylocale");
        registry.addInterceptor(interceptor);
        // registry.addInterceptor(requestInterceptor());
    }

    /* @Bean
     public RequestInterceptor requestInterceptor() {
         return new RequestInterceptor();
     }*/

    @Bean
    public RequestContextListener requestContextListener() {
        return new RequestContextListener();
    }

/*
    @Bean
    public ServletContextInitializer servletContextInitializer(@Value("${secure.cookie}") boolean secure) {
        return new ServletContextInitializer() {

            @Override
            public void onStartup(ServletContext servletContext) throws ServletException {
                servletContext.getSessionCookieConfig().setSecure(secure);
            }
        };
    }
*/
}