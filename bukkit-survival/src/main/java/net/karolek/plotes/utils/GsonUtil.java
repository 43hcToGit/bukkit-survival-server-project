package net.karolek.plotes.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.Getter;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class GsonUtil {

    @Getter
    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    public static Object load(Plugin plugin, Object newObject) {
        Object toReturn = 0;
        final File file = new File(plugin.getDataFolder(), newObject.getClass().getSimpleName() + ".gson");
        if (file.exists() || file.length() != 0L) {
            try (FileReader reader = new FileReader(file)) {
                toReturn = gson.fromJson(reader, newObject.getClass());
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            try {
                if (!file.getParentFile().exists()) {
                    file.getParentFile().mkdirs();
                }
                if (!file.exists()) {
                    file.createNewFile();
                }
                try (FileWriter writer = new FileWriter(file)) {
                    writer.write(gson.toJson(toReturn = newObject));
                    writer.flush();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        return toReturn;
    }

    public static void save(Plugin plugin, Object object) {
        final File file = new File(plugin.getDataFolder(), object.getClass().getSimpleName() + ".gson");
        try (FileWriter writer = new FileWriter(file)) {
            writer.write(gson.toJson(object));
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static <T> Object fromJson(String json, Class<T> aClass) {
        return gson.fromJson(json, aClass);
    }
}
