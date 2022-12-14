package game.items.weapons;

import game.data.Command;
import game.data.K;
import game.data.PrintCollection;
import game.entities.Entity;
import game.interfaces.Printable;

/**
 * This class represents an explosive weapon, a grenade used by an entity during
 * battle to cause damage on the attacked entity.
 *
 * @author Toni Sihvola
 * @author Ludwig Ahnqvist
 */
public class Grenade extends Weapon implements K, Printable
{
    /**
     * Damage caused by the grenade.
     */
    private final int damage;

    /**
     * @param name Name of the grenade.
     * @param itemDesc Description of the grenade.
     * @param instantlyAffectsOnPickup {@code true} if the grenade has an
     *        instant effect when picked up, {@code false} otherwise.
     * @param useCommand Command to use the grenade.
     * @param usableDuringBattle {@code true} if the grenade is usable during
     *        battle, {@code false} if not.
     * @param damage Damage caused by the grenade.
     */
    public Grenade(String name,
                   String itemDesc,
                   boolean instantlyAffectsOnPickup,
                   Character useCommand,
                   boolean usableDuringBattle,
                   int damage)
    {
        super(name,
                itemDesc,
                instantlyAffectsOnPickup,
                useCommand,
                usableDuringBattle);
        this.damage = damage;
    }

    public Grenade()
    {
        this("En granat",
                String.format("En explosiv som gör %d DMG", GRENADE_DMG),
                false,
                Command.USE_GRENADE.commandValue,
                true, GRENADE_DMG);
    }

    /**
     * The effect of the grenade applied on the provided entity. Causes damage
     * on the provided entity and prints out a message informing about the
     * amount of damage caused and the updated HP of the entity after the effect
     * has been applied.
     *
     * @param entity Entity on which to apply the item effect.
     */
    @Override public void applyEffect(Entity entity)
    {
        entity.setHealthPoints(entity.getHealthPoints() - this.damage);
        System.out.printf("%s gör %d DMG mot %s - (%s HP: %d)\n",
                super.name,
                this.damage,
                entity.getName(),
                entity.getName(),
                entity.getHealthPoints());

    }

    /**
     * Prints an ASCII grenade.
     */
    @Override public void printObject()
    {
        PrintCollection.printGrenade();
    }
}