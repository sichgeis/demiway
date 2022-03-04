package de.demiway.bonus.backend.domain.bonus;

import java.util.List;

public interface BonusRepository {
    Bonus create(Bonus bonus);

    Bonus findById(Long id);

    Bonus update(Bonus bonus);

    void delete(Long id);

    List<Bonus> findAll();
}
