
package meteor.plugins.groundmarkers

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import eventbus.events.ConfigChanged
import eventbus.events.GameStateChanged
import eventbus.events.MenuEntryAdded
import eventbus.events.MenuOptionClicked
import meteor.Logger
import meteor.config.ConfigManager
import meteor.game.chatbox.ChatboxPanelManager
import meteor.plugins.Plugin
import meteor.plugins.PluginDescriptor
import net.runelite.api.GameState
import net.runelite.api.KeyCode
import net.runelite.api.MenuAction
import net.runelite.api.Tile
import net.runelite.api.coords.LocalPoint
import net.runelite.api.coords.WorldPoint
import java.util.*

@PluginDescriptor(
    name = "Ground Markers",
    description = "Enable marking of tiles using the Shift key",
    tags = ["overlay", "tiles"]
)
class GroundMarkerPlugin : Plugin() {

    var points: MutableCollection<ColorTileMarker> = mutableListOf()

    private val config = configuration<GroundMarkerConfig>()
    private val overlay = overlay(GroundMarkerOverlay(config,this))
    private val minimapOverlay = overlay(GroundMarkerMinimapOverlay(config,this))
    private val chatboxPanelManager = ChatboxPanelManager
    private val sharingManager = GroundMarkerSharingManager(this)
    private val gson = Gson()
    private val log = Logger("Ground Marker Plugin")

    fun savePoints(regionId: Int, points: Collection<GroundMarkerPoint?>?) {
        if (points == null || points.isEmpty()) {
            ConfigManager.unsetConfiguration(
                CONFIG_GROUP,
                REGION_PREFIX + regionId
            )
            return
        }
        val json = gson.toJson(points)
        ConfigManager.setConfiguration(
            CONFIG_GROUP,
            REGION_PREFIX + regionId,
            json
        )
    }

    fun getPoints(regionId: Int): MutableCollection<GroundMarkerPoint> {
        val json: String? =
            ConfigManager.getConfiguration(CONFIG_GROUP, REGION_PREFIX + regionId)
        return if (json.isNullOrEmpty()) {
            mutableListOf()
        } else gson.fromJson(json, object : TypeToken<MutableCollection<GroundMarkerPoint>>() {}.type)


    }


    fun loadPoints() {
        points.clear()
        val regions = client.mapRegions ?: return
        for (regionId in regions) {
            // load points for region
            val regionPoints = getPoints(regionId)
            val colorTileMarkers = translateToColorTileMarker(regionPoints)
            points.addAll(colorTileMarkers)
        }
    }

    /**
     * Translate a collection of ground marker points to color tile markers, accounting for instances
     *
     * @param points [GroundMarkerPoint]s to be converted to [ColorTileMarker]s
     * @return A collection of color tile markers, converted from the passed ground marker points, accounting for local
     * instance points. See [WorldPoint.toLocalInstance]
     */
    private fun translateToColorTileMarker(points: MutableCollection<GroundMarkerPoint>): Collection<ColorTileMarker> {
        return if (points.isEmpty()) {
                emptyList()
            } else points
                .map { point: GroundMarkerPoint ->
                    ColorTileMarker(

                        WorldPoint.fromRegion(point.regionId, point.regionX, point.regionY, point.z),
                        point.color, point.label
                    )
                }
                .flatMap { colorTile: ColorTileMarker ->
                    val localWorldPoints =
                        WorldPoint.toLocalInstance(client, colorTile.worldPoint)
                    localWorldPoints
                        .map{ wp: WorldPoint? ->
                            ColorTileMarker(
                                wp,
                                colorTile.color,
                                colorTile.label
                            )
                        }
                }.toList()
    }

    override fun onStart() {
        overlayManager.add(overlay)
        overlayManager.add(minimapOverlay)
        sharingManager
        if (config.showImportExport()) {
            sharingManager.addImportExportMenuOptions()
        }
        if (config.showClear()) {
            sharingManager.addClearMenuOption()
        }
        loadPoints()
    }

    override fun onStop() {

        overlayManager.remove(overlay)
        overlayManager.remove(minimapOverlay)
        sharingManager.removeMenuOptions()
        points.clear()
    }

    override fun onGameStateChanged(it: GameStateChanged) {
        if (it.gameState != GameState.LOGGED_IN) {
            return
        }

        // map region has just been updated
        loadPoints()
    }

