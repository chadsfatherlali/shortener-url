package com.api.shortenerUrl.controllers;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.not;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.api.shortenerUrl.entities.UrlEntity;
import com.api.shortenerUrl.responses.GenericResponsePageable;
import com.api.shortenerUrl.services.UrlService;
import com.api.shortenerUrl.utils.Id;

@SpringBootTest
@AutoConfigureMockMvc
public class UrlControllerTest {
	
	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private UrlService service;
	
	@Autowired
	private UrlController controller;
	
	private GenericResponsePageable<List<UrlEntity>> urlPageableList = new GenericResponsePageable<List<UrlEntity>>();
	private String idUrlFacebook = Id.generate();
	private String idUrlGoogle = Id.generate();
	private List<UrlEntity> urlList = new ArrayList<>();
	
	@BeforeEach
	void init() {
		String domain = "https://agile-waters-42933-15aa8f254d4e.herokuapp.com/";
		Instant createdDate = Instant.now();
		UrlEntity urlEntityFacebook = new UrlEntity();
		UrlEntity urlEntityGoogle = new UrlEntity();
		
		urlEntityFacebook.setLongUrl("http://www.facebook.com");
		urlEntityFacebook.setShortUrl(domain + idUrlFacebook);
		urlEntityFacebook.setId(idUrlFacebook);
		urlEntityFacebook.setCreatedDate(createdDate);
		
		urlEntityGoogle.setLongUrl("http://www.google.com");
		urlEntityGoogle.setShortUrl(domain + idUrlGoogle);
		urlEntityGoogle.setId(idUrlGoogle);
		urlEntityGoogle.setCreatedDate(createdDate);
		
		urlList.add(urlEntityFacebook);
		urlList.add(urlEntityFacebook);
		urlList.add(urlEntityGoogle);
	}
	
	@AfterEach
    void teardown() {
		urlPageableList.setData(null);
		urlPageableList.setErrorMessage(null);
		urlPageableList.setStatusCode(null);
	}
	
	@Test
	void contextLoads() throws  Exception {
		assertThat(controller).isNotNull();
	}
	
	@Test
	void shouldReturnListOfUrlDocuments() throws Exception {
		Page<UrlEntity> page = new PageImpl<>(urlList);
		
		urlPageableList.setData(page);
		urlPageableList.setErrorMessage(null);
		urlPageableList.setStatusCode(HttpStatus.OK);
		
		when(service.listUrls(null, 0, 10)).thenReturn(urlPageableList);
		
		this.mockMvc.perform(get("/url"))
			.andDo(print())
			.andExpect(status().isOk())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON))
			.andExpect(jsonPath("$.statusCode").exists())
			.andExpect(jsonPath("$.statusCode").value("OK"))
			.andExpect(jsonPath("$.errorMessage").doesNotExist())
			.andExpect(jsonPath("$.data").exists())
			.andExpect(jsonPath("$.data").isMap())
			.andExpect(jsonPath("$.data.content").isArray())
			.andExpect(jsonPath("$.data.content[0].id").value(idUrlFacebook));
	}
	
	@Test
	void shouldReturnListOfUrlDocumentsContainsFacebookOnly() throws Exception {
		String filterParam = "facebook";
		List<UrlEntity> filteredUrlList;
		
		filteredUrlList = urlList.stream()
				.filter(url -> url.longUrl.contains(filterParam))
				.collect(Collectors.toList());
		
		Page<UrlEntity> page = new PageImpl<>(filteredUrlList);
		
		urlPageableList.setData(page);
		urlPageableList.setErrorMessage(null);
		urlPageableList.setStatusCode(HttpStatus.OK);
		
		when(service.listUrls(filterParam, 0, 10)).thenReturn(urlPageableList);
		
		this.mockMvc.perform(get("/url?search=facebook"))
			.andDo(print())
			.andExpect(status().isOk())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON))
			.andExpect(jsonPath("$.statusCode").exists())
			.andExpect(jsonPath("$.statusCode").value("OK"))
			.andExpect(jsonPath("$.errorMessage").doesNotExist())
			.andExpect(jsonPath("$.data").exists())
			.andExpect(jsonPath("$.data").isMap())
			.andExpect(jsonPath("$.data.content").isArray())
			.andExpect(content().string(not(containsString("google"))));
	}
	
}
