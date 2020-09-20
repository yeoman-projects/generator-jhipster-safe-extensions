package <%= packageName %>.service.<%= subPackageName %>;

import <%= packageName %>.repository.<%= entityName %>Repository;
import <%= packageName %>.service.<%= entityName %>Service;
import <%= packageName %>.service.mapper.<%= entityName %>Mapper;

import javax.inject.Singleton;
import javax.transaction.Transactional;

@Singleton
@Transactional
public class <%= entityName %>Service<%= suffix %> extends <%= entityName %>Service {
    public <%= entityName %>Service<%= suffix %>(<%= entityName %>Repository <%= entityVariableName %>Repository, <%= entityName %>Mapper <%= entityVariableName %>Mapper) {
        super(<%= entityVariableName %>Repository, <%= entityVariableName %>Mapper);
    }
}
