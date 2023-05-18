package com.platform.parkingsystem.api.model;
import com.platform.parkingsystem.api.token.Token;
import com.platform.parkingsystem.api.user.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;


import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

@Builder


@Document(collection = "users")

@AllArgsConstructor
public class User  implements UserDetails {
    @Id
    private String id;


    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String licensePlate;
    private String password;



    @DBRef(lazy = true)
    private Role roles;
    @DBRef(lazy = true)
    private List<Token> tokens;

    @DBRef(lazy = true)
    private List<ParkingLot> favourites;

    @DBRef(lazy = true)
    private List<Reservation> reservations;
    // Constructor
    public User(String firstName, String lastName, String email, String phone, String licensePlate, String password) {

        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phone = phone;
        this.licensePlate = licensePlate;
        this.password = password;
    }

    public User(){

    }
    // Getters and Setters


    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getLicensePlate() {
        return licensePlate;
    }

    public void setLicensePlate(String licensePlate) {
        this.licensePlate = licensePlate;
    }



    public void setPassword(String password) {
        this.password = password;
    }


    public String getPassword() {
        return password;
    }
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {return roles.getAuthorities();
    }

    public List<Reservation> getReservations() {
        return reservations;
    }

    public void setReservations(List<Reservation> reservations) {
        this.reservations = reservations;
    }



    @Override
    public String getUsername() {
        return email;
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

    public List<ParkingLot> getFavourites() {
        return favourites;
    }



}