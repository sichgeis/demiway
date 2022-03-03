package de.demiway.bonus.backend.bff.resources;

import de.demiway.bonus.backend.domain.bonus.Bonus;
import de.demiway.bonus.backend.domain.bonus.BonusRepository;
import io.dropwizard.hibernate.UnitOfWork;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("bonuses")
@Produces(MediaType.APPLICATION_JSON)
public class BonusResource {

    private BonusRepository bonusRepository;

    public BonusResource(BonusRepository bonusRepository) {
        this.bonusRepository = bonusRepository;
    }

    @POST
    @UnitOfWork
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Bonus createBonus(@NotNull @Valid final Bonus bonus) {
        return bonusRepository.create(bonus);
    }

    @GET
    @UnitOfWork
    @Produces(MediaType.APPLICATION_JSON)
    public List<Bonus> getBonuses() {
        return bonusRepository.findAll();
    }

    @PUT
    @UnitOfWork
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Bonus updateBonus(@NotNull @Valid final Bonus bonus) {
        return bonusRepository.update(bonus);
    }

    @DELETE
    @UnitOfWork
    @Path("/{id}")
    public void deleteBonus(@PathParam("id") Long id) {
        bonusRepository.delete(id);
    }
}
