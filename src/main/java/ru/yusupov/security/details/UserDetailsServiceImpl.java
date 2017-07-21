package ru.yusupov.security.details;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import ru.yusupov.dao.UsersDao;
import ru.yusupov.models.User;

import java.util.ArrayList;
import java.util.List;

@Component
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UsersDao usersDao;

    public UserDetails loadUserByUsername(String token) throws UsernameNotFoundException {
        if (usersDao.isExistToken(token)) {
            User user = usersDao.findByToken(token);
            List<GrantedAuthority> authorities = createGrantedAuthorities();

            return new UserDetailsImpl(user.getLogin(), user.getHashPassword(), authorities);
        } else {
            return null;
        }
    }

    public static List<GrantedAuthority> createGrantedAuthorities() {
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("USER"));
        return authorities;
    }
}
