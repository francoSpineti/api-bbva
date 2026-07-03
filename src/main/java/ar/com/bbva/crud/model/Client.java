package ar.com.bbva.crud.model;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "clients")
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer dni;

    private String name;

    @Column(name = "last_name")
    private String lastName;

    private String street;

    @Column(name = "street_number")
    private Integer streetNumber;

    @Column(name = "zip_code")
    private Integer zipCode;

    private String phone;

    @Column(name = "cell_phone")
    private String cellPhone;

    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ClientProduct> clientProducts = new ArrayList<>();

    public Client() {
    }

    public Client(Long id, Integer dni, String name, String lastName, String street, Integer streetNumber, Integer zipCode, String phone, String cellPhone) {
        this.id = id;
        this.dni = dni;
        this.name = name;
        this.lastName = lastName;
        this.street = street;
        this.streetNumber = streetNumber;
        this.zipCode = zipCode;
        this.phone = phone;
        this.cellPhone = cellPhone;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getDni() {
        return dni;
    }

    public void setDni(Integer dni) {
        this.dni = dni;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public Integer getStreetNumber() {
        return streetNumber;
    }

    public void setStreetNumber(Integer streetNumber) {
        this.streetNumber = streetNumber;
    }

    public Integer getZipCode() {
        return zipCode;
    }

    public void setZipCode(Integer zipCode) {
        this.zipCode = zipCode;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCellPhone() {
        return cellPhone;
    }

    public void setCellPhone(String cellPhone) {
        this.cellPhone = cellPhone;
    }

    public List<ClientProduct> getClientProducts() {
        return clientProducts;
    }

    public void setClientProducts(List<ClientProduct> clientProducts) {
        this.clientProducts = clientProducts;
    }
}