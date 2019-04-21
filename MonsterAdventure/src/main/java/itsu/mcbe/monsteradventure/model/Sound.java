package itsu.mcbe.monsteradventure.sound;

public enum Sound {

    SOUND_BATTLE("battle");

    private String name;

    Sound(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

}
