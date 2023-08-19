package com.lukianchikov.lego.controller;

import com.lukianchikov.lego.processor.ProcessorApplication;
import com.lukianchikov.lego.processor.dto.JobNotSubmitted;
import com.lukianchikov.lego.processor.dto.JobSubmitted;
import io.awspring.cloud.autoconfigure.s3.S3AutoConfiguration;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobInstance;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URL;
import java.nio.file.Paths;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;


@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = ProcessorApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles({"test"})
@EnableAutoConfiguration(exclude = S3AutoConfiguration.class)
public class ProcessorControllerTest {

    @LocalServerPort
    private int port;

    @MockBean
    JobLauncher jobLauncher;

    @Autowired
    private TestRestTemplate testRestTemplate;

    @SneakyThrows
    @Test
    void given_post_product_with_invalid_path_returns_bad_request() throws Exception {
        JobExecution mockedJobExecution = Mockito.mock(JobExecution.class);
        Mockito.when(jobLauncher.run(any(), any())).thenReturn(mockedJobExecution);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl("http://localhost:" + port + "/api/v1/products")
                .queryParam("filepath", "/foobar");

        ResponseEntity<JobNotSubmitted> response = testRestTemplate.exchange(
                uriBuilder.toUriString(),
                HttpMethod.POST,
                new HttpEntity<>(headers),
                JobNotSubmitted.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @SneakyThrows
    @Test
    void given_post_product_with_valid_path_returns_accepted() throws Exception {
        JobExecution mockedJobExecution = Mockito.mock(JobExecution.class);
        JobInstance mockedJobInstance = Mockito.mock(JobInstance.class);
        Mockito.when(mockedJobInstance.getJobName()).thenReturn("Superjob");

        Mockito.when(mockedJobExecution.getJobId()).thenReturn(3L);
        Mockito.when(mockedJobExecution.getJobInstance()).thenReturn(mockedJobInstance);

        Mockito.when(jobLauncher.run(any(), any())).thenReturn(mockedJobExecution);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        URL resource = getClass().getClassLoader().getResource("product-avro-schema.json");
        String filepath = Paths.get(resource.toURI()).toFile().getPath();

        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl("http://localhost:" + port + "/api/v1/products")
                .queryParam("filepath", filepath);

        ResponseEntity<JobSubmitted> response = testRestTemplate.exchange(
                uriBuilder.toUriString(),
                HttpMethod.POST,
                new HttpEntity<>(headers),
                JobSubmitted.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.ACCEPTED);
    }
}
