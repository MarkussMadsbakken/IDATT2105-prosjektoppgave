package edu.ntnu.stud.service;

import edu.ntnu.stud.model.User;
import edu.ntnu.stud.model.UserPrincipal;
import edu.ntnu.stud.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * Service class that implements UserDetailsService to load user-specific data.
 */
@Service
public class MyUserDetailsService implements UserDetailsService {

  @Autowired
  private UserRepo userRepo;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    User user = userRepo.getUserByUsername(username);
    if (user == null) {
      System.out.println("User Not Found");
      throw new UsernameNotFoundException("user not found");
    }

    return new UserPrincipal(user);
  }
}