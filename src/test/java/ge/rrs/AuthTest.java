package ge.rrs;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.security.config.http.FormLoginBeanDefinitionParser;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.FormLoginRequestBuilder;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.unauthenticated;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.*;

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
	public void testRegister() throws Exception {
		String TOKEN_ATTR_NAME = "org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository.CSRF_TOKEN";

		HttpSessionCsrfTokenRepository httpSessionCsrfTokenRepository = new HttpSessionCsrfTokenRepository();
		CsrfToken csrfToken = httpSessionCsrfTokenRepository.generateToken(new MockHttpServletRequest());

		mockMvc.perform(
			post("/register")
			.sessionAttr(TOKEN_ATTR_NAME, csrfToken)
			.param(csrfToken.getParameterName(), csrfToken.getToken())
			.param("username", "testUsername")
			.param("password", "testPassword")
			.param("email", "testEmail")
			.contentType(MediaType.APPLICATION_JSON))
				.andReturn();

		// Log in to the account
		FormLoginRequestBuilder login = formLogin()
			.user("testUsername")
			.password("testPassword");

		mockMvc.perform(login)
			.andExpect(authenticated().withUsername("testUsername"));
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
