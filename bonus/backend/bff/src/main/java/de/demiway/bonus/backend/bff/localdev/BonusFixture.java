package de.demiway.bonus.backend.bff.localdev;

import de.demiway.bonus.backend.domain.bonus.Bonus;
import io.dropwizard.lifecycle.Managed;
import org.hibernate.SessionFactory;

public class BonusFixture implements Managed {
    private SessionFactory sessionFactory;

    public BonusFixture(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public void run() {

        var session = this.sessionFactory.openSession();
        session.beginTransaction();

        var bonus = Bonus.builder().bonusCode("SPAREN").build();

        session.persist(bonus);
        session.getTransaction().commit();
        session.close();
    }

    @Override public void start() {
        this.run();
    }

    @Override public void stop() {}
}
