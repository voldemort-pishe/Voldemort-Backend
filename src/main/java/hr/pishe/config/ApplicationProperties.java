package hr.pishe.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Properties specific to Voldemort.
 * <p>
 * Properties are configured in the application.yml file.
 * See {@link io.github.jhipster.config.JHipsterProperties} for a good example.
 */
@ConfigurationProperties(prefix = "application", ignoreUnknownFields = false)
public class ApplicationProperties {

    private final ApplicationProperties.Base base = new ApplicationProperties.Base();

    public ApplicationProperties.Base getBase() {
        return this.base;
    }

    public static class Base {
        private String url = "";
        private String panel = "";

        public Base() {

        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getPanel() {
            return panel;
        }

        public void setPanel(String panel) {
            this.panel = panel;
        }
    }

}
