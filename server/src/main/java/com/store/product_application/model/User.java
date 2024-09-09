package com.store.product_application.model;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.store.product_application.common.enums.UserRole;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/*
 * @author Pukhraj Singh
 * @version 1.0
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "users")
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private long userId;

    private String firstname;
    private String lastname;
    private String email;

    @Enumerated(EnumType.STRING)
    private UserRole role;

    private String password;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private Date createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private Date updatedAt;

    // User Detail Methods implemented here ...

    /**
     * This method provide authority to the user based on his role.
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    /**
     * This method is used to specify that we are using email as our username.
     */
    @Override
    public String getUsername() {
        return email;
    }

    // overriding method of UserDetails too.
    @Override
    public String getPassword() {
        return password;
    }

    /**
     * This method provide info about whether the account expired or not.
     */
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    /**
     * This method provide info about whether the account locked or not.
     */
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    /**
     * This method provide info about whether the credentials not expired.
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    /**
     * This method provide info about whether the account enabled or not.
     */
    @Override
    public boolean isEnabled() {
        return true;
    }

}
