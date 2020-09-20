package <%= packageName %>.web.rest;


import <%= packageName %>.domain.<%= entityVariableName %>;
import <%= packageName %>.repository.<%= entityVariableName %>Repository;
import <%= packageName %>.service.dto.<%= entityVariableName %>DTO;
import <%= packageName %>.service.mapper.<%= entityVariableName %>Mapper;
import io.micronaut.context.annotation.Property;
import io.micronaut.core.type.Argument;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.client.RxHttpClient;
import io.micronaut.http.client.annotation.Client;
import io.micronaut.http.client.exceptions.HttpClientResponseException;
import io.micronaut.test.annotation.MicronautTest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import javax.inject.Inject;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


/**
 * Integration tests for the {@Link <%= entityVariableName %>Resource} REST controller.
 */
@MicronautTest(transactional = false)
@Property(name = "micronaut.security.enabled", value = "false")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class <%= entityVariableName %>Resource<%= suffix %>IT {

    private static final ZonedDateTime DEFAULT_CREATED_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_CREATED_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final ZonedDateTime DEFAULT_LAST_MODIFIED_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_LAST_MODIFIED_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final Double DEFAULT_SCORE = 1D;
    private static final Double UPDATED_SCORE = 2D;

    private static final String DEFAULT_IMAGE_URL = "AAAAAAAAAA";
    private static final String UPDATED_IMAGE_URL = "BBBBBBBBBB";

    private static final Boolean DEFAULT_IS_ACTIVE = false;
    private static final Boolean UPDATED_IS_ACTIVE = true;

    @Inject
    private <%= entityVariableName %>Mapper <%= entityVariableName %>Mapper;
    @Inject
    private <%= entityVariableName %>Repository <%= entityVariableName %>Repository;

    @Inject @Client("/")
    RxHttpClient client;

    private <%= entityVariableName %> <%= entityVariableName %>;

    @BeforeEach
    public void initTest() {
        <%= entityVariableName %> = createEntity();
    }

    @AfterEach
    public void cleanUpTest() {
        <%= entityVariableName %>Repository.deleteAll();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static <%= entityVariableName %> createEntity() {
        <%= entityVariableName %> <%= entityVariableName %> = new <%= entityVariableName %>()
            .createdDate(DEFAULT_CREATED_DATE)
            .lastModifiedDate(DEFAULT_LAST_MODIFIED_DATE)
            .name(DEFAULT_NAME)
            .score(DEFAULT_SCORE)
            .imageUrl(DEFAULT_IMAGE_URL)
            .isActive(DEFAULT_IS_ACTIVE);
        return <%= entityVariableName %>;
    }


    @Test
    public void create<%= entityVariableName %>() throws Exception {
        int data<%= entityVariableName %>SizeBeforeCreate = <%= entityVariableName %>Repository.findAll().size();

        <%= entityVariableName %>DTO <%= entityVariableName %>DTO = <%= entityVariableName %>Mapper.toDto(<%= entityVariableName %>);

        // Create the <%= entityVariableName %>
        HttpResponse<<%= entityVariableName %>DTO> response = client.exchange(HttpRequest.POST("/api/<%= entityVariableName %>s", <%= entityVariableName %>DTO), <%= entityVariableName %>DTO.class).blockingFirst();

        assertThat(response.status().getCode()).isEqualTo(HttpStatus.CREATED.getCode());

        // Validate the <%= entityVariableName %> in the data<%= entityVariableName %>
        List<<%= entityVariableName %>> <%= entityVariableName %>List = <%= entityVariableName %>Repository.findAll();
        assertThat(<%= entityVariableName %>List).hasSize(data<%= entityVariableName %>SizeBeforeCreate + 1);
        <%= entityVariableName %> test<%= entityVariableName %> = <%= entityVariableName %>List.get(<%= entityVariableName %>List.size() - 1);

        assertThat(test<%= entityVariableName %>.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(test<%= entityVariableName %>.getLastModifiedDate()).isEqualTo(DEFAULT_LAST_MODIFIED_DATE);
        assertThat(test<%= entityVariableName %>.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(test<%= entityVariableName %>.getScore()).isEqualTo(DEFAULT_SCORE);
        assertThat(test<%= entityVariableName %>.getImageUrl()).isEqualTo(DEFAULT_IMAGE_URL);
        assertThat(test<%= entityVariableName %>.isIsActive()).isEqualTo(DEFAULT_IS_ACTIVE);
    }

    @Test
    public void create<%= entityVariableName %>WithExistingId() throws Exception {
        int data<%= entityVariableName %>SizeBeforeCreate = <%= entityVariableName %>Repository.findAll().size();

        // Create the <%= entityVariableName %> with an existing ID
        <%= entityVariableName %>.setId(1L);
        <%= entityVariableName %>DTO <%= entityVariableName %>DTO = <%= entityVariableName %>Mapper.toDto(<%= entityVariableName %>);

        // An entity with an existing ID cannot be created, so this API call must fail
        @SuppressWarnings("unchecked")
        HttpResponse<<%= entityVariableName %>DTO> response = client.exchange(HttpRequest.POST("/api/<%= entityVariableName %>s", <%= entityVariableName %>DTO), <%= entityVariableName %>DTO.class)
            .onErrorReturn(t -> (HttpResponse<<%= entityVariableName %>DTO>) ((HttpClientResponseException) t).getResponse()).blockingFirst();

        assertThat(response.status().getCode()).isEqualTo(HttpStatus.BAD_REQUEST.getCode());

        // Validate the <%= entityVariableName %> in the data<%= entityVariableName %>
        List<<%= entityVariableName %>> <%= entityVariableName %>List = <%= entityVariableName %>Repository.findAll();
        assertThat(<%= entityVariableName %>List).hasSize(data<%= entityVariableName %>SizeBeforeCreate);
    }


    @Test
    public void checkNameIsRequired() throws Exception {
        int data<%= entityVariableName %>SizeBeforeTest = <%= entityVariableName %>Repository.findAll().size();
        // set the field null
        <%= entityVariableName %>.setName(null);

        // Create the <%= entityVariableName %>, which fails.
        <%= entityVariableName %>DTO <%= entityVariableName %>DTO = <%= entityVariableName %>Mapper.toDto(<%= entityVariableName %>);

        @SuppressWarnings("unchecked")
        HttpResponse<<%= entityVariableName %>DTO> response = client.exchange(HttpRequest.POST("/api/<%= entityVariableName %>s", <%= entityVariableName %>DTO), <%= entityVariableName %>DTO.class)
            .onErrorReturn(t -> (HttpResponse<<%= entityVariableName %>DTO>) ((HttpClientResponseException) t).getResponse()).blockingFirst();

        assertThat(response.status().getCode()).isEqualTo(HttpStatus.BAD_REQUEST.getCode());

        List<<%= entityVariableName %>> <%= entityVariableName %>List = <%= entityVariableName %>Repository.findAll();
        assertThat(<%= entityVariableName %>List).hasSize(data<%= entityVariableName %>SizeBeforeTest);
    }

    @Test
    public void checkImageUrlIsRequired() throws Exception {
        int data<%= entityVariableName %>SizeBeforeTest = <%= entityVariableName %>Repository.findAll().size();
        // set the field null
        <%= entityVariableName %>.setImageUrl(null);

        // Create the <%= entityVariableName %>, which fails.
        <%= entityVariableName %>DTO <%= entityVariableName %>DTO = <%= entityVariableName %>Mapper.toDto(<%= entityVariableName %>);

        @SuppressWarnings("unchecked")
        HttpResponse<<%= entityVariableName %>DTO> response = client.exchange(HttpRequest.POST("/api/<%= entityVariableName %>s", <%= entityVariableName %>DTO), <%= entityVariableName %>DTO.class)
            .onErrorReturn(t -> (HttpResponse<<%= entityVariableName %>DTO>) ((HttpClientResponseException) t).getResponse()).blockingFirst();

        assertThat(response.status().getCode()).isEqualTo(HttpStatus.BAD_REQUEST.getCode());

        List<<%= entityVariableName %>> <%= entityVariableName %>List = <%= entityVariableName %>Repository.findAll();
        assertThat(<%= entityVariableName %>List).hasSize(data<%= entityVariableName %>SizeBeforeTest);
    }

    @Test
    public void getAll<%= entityVariableName %>s() throws Exception {
        // Initialize the data<%= entityVariableName %>
        <%= entityVariableName %>Repository.saveAndFlush(<%= entityVariableName %>);

        // Get the <%= entityVariableName %>List w/ all the <%= entityVariableName %>s
        List<<%= entityVariableName %>DTO> <%= entityVariableName %>s = client.retrieve(HttpRequest.GET("/api/<%= entityVariableName %>s?eagerload=true"), Argument.listOf(<%= entityVariableName %>DTO.class)).blockingFirst();
        <%= entityVariableName %>DTO test<%= entityVariableName %> = <%= entityVariableName %>s.get(0);


        assertThat(test<%= entityVariableName %>.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(test<%= entityVariableName %>.getLastModifiedDate()).isEqualTo(DEFAULT_LAST_MODIFIED_DATE);
        assertThat(test<%= entityVariableName %>.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(test<%= entityVariableName %>.getScore()).isEqualTo(DEFAULT_SCORE);
        assertThat(test<%= entityVariableName %>.getImageUrl()).isEqualTo(DEFAULT_IMAGE_URL);
        assertThat(test<%= entityVariableName %>.isIsActive()).isEqualTo(DEFAULT_IS_ACTIVE);
    }

    @Test
    public void get<%= entityVariableName %>() throws Exception {
        // Initialize the data<%= entityVariableName %>
        <%= entityVariableName %>Repository.saveAndFlush(<%= entityVariableName %>);

        // Get the <%= entityVariableName %>
        <%= entityVariableName %>DTO test<%= entityVariableName %> = client.retrieve(HttpRequest.GET("/api/<%= entityVariableName %>s/" + <%= entityVariableName %>.getId()), <%= entityVariableName %>DTO.class).blockingFirst();


        assertThat(test<%= entityVariableName %>.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(test<%= entityVariableName %>.getLastModifiedDate()).isEqualTo(DEFAULT_LAST_MODIFIED_DATE);
        assertThat(test<%= entityVariableName %>.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(test<%= entityVariableName %>.getScore()).isEqualTo(DEFAULT_SCORE);
        assertThat(test<%= entityVariableName %>.getImageUrl()).isEqualTo(DEFAULT_IMAGE_URL);
        assertThat(test<%= entityVariableName %>.isIsActive()).isEqualTo(DEFAULT_IS_ACTIVE);
    }

    @Test
    public void getNonExisting<%= entityVariableName %>() throws Exception {
        // Get the <%= entityVariableName %>
        @SuppressWarnings("unchecked")
        HttpResponse<<%= entityVariableName %>DTO> response = client.exchange(HttpRequest.GET("/api/<%= entityVariableName %>s/"+ Long.MAX_VALUE), <%= entityVariableName %>DTO.class)
            .onErrorReturn(t -> (HttpResponse<<%= entityVariableName %>DTO>) ((HttpClientResponseException) t).getResponse()).blockingFirst();

        assertThat(response.status().getCode()).isEqualTo(HttpStatus.NOT_FOUND.getCode());
    }

    @Test
    public void update<%= entityVariableName %>() throws Exception {
        // Initialize the data<%= entityVariableName %>
        <%= entityVariableName %>Repository.saveAndFlush(<%= entityVariableName %>);

        int data<%= entityVariableName %>SizeBeforeUpdate = <%= entityVariableName %>Repository.findAll().size();

        // Update the <%= entityVariableName %>
        <%= entityVariableName %> updated<%= entityVariableName %> = <%= entityVariableName %>Repository.findById(<%= entityVariableName %>.getId()).get();

        updated<%= entityVariableName %>
            .createdDate(UPDATED_CREATED_DATE)
            .lastModifiedDate(UPDATED_LAST_MODIFIED_DATE)
            .name(UPDATED_NAME)
            .score(UPDATED_SCORE)
            .imageUrl(UPDATED_IMAGE_URL)
            .isActive(UPDATED_IS_ACTIVE);
        <%= entityVariableName %>DTO updated<%= entityVariableName %>DTO = <%= entityVariableName %>Mapper.toDto(updated<%= entityVariableName %>);

        @SuppressWarnings("unchecked")
        HttpResponse<<%= entityVariableName %>DTO> response = client.exchange(HttpRequest.PUT("/api/<%= entityVariableName %>s", updated<%= entityVariableName %>DTO), <%= entityVariableName %>DTO.class)
            .onErrorReturn(t -> (HttpResponse<<%= entityVariableName %>DTO>) ((HttpClientResponseException) t).getResponse()).blockingFirst();

        assertThat(response.status().getCode()).isEqualTo(HttpStatus.OK.getCode());

        // Validate the <%= entityVariableName %> in the data<%= entityVariableName %>
        List<<%= entityVariableName %>> <%= entityVariableName %>List = <%= entityVariableName %>Repository.findAll();
        assertThat(<%= entityVariableName %>List).hasSize(data<%= entityVariableName %>SizeBeforeUpdate);
        <%= entityVariableName %> test<%= entityVariableName %> = <%= entityVariableName %>List.get(<%= entityVariableName %>List.size() - 1);

        assertThat(test<%= entityVariableName %>.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(test<%= entityVariableName %>.getLastModifiedDate()).isEqualTo(UPDATED_LAST_MODIFIED_DATE);
        assertThat(test<%= entityVariableName %>.getName()).isEqualTo(UPDATED_NAME);
        assertThat(test<%= entityVariableName %>.getScore()).isEqualTo(UPDATED_SCORE);
        assertThat(test<%= entityVariableName %>.getImageUrl()).isEqualTo(UPDATED_IMAGE_URL);
        assertThat(test<%= entityVariableName %>.isIsActive()).isEqualTo(UPDATED_IS_ACTIVE);
    }

    @Test
    public void updateNonExisting<%= entityVariableName %>() throws Exception {
        int data<%= entityVariableName %>SizeBeforeUpdate = <%= entityVariableName %>Repository.findAll().size();

        // Create the <%= entityVariableName %>
        <%= entityVariableName %>DTO <%= entityVariableName %>DTO = <%= entityVariableName %>Mapper.toDto(<%= entityVariableName %>);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        @SuppressWarnings("unchecked")
        HttpResponse<<%= entityVariableName %>DTO> response = client.exchange(HttpRequest.PUT("/api/<%= entityVariableName %>s", <%= entityVariableName %>DTO), <%= entityVariableName %>DTO.class)
            .onErrorReturn(t -> (HttpResponse<<%= entityVariableName %>DTO>) ((HttpClientResponseException) t).getResponse()).blockingFirst();

        assertThat(response.status().getCode()).isEqualTo(HttpStatus.BAD_REQUEST.getCode());

        // Validate the <%= entityVariableName %> in the data<%= entityVariableName %>
        List<<%= entityVariableName %>> <%= entityVariableName %>List = <%= entityVariableName %>Repository.findAll();
        assertThat(<%= entityVariableName %>List).hasSize(data<%= entityVariableName %>SizeBeforeUpdate);
    }

    @Test
    public void delete<%= entityVariableName %>() throws Exception {
        // Initialize the data<%= entityVariableName %> with one entity
        <%= entityVariableName %>Repository.saveAndFlush(<%= entityVariableName %>);

        int data<%= entityVariableName %>SizeBeforeDelete = <%= entityVariableName %>Repository.findAll().size();

        // Delete the <%= entityVariableName %>
        @SuppressWarnings("unchecked")
        HttpResponse<<%= entityVariableName %>DTO> response = client.exchange(HttpRequest.DELETE("/api/<%= entityVariableName %>s/"+ <%= entityVariableName %>.getId()), <%= entityVariableName %>DTO.class)
            .onErrorReturn(t -> (HttpResponse<<%= entityVariableName %>DTO>) ((HttpClientResponseException) t).getResponse()).blockingFirst();

        assertThat(response.status().getCode()).isEqualTo(HttpStatus.NO_CONTENT.getCode());

            // Validate the data<%= entityVariableName %> is now empty
        List<<%= entityVariableName %>> <%= entityVariableName %>List = <%= entityVariableName %>Repository.findAll();
        assertThat(<%= entityVariableName %>List).hasSize(data<%= entityVariableName %>SizeBeforeDelete - 1);
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(<%= entityVariableName %>.class);
        <%= entityVariableName %> <%= entityVariableName %>1 = new <%= entityVariableName %>();
        <%= entityVariableName %>1.setId(1L);
        <%= entityVariableName %> <%= entityVariableName %>2 = new <%= entityVariableName %>();
        <%= entityVariableName %>2.setId(<%= entityVariableName %>1.getId());
        assertThat(<%= entityVariableName %>1).isEqualTo(<%= entityVariableName %>2);
        <%= entityVariableName %>2.setId(2L);
        assertThat(<%= entityVariableName %>1).isNotEqualTo(<%= entityVariableName %>2);
        <%= entityVariableName %>1.setId(null);
        assertThat(<%= entityVariableName %>1).isNotEqualTo(<%= entityVariableName %>2);
    }
}
