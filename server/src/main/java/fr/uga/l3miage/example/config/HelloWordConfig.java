package fr.uga.l3miage.example.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Cette classe correspond à une configuration quelconque qui permet de déclarer un bean qui pourra être injecté par la suite<br>
 * Les Annotations :
 * <ul>
 *     <li>{@link Configuration} permet de créer une configuration spring, cette annotation est en quelque sorte un stéréotype d'un @{@link Bean}, car elle va permettre de créer un candidat à l'injection.</li>
 * </ul>
 */
@Configuration
public class HelloWordConfig {
    /**
     * Cette fonction permet de créer le {@link Bean} 'Hello Word' qui sera injecté si on demande un string quelconque dans un constructeur<br>
     * Les Annotations :
     * <ul>
     *     <li>{@link Bean} permet de dire que le résultat de la fonction est un bean ajouté dans l'application context</li>
     * </ul>
     * @return la chaine de caractère <b>'Hello Word'</b>
     */
    @Bean
    public String getHelloWord(){
        return "Hello Word";
    }
}
