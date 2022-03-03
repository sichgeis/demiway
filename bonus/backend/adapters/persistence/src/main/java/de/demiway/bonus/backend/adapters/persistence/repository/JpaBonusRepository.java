package de.demiway.bonus.backend.adapters.persistence.repository;


import de.demiway.bonus.backend.domain.bonus.Bonus;
import de.demiway.bonus.backend.domain.bonus.BonusRepository;
import org.hibernate.SessionFactory;

import javax.persistence.EntityNotFoundException;
import java.util.List;

public class JpaBonusRepository extends AbstractRepository<Bonus> implements BonusRepository {

    public JpaBonusRepository(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    public Bonus findById(Long id) {
        var bonus = currentSession().find(Bonus.class, id);

        if (bonus == null) {
            throw new EntityNotFoundException("Bonus does not exist for id: " + id);
        }

        return bonus;
    }

    @Override
    public Bonus create(Bonus bonus) {
        currentSession().persist(bonus);
        return bonus;
    }

    @Override
    public Bonus update(Bonus bonus) {
        currentSession().update(bonus);
        return bonus;
    }

    @Override
    public void delete(Long id) {
        currentSession().remove(this.findById(id));
    }

    @Override
    public List<Bonus> findAll() {
        var query = getCriteriaBuilder().createQuery(Bonus.class);
        query.from(Bonus.class);
        return currentSession().createQuery(query).getResultList();
    }
}
