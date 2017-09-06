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
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.MOCK)
@AutoConfigureMockMvc
@FixMethodOrder(org.junit.runners.MethodSorters.NAME_ASCENDING)
public class DepartmentScanUnitTest {

	@Autowired
	private MockMvc mockMvc;

	@Test
	public void _a() throws Exception {		
		MockMultipartFile multipartFile = _createMultipartFile("alpha.jpg");
		mockMvc.perform(fileUpload("/personnel/api/v1/departments/1/blob").file(multipartFile))
				.andExpect(status().isCreated());
	}
	
	@Test
	public void _aa() throws Exception {		
		MockMultipartFile multipartFile = _createMultipartFile("beta.jpg");
		mockMvc.perform(fileUpload("/personnel/api/v1/departments/1/blob").file(multipartFile))
				.andExpect(status().isCreated());
	}	
	
	@Test
	public void _aaa() throws Exception {		
		MockMultipartFile multipartFile = _createMultipartFile("gamma.jpg");
		mockMvc.perform(fileUpload("/personnel/api/v1/departments/1/blob").file(multipartFile))
				.andExpect(status().isCreated());
	}
	
	@Test
	public void _aaaa() throws Exception {		
		MockMultipartFile multipartFile = _createMultipartFile("tutorial.pdf");
		mockMvc.perform(fileUpload("/personnel/api/v1/departments/1/blob").file(multipartFile))
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
