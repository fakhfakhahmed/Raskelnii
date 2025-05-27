package tn.ecnam.authorization.repository;

import tn.ecnam.authorization.entity.AuthUser;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;
import java.util.Optional;

public interface UserRepository extends JpaRepository<AuthUser, Long> {

    Optional<AuthUser> findByUserName(String username);

    Optional<AuthUser> findByUserNameOrEmail(String username, String email);

    Optional<AuthUser> findByEmail(String email) ;

    @Transactional
    void  deleteByEmail(String email);
}
