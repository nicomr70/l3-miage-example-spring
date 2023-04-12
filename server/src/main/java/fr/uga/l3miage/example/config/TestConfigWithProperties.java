/*
 * Copyright (c) 2023. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package fr.uga.l3miage.example.config;

import fr.uga.l3miage.example.config.properties.TestProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Cette classe correspond à une configuration quelconque qui permet de déclarer un bean qui pourra être injecté par la suite<br>
 * Elle donne un exemple d'utilisation d'injection de propriétés de configuration<br>
 *
 * Les Annotations :
 * <ul>
 *     <li>{@link Configuration} permet de créer une configuration spring, cette annotation est en quelque sorte un stéréotype d'un @{@link Bean}, car elle va permettre de créer un candidat à l'injection.</li>
 *     <li>{@link EnableConfigurationProperties} permet d'utiliser une classe qui à des propriétés de configuration</li>
 *     <li>{@link Slf4j} permet de récupérer un logger nommé <b color="orange">log</b> afin de pouvoir afficher des messages lors de l'exécution.<br>Voir la doc <a href="https://projectlombok.org/features/log">projectlombok/features/log</a</li>
 * </ul>
 */
@Configuration
@Slf4j
@EnableConfigurationProperties(TestProperties.class)
public class TestConfigWithProperties {

    /**
     * Les Annotations :
     * <ul>
     *  <li>{@link Bean} permet de dire que le résultat de la fonction est un bean ajouté dans l'application context</li>
     * </ul>
     *
     * @param testProperties injecté directement par spring
     * @return la somme de tous les entiers présents dans les properties
     */
    @Bean
    int config(TestProperties testProperties){
        return testProperties.getAvez()+testProperties.getBesoin()+testProperties.getDans();
    }
}
