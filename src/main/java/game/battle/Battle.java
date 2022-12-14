package game.battle;

import game.data.Command;
import game.data.K;
import game.data.PrintCollection;
import game.entities.Entity;
import game.entities.Player;
import game.items.Item;
import game.items.consumables.Consumable;
import utils.VisualEffectManager;
import utils.TimeManipulator;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

/**
 * This class represents a battle, where a player battles against an entity.
 *
 * @author Toni Sihvola
 * @author Ludwig Ahnqvist
 */
public class Battle
{
    /**
     * Represents the player in a battle operated by the user.
     */
    private final Player player;

    /**
     * Represents the entity who fights with the player.
     */
    private final Entity entity;

    /**
     * Inventory of usable player items during battle.
     */
    private final ArrayList<Item> battleInventory;

    /**
     * Stores a reference to a scanner which is used to record user input.
     */
    private final Scanner scanner;

    /**
     * Stores a visual effect manager which is used to clear the console.
     */
    private final VisualEffectManager visualEffectManager;

    /**
     * @param player Player in the battle.
     * @param entity Entity which fights the player.
     * @param scanner Scanner for taking user input.
     * @param visualEffectManager Used to clear the console.
     */
    public Battle(Player player,
                  Entity entity,
                  Scanner scanner,
                  VisualEffectManager visualEffectManager)
    {
        this.player = player;
        this.entity = entity;
        this.battleInventory = getBattleInventory(this.player);
        this.scanner = scanner;
        this.visualEffectManager = visualEffectManager;
    }

    /**
     * Core battle gameplay occurs inside this method.
     *
     * @return {@code true} if player wins, {@code false} otherwise.
     * @throws Exception If something goes wrong while the console is being
     *                   cleared.
     */
    public boolean doBattle() throws Exception
    {
        TimeManipulator.wait(500);
        System.out.printf("%s dyker upp\n", this.entity.getDesc());
        TimeManipulator.wait(1000);
        this.entity.printObject();
        TimeManipulator.wait(1000);

        while (true)
        {
            TimeManipulator.wait(500);
            this.doAttack(this.entity, this.player);
            TimeManipulator.wait(1000);

            if (!this.player.isAlive())
            {
                this.printWinsMessage(this.entity, this.player);
                return false;
            } else if (!this.battlePlayerMenu())
            {
                this.visualEffectManager.clearConsole();
                return false;
            }
            if (!this.entity.isAlive())
            {
                this.printWinsMessage(this.player, this.entity);
                player.addMonsterKilled();
                return true;
            }

        }
    }

    /**
     * A battle menu for the player. Provides the user with different options,
     * e.g. to use items against the enemy entity, attack the entity or to use
     * consumables.
     *
     * @return {@code true} if the player chooses to exit the game.
     *         {@code false} otherwise.
     * @throws Exception If something goes wrong while the console is being
     *                   cleared.
     */
    private boolean battlePlayerMenu() throws Exception
    {
        while (true)
        {
            System.out.println();
            printBattleWeaponOptions();
            printAttackOption();
            System.out.println();
            PrintCollection.printEscapeOption();

            PrintCollection.printConsoleMarker();
            String ansStr = this.scanner.nextLine();

            this.visualEffectManager.clearConsole();
            PrintCollection.printLinesWithPlusCorners();

            if (ansStr.isBlank()) continue;
            char ansChar = ansStr.trim().toLowerCase().charAt(0);
            if (ansChar == Command.EXIT_GAME.commandValue) return false;

            Item battleItem = getBattleItemWithCommand(ansChar);
            if (ansChar == Command.ATTACK.commandValue || battleItem != null)
            {
                this.entity.printObject();

                if (ansChar == Command.ATTACK.commandValue)
                {
                    this.doAttack(this.player, this.entity);
                } else
                {
                    this.battleInventory.remove(battleItem);
                    this.player.removeItem(battleItem);

                    if (battleItem instanceof Consumable)
                    {
                        this.player.useConsumablesWithCommand(ansChar);
                    } else battleItem.applyEffect(this.entity);
                }
                break;
            }
            this.entity.printObject();
        }
        return true;
    }

