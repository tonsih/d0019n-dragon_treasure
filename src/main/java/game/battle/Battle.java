package game.battle;

import game.data.Command;
import game.data.K;
import game.data.PrintCollection;
import game.entities.Entity;
import game.entities.Monster;
import game.entities.Player;
import game.general.Dungeon;
import game.items.Item;
import game.items.consumables.Consumable;
import utils.ConsoleCleaner;
import utils.TimeManipulator;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Battle
{
    private final Player player;
    private final Monster monster;

    private final ArrayList<Item> battleInventory;

    private final Scanner scanner;

    public Battle(Player player, Monster monster, Scanner scanner)
    {
        this.player = player;
        this.monster = monster;
        this.battleInventory = getBattleInventory(this.player);
        this.scanner = scanner;
    }

    private ArrayList<Item> getBattleInventory(Player player)
    {
        ArrayList<Item> items = new ArrayList<>(player.getItems());
        items.addAll(player.getConsumables());

        ArrayList<Item> tempBattleInventory = new ArrayList<>();
        for (Item item : items)
        {
            if (item.getUsableDuringBattle())
            {
                tempBattleInventory.add(item);
            }
        }

        return tempBattleInventory;
    }

    public boolean newBattle()
    {
        System.out.printf("%s dyker upp\n", this.monster.getMonsterDesc());
        TimeManipulator.wait(1000);
        this.monster.printObject();
        TimeManipulator.wait(500);

        while (true)
        {

            TimeManipulator.wait(500);
            this.doAttack(this.monster, this.player);
            TimeManipulator.wait(1000);

            if (player.getHealthPoints() <= 0)
            {
                this.printWinsMessage(this.monster, this.player);
                player.setAlive(false);
                return false;
            }

            if (!this.battlePlayerMenu()) return false;

            if (monster.getHealthPoints() <= 0)
            {
                this.printWinsMessage(this.player, this.monster);
                player.addMonsterKilled();
                return true;
            }

        }
    }

    private boolean battlePlayerMenu()
    {
        while (true)
        {
            System.out.println();
            printBattleWeaponOptions();
            printAttackOption();

            System.out.println();
            Dungeon.printEscapeOption();
            System.out.println();

            PrintCollection.printConsoleMarker();
            String ansStr = this.scanner.nextLine();

            ConsoleCleaner.clearConsole();
            PrintCollection.printLinesWithPlusCorners();

            if (ansStr.isBlank()) continue;

            char ansChar = ansStr.trim().toLowerCase().charAt(0);

            if (ansChar == Command.EXIT_GAME.commandValue) return false;

            Item battleItem = getBattleItemWithCommand(ansChar);

            if (ansChar == Command.ATTACK.commandValue || battleItem != null)
            {
                this.monster.printObject();

                if (ansChar == Command.ATTACK.commandValue)
                {
                    this.doAttack(this.player, this.monster);
                } else
                {
                    this.battleInventory.remove(battleItem);
                    this.player.removeItem(battleItem);

                    if (battleItem instanceof Consumable)
                    {
                        this.player.useConsumablesWithCommand(ansChar);
                    } else
                    {
                        battleItem.applyEffect(monster);
                    }
                }

                break;

            }

            this.monster.printObject();
        }
        return true;
    }

    private void doAttack(Entity attacker, Entity attacked)
    {
        int inflictedDamage = new Random().nextInt(attacker.getMaxDamage()) + 1;

        attacked.setHealthPoints(
                attacked.getHealthPoints() - inflictedDamage);

        printAttackMessage(attacker, attacked, inflictedDamage);
    }

    private Item getBattleItemWithCommand(char c)
    {
        for (Item item : battleInventory)
        {
            if (c == item.getUseCommand())
            {
                return item;
            }
        }
        return null;
    }

    private void printBattleWeaponOptions()
    {
        for (Item item : battleInventory)
        {
            System.out.printf("Använd %s [%c]\n",
                    item.getName().toLowerCase(),
                    item.getUseCommand());
        }
    }

    private void printAttackOption()
    {
        System.out.printf("Attackera [%c]\n", Command.ATTACK.commandValue);
    }

    private void printAttackMessage(Entity attacker, Entity attacked, int inflictedDMG)
    {
        System.out.printf("%s attackerar %s och gör %d skada - (%s HP: %d)\n",
                attacker.getName(),
                attacked.getName(),
                inflictedDMG,
                attacked.getName(),
                attacked.getHealthPoints());
    }

    private void printWinsMessage(Entity winner, Entity loser)
    {
        ConsoleCleaner.clearConsole();

        PrintCollection.printLinesWithPlusCorners();
        String winsString = String.format("| %s besegrar %s!",
                winner.getName(),
                loser.getName());
        System.out.print(winsString);
        PrintCollection.printAmountOfSpaces(K.AMOUNT_OF_LINES-winsString.length());
        System.out.println(" |");
        PrintCollection.printLinesWithPlusCorners();
    }

}
