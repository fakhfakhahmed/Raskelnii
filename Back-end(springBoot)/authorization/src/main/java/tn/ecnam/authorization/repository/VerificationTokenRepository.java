package tn.ecnam.authorization.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.ecnam.authorization.entity.AuthUser;
import tn.ecnam.authorization.entity.VerificationToken;

public interface VerificationTokenRepository extends JpaRepository<VerificationToken, Long> {

    VerificationToken findByToken(String token);

    void deleteByToken(String token) ;

    VerificationToken findByUser(AuthUser user);
}