package itsu.mcbe.urbanbuilder.core;

import cn.nukkit.Player;
import cn.nukkit.block.Block;
import cn.nukkit.block.BlockItemFrame;
import cn.nukkit.blockentity.BlockEntityItemFrame;
import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;
import cn.nukkit.command.ConsoleCommandSender;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.Listener;
import cn.nukkit.event.player.PlayerInteractEvent;
import cn.nukkit.item.ItemMap;
import cn.nukkit.plugin.PluginBase;

public class Main extends PluginBase implements Listener {

    private Display display;

    @Override
    public void onEnable() {
        getLogger().info("Enabled");
        getServer().getPluginManager().registerEvents(display = new Display(), this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String args[]) {
        switch(command.getName()) {
            case "web": {
                if (args.length < 1) {
                    sender.sendMessage("Arguments count must be more than one.");
                    return true;
                }

                if (sender instanceof ConsoleCommandSender) {
                    sender.sendMessage("Run on the game.");
                    return true;
                }

                Player player = (Player) sender;

                switch (args[0]) {
                    case "load": {
                        display.load(args[0]);
                        return true;
                    }

                    case "create": {
                        display.create(player);
                        return true;
                    }
                }

            }
        }
        return false;
    }

    @EventHandler
    public void onTap(PlayerInteractEvent e) {
        if (e.getBlock().getId() == Block.ITEM_FRAME_BLOCK) {
            BlockItemFrame block = (BlockItemFrame) e.getBlock();
            BlockEntityItemFrame frame = (BlockEntityItemFrame) block.getLevel().getBlockEntity(block);

            ItemMap map = new ItemMap();
            map.setImage(ImageAPI.init());
            map.sendImage(e.getPlayer());
            frame.setItem(map);
        }
    }
}
