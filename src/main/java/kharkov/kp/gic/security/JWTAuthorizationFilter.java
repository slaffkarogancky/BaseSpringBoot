package kharkov.kp.gic.security;

import static kharkov.kp.gic.security.SecurityConstants.HEADER_STRING;
import static kharkov.kp.gic.security.SecurityConstants.SECRET;
import static kharkov.kp.gic.security.SecurityConstants.TOKEN_PREFIX;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import io.jsonwebtoken.Jwts;

public class JWTAuthorizationFilter extends BasicAuthenticationFilter {

	public JWTAuthorizationFilter(AuthenticationManager authManager) {
		super(authManager);		
	}	

	@Autowired
	private UserDetailsService userDetailsService;
	
	@Override
	protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain chain)
			throws IOException, ServletException {
		String header = req.getHeader(HEADER_STRING);
		if (header == null || !header.startsWith(TOKEN_PREFIX)) {
			chain.doFilter(req, res);
			return;
		}
		
		 if(userDetailsService==null){
	            ServletContext servletContext = req.getServletContext();
	            WebApplicationContext webApplicationContext = WebApplicationContextUtils.getWebApplicationContext(servletContext);
	            userDetailsService = webApplicationContext.getBean(UserDetailsService.class);
	    }
		
		
		UsernamePasswordAuthenticationToken authentication = getAuthentication(req);
		SecurityContextHolder.getContext().setAuthentication(authentication);
		chain.doFilter(req, res);
	}

	private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request) {
		String token = request.getHeader(HEADER_STRING);
		if (token != null) {
			// парсим токен
			String username = Jwts.parser()
							  .setSigningKey(SECRET)
							  .parseClaimsJws(token.replace(TOKEN_PREFIX, ""))
							  .getBody()
							  .getSubject();

			if (username != null) {
				UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);
				if (userDetails != null) {
					return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
				}				
				//return new UsernamePasswordAuthenticationToken(username, null, new ArrayList<>());
			}
			return null;
		}
		return null;
	}
}
