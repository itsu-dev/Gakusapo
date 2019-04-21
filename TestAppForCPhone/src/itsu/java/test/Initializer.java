package itsu.java.test;

import cn.nukkit.event.EventHandler;
import cn.nukkit.event.Listener;
import cn.nukkit.event.player.PlayerJoinEvent;
import net.comorevi.cphone.cphone.application.Initializer;
import net.comorevi.cphone.presenter.SharingData;

public class InitializeProcessor extends Initializer implements Listener {

    public InitializeProcessor() {

    }

    public void initialize() {
        SharingData.server.getPluginManager().registerEvents(this, SharingData.pluginInstance);
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        event.getPlayer().sendMessage("TESTDESU");
    }
}
