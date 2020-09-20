package <%= packageName %>.service.<%= subPackageName %>;

import <%= packageName %>.repository.<%= entityName %>Repository;
import <%= packageName %>.service.<%= entityName %>Service;
import <%= packageName %>.service.mapper.<%= entityName %>Mapper;

import javax.inject.Singleton;
import javax.transaction.Transactional;

@Singleton
@Transactional
public class <%= entityName %>Service<%= suffix %> {
    private final <%= entityName %>Mapper <%= entityVariableName %>Mapper;
    private final <%= entityName %>Repository<%= suffix %> <%= entityVariableName %>Repository<%= suffix %>;

    public <%= entityName %>Service<%= suffix %>(<%= entityVariableName %>Mapper <%= entityVariableName %>Mapper, <%= entityVariableName %>Repository<%= suffix %> <%= entityVariableName %>Repository<%= suffix %>) {
        this.<%= entityVariableName %>Mapper = <%= entityVariableName %>Mapper;
        this.<%= entityVariableName %>Repository<%= suffix %> = <%= entityVariableName %>Repository<%= suffix %>;
    }
}
