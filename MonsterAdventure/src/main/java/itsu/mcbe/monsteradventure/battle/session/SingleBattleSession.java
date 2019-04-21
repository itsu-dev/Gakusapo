package itsu.mcbe.monsteradventure.session;

import cn.nukkit.Player;
import cn.nukkit.level.Position;
import cn.nukkit.utils.DummyBossBar;
import cn.nukkit.utils.TextFormat;
import gt.creeperface.nukkit.scoreboardapi.ScoreboardAPI;
import gt.creeperface.nukkit.scoreboardapi.scoreboard.Scoreboard;
import gt.creeperface.nukkit.scoreboardapi.scoreboard.SimpleScoreboard;
import itsu.mcbe.monsteradventure.api.GameAPI;
import itsu.mcbe.monsteradventure.model.BattleGround;
import itsu.mcbe.monsteradventure.session.base.DelaySession;

import java.awt.*;

public class SingleBattleSession extends DelaySession {

    private BattleGround ground;
    private Player player;
    private Position beforePosition;

    public SingleBattleSession(Player player, BattleGround ground) {
        this.player = player;
        this.ground = ground;
    }

    @Override
    public void run() {
        beforePosition = player.getPosition();
        GameAPI.teleportToBattleGround(player, ground);
        player.sendMessage("やせいのクリーパーがあらわれた！");

        SimpleScoreboard board = ScoreboardAPI.builder().build();
        board.setDisplayName("クリーパー Lv.21");
        board.setScore(1, "HP: " + TextFormat.GREEN + "||||||||||" + TextFormat.WHITE + " 121/121", 0);
        board.addPlayer(player);

        DummyBossBar bossbar = new DummyBossBar.Builder(player)
                .color(0, 255, 0)
                .length(100)
                .text("Lv.21 クリーパー   121/121")
                .build();
        player.createBossBar(bossbar);

        player.setExperience(100, 100);

    }

    @Override
    public int getDelay() {
        return 20;
    }
}
