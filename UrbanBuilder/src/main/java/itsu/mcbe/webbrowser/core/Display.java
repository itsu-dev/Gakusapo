package itsu.mcbe.urbanbuilder.core;

import cn.nukkit.Player;
import cn.nukkit.Server;
import cn.nukkit.block.Block;
import cn.nukkit.blockentity.BlockEntityItemFrame;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.Listener;
import cn.nukkit.event.player.PlayerInteractEvent;
import cn.nukkit.event.server.DataPacketReceiveEvent;
import cn.nukkit.item.ItemMap;
import cn.nukkit.level.Position;
import cn.nukkit.math.Vector3;
import cn.nukkit.network.protocol.PlayerActionPacket;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Worker;
import javafx.embed.swing.JFXPanel;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.Scene;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class Display implements Listener {

    private static final int WIDTH = 8;
    private static final int HEIGHT = 5;

    private List<ItemMap> display;
    private WebView view;

    private boolean isSet = false;
    private boolean started;
    private int count = 0;

    public Display() {
        display = new ArrayList<>();

        new JFXPanel();

        Platform.runLater(() -> {
            view = new WebView();
            view.getEngine().setUserAgent("Mozilla/5.0 (Windows NT 10.0) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/51.0.2704.79 Safari/537.36 Edge/14.14393");
            view.setPrefSize(WIDTH * 128, HEIGHT * 128);

            Scene scene = new Scene(view);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setTitle("fx");
            stage.show();
        });
    }

    public void create(Player player) {
        display.clear();
        player.sendMessage("Create display mode");
        count = 0;
        isSet = false;
    }

    public void load(String url) {
        Platform.runLater(() -> {
            view.getEngine().load(url);
            view.getEngine().getLoadWorker().stateProperty().addListener(new ChangeListener<Worker.State>() {
                @Override
                public void changed(ObservableValue<? extends Worker.State> observableValue, Worker.State state, Worker.State t1) {
                    if (t1 == Worker.State.SUCCEEDED) {
                        Server.getInstance().getLogger().info("Work end.");
                        BufferedImage bufferedImage = SwingFXUtils.fromFXImage(view.snapshot(null, null), null);
                        setImage(bufferedImage);
                    }
                }
            });
        });
    }

    private void takeImage() {
        Platform.runLater(() -> {
            BufferedImage bufferedImage = SwingFXUtils.fromFXImage(view.snapshot(null, null), null);
            setImage(bufferedImage);
        });
    }

    public void setImage(BufferedImage image) {
        List<BufferedImage> images = new ArrayList<>();
        int w = 0;
        int h = 0;

        int count = 0;
        for (ItemMap itemMap : display) {
            itemMap.setImage(image.getSubimage(w, h, 128, 128));

            for (Player player : Server.getInstance().getOnlinePlayers().values()) {
                itemMap.sendImage(player);
            }

            count++;
            w += 128;

            if (count % WIDTH == 0) {
                w = 0;
                h += 128;
            }
        }
    }

    @EventHandler
    public void onTap(PlayerInteractEvent e) {
        if (!isSet) {
            if (e.getBlock().getId() == Block.ITEM_FRAME_BLOCK) {
                BlockEntityItemFrame block = (BlockEntityItemFrame) e.getPlayer().getLevel().getBlockEntity(e.getBlock());

                ItemMap map = new ItemMap();
                map.setImage(new BufferedImage(128, 128, BufferedImage.TYPE_INT_RGB));
                map.sendImage(e.getPlayer());

                block.setItem(map);
                block.setItemRotation(3);

                display.add(map);
                count++;

                e.getPlayer().sendMessage("Display: " + count);
            }

            if (count == WIDTH * HEIGHT) {
                e.getPlayer().sendMessage("Display set ok.");
                isSet = true;

                load("https://www.google.co.jp");
                start();
            }
        }
    }

    private void start() {
        if (!started) {
            started = true;

            Server.getInstance().getScheduler().scheduleRepeatingTask(new Runnable() {
                @Override
                public void run() {
                    takeImage();
                }
            }, 20);

        }
    }

}
