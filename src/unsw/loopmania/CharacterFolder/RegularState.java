package unsw.loopmania.CharacterFolder;

import unsw.loopmania.BasicItems.AttackingStrategy;
import unsw.loopmania.Enemies.*;

public class RegularState extends CharacterState {

    public RegularState(Character character) {
        super(character);
    }

    public void dealDamage(BasicEnemy enemy, int loopCount){

        //a doggie can only stun if its health is greater than 30
        if (enemy instanceof Doggie && enemy.getCurrentHealth() >= 30) {
            character.changeState(new StunnedState(character));
            
        }
        super.dealDamage(enemy, loopCount);
    }
   
    
}
