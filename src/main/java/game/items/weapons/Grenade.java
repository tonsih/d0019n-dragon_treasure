package game.items.weapons;

import game.data.Command;
import game.data.K;
import game.data.PrintCollection;
import game.entities.Entity;
import game.interfaces.Printable;

public class Grenade extends Weapon implements K, Printable
{
    private final int damage;

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
                String.format("En explosiv som gör %d DMG", BOMB_DMG),
                false,
                Command.USE_GRENADE.commandValue,
                true,
                BOMB_DMG);
    }


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

    @Override public void printObject()
    {
        PrintCollection.printGrenade();
    }
}
