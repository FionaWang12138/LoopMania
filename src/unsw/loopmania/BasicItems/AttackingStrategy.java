package unsw.loopmania.BasicItems;

import unsw.loopmania.LoopManiaWorld;
import unsw.loopmania.CharacterFolder.Character;
import unsw.loopmania.Enemies.Doggie;
import unsw.loopmania.Enemies.ElanMuske;
import unsw.loopmania.Enemies.Slug;
import unsw.loopmania.Enemies.Vampire;
import unsw.loopmania.Enemies.Zombie;


public interface AttackingStrategy {

    public void reduceSlugHealth(Slug slug, int loopCount);
    public void reduceZombieHealth(Zombie zombie, int loopCount);
    public void reduceVampireHealth(Vampire vampire, int loopCount);
    public void reduceDoggieHealth(Doggie doggie, Character character);
    public void reduceElanMuskeHealth(ElanMuske elanMuske, Character character);
    
}
