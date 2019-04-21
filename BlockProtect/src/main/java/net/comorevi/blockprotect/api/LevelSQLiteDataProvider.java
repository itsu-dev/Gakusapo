package net.comorevi.blockprotect.api;

import cn.nukkit.Server;
import cn.nukkit.level.Level;

import java.sql.*;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

public class SQLiteDataProvider {

    private Connection connection = null;

    SQLiteDataProvider() {
        connectSQL();
    }

    boolean existsProtectData(int x, int y, int z, String levelname) {
        try {
            String sql = "SELECT * FROM " + levelname + " WHERE x = ? AND y = ? AND z = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setQueryTimeout(30);
            statement.setInt(1, x);
            statement.setInt(2, y);
            statement.setInt(3, z);

            if (statement.executeQuery().next()) {
                statement.close();
                return true;
            } else {
                statement.close();
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    void addBlockProtect(String owner, String levelname, String blockName, int blockId, int damage, int x, int y, int z, int protecttype) {
        try {
            if (existsProtectData(x, y, z, levelname)) return;

            String sql = "INSERT INTO " + levelname + " (owner, bname, bid, meta, x, y, z, date, type, editable) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, owner);
            statement.setString(2, blockName);
            statement.setInt(3, blockId);
            statement.setInt(4, damage);
            statement.setInt(5, x);
            statement.setInt(6, y);
            statement.setInt(7, z);
            statement.setLong(8, System.currentTimeMillis());
            statement.setInt(9, protecttype);
            statement.setInt(10, EditType.EDITTYPE_DENY);

            statement.executeUpdate();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    Map<String, Object> getProtectData(int x, int y, int z, String levelname) {
        try {
            if (!existsProtectData(x, y, z, levelname)) return Collections.emptyMap();

            String sql = "SELECT * FROM " + levelname + " WHERE x = ? AND y = ? AND z = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            //statement.setString(1, levelname);
            statement.setInt(1, x);
            statement.setInt(2, y);
            statement.setInt(3, z);

            ResultSet rs = statement.executeQuery();
            Map<String, Object> map = new LinkedHashMap<>();
            if (rs.next()) {
                map.put("owner", rs.getString("owner"));
                map.put("binfo", rs.getString("bname") + "." + rs.getString("bid") + ":" + rs.getString("meta"));
                map.put("position", rs.getString("x") + "." + rs.getString("y") + "." + rs.getString("z") + ":" + levelname);
                map.put("date", rs.getLong("date"));
                map.put("type", rs.getString("type"));
            }

            rs.close();
            statement.close();

            return map;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Collections.emptyMap();
    }

    void removeBlockProtect(String owner, String levelname, int x, int y, int z) {
        try {
            if (!existsProtectData(x, y, z, levelname)) return;
            if (!Objects.equals(getProtectData(x, y, z, levelname).get("owner"), owner)) return;

            String sql = "DELETE FROM " + levelname + " WHERE owner = ? AND x = ? AND y = ? AND z = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, owner);
            statement.setInt(2, x);
            statement.setInt(3, y);
            statement.setInt(4, z);

            statement.executeUpdate();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void connectSQL() {
        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:" + "./plugins/BlockProtect/DataDB.db");
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);

            for (Map.Entry<Integer, Level> entry : Server.getInstance().getLevels().entrySet()) {
                statement.executeUpdate(
                        "CREATE TABLE IF NOT EXISTS " + entry.getValue().getFolderName() + " (" +
                                " id INTEGER PRIMARY KEY AUTOINCREMENT," +
                                " owner TEXT NOT NULL," +
                                " bname TEXT NOT NULL," +
                                " bid INTEGER NOT NULL," +
                                " meta INTEGER NOT NULL," +
                                " x INTEGER NOT NULL," +
                                " y INTEGER NOT NULL," +
                                " z INTEGER NOT NULL," +
                                " date LONG NOT NULL," +
                                " type INTEGER NOT NULL," +
                                " editable int NOT NULL)"
                );
            }
            statement.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void disConnectSQL() {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}