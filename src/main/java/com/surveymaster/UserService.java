package com.surveymaster;

import com.surveymaster.entity.User;
import com.surveymaster.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User newUser = userRepository.findByUsername(username);
        if (Objects.isNull(newUser)) {
            throw new UsernameNotFoundException("User doesn't exists in the database..");
        }
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("USER"));

        UserDetails userDetails = new org.springframework.security.core.userdetails.User(newUser.getUsername(),
                newUser.getPassword(), true, true, true, true, authorities);

        return userDetails;
    }
}
