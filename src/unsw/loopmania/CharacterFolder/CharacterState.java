package unsw.loopmania.CharacterFolder;

import unsw.loopmania.BasicItems.AttackingStrategy;
import unsw.loopmania.Enemies.BasicEnemy;
import unsw.loopmania.Enemies.Slug;
import unsw.loopmania.Enemies.Vampire;
import unsw.loopmania.Enemies.Zombie;
import unsw.loopmania.Enemies.Doggie;
import unsw.loopmania.Enemies.ElanMuske;

public abstract class CharacterState {
    Character character;
    
    public CharacterState (Character character){
        this.character = character;
    }

    /**
     * 
     * @param enemy
     * @param loopCount
     * deals damage to the enemy accordningly
     */
    public void dealDamage(BasicEnemy enemy, int loopCount) {
        AttackingStrategy weapon = character.getEquippedWeapon ();

        enemy.decreaseHealth(character.getBaseDamage());
        if (weapon != null){
            if (enemy instanceof Slug){
                Slug slug = (Slug) enemy;
                weapon.reduceSlugHealth(slug, loopCount);
            }
            else if (enemy instanceof Zombie){
                Zombie zombie = (Zombie) enemy;
                weapon.reduceZombieHealth(zombie, loopCount);
            }
            else if (enemy instanceof Vampire){
                Vampire vampire = (Vampire) enemy;
                weapon.reduceVampireHealth(vampire, loopCount);
            }
            else if (enemy instanceof Doggie){
                Doggie doggie = (Doggie) enemy;
                weapon.reduceDoggieHealth(doggie, character);
            }
            else if (enemy instanceof ElanMuske){
                ElanMuske elan = (ElanMuske) enemy;
                weapon.reduceElanMuskeHealth(elan, character);
            }
        }
    }
    
    public void move() {
        character.moveDownPath();
    }
}
