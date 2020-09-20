package <%= packageName %>.web.rest.<%= subPackageName %>.v1;

import <%= packageName %>.service.<%= subPackageName %>.<%= entityName %>Service<%= suffix %>;
import <%= packageName %>.service.dto.<%= entityName %>DTO;
import <%= packageName %>.web.rest.<%= entityName %>Resource;
import io.micronaut.context.annotation.Value;
import io.micronaut.data.model.Pageable;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.*;
import io.micronaut.scheduling.TaskExecutors;
import io.micronaut.scheduling.annotation.ExecuteOn;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

@Controller("/api/<%= subPackageName %>/v1")
public class <%= entityName %>Resource<%= suffix %> {

    private final Logger log = LoggerFactory.getLogger(<%= entityName %>Resource.class);

    private static final String ENTITY_NAME = "<%= entityVariableName %>";

    @Value("${jhipster.client<%= suffix %>.name}")
    private String applicationName;

    private final <%= entityName %>Resource <%= entityVariableName %>Resource;

    private final <%= entityName %>Service<%= suffix %> <%= entityVariableName %>Service<%= suffix %>;

    public <%= entityName %>Resource<%= suffix %>(<%= entityName %>Resource <%= entityVariableName %>Resource,  <%= entityName %>Service<%= suffix %> <%= entityVariableName %>Service<%= suffix %>) {
        this.<%= entityVariableName %>Resource = <%= entityVariableName %>Resource;
        this.<%= entityVariableName %>Service<%= suffix %> = <%= entityVariableName %>Service<%= suffix %>;
    }

    /**
     * {@code POST  /<%= entityVariableName %>s} : Create a new <%= entityVariableName %>.
     *
     * @param <%= entityVariableName %>DTO the <%= entityVariableName %>DTO to create.
     * @return the {@link HttpResponse} with status {@code 201 (Created)} and with body the new <%= entityVariableName %>DTO, or with status {@code 400 (Bad Request)} if the <%= entityVariableName %> has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @Post("/<%= entityVariableName %>s")
    @ExecuteOn(TaskExecutors.IO)
    public HttpResponse<<%= entityName %>DTO> create<%= entityName %>(@Body <%= entityName %>DTO <%= entityVariableName %>DTO) throws URISyntaxException {
        log.debug("<%= suffix %> REST request to create <%= entityName %> : {}", <%= entityVariableName %>DTO);
        return <%= entityVariableName %>Resource.create<%= entityName %>(<%= entityVariableName %>DTO);
    }

    /**
     * {@code PUT  /<%= entityVariableName %>s} : Updates an existing <%= entityVariableName %>.
     *
     * @param <%= entityVariableName %>DTO the <%= entityVariableName %>DTO to update.
     * @return the {@link HttpResponse} with status {@code 200 (OK)} and with body the updated <%= entityVariableName %>DTO,
     * or with status {@code 400 (Bad Request)} if the <%= entityVariableName %>DTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the <%= entityVariableName %>DTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @Put("/<%= entityVariableName %>s")
    @ExecuteOn(TaskExecutors.IO)
    public HttpResponse<<%= entityName %>DTO> update<%= entityName %>(@Body <%= entityName %>DTO <%= entityVariableName %>DTO) throws URISyntaxException {
        log.debug("<%= suffix %> REST request to update <%= entityName %> : {}", <%= entityVariableName %>DTO);
        return <%= entityVariableName %>Resource.update<%= entityName %>(<%= entityVariableName %>DTO);
    }

    /**
     * {@code GET  /<%= entityVariableName %>s} : get all the <%= entityVariableName %>s.
     *
     * @param pageable the pagination information.
     * @return the {@link HttpResponse} with status {@code 200 (OK)} and the list of <%= entityVariableName %>s in body.
     */
    @Get("/<%= entityVariableName %>s")
    @ExecuteOn(TaskExecutors.IO)
    public HttpResponse<List<<%= entityName %>DTO>> getAll<%= entityName %>s(HttpRequest request, Pageable pageable) {
        log.debug("<%= suffix %> REST request to get all <%= entityName %>s");
        return <%= entityVariableName %>Resource.getAll<%= entityName %>s(request, pageable);
    }

    /**
     * {@code GET  /<%= entityVariableName %>s/:id} : get the "id" <%= entityVariableName %>.
     *
     * @param id the id of the <%= entityVariableName %>DTO to retrieve.
     * @return the {@link HttpResponse} with status {@code 200 (OK)} and with body the <%= entityVariableName %>DTO, or with status {@code 404 (Not Found)}.
     */
    @Get("/<%= entityVariableName %>s/{id}")
    @ExecuteOn(TaskExecutors.IO)
    public Optional<<%= entityName %>DTO> get<%= entityName %>(@PathVariable Long id) {
        log.debug("REST request to get <%= entityName %> : {}", id);
        return <%= entityVariableName %>Resource.get<%= entityName %>(id);
    }

    /**
     * {@code DELETE  /<%= entityVariableName %>s/:id} : delete the "id" <%= entityVariableName %>.
     *
     * @param id the id of the <%= entityVariableName %>DTO to delete.
     * @return the {@link HttpResponse} with status {@code 204 (NO_CONTENT)}.
     */
    @Delete("/<%= entityVariableName %>s/{id}")
    @ExecuteOn(TaskExecutors.IO)
    public HttpResponse delete<%= entityName %>(@PathVariable Long id) {
        log.debug("REST request to get <%= entityName %> : {}", id);
        return <%= entityVariableName %>Resource.delete<%= entityName %>(id);
    }
}
