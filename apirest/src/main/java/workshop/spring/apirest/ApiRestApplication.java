package workshop.spring.apirest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 🏰 PASSO 1: INICIALIZAÇÃO DA APLICAÇÃO
 * 
 * Esta é a classe principal da Guilda de Aventureiros, responsável por iniciar
 * nossa aplicação Spring Boot. Ela é o ponto de partida de todo o sistema.
 * 
 * A anotação @SpringBootApplication combina três anotações essenciais:
 * - @Configuration: Permite registrar beans no contexto do Spring
 * - @EnableAutoConfiguration: Configura automaticamente o Spring Boot
 * - @ComponentScan: Procura por componentes no pacote atual e subpacotes
 */
@SpringBootApplication
public class ApiRestApplication {

    /**
     * 🚀 Ponto de entrada da aplicação
     * 
     * Este método é executado quando a aplicação é iniciada. Ele é responsável por:
     * 1. Inicializar o contexto do Spring Boot
     * 2. Configurar o servidor web embutido (Tomcat na porta 8080)
     * 3. Escanear e registrar todos os componentes (@Component, @Service, etc.)
     * 4. Iniciar a aplicação e deixá-la pronta para receber requisições
     * 
     * @param args Argumentos de linha de comando (opcional)
     * 
     * Exemplo de uso:
     * 1. Execute a classe diretamente no IDE
     * 2. Ou use: mvn spring-boot:run
     * 3. Acesse: http://localhost:8080
     */
    public static void main(String[] args) {
        // Inicia a aplicação Spring Boot
        SpringApplication.run(ApiRestApplication.class, args);
        
        // DICA: Você pode personalizar a inicialização adicionando configurações aqui
        // Por exemplo, carregar dados iniciais ou configurar variáveis de ambiente
    }
}
