package es.quark.fullstatckangularspring.config;


import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableJpaRepositories("es.quark.fullstatckangularspring.repository")
@EnableTransactionManagement
public class DatabaseConfig {
}
