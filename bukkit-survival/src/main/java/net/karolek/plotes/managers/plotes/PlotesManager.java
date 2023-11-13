package net.karolek.plotes.managers.plotes;

import com.mongodb.Block;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import net.karolek.plotes.BukkitPlugin;
import net.karolek.plotes.data.plotes.Plotes;
import net.karolek.plotes.data.plotes.type.PloteType;
import net.karolek.plotes.utils.KeyUtil;
import org.bson.Document;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.UUID;

public class PlotesManager {
    private final HashMap<UUID, Plotes> plotes;

    public PlotesManager(){
        this.plotes = new HashMap<>();
    }

    MongoClientURI clientURI = new MongoClientURI("mongodb://localhost:27017");
    MongoClient mongoClient = new MongoClient(clientURI);

    MongoDatabase mongoDatabase = mongoClient.getDatabase("admin");
    MongoCollection<Document> collection = mongoDatabase.getCollection(KeyUtil.PLATFORM_PLOTES);

    Block<Document> printBlock = document -> System.out.println(document.toJson());

    public void create(final Player player, final PloteType ploteType){
        final Plotes plote = new Plotes(player, ploteType);
        this.plotes.put(player.getUniqueId(), plote);
    }

    public void delete(final Plotes plote){

        this.plotes.remove(plote);
        plote.delete();
    }

    public Plotes getPloteByPlayer(final Player player){
        return this.plotes.values().stream().filter(plotes -> plotes.getPlayerUuid().equals(player.getUniqueId())).findFirst().orElse(null);
    }

    public HashMap<UUID, Plotes> getPlotes() {
        return plotes;
    }

    public void load() {
        this.printBlock = document -> {
            final Plotes plote = new Plotes(document);
            final Player player = Bukkit.getPlayer(plote.getPlayerUuid());
            this.create(player, plote.getPloteType());
        };
        collection = mongoDatabase.getCollection(KeyUtil.PLATFORM_PLOTES);
        collection.find().forEach(this.printBlock);
        BukkitPlugin.getInstance().getLogger().info("Loaded " + plotes.size() + " plotes!");
    }
}


