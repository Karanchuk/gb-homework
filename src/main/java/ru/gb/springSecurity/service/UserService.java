package ru.gb.springSecurity.service;

import ru.gb.springSecurity.dto.UserDto;
import ru.gb.springSecurity.model.Role;
import ru.gb.springSecurity.model.User;
import ru.gb.springSecurity.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;

    public User findByUsername(String username) {
        return userRepository.findByUsername(username).orElseThrow(() -> new RuntimeException("unable to fing user by username: " + username));
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = findByUsername(username);
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), mapRolesToAuthorities(user.getRoles()));
    }

    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles) {
        return roles.stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
    }

    public UserDto incrementScore(String username) {
        User user = findByUsername(username);
        userRepository.incrementScore(username);
        return UserDto.valueOf(user);
    }

    public UserDto decrementScore(String username) {
        User user = findByUsername(username);
        userRepository.decrementScore(username);
        return UserDto.valueOf(user);
    }

    public Integer getScore(Object username, Object id) {
        User user = null;
        if (username instanceof String)
            user = findByUsername((String) username);
        else if (id instanceof Integer)
            user = userRepository.findById((Integer) id).orElseThrow(() -> new RuntimeException("unable to fing user by id: " + id));
        return user.getScore();
    }
}