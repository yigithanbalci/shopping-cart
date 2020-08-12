package tr.com.yigithanbalci.shoppingcartservice.auth;

import java.util.Arrays;
import java.util.Collection;
import java.util.Objects;
import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import tr.com.yigithanbalci.shoppingcartservice.model.User;

@AllArgsConstructor
public class UserDetailsImpl implements UserDetails {

  private User user;

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    SimpleGrantedAuthority authority = new SimpleGrantedAuthority(user.getRole());
    return Arrays.asList(authority);
  }

  @Override
  public String getPassword() {
    return user.getPassword();
  }

  @Override
  public String getUsername() {
    return user.getUsername();
  }

  public Long getUserId() {
    return user.getId();
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return true;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    UserDetailsImpl that = (UserDetailsImpl) o;
    return user.equals(that.user);
  }

  @Override
  public int hashCode() {
    return Objects.hash(user);
  }

  @Override
  public String toString() {
    return "UserDetailsImpl{" +
        "user=" + user +
        '}';
  }
}