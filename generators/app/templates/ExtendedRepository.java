package <%= packageName %>.repository.<%= subPackageName %>;

import <%= packageName %>.domain.<%= entityName %>;
import <%= packageName %>.repository.<%= entityName %>Repository;
import io.micronaut.context.annotation.Primary;
import io.micronaut.data.annotation.Query;
import io.micronaut.data.annotation.Repository;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

/**
 * Micronaut Data  repository for the extended <%= entityName %> entity.
 */
@SuppressWarnings("unused")
@Repository
@Primary
public abstract class <%= entityName %>Repository<%= suffix %> extends <%= entityName %>Repository {

    public <%= entityName %>Repository<%= suffix %>(EntityManager entityManager) {
        super(entityManager);
    }
}
