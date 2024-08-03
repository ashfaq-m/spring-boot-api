package springbootdemo.springbootdemo.customer;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "customer")
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "customerid", nullable = false)
    private Integer id;

    @Column(name = "customernumber", nullable = false, length = 30)
    private String customernumber;

    @Column(name = "lastname", nullable = false, length = 30)
    private String lastname;

    @Column(name = "firstname", nullable = false, length = 30)
    private String firstname;

    @ColumnDefault("587315")
    @Column(name = "areacode")
    private Integer areacode;

    @Column(name = "address", length = 50)
    private String address;

    @ColumnDefault("'INDIA'")
    @Column(name = "country", length = 50)
    private String country;

    @Column(name = "salary")
    private Integer salary;

}