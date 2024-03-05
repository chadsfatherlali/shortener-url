package com.api.shortenerUrl.controllers;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.containsString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.net.URI;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.test.web.servlet.MockMvc;

import com.api.shortenerUrl.services.HomeService;

@SpringBootTest
@AutoConfigureMockMvc
public class HomeControllerTest {

	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private HomeService service;
	
	@Autowired
	private HomeController controller;
	
	@Test
	void contextLoads() throws  Exception {
		assertThat(controller).isNotNull();
	}
	
	@Test
	void shouldReturnGangnamStyleMessage() throws Exception {
		this.mockMvc.perform(get("/"))
			.andDo(print())
			.andExpect(status().isOk())
			.andExpect(content().string(containsString("Gangnam Style")));
	}
	
	@Test
	void shouldReturnRedirectHeaders() throws Exception {
		String urlId = "CUirrYVjpO";
		String longUrl = "https://www.facebook.com";
		HttpHeaders headers = new HttpHeaders();
		
		headers.setLocation(URI.create(longUrl));
		
		when(service.redirectHeaders(urlId)).thenReturn(headers);
		
		this.mockMvc.perform(get("/" + urlId))
			.andDo(print())
			.andExpect(header().exists("Location"))
			.andExpect(header().string("Location", longUrl))
			.andExpect(status().is3xxRedirection());
	}
	
}
