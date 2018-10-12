package idv.mission.example.SpringBootSecurity.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {

    @Query("SELECT account FROM Account account WHERE account.username=(:username)")
    public Account findByUsername(@Param("username") String username);
}
