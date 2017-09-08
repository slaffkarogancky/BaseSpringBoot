package kharkov.kp.gic.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import kharkov.kp.gic.domain.Userok;
import kharkov.kp.gic.repository.UserokRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
	
    private UserokRepository userokRepository;

    public UserDetailsServiceImpl(UserokRepository userokRepository) {
        this.userokRepository = userokRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Userok user = userokRepository.findByUserName(username);
        if (user == null) {
            throw new UsernameNotFoundException(username);
        }
        List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
        if (user.isAdmin()) {
			authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
		}
        if (user.isOperator()) {
			authorities.add(new SimpleGrantedAuthority("ROLE_OPERATOR"));
		}
        return new User(user.getUserName(), user.getPassword(), authorities);
    }
}