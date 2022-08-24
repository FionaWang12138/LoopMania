package unsw.loopmania.CharacterFolder;

import unsw.loopmania.BasicItems.AttackingStrategy;
import unsw.loopmania.Enemies.*;

public class JailState extends CharacterState {
    private int ticks;

    public JailState(Character character) {
        super(character);
        ticks = 0;
    }

    public void dealDamage(BasicEnemy enemy, int loopCount){
        super.dealDamage(enemy, loopCount);
    }
    /**
     * in the jailed state, the character will not move for 10 ticks, after this, will switch back to RegularState
     */
    @Override
    public void move() {
       if (ticks < 10){
           ticks ++;
       }
       else {
            character.changeState(new RegularState(character));
            character.moveDownPath();
       }
    }
    
}
