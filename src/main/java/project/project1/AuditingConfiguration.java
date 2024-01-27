package project.project1;

import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.stereotype.Component;

@Profile("local")
@EnableJpaAuditing
@Component
public class AuditingConfiguration {
}
