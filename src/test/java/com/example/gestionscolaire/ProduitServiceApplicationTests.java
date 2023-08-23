package com.example.gestionscolaire;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
//import org.junit.jupiter.api.Test;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.testcontainers.junit.jupiter.Testcontainers;
//
//@SpringBootTest
//@Testcontainers
//@AutoConfigureMockMvc
@RequiredArgsConstructor
class ProduitServiceApplicationTests {

//	@Container
//	static MongoDBContainer mongoDBContainer = new MongoDBContainer("mongo:4.4.2");


//	private final MockMvc mockMvc;

//    private final ObjectMapper objectMapper;

//    private final ProducRepo producRepo;

//	@DynamicPropertySource
//	static void setProperties(DynamicPropertyRegistry dynamicPropertyRegistry){
//		dynamicPropertyRegistry.add("spring.data.mongodb.uri", mongoDBContainer::getReplicaSetUrl);
//	}

//    @Test
//    void shouldCreateProduct() throws Exception {
//		ProductRequest productRequest = getProductRequest();
//		String productRequestString = objectMapper.writeValueAsString(productRequest);
//		mockMvc.perform(MockMvcRequestBuilders.post("/api/product")
//				.contentType(MediaType.APPLICATION_JSON)
//				.content(productRequestString)
//		).andExpect(status().isCreated());
//		producRepo.findAll().size();
//    }

//	private ProductRequest getProductRequest(){
//		return ProductRequest.builder()
//				.name("iPhone 13")
//				.description("iPhone 13")
//				.price(BigDecimal.valueOf(1200))
//				.build();
//	}

}
