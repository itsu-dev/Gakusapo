package itsu.mcbe.monsteradventure.session;

import cn.nukkit.Player;
import itsu.mcbe.monsteradventure.data.GameData;
import itsu.mcbe.monsteradventure.session.base.DelaySession;

public class RefuseBattleSchedulerSession extends DelaySession {

    private Player player;

    public RefuseBattleSchedulerSession(Player player) {
        this.player = player;
    }

    @Override
    public int getDelay() {
        return 200;
    }

    @Override
    public void run() {
        GameData.removeRefuseBattleStart(player);
    }

}
