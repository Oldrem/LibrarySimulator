package server.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import server.repositories.UserRepository;
import server.model.User;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class UserDetailsProvider implements UserDetailsService
{
    private UserRepository userRepository;

    public UserDetailsProvider(UserRepository users)
    {
        this.userRepository = users;
    }

    @Transactional
    public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException{
        if(!userRepository.existsById(name)) throw new UsernameNotFoundException("Username not found");
        User user = userRepository.getOne(name);
        //Set<GrantedAuthority> roles = new HashSet<>();
        //roles.add(new SimpleGrantedAuthority("USER"));
        List<SimpleGrantedAuthority> authorities = Arrays.asList(new SimpleGrantedAuthority("user"));
        return new org.springframework.security.core.userdetails.User(user.getUsername(),
                user.getPassword(),
                authorities);
    }

    public User findUserByName(String name) {
        Optional<User> userFromDb = userRepository.findById(name);
        return userFromDb.orElse(new User());
    }

    public List<User> allUsers() {
        return userRepository.findAll();
    }

    public boolean saveUser(User user) {
        Optional<User> optionalUser = userRepository.findById(user.getUsername());
        if (optionalUser.isPresent()){
            return false;
        }

        userRepository.save(user);
        return true;
    }

}