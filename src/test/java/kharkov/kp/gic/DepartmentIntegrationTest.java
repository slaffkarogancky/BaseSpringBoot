package kharkov.kp.gic;

import static org.junit.Assert.*;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.jdbc.JdbcTestUtils;

import kharkov.kp.gic.domain.Department;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@FixMethodOrder(org.junit.runners.MethodSorters.NAME_ASCENDING)
public class DepartmentIntegrationTest {

	@Autowired
	private TestRestTemplate restTemplate;
	
	@Autowired
	private JdbcTemplate jdbcTemplate;

	@LocalServerPort 
	int port;	
	
	// проверяем, что сервер пингуется
	@Test
	public void _a() throws Exception {
		String pong = restTemplate.getForObject("/personnel/api/v1/ping", String.class);
		assertTrue("Ожидалось PONG, но прибыло " + pong, pong.equals("PONG"));
	}
	
	// еще один способ проверить, что сервер пингуется
	@Test
	public void _aa() throws Exception {
		ResponseEntity<String> response = restTemplate.getForEntity("/personnel/api/v1/ping", String.class);
		String pong = response.getBody();
		assertTrue("Ожидалось PONG, но прибыло " + pong, pong.equals("PONG"));
	}	
	
	// проверяем, что информация о департаменте возвращается корректно
	@Test
	public void _b() throws Exception {
		ResponseEntity<Department> response = restTemplate.getForEntity("/personnel/api/v1/departments/1", Department.class);
		Department d = response.getBody();
		assertTrue("", d.getId() == 1);
		assertEquals("", d.getDepartmentName(), "Транспортный цех");		
	}	
	
	// проверяем, что возвращается ошибка если запрашиваем несуществующий департамент
	@Test
	public void _c() throws Exception {
		ResponseEntity<Department> response = restTemplate.getForEntity("/personnel/api/v1/departments/1000000", Department.class);
		assertTrue("", response.getStatusCode() == HttpStatus.NOT_FOUND);
	}	
	
	// проверяем, что сервер правильно возвращает отсортированный список департаментов
	@Test
	public void _d() throws Exception {		
		Department[] departaments = restTemplate.getForObject("/personnel/api/v1/departments", Department[].class);
		assertTrue(departaments.length == 5);
		assertTrue((departaments[0]).getId() == 4);
		assertTrue((departaments[0]).getDepartmentName().equals("Бухгалтерия"));
    	assertTrue((departaments[1]).getId() == 5);
    	assertTrue((departaments[1]).getDepartmentName().equals("Отдел разработки ПО"));
	}	
	
	// проверяем, что департамент добавляется успешно
	@Test
	public void _e() throws Exception {
		int before = JdbcTestUtils.countRowsInTable(jdbcTemplate, "gr_department");
		Department d = Department.builder().departmentName("Уборщицы").build();
		HttpHeaders headers = new HttpHeaders();
	    headers.setContentType(MediaType.APPLICATION_JSON);
	    HttpEntity<Department> request = new HttpEntity<Department>(d, headers);		
		ResponseEntity<?> response = restTemplate.postForEntity("/personnel/api/v1/departments", request, Void.class);
		assertTrue(response.getStatusCode() == HttpStatus.CREATED);
		int after = JdbcTestUtils.countRowsInTable(jdbcTemplate, "gr_department");
		assertTrue("Количество департаментов должно увеличиться на единицу", after - before == 1);
	}
	
	// проверяем, что нельзя добавить департамент с пустым именем
	@Test
	public void _f() throws Exception {
		int before = JdbcTestUtils.countRowsInTable(jdbcTemplate, "gr_department");
		Department d = Department.builder().departmentName("").build();
		HttpHeaders headers = new HttpHeaders();
	    headers.setContentType(MediaType.APPLICATION_JSON);
	    HttpEntity<Department> request = new HttpEntity<Department>(d, headers);		
		ResponseEntity<?> response = restTemplate.postForEntity("/personnel/api/v1/departments", request, Void.class);
		assertTrue(response.getStatusCode() == HttpStatus.BAD_REQUEST);
		int after = JdbcTestUtils.countRowsInTable(jdbcTemplate, "gr_department");
		assertTrue("Количество департаментов должно остаться неизменным", after - before == 0);
	}
	
	// проверяем, что нельзя добавить департамент с уже существующим именем
	@Test
	public void _g() throws Exception {
		Department d = Department.builder().departmentName("Отдел тестирования ПО").build();
		HttpHeaders headers = new HttpHeaders();
	    headers.setContentType(MediaType.APPLICATION_JSON);
	    HttpEntity<Department> request = new HttpEntity<Department>(d, headers);		
		ResponseEntity<?> response = restTemplate.postForEntity("/personnel/api/v1/departments", request, Void.class);
		assertTrue(response.getStatusCode() == HttpStatus.BAD_REQUEST);
	}
	
	// проверяем, что имя департамент обновлено успешно
	@Test
	public void _h() throws Exception {
		int before = JdbcTestUtils.countRowsInTable(jdbcTemplate, "gr_department");
		Department d = Department.builder().departmentName("Транспортный цех переименовали!!!").build();
		HttpHeaders headers = new HttpHeaders();
	    headers.setContentType(MediaType.APPLICATION_JSON);
	    HttpEntity<Department> request = new HttpEntity<Department>(d, headers);
		ResponseEntity<?> response = restTemplate.exchange("/personnel/api/v1/departments/1", HttpMethod.PUT,
				request, Void.class);
		assertTrue(response.getStatusCode() == HttpStatus.OK);
		int after = JdbcTestUtils.countRowsInTable(jdbcTemplate, "gr_department");
		assertTrue("Количество департаментов должно остаться неизменным", after - before == 0);
	}
	
	// проверяем, что нельзя обновить имя департамента на уже существующее
	@Test
	public void _i() throws Exception {
		Department d = Department.builder().departmentName("Бухгалтерия").build();
		HttpHeaders headers = new HttpHeaders();
	    headers.setContentType(MediaType.APPLICATION_JSON);
	    HttpEntity<Department> request = new HttpEntity<Department>(d, headers);
		ResponseEntity<?> response = restTemplate.exchange("/personnel/api/v1/departments/1", HttpMethod.PUT,
				request, Void.class);
		assertTrue(response.getStatusCode() == HttpStatus.BAD_REQUEST);
	}
	
	// проверяем, что департамент удаляется успешно
	@Test
	public void _j() throws Exception {
		int before = JdbcTestUtils.countRowsInTable(jdbcTemplate, "gr_department");
		restTemplate.delete("/personnel/api/v1/departments/1");
		int after = JdbcTestUtils.countRowsInTable(jdbcTemplate, "gr_department");
		assertTrue("Количество департаментов должно уменьшиться на единицу", before - after == 1);
	}
}
