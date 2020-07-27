package ge.rrs; 

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.config.http.FormLoginBeanDefinitionParser;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.FormLoginRequestBuilder;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.unauthenticated;

@SpringBootTest
@AutoConfigureMockMvc
public class AuthTest {
	@Autowired
	private MockMvc mockMvc;

	@Test
	public void testValidLogin() throws Exception {
		FormLoginRequestBuilder login = formLogin()
			.user("Human")
			.password("password");

		mockMvc.perform(login)
			.andExpect(authenticated().withUsername("Human"));

		login = formLogin()
			.user("Human 1")
			.password("password");
		mockMvc.perform(login)
			.andExpect(authenticated().withUsername("Human 1"));
	}

	@Test
	public void testInvalidLogin() throws Exception {
		FormLoginRequestBuilder login = formLogin()
			.user("NotHuman")
			.password("notPassword");

		mockMvc.perform(login)
			.andExpect(unauthenticated());
	}
}
