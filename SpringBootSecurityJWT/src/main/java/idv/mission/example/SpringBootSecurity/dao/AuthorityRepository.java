package idv.mission.example.SpringBootSecurity.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorityRepository extends JpaRepository<Authority, Long> {

    @Query("SELECT authority FROM Authority authority WHERE authority.username=(:username)")
    public Authority findByUsername(@Param("username") String username);
}