    /**
     * Performs an attack on the attacked entity with a random value derived
     * from the attacker's max damage output. The attack can reduce the attacked
     * entity's health with an attack by a value between zero up to the value of
     * the attacker's max damage output.
     *
     * @param attacker Entity which performs the attack on the attacked
     *         entity.
     * @param attacked Entity attacked on by the attacker entity.
     */
    private void doAttack(Entity attacker, Entity attacked)
    {
        int inflictedDamage = new Random().nextInt(attacker.getMaxDamage()) + 1;
        attacked.setHealthPoints(attacked.getHealthPoints() - inflictedDamage);
        printAttackMessage(attacker, attacked, inflictedDamage);
    }

    /**
     * Prints a message with information about the attacker and the attacked
     * entities. Informs the user about how much damage was dealt by the
     * attacker on the attacked and how much health the attacked has left in
     * total after the attack was performed.
     *
     * @param attacker Entity which performed an attack.
     * @param attacked Entity which was attacked.
     * @param inflictedDMG Amount of damage inflicted on the attacked
     *         entity.
     */
    private void printAttackMessage(Entity attacker,
                                    Entity attacked,
                                    int inflictedDMG)
    {
        System.out.printf("%s attackerar %s och gör %d skada - (%s HP: %d)\n",
                attacker.getName(),
                attacked.getName(),
                inflictedDMG,
                attacked.getName(),
                attacked.getHealthPoints());
    }

    /**
     * Prints a message with information about which entity won the battle.
     *
     * @param winner Entity which lost the battle.
     * @param loser Entity which won the battle.
     * @throws Exception If something goes wrong while clearing the console.
     */
    private void printWinsMessage(Entity winner, Entity loser) throws Exception
    {
        this.visualEffectManager.clearConsole();

        PrintCollection.printLinesWithPlusCorners();
        String winsString = String.format("| %s besegrar %s!",
                winner.getName(),
                loser.getName());
        System.out.print(winsString);
        PrintCollection.printSpaces(
                K.AMOUNT_OF_LINES - winsString.length());
        System.out.println(" |");
        PrintCollection.printLinesWithPlusCorners();
    }

    /**
     * Used to recognize and thereafter store items usable during battle for the
     * player.
     *
     * @param player The player in the battle.
     * @return Battle items (with consumables included)
     */
    private ArrayList<Item> getBattleInventory(Player player)
    {
        ArrayList<Item> items = new ArrayList<>(player.getItems());
        items.addAll(player.getConsumables());
        ArrayList<Item> tempBattleInventory = new ArrayList<>();
        for (Item item : items)
        {
            if (item.getUsableDuringBattle()) tempBattleInventory.add(item);
        }
        return tempBattleInventory;
    }

    /**
     * Returns an item usable during battle if the provided command coincides
     * with the item's command property.
     *
     * @param c Character representing the use command of item.
     * @return {@code true} if item with the provided use command exists in the
     *         battle inventory. {@code false} otherwise.
     */
    private Item getBattleItemWithCommand(char c)
    {
        for (Item item : battleInventory)
        {
            if (c == item.getUseCommand()) return item;
        }
        return null;
    }

    /**
     * Prints the weapons available for use by the player during battle.
     */
    private void printBattleWeaponOptions()
    {
        for (Item item : battleInventory)
        {
            System.out.printf("Använd %s [%c]\n",
                    item.getName().toLowerCase(),
                    item.getUseCommand());
        }
    }

    /**
     * Prints the option for the player to attack with the valid
     * attack-command.
     */
    private void printAttackOption()
    {
        System.out.printf("Attackera [%c]\n", Command.ATTACK.commandValue);
    }
}