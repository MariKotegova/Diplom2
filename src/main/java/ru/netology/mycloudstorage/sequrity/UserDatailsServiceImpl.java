package ru.netology.mycloudstorage.sequrity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.netology.mycloudstorage.modele.User;
import ru.netology.mycloudstorage.repositopy.UserRepository;

@Service("userDatailsServiceImpl")
public class UserDatailsServiceImpl implements UserDetailsService {
    private final UserRepository userRepository;

    @Autowired
    public UserDatailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        User user = userRepository.findByLogin(login).orElseThrow(() ->
            new UsernameNotFoundException("юзер не действителен"));
        return SequrityUser.fromUser(user);
    }
}
