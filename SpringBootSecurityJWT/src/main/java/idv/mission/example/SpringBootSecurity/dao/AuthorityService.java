package idv.mission.example.SpringBootSecurity.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthorityService {

    @Autowired
    private AuthorityRepository repository;

    public Authority findByUsername(String username) {
        return repository.findByUsername(username);
    }

}
