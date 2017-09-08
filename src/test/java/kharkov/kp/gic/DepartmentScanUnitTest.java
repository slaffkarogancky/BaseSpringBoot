package kharkov.kp.gic;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.fileUpload;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.HttpHeaders;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.MOCK)
@AutoConfigureMockMvc
@FixMethodOrder(org.junit.runners.MethodSorters.NAME_ASCENDING)
public class DepartmentScanUnitTest {

	@Autowired
	private MockMvc mockMvc;
	
	// тестовый токен, действителен до 7.09.2018
	private String token = "Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJiYXJpbiIsImV4cCI6MTgyMDE0NzU4MX0.eeiNrWBX7NbhAr6jBhKJuEpu9Jq3GOXt6NZaaSfZlWHXeyLV5u8S4p0G766GpDA6Q3QZQKF2DaJc-qxGoGoZkw";

	@Test
	public void _a() throws Exception {		
		MockMultipartFile multipartFile = _createMultipartFile("alpha.jpg");
		mockMvc.perform(fileUpload("/personnel/api/v1/departments/1/blob")
				.file(multipartFile)
				.header(HttpHeaders.AUTHORIZATION, token))
				.andExpect(status().isCreated());
	}
	
	@Test
	public void _aa() throws Exception {		
		MockMultipartFile multipartFile = _createMultipartFile("beta.jpg");
		mockMvc.perform(fileUpload("/personnel/api/v1/departments/1/blob")
				.file(multipartFile)
				.header(HttpHeaders.AUTHORIZATION, token))
				.andExpect(status().isCreated());
	}	
	
	@Test
	public void _aaa() throws Exception {		
		MockMultipartFile multipartFile = _createMultipartFile("gamma.jpg");
		mockMvc.perform(fileUpload("/personnel/api/v1/departments/1/blob")
				.file(multipartFile)
				.header(HttpHeaders.AUTHORIZATION, token))
				.andExpect(status().isCreated());
	}
	
	@Test
	public void _aaaa() throws Exception {		
		MockMultipartFile multipartFile = _createMultipartFile("tutorial.pdf");
		mockMvc.perform(fileUpload("/personnel/api/v1/departments/1/blob")
				.file(multipartFile)
				.header(HttpHeaders.AUTHORIZATION, token))
				.andExpect(status().isCreated());
	}	
	
	private MockMultipartFile _createMultipartFile(String fileName) {
		try {		
			File file = new File(getClass().getClassLoader().getResource("scans/" + fileName).getFile());			
			return new MockMultipartFile("file", fileName, null, Files.readAllBytes(Paths.get(file.getAbsolutePath())));
		} catch (IOException e) {
			return null;
		}
	}
}
