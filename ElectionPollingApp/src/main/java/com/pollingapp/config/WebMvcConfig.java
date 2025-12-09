package com.pollingapp.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.*;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    /**
     * Configure static resource handling.
     * This allows Spring to serve files from:
     *  - /static
     *  - /public
     *  - /resources
     *  - /META-INF/resources
     *
     * AND also serve uploaded images (candidate symbols)
     * from the local 'uploads/' folder.
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {

        // Serve candidate symbol images from filesystem
        registry.addResourceHandler("/uploads/**")
                .addResourceLocations("file:uploads/");

        // (Optional) Serve static resources if needed
        registry.addResourceHandler("/static/**")
                .addResourceLocations("classpath:/static/");
    }

    /**
     * Map simple URLs directly to templates
     * without needing a controller.
     *
     * For example:
     *   /login → login.html
     *   /home → home.html
     */
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {

        // Login page
        registry.addViewController("/login")
                .setViewName("login");

        // Home / landing page
        registry.addViewController("/home")
                .setViewName("home");
    }

    /**
     * (Optional) Enable CORS if you ever expose APIs.
     * Useful only if an external frontend calls backend.
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedMethods("*");   // GET, POST, PUT, DELETE etc.
    }
}
