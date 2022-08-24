package unsw.loopmania.BasicItems;

import unsw.loopmania.Enemies.Doggie;
import unsw.loopmania.Enemies.ElanMuske;
import unsw.loopmania.CharacterFolder.Character;
import unsw.loopmania.Enemies.Slug;
import unsw.loopmania.Enemies.Vampire;
import unsw.loopmania.Enemies.Zombie;

public interface DefendingStrategy {

    public void reduceSlugDamage(Slug slug, Character c);

    public void reduceZombieDamage(Zombie zombie, Character c);

    public void reduceVampireDamage(Vampire vampire, Character c);

    public void reduceDoggieDamage(Character character, Doggie doggie);

    public void reduceElanMuskeDamage(Character character, ElanMuske elanMuske);
    
}
