package idv.mission.example.SpringBootSecurity.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountService {

    @Autowired
    private AccountRepository repository;

    public Account findByUsername(String username) {
        return repository.findByUsername(username);
    }

}
