package unsw.loopmania;

public class Experience {
    private int experience;
    public Experience () {
        this.experience = 0;
    }
    
    public void addExperience (int experience) {
        this.experience += experience;
    }

    public int getExperience () {
        return this.experience;
    }
}
