package com.bank.rest.repo;

import java.util.Optional;

import com.bank.rest.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author Jigyasu Garg
 * @since 20 02 23
 */

public interface UserDetailRepository extends JpaRepository<User,Integer> {

    Optional<User> findByUsername(String name);

	boolean existsByUsername(String name);

}
