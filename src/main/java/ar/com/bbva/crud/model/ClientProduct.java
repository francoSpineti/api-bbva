package ar.com.bbva.crud.model;

import jakarta.persistence.*;

@Entity
@Table(name = "client_products")
public class ClientProduct {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client_id", nullable = false)
    private Client client;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    public ClientProduct() {
    }

    public ClientProduct(Client client, Product product) {
        this.client = client;
        this.product = product;
    }

    public ClientProduct(Long id, Client client, Product product) {
        this.id = id;
        this.client = client;
        this.product = product;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
}
