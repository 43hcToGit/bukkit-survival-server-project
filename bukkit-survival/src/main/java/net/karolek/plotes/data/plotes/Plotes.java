package net.karolek.plotes.data.plotes;

import lombok.Getter;
import lombok.Setter;
import net.karolek.plotes.BukkitPlugin;
import net.karolek.plotes.data.plotes.type.PloteType;
import net.karolek.plotes.database.DatabaseModel;
import net.karolek.plotes.utils.KeyUtil;
import org.bson.Document;
import org.bukkit.entity.Player;

import java.util.UUID;

@Getter
@Setter
public class Plotes extends DatabaseModel {

    private final BukkitPlugin bukkitPlugin = BukkitPlugin.getInstance();

    private final UUID playerUuid;

    private final PloteType ploteType;

    public Plotes(final Player player, final PloteType ploteType) {
        this.playerUuid = player.getUniqueId();
        this.ploteType = ploteType;
    }

    public Plotes(final Document document){
        this.playerUuid = UUID.fromString(document.getString("playerUuid"));
        this.ploteType = PloteType.valueOf(document.getString("ploteType"));

    }

    public Document buildDocument() {
        return new Document()
                .append("playerUuid", this.getPlayerUuid())
                .append("ploteType", this.getPloteType())
                ;
    }

    @Override
    protected void insert() {
        this.bukkitPlugin.getMongoManager().insert(KeyUtil.PLATFORM_PLOTES, this.buildDocument());
    }

    @Override
    public void update() {
        this.bukkitPlugin.getMongoManager().update(KeyUtil.PLATFORM_PLOTES, this.buildDocument(), "playerUuid", this.getPlayerUuid(), true);
    }

    @Override
    public void delete() {
        this.bukkitPlugin.getMongoManager().remove(KeyUtil.PLATFORM_PLOTES      , this.buildDocument(), true);
    }
}
