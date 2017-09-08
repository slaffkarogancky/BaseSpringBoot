package kharkov.kp.gic;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.jdbc.JdbcTestUtils;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import kharkov.kp.gic.domain.Department;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.MOCK)
@AutoConfigureMockMvc
@FixMethodOrder(org.junit.runners.MethodSorters.NAME_ASCENDING)
public class DepartmentUnitTest {  
	
	@Autowired
    private MockMvc mockMvc;	
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	private JacksonTester<Department> json;
	
	@Before
	public void setup() {
		ObjectMapper objectMapper = new ObjectMapper();
		JacksonTester.initFields(this, objectMapper);
	}
	
	// тестовый токен, действителен до 7.09.2018
	private String token = "Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJiYXJpbiIsImV4cCI6MTgyMDE0NzU4MX0.eeiNrWBX7NbhAr6jBhKJuEpu9Jq3GOXt6NZaaSfZlWHXeyLV5u8S4p0G766GpDA6Q3QZQKF2DaJc-qxGoGoZkw";
	
	// проверяем, что сервер пингуется
	@Test
	public void _a() throws Exception {
		mockMvc.perform(get("/personnel/api/v1/ping")
				.header(HttpHeaders.AUTHORIZATION, token))
				.andExpect(status().isOk())
				.andExpect(content().string("PONG"));
	}
	
	// проверяем, что информация о департаменте возвращается корректно
	@Test
	public void _b() throws Exception {
		mockMvc.perform(get("/personnel/api/v1/departments/1")
				.accept(MediaType.APPLICATION_JSON_VALUE)
				.header(HttpHeaders.AUTHORIZATION, token))
				.andExpect(status().isOk())
				.andExpect(content().contentType("application/json;charset=UTF-8"))
				.andExpect(jsonPath("$.id").value(1))
				.andExpect(jsonPath("$.departmentName").value("Транспортный цех"));
	}
	
	// проверяем, что возвращается ошибка если запрашиваем несуществующий департамент
	@Test
	public void _c() throws Exception {
		mockMvc.perform(get("/personnel/api/v1/departments/100000000")
				.accept(MediaType.APPLICATION_JSON_VALUE)
				.header(HttpHeaders.AUTHORIZATION, token))
				.andExpect(status().isNotFound());
	}
	
	// проверяем, что сервер правильно возвращает отсортированный список департаментов
	@Test
	public void _d() throws Exception {
		mockMvc.perform(get("/personnel/api/v1/departments")
				.accept(MediaType.APPLICATION_JSON_VALUE)
				.header(HttpHeaders.AUTHORIZATION, token))
				.andExpect(status().isOk())
				.andExpect(content().contentType("application/json;charset=UTF-8"))
				.andExpect(jsonPath("$", hasSize(5)))
				.andExpect(jsonPath("$.[0].id").value(4))
	        	.andExpect(jsonPath("$.[0].departmentName").value("Бухгалтерия"))
	        	.andExpect(jsonPath("$.[1].id").value(5))
	        	.andExpect(jsonPath("$.[1].departmentName").value("Отдел разработки ПО"));
	}
	
	// проверяем, что департамент добавляется успешно
	@Test
	public void _e() throws Exception {
		Department d = Department.builder().departmentName("Уборщицы").build();
		String departmentContent = json.write(d).getJson();
		int before = JdbcTestUtils.countRowsInTable(jdbcTemplate, "gr_department");
		mockMvc.perform(post("/personnel/api/v1/departments")
				.content(departmentContent)
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.accept(MediaType.APPLICATION_JSON_VALUE)
				.header(HttpHeaders.AUTHORIZATION, token))
				.andExpect(status().isCreated());
		int after = JdbcTestUtils.countRowsInTable(jdbcTemplate, "gr_department");
		assertTrue("Количество департаментов должно увеличиться на единицу", after - before == 1);
	}
	
	// проверяем, что нельзя добавить департамент с пустым именем
	@Test
	public void _f() throws Exception {
		int before = JdbcTestUtils.countRowsInTable(jdbcTemplate, "gr_department");
		mockMvc.perform(post("/personnel/api/v1/departments")
				.content("{\"departmentName\": \"\"}")
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.accept(MediaType.APPLICATION_JSON_VALUE)
				.header(HttpHeaders.AUTHORIZATION, token))
				.andExpect(status().isBadRequest());
		int after = JdbcTestUtils.countRowsInTable(jdbcTemplate, "gr_department");
		assertTrue("Количество департаментов должно остаться неизменным", after - before == 0);
	}
	
	// проверяем, что нельзя добавить департамент с уже существующим именем
	@Test
	public void _g() throws Exception {
		mockMvc.perform(post("/personnel/api/v1/departments")
				.content("{\"departmentName\": \"Отдел тестирования ПО\"}")
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.accept(MediaType.APPLICATION_JSON_VALUE)
				.header(HttpHeaders.AUTHORIZATION, token))
				.andExpect(status().isBadRequest());
	}
	
	// проверяем, что имя департамент обновлено успешно
	@Test
	public void _h() throws Exception {
		int before = JdbcTestUtils.countRowsInTable(jdbcTemplate, "gr_department");
		Department d = Department.builder().departmentName("Транспортный цех переименовали!!!").build();
		String departmentContent = json.write(d).getJson();
		mockMvc.perform(put("/personnel/api/v1/departments/1")
				.content(departmentContent)
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.accept(MediaType.APPLICATION_JSON_VALUE)
				.header(HttpHeaders.AUTHORIZATION, token))
				.andExpect(status().isOk());
		int after = JdbcTestUtils.countRowsInTable(jdbcTemplate, "gr_department");
		assertTrue("Количество департаментов должно остаться неизменным", after - before == 0);
	}
	
	// проверяем, что нельзя обновить имя департамента на уже существующее
	@Test
	public void _i() throws Exception {
		Department d = Department.builder().departmentName("Бухгалтерия").build();
		String departmentContent = json.write(d).getJson();
		mockMvc.perform(put("/personnel/api/v1/departments/4")
				.content(departmentContent)
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.accept(MediaType.APPLICATION_JSON_VALUE)
				.header(HttpHeaders.AUTHORIZATION, token))
				.andExpect(status().isBadRequest());
	}
	
	// проверяем, что департамент удаляется успешно
	@Test
	public void _j() throws Exception {
		int before = JdbcTestUtils.countRowsInTable(jdbcTemplate, "gr_department");
		mockMvc.perform(delete("/personnel/api/v1/departments/1")
				.accept(MediaType.APPLICATION_JSON_VALUE)
				.header(HttpHeaders.AUTHORIZATION, token))
				.andExpect(status().isOk());
		int after = JdbcTestUtils.countRowsInTable(jdbcTemplate, "gr_department");
		assertTrue("Количество департаментов должно уменьшиться на единицу", before - after == 1);
	}

}
