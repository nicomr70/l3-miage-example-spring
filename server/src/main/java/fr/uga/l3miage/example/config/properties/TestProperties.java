package fr.uga.l3miage.example.config.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import java.util.List;


/**
 * Cette classe va permettre de récupérer des properties présent dans le fichier <b color="red">server/main/ressources/application.yml</b><br>
 *
 * Les annotations :
 * <ul>
 *     <li>{@link Data} est une annotation raccourcie pour plusieurs annotations de lombok<br>Aller voir la doc sur <a href="https://projectlombok.org/features/Data">projetlombok.org/features/data</a></a></li></li>
 *     <li>{@link ConstructorBinding} permet de dire quel constructeur utiliser pour récupérer les propriétés de configuration</li>
 *     <li>{@link ConfigurationProperties} permet de dire que les attributs de cette classe sont des propriétés de configuration, avec comme préfixe <b>vous.pouvez</b><br>
 *     <b color="red">Attention</b>, il est obligatoire d'utiliser cette classe dans une configuration via l'annotation {@link EnableConfigurationProperties}
 *     </li>
 * </ul>
 *
 */
@Data
@ConstructorBinding
@ConfigurationProperties(prefix = "vous.pouvez")
public class TestProperties {
    private List<String> utiliser;
    private Integer avez;
    private Integer besoin;
    private Integer dans;
    private String lesProperties;
}
