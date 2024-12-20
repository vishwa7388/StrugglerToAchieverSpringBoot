package struggler.to.achiever.model;

import jakarta.persistence.*;
import jakarta.persistence.JoinColumn;

import java.util.Collection;

@Entity
@Table(name = "user")
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String password;
    private String email;
    private String encrypted_password;
    private String email_verification_token;
    private Boolean email_verification_status;
    @Column(name = "userId")
    private String userId;

    @ManyToMany(cascade={CascadeType.PERSIST}, fetch = FetchType.EAGER)
    @JoinTable(name = "users_roles", joinColumns = @JoinColumn(name ="users_id", referencedColumnName="id"),
            inverseJoinColumns = @JoinColumn(name="roles_id", referencedColumnName = "id"))
    private Collection<RoleEntity> roles;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEncrypted_password() {
        return encrypted_password;
    }

    public void setEncrypted_password(String encrypted_password) {
        this.encrypted_password = encrypted_password;
    }

    public String getEmail_verification_token() {
        return email_verification_token;
    }

    public void setEmail_verification_token(String email_verification_token) {
        this.email_verification_token = email_verification_token;
    }

    public Boolean getEmail_verification_status() {
        return email_verification_status;
    }

    public void setEmail_verification_status(Boolean email_verification_status) {
        this.email_verification_status = email_verification_status;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Collection<RoleEntity> getRoles() {
        return roles;
    }

    public void setRoles(Collection<RoleEntity> roles) {
        this.roles = roles;
    }


}
