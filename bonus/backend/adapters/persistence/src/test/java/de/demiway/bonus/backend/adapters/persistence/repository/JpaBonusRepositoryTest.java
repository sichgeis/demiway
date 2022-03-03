package de.demiway.bonus.backend.adapters.persistence.repository;

import de.demiway.bonus.backend.domain.bonus.Bonus;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


class JpaBonusRepositoryTest {

    private static final SessionFactory SESSION_FACTORY = mock(SessionFactory.class);
    private static final Session SESSION = mock(Session.class);

    private JpaBonusRepository jpaBonusRepository = new JpaBonusRepository(SESSION_FACTORY);

    @Test
    void createBonus() {
        when(SESSION_FACTORY.getCurrentSession()).thenReturn(SESSION);

        var bonus = new Bonus();
        jpaBonusRepository.create(bonus);

        verify(SESSION).persist(bonus);
    }

    @Test
    void upddateBonus() {
        when(SESSION_FACTORY.getCurrentSession()).thenReturn(SESSION);

        var bonus = new Bonus();
        jpaBonusRepository.update(bonus);

        verify(SESSION).update(bonus);
    }

    @Test
    void getBonus() {
        var bonus = new Bonus();
        Long id = 42L;

        when(SESSION_FACTORY.getCurrentSession()).thenReturn(SESSION);
        when(SESSION.find(Bonus.class, id)).thenReturn(bonus);

        var result = jpaBonusRepository.findById(id);

        assertThat(result).isEqualTo(bonus);
    }
}
