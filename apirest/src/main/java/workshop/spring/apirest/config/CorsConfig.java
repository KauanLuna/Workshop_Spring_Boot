package workshop.spring.apirest.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Configuração de CORS (Cross-Origin Resource Sharing) para a aplicação.
 * Esta classe permite que a API seja acessada por aplicações frontend em domínios diferentes.
 * 
 * A anotação @Configuration indica ao Spring que esta é uma classe de configuração.
 */
@Configuration
public class CorsConfig implements WebMvcConfigurer {
    /**
     * Configura as políticas de CORS para a aplicação.
     * 
     * @param registry O registro de configurações CORS
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")  // Aplica a configuração a todos os endpoints da API
                .allowedOrigins("*")  // Permite requisições de qualquer origem (não recomendado para produção)
                // Em produção, substitua "*" pelos domínios permitidos, por exemplo:
                // .allowedOrigins("https://meusite.com", "https://app.meusite.com")
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS", "HEAD", "TRACE", "CONNECT")  // Métodos HTTP permitidos
                .allowedHeaders("*")  // Permite todos os cabeçalhos
                .maxAge(3600);  // Tempo em segundos que o navegador pode armazenar em cache a resposta de preflight (OPTIONS)
    }
}