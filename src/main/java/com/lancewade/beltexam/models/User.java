package com.lancewade.beltexam.models;

import com.lancewade.beltexam.models.Idea;
import org.hibernate.validator.constraints.UniqueElements;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.List;

@Entity
@Table(name="users")
public class User {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
    @NotBlank(message = "Name must not be blank")
    private String name;
    @NotBlank(message = "Email must not be blank")
    @Email(message = "Email must be valid")
    private String email;
    @Size(min = 8, message = "Password must be at least 8 characters")
    private String password;
    @Transient
    private String passwordConfirmation;
    @Column(updatable=false)
    private Date createdAt;
    private Date updatedAt;
    @PrePersist
    protected void onCreate(){
        this.createdAt = new Date();
    }
    @PreUpdate
    protected void onUpdate(){
        this.updatedAt = new Date();
    }

    //relationship
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "users_ideas",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "idea_id")
    )
    private List<Idea> ideasLiked;
    @OneToMany(mappedBy="creator", fetch = FetchType.LAZY)
    private List<Idea> ideasCreated;
    public List<Idea> getIdeasLiked() {
        return ideasLiked;
    }
    public void setIdeasLiked(List<Idea> ideasLiked) {
        this.ideasLiked = ideasLiked;
    }
    public List<Idea> getIdeasCreated() {
        return ideasCreated;
    }
    public void setIdeasCreated(List<Idea> ideasCreated) {
        this.ideasCreated = ideasCreated;
    }
    public User() {
    }
    public User(String name, String email, String password){
        this.name = name;
        this.email = email;
        this.password = password;
    }

    //Getters and setters
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
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
    public String getPasswordConfirmation() {
        return passwordConfirmation;
    }
    public void setPasswordConfirmation(String passwordConfirmation) {
        this.passwordConfirmation = passwordConfirmation;
    }
    public Date getCreatedAt() {
        return createdAt;
    }
    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }
    public Date getUpdatedAt() {
        return updatedAt;
    }
    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }
}
