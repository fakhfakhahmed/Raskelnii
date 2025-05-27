package tn.ecnam.authorization.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tn.ecnam.authorization.entity.PasswordResetToken;


@Repository
public interface PasswordTokenRepository extends JpaRepository<PasswordResetToken ,Long> {

    PasswordResetToken findByToken(Integer  token)  ;
}
