package midtermjava.application.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import midtermjava.application.entity.ShoesCart;

import java.util.List;

@Entity
@Table(name = "shoes_cart")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString

@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class ShoesCart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "status")
    private Integer status;
    @Column(name = "price")
    private Long price;

    @OneToMany(mappedBy = "shoesCart",cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<ShoesDetail> shoesDetails;

    public void removeCartDetail(ShoesDetail detail) {
        shoesDetails.remove(detail);
        detail.setShoesCart(null);
    }


}
