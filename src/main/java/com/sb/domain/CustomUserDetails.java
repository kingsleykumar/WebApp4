package com.sb.domain;

import com.sb.services.utils.Constants;
import com.sb.services.utils.CryptoUtils;
import lombok.Getter;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Arrays;
import java.util.Collection;
@Getter
@ToString
public class CustomUserDetails implements UserDetails {

    private String username;
    private String userId;
    private String password;
    private String dateFormat;
    private String defaultPageAfterLogin;
    private String defaultTimeRangeForTxView;

    public CustomUserDetails(final User user) {
        this.username = user.getUsername();
        this.userId = user.getUserid();
        this.password = CryptoUtils.getOriginalPwFromEncrypted(Constants.SIGN_UP_KEY, user.getPassword());
        this.dateFormat = user.getDateformat();
        this.defaultPageAfterLogin = user.getDefaultPageAfterLogin();
        this.defaultTimeRangeForTxView = user.getDefaultTimeRangeForTxView();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        return Arrays.asList(new SimpleGrantedAuthority("ROLE_USER"), new SimpleGrantedAuthority("ROLE_ADMIN"));

//        return getRoles()
//                .stream()
//                .map(role -> new SimpleGrantedAuthority("ROLE_" + role.getRole()))
//                .collect(Collectors.toList());
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
}