    override fun onMenuOptionClicked(it: MenuOptionClicked) {
        if (it.menuEntry.option == UNMARK || it.menuEntry.option == MARK) {
            val target = client.selectedSceneTile
            if (target != null) {
                markTile(target.localLocation)
            }
        }
        if (it.menuEntry.option == LABEL) {
            val target = client.selectedSceneTile
            target?.let { labelTile(it) }
        }
    }

    override fun onMenuEntryAdded(it: MenuEntryAdded) {

        val hotKeyPressed = client.isKeyPressed(KeyCode.KC_SHIFT)

        if (hotKeyPressed && it.option.equals(WALK_HERE)) {
            val selectedSceneTile = client.selectedSceneTile?.localLocation ?: return
            val worldPoint = WorldPoint.fromLocalInstance(client, selectedSceneTile)
            val regionId = worldPoint.regionID

            var searchPoint: GroundMarkerPoint? = null
            val pnts = getPoints(regionId)

                val point = GroundMarkerPoint(
                    regionId,
                    worldPoint.regionX,
                    worldPoint.regionY,
                    worldPoint.plane,


                )
                searchPoint = point


                val exists = point in pnts
                val menuOption = if (exists) UNMARK else MARK
                client.createMenuEntry(-1)
                    .setOption(menuOption)
                    .setTarget(it.target)
                    .setType(MenuAction.RUNELITE)

                if (exists) {
                    client.createMenuEntry(-2)
                        .setOption(LABEL)
                        .setTarget(it.target)
                        .setType(MenuAction.RUNELITE)
                }
            }

    }

    override fun onConfigChanged(it: ConfigChanged) {
        if (it.group.equals( "groundMarker")
            && (it.key.equals("showImportExport")
                    || it.key.equals("showClear"))
        ) {
            // Maintain consistent menu option order by removing everything then adding according to config
            sharingManager.removeMenuOptions()
            if (config.showImportExport()) {
                sharingManager.addImportExportMenuOptions()
            }
            if (config.showClear()) {
                sharingManager.addClearMenuOption()
            }
        }
    }

    private fun markTile(localPoint: LocalPoint?) {
        if (localPoint == null) {
            return
        }

        val worldPoint = WorldPoint.fromLocalInstance(client, localPoint)

        val regionId = worldPoint.regionID

        val point = GroundMarkerPoint(
            regionId,
            worldPoint.regionX,
            worldPoint.regionY,
            worldPoint.plane,
            config.markerColor()!!.rgb,
            "",
        )
        log.debug("Updating point: {} - {}", point, worldPoint)

        val groundMarkerPoints: MutableCollection<GroundMarkerPoint> = ArrayList(getPoints(regionId))
        if (point in groundMarkerPoints) {
            groundMarkerPoints.remove(point)
        } else {
            groundMarkerPoints.add(point)
        }

        savePoints(regionId, groundMarkerPoints)

        loadPoints()
    }

    private fun labelTile(tile: Tile) {
        val localPoint = tile.localLocation
        val worldPoint = WorldPoint.fromLocalInstance(client, localPoint)
        val regionId = worldPoint.regionID
        val searchPoint =
            GroundMarkerPoint(regionId, worldPoint.regionX, worldPoint.regionY, worldPoint.plane)
        val points = getPoints(regionId)
        val existing = points.firstOrNull { p: GroundMarkerPoint -> p == searchPoint } ?: return
        chatboxPanelManager.openTextInput("Tile label")
            .value(Optional.ofNullable(existing.label).orElse(""))
            .onDone { input ->
                val newPoint = GroundMarkerPoint(
                    regionId,
                    worldPoint.regionX,
                    worldPoint.regionY,
                    worldPoint.plane,
                    existing.color,
                    input ?: ""
                )
                points.remove(searchPoint)
                points.add(newPoint)
                savePoints(regionId, points)
                loadPoints()
            }
            ?.build()
    }

    companion object {
        private const val CONFIG_GROUP = "groundMarker"
        private const val MARK = "Mark tile"
        private const val UNMARK = "Unmark tile"
        private const val LABEL = "Label tile"
        private const val WALK_HERE = "Walk here"
        private const val REGION_PREFIX = "region_"
    }
}