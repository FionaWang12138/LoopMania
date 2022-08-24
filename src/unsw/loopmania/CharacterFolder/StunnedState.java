package unsw.loopmania.CharacterFolder;

import unsw.loopmania.BasicItems.AttackingStrategy;
import unsw.loopmania.Enemies.*;

public class StunnedState extends CharacterState {

    public StunnedState(Character character) {
        super(character);
    }

    public void dealDamage(BasicEnemy enemy, int loopCount){
        if (enemy instanceof Doggie){
            character.changeState(new RegularState(character));
        }
    }
    
}
