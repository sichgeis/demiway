package de.demiway.bonus.backend.domain.bonus;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

@Entity
@Table(name = "tbl_bonus")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Bonus implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotEmpty
    @Column(name = "bonus_code")
    private String bonusCode;

    @JsonProperty public Long getId() {
        return id;
    }

    @JsonProperty public String getBonusCode() {
        return bonusCode;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setBonusCode(String bonusCode) {
        this.bonusCode = bonusCode;
    }
}
