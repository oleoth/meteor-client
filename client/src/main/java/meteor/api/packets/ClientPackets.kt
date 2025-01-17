@file:Suppress("DEPRECATION")

package meteor.api.packets

import dev.hoot.api.InteractionException
import dev.hoot.api.events.AutomatedMenu
import dev.hoot.api.packets.WidgetPackets
import dev.hoot.api.widgets.Widgets
import meteor.Main
import meteor.model.ObfuscatedBufferStructure
import meteor.rs.ClientThread.invoke
import net.runelite.api.MenuAction
import net.runelite.api.packets.PacketBuffer
import net.runelite.api.packets.PacketBufferNode
import net.runelite.api.widgets.Widget
import net.runelite.api.widgets.WidgetType


object ClientPackets {

    // OPLOC1
    // param0: worldX
    // param1: worldY
    fun createGroundItemAction1Packet(groundItemId: Int, worldX: Int, worldY: Int, shiftPressed: Boolean): PacketBufferNode {
        val bufferNode = preparePacketBuffer(16, 7)
        bufferNode.packetBuffer.`writeShortLE$api`(worldY)
        bufferNode.packetBuffer.`writeByte$api`(if (shiftPressed) 1 else 0)
        bufferNode.packetBuffer.writeShortAddLE(groundItemId)
        bufferNode.packetBuffer.`writeShortLE$api`(worldX)
        return bufferNode
    }

    // OPLOC2
    // param0: worldX
    // param1: worldY
    fun createGroundItemAction2Packet(groundItemId: Int, worldX: Int, worldY: Int, shiftPressed: Boolean): PacketBufferNode {
        val bufferNode = preparePacketBuffer(59, 7)
        bufferNode.packetBuffer.`writeShortLE$api`(groundItemId)
        bufferNode.packetBuffer.writeByteAdd(if (shiftPressed) 1 else 0)
        bufferNode.packetBuffer.`writeShort$api`(worldY)
        bufferNode.packetBuffer.writeShortAdd(worldX)
        return bufferNode
    }

    // OPLOC3
    // param0: worldX
    // param1: worldY
    fun createGroundItemAction3Packet(groundItemId: Int, worldX: Int, worldY: Int, shiftPressed: Boolean): PacketBufferNode {
        val bufferNode = preparePacketBuffer(89, 7)
        bufferNode.packetBuffer.writeShortAdd(worldX)
        bufferNode.packetBuffer.`writeShortLE$api`(groundItemId)
        bufferNode.packetBuffer.`writeShortLE$api`(worldY)
        bufferNode.packetBuffer.writeByteAdd(if (shiftPressed) 1 else 0)
        return bufferNode
    }

    // OPLOC4
    // param0: worldX
    // param1: worldY
    fun createGroundItemAction4Packet(groundItemId: Int, worldX: Int, worldY: Int, shiftPressed: Boolean): PacketBufferNode {
        val bufferNode = preparePacketBuffer(36, 7)
        bufferNode.packetBuffer.writeShortAdd(worldX)
        bufferNode.packetBuffer.writeByteSub(if (shiftPressed) 1 else 0)
        bufferNode.packetBuffer.`writeShort$api`(worldY)
        bufferNode.packetBuffer.`writeShort$api`(groundItemId)
        return bufferNode
    }

    // OPLOC5
    // param0: worldX
    // param1: worldY
    fun createGroundItemAction5Packet(groundItemId: Int, worldX: Int, worldY: Int, shiftPressed: Boolean): PacketBufferNode {
        val bufferNode = preparePacketBuffer(72, 7)
        bufferNode.packetBuffer.writeShortAddLE(worldY)
        bufferNode.packetBuffer.`writeShort$api`(worldX)
        bufferNode.packetBuffer.writeShortAdd(groundItemId)
        bufferNode.packetBuffer.writeByteAdd(if (shiftPressed) 1 else 0)
        return bufferNode
    }

    // OPHELD1
    // param0: widgetId
    // param1: itemSlot
    fun createItemAction1Packet(itemId: Int, worldX: Int, worldY: Int): PacketBufferNode {
        val bufferNode = preparePacketBuffer(48, 8)
        bufferNode.packetBuffer.`writeShortLE$api`(worldX)
        bufferNode.packetBuffer.`writeIntME$api`(worldY)
        bufferNode.packetBuffer.writeShortAdd(itemId)
        return bufferNode
    }

    //OPHELD2
    // param0: widgetId
    // param1: itemSlot
    fun createItemAction2Packet(itemId: Int, worldX: Int, worldY: Int): PacketBufferNode {
        val bufferNode = preparePacketBuffer(54, 8)
        bufferNode.packetBuffer.writeShortAddLE(itemId)
        bufferNode.packetBuffer.`writeIntLE$api`(worldY)
        bufferNode.packetBuffer.writeShortAdd(worldX)
        return bufferNode
    }

    //OPHELD3
    // param0: widgetId
    // param1: itemSlot
    fun createItemAction3Packet(itemId: Int, worldX: Int, worldY: Int): PacketBufferNode {
        val bufferNode = preparePacketBuffer(46, 8)
        bufferNode.packetBuffer.`writeInt$api`(worldY)
        bufferNode.packetBuffer.writeShortAdd(itemId)
        bufferNode.packetBuffer.writeShortAdd(worldX)
        return bufferNode
    }

    //OPHELD4
    // param0: widgetId
    // param1: itemSlot
    fun createItemAction4Packet(itemId: Int, worldX: Int, worldY: Int): PacketBufferNode {
        val bufferNode = preparePacketBuffer(56, 8)
        bufferNode.packetBuffer.`writeIntME$api`(worldY)
        bufferNode.packetBuffer.`writeShort$api`(worldX)
        bufferNode.packetBuffer.writeShortAddLE(itemId)
        return bufferNode
    }

    //OPHELD5
    // param0: widgetId
    // param1: itemSlot
    fun createItemAction5Packet(itemId: Int, worldX: Int, worldY: Int): PacketBufferNode {
        val bufferNode = preparePacketBuffer(57, 8)
        bufferNode.packetBuffer.`writeIntME$api`(worldY)
        bufferNode.packetBuffer.`writeShort$api`(itemId)
        bufferNode.packetBuffer.writeShortAdd(worldX)
        return bufferNode
    }

    // OPOBJ1
    // param0: worldX
    // param1: worldY
    fun createObjectAction1Packet(
        objectID: Int,
        worldX: Int,
        worldY: Int,
        shiftPressed: Boolean
    ): PacketBufferNode {
        val bufferNode = preparePacketBuffer(70, 7)
        bufferNode.packetBuffer.`writeByte$api`(if (shiftPressed) 1 else 0)
        bufferNode.packetBuffer.writeShortAddLE(worldX)
        bufferNode.packetBuffer.writeShortAdd(worldY)
        bufferNode.packetBuffer.`writeShort$api`(objectID)
        return bufferNode
    }

    // OPOBJ2
    // param0: worldX
    // param1: worldY
    fun createObjectAction2Packet(
        objectID: Int,
        worldX: Int,
        worldY: Int,
        shiftPressed: Boolean
    ): PacketBufferNode {
        val bufferNode = preparePacketBuffer(53, 7)
        bufferNode.packetBuffer.writeByteAdd(if (shiftPressed) 1 else 0)
        bufferNode.packetBuffer.`writeShortLE$api`(objectID)
        bufferNode.packetBuffer.writeShortAddLE(worldY)
        bufferNode.packetBuffer.`writeShortLE$api`(worldX)
        return bufferNode
    }

    // OPOBJ3
    // param0: worldX
    // param1: worldY
    fun createObjectAction3Packet(
        objectID: Int,
        worldX: Int,
        worldY: Int,
        shiftPressed: Boolean
    ): PacketBufferNode {
        val bufferNode = preparePacketBuffer(33, 7)
        bufferNode.packetBuffer.writeByteAdd(if (shiftPressed) 1 else 0)
        bufferNode.packetBuffer.writeShortAddLE(worldY)
        bufferNode.packetBuffer.writeShortAdd(worldX)
        bufferNode.packetBuffer.writeShortAdd(objectID)
        return bufferNode
    }

    // OPOBJ4
    // param0: worldX
    // param1: worldY
    fun createObjectAction4Packet(
        objectID: Int,
        worldX: Int,
        worldY: Int,
        shiftPressed: Boolean
    ): PacketBufferNode {
        val bufferNode = preparePacketBuffer(8, 7)
        bufferNode.packetBuffer.`writeShort$api`(objectID)
        bufferNode.packetBuffer.writeByteNeg(if (shiftPressed) 1 else 0)
        bufferNode.packetBuffer.writeShortAddLE(worldY)
        bufferNode.packetBuffer.`writeShortLE$api`(worldX)
        return bufferNode
    }

    // OPOBJ5
    // param0: worldX
    // param1: worldY
    fun createObjectAction5Packet(
        objectID: Int,
        worldX: Int,
        worldY: Int,
        shiftPressed: Boolean
    ): PacketBufferNode {
        val bufferNode = preparePacketBuffer(72, 7)
        bufferNode.packetBuffer.writeShortAddLE(worldY)
        bufferNode.packetBuffer.`writeShort$api`(worldX)
        bufferNode.packetBuffer.writeShortAdd(objectID)
        bufferNode.packetBuffer.writeByteAdd(if (shiftPressed) 1 else 0)
        return bufferNode
    }

    // OPOBJT
    // param0: worldX
    // param1: worldY
    fun createItemWidgetOnGameObjectPacket(
        objectID: Int,
        worldX: Int,
        worldY: Int,
        itemSlot: Int,
        itemId: Int,
        itemWidgetID: Int,
        shiftPressed: Boolean
    ): PacketBufferNode {
        val bufferNode = preparePacketBuffer(105, 15)
        bufferNode.packetBuffer.writeShortAddLE(worldX)
        bufferNode.packetBuffer.`writeShortLE$api`(itemSlot)
        bufferNode.packetBuffer.writeIntIME(itemWidgetID)
        bufferNode.packetBuffer.writeShortAdd(itemId)
        bufferNode.packetBuffer.`writeShort$api`(worldY)
        bufferNode.packetBuffer.writeByteNeg(if (shiftPressed) 1 else 0)
        bufferNode.packetBuffer.writeShortAdd(objectID)
        return bufferNode
    }

    //OPNPCT
    fun createItemWidgetOnNPCPacket(
        npcIdx: Int,
        itemWidgetID: Int,
        itemID: Int,
        itemSlot: Int,
        shiftPressed: Boolean
    ): PacketBufferNode {
        val bufferNode = preparePacketBuffer(14, 11)
        bufferNode.packetBuffer.writeShortAdd(itemSlot)
        bufferNode.packetBuffer.writeShortAddLE(npcIdx)
        bufferNode.packetBuffer.writeIntIME(itemWidgetID)
        bufferNode.packetBuffer.writeShortAdd(itemID)
        bufferNode.packetBuffer.writeByteSub(if (shiftPressed) 1 else 0)
        return bufferNode
    }

    //OPNPC1
    fun createNPCAction1Packet(npcIdx: Int, shiftPressed: Boolean): PacketBufferNode {
        val bufferNode = preparePacketBuffer(90, 3)
        bufferNode.packetBuffer.`writeShortLE$api`(npcIdx)
        bufferNode.packetBuffer.writeByteAdd(if (shiftPressed) 1 else 0)
        return bufferNode
    }

    //OPNPC2
    fun createNPCAction2Packet(npcIdx: Int, shiftPressed: Boolean): PacketBufferNode {
        val bufferNode = preparePacketBuffer(21, 3)
        bufferNode.packetBuffer.writeShortAddLE(npcIdx)
        bufferNode.packetBuffer.`writeByte$api`(if (shiftPressed) 1 else 0)
        return bufferNode
    }

    //OPNPC3
    fun createNPCAction3Packet(npcIdx: Int, shiftPressed: Boolean): PacketBufferNode {
        val bufferNode = preparePacketBuffer(34, 3)
        bufferNode.packetBuffer.writeShortAdd(npcIdx)
        bufferNode.packetBuffer.`writeByte$api`(if (shiftPressed) 1 else 0)
        return bufferNode
    }

    //OPNPC4
    fun createNPCAction4Packet(npcIdx: Int, shiftPressed: Boolean): PacketBufferNode {
        val bufferNode = preparePacketBuffer(55, 3)
        bufferNode.packetBuffer.writeByteNeg(if (shiftPressed) 1 else 0)
        bufferNode.packetBuffer.`writeShort$api`(npcIdx)
        return bufferNode
    }

    //OPNPC5
    fun createNPCAction5Packet(npcIdx: Int, shiftPressed: Boolean): PacketBufferNode {
        val bufferNode = preparePacketBuffer(20, 3)
        bufferNode.packetBuffer.writeShortAddLE(npcIdx)
        bufferNode.packetBuffer.writeByteNeg(if (shiftPressed) 1 else 0)
        return bufferNode
    }

    //OPPLAYERT
    fun createItemWidgetOnPlayerPacket(
        playerIdx: Int,
        itemId: Int,
        itemSlot: Int,
        itemWidgetID: Int,
        shiftPressed: Boolean
    ): PacketBufferNode {
        val bufferNode = preparePacketBuffer(0, 11)
        bufferNode.packetBuffer.`writeShort$api`(playerIdx)
        bufferNode.packetBuffer.writeByteAdd(if (shiftPressed) 1 else 0)
        bufferNode.packetBuffer.`writeShortLE$api`(itemSlot)
        bufferNode.packetBuffer.`writeInt$api`(itemWidgetID)
        bufferNode.packetBuffer.writeShortAddLE(itemId)
        return bufferNode
    }

    ///OPPLAYER1
    fun createPlayerAction1Packet(playerIdx: Int, shiftPressed: Boolean): PacketBufferNode {
        val bufferNode = preparePacketBuffer(44, 3)
        bufferNode.packetBuffer.`writeByte$api`(if (shiftPressed) 1 else 0)
        bufferNode.packetBuffer.`writeShort$api`(playerIdx)
        return bufferNode
    }

    ///OPPLAYER2
    fun createPlayerAction2Packet(playerIdx: Int, shiftPressed: Boolean): PacketBufferNode {
        val bufferNode = preparePacketBuffer(100, 3)
        bufferNode.packetBuffer.writeShortAdd(playerIdx)
        bufferNode.packetBuffer.`writeByte$api`(if (shiftPressed) 1 else 0)
        return bufferNode
    }

    ///OPPLAYER3
    fun createPlayerAction3Packet(playerIdx: Int, shiftPressed: Boolean): PacketBufferNode {
        val bufferNode = preparePacketBuffer(87, 3)
        bufferNode.packetBuffer.`writeByte$api`(if (shiftPressed) 1 else 0)
        bufferNode.packetBuffer.writeShortAddLE(playerIdx)
        return bufferNode
    }

    ///OPPLAYER4
    fun createPlayerAction4Packet(playerIdx: Int, shiftPressed: Boolean): PacketBufferNode {
        val bufferNode = preparePacketBuffer(47, 3)
        bufferNode.packetBuffer.`writeShort$api`(playerIdx)
        bufferNode.packetBuffer.writeByteSub(if (shiftPressed) 1 else 0)
        return bufferNode
    }

    ///OPPLAYER5
    fun createPlayerAction5Packet(playerIdx: Int, shiftPressed: Boolean): PacketBufferNode {
        val bufferNode = preparePacketBuffer(41, 3)
        bufferNode.packetBuffer.`writeShortLE$api`(playerIdx)
        bufferNode.packetBuffer.writeByteSub(if (shiftPressed) 1 else 0)
        return bufferNode
    }

    ///OPPLAYER6
    fun createPlayerAction6Packet(playerIdx: Int, shiftPressed: Boolean): PacketBufferNode {
        val bufferNode = preparePacketBuffer(19, 3)
        bufferNode.packetBuffer.writeByteSub(if (shiftPressed) 1 else 0)
        bufferNode.packetBuffer.`writeShort$api`(playerIdx)
        return bufferNode
    }

    ///OPPLAYER7
    fun createPlayerAction7Packet(playerIdx: Int, shiftPressed: Boolean): PacketBufferNode {
        val bufferNode = preparePacketBuffer(39, 3)
        bufferNode.packetBuffer.writeShortAddLE(playerIdx)
        bufferNode.packetBuffer.writeByteSub(if (shiftPressed) 1 else 0)
        return bufferNode
    }

    ///OPPLAYER8
    fun createPlayerAction8Packet(playerIdx: Int, shiftPressed: Boolean): PacketBufferNode {
        val bufferNode = preparePacketBuffer(73, 3)
        bufferNode.packetBuffer.writeShortAddLE(playerIdx)
        bufferNode.packetBuffer.writeByteAdd(if (shiftPressed) 1 else 0)
        return bufferNode
    }

    //OPLOCT
    fun createItemWidgetOnGroundItemPacket(
        groundItemID: Int,
        worldX: Int,
        worldY: Int,
        itemSlot: Int,
        itemID: Int,
        itemWidgetID: Int,
        shiftPressed: Boolean
    ): PacketBufferNode {
        val bufferNode = preparePacketBuffer(17, 15)
        bufferNode.packetBuffer.`writeShort$api`(worldY)
        bufferNode.packetBuffer.`writeShort$api`(itemSlot)
        bufferNode.packetBuffer.`writeIntME$api`(itemWidgetID)
        bufferNode.packetBuffer.writeShortAddLE(worldX)
        bufferNode.packetBuffer.`writeShort$api`(groundItemID)
        bufferNode.packetBuffer.writeByteAdd(if (shiftPressed) 1 else 0)
        bufferNode.packetBuffer.`writeShort$api`(itemID)
        return bufferNode
    }

    //IF_BUTTONT
    // param0: destinationSlot
    // param1: destinationWidgetId
    fun createItemWidgetOnItemWidgetPacket(
        sourceWidgetId: Int,
        sourceSlot: Int,
        sourceItemId: Int,
        destinationWidgetId: Int,
        destinationSlot: Int,
        destinationItemId: Int
    ): PacketBufferNode {
        val bufferNode = preparePacketBuffer(15, 16)
        bufferNode.packetBuffer.`writeShortLE$api`(sourceItemId)
        bufferNode.packetBuffer.`writeInt$api`(destinationWidgetId)
        bufferNode.packetBuffer.`writeIntME$api`(sourceWidgetId)
        bufferNode.packetBuffer.writeShortAddLE(sourceSlot)
        bufferNode.packetBuffer.`writeShortLE$api`(destinationSlot)
        bufferNode.packetBuffer.writeShortAddLE(destinationItemId)
        return bufferNode
    }

    // IF_BUTTON1
    // param0: childId
    // param1: widgetId
    fun createWidgetAction1Packet(
        widgetId: Int,
        itemId: Int,
        childId: Int,
    ): PacketBufferNode {
        val bufferNode = preparePacketBuffer(13, 8)
        bufferNode.packetBuffer.`writeInt$api`(widgetId)
        bufferNode.packetBuffer.`writeShort$api`(childId)
        bufferNode.packetBuffer.`writeShort$api`(itemId)
        return bufferNode
    }

    // IF_BUTTON2
    // param0: childId
    // param1: widgetId
    fun createWidgetAction2Packet(
        widgetId: Int,
        itemId: Int,
        childId: Int,
    ): PacketBufferNode {
        val bufferNode = preparePacketBuffer(97, 8)
        bufferNode.packetBuffer.`writeInt$api`(widgetId)
        bufferNode.packetBuffer.`writeShort$api`(childId)
        bufferNode.packetBuffer.`writeShort$api`(itemId)
        return bufferNode
    }

    // IF_BUTTON3
    // param0: childId
    // param1: widgetId
    fun createWidgetAction3Packet(
        widgetId: Int,
        itemId: Int,
        childId: Int,
    ): PacketBufferNode {
        val bufferNode = preparePacketBuffer(23, 8)
        bufferNode.packetBuffer.`writeInt$api`(widgetId)
        bufferNode.packetBuffer.`writeShort$api`(childId)
        bufferNode.packetBuffer.`writeShort$api`(itemId)
        return bufferNode
    }

    // IF_BUTTON4
    // param0: childId
    // param1: widgetId
    fun createWidgetAction4Packet(
        widgetId: Int,
        itemId: Int,
        childId: Int,
    ): PacketBufferNode {
        val bufferNode = preparePacketBuffer(81, 8)
        bufferNode.packetBuffer.`writeInt$api`(widgetId)
        bufferNode.packetBuffer.`writeShort$api`(childId)
        bufferNode.packetBuffer.`writeShort$api`(itemId)
        return bufferNode
    }

    // IF_BUTTON5
    // param0: childId
    // param1: widgetId
    fun createWidgetAction5Packet(
        widgetId: Int,
        itemId: Int,
        childId: Int,
    ): PacketBufferNode {
        val bufferNode = preparePacketBuffer(25, 8)
        bufferNode.packetBuffer.`writeInt$api`(widgetId)
        bufferNode.packetBuffer.`writeShort$api`(childId)
        bufferNode.packetBuffer.`writeShort$api`(itemId)
        return bufferNode
    }

    // IF_BUTTON6
    // param0: childId
    // param1: widgetId
    fun createWidgetAction6Packet(
        widgetId: Int,
        itemId: Int,
        childId: Int,
    ): PacketBufferNode {
        val bufferNode = preparePacketBuffer(51, 8)
        bufferNode.packetBuffer.`writeInt$api`(widgetId)
        bufferNode.packetBuffer.`writeShort$api`(childId)
        bufferNode.packetBuffer.`writeShort$api`(itemId)
        return bufferNode
    }

    // IF_BUTTON7
    // param0: childId
    // param1: widgetId
    fun createWidgetAction7Packet(
        widgetId: Int,
        itemId: Int,
        childId: Int,
    ): PacketBufferNode {
        val bufferNode = preparePacketBuffer(32, 8)
        bufferNode.packetBuffer.`writeInt$api`(widgetId)
        bufferNode.packetBuffer.`writeShort$api`(childId)
        bufferNode.packetBuffer.`writeShort$api`(itemId)
        return bufferNode
    }

    // IF_BUTTON8
    // param0: childId
    // param1: widgetId
    fun createWidgetAction8Packet(
        widgetId: Int,
        itemId: Int,
        childId: Int,
    ): PacketBufferNode {
        val bufferNode = preparePacketBuffer(27, 8)
        bufferNode.packetBuffer.`writeInt$api`(widgetId)
        bufferNode.packetBuffer.`writeShort$api`(childId)
        bufferNode.packetBuffer.`writeShort$api`(itemId)
        return bufferNode
    }

    // IF_BUTTON9
    // param0: childId
    // param1: widgetId
    fun createWidgetAction9Packet(
        widgetId: Int,
        itemId: Int,
        childId: Int,
    ): PacketBufferNode {
        val bufferNode = preparePacketBuffer(67, 8)
        bufferNode.packetBuffer.`writeInt$api`(widgetId)
        bufferNode.packetBuffer.`writeShort$api`(childId)
        bufferNode.packetBuffer.`writeShort$api`(itemId)
        return bufferNode
    }

    // IF_BUTTON10
    // param0: childId
    // param1: widgetId
    fun createWidgetAction10Packet(
        widgetId: Int,
        itemId: Int,
        childId: Int,
    ): PacketBufferNode {
        val bufferNode = preparePacketBuffer(29, 8)
        bufferNode.packetBuffer.`writeInt$api`(widgetId)
        bufferNode.packetBuffer.`writeShort$api`(childId)
        bufferNode.packetBuffer.`writeShort$api`(itemId)
        return bufferNode
    }

    fun createClickPacket(mouseInfo: Int, x: Int, y: Int): PacketBufferNode {
        val bufferNode = preparePacketBuffer(58, 6)
        bufferNode.packetBuffer.`writeShort$api`(mouseInfo)
        bufferNode.packetBuffer.`writeShort$api`(x)
        bufferNode.packetBuffer.`writeShort$api`(y)
        return bufferNode
    }

    fun createMovementPacket(worldX: Int, worldY: Int, shiftPressed: Boolean): PacketBufferNode {
        val bufferNode = preparePacketBuffer(92, -1)
        bufferNode.packetBuffer.`writeByte$api`(5)
        bufferNode.packetBuffer.writeByteSub(if (shiftPressed) 1 else 0)
        bufferNode.packetBuffer.`writeShortLE$api`(worldX)
        bufferNode.packetBuffer.writeShortAdd(worldY)
        return bufferNode
    }

    fun createContinuePacket(widgetId: Int, childId: Int): PacketBufferNode {
        val bufferNode = preparePacketBuffer(9, 6)
        bufferNode.packetBuffer.writeShortAdd(childId)
        bufferNode.packetBuffer.`writeInt$api`(widgetId)
        return bufferNode
    }

    fun preparePacketBuffer(opcode: Int, size: Int): PacketBufferNode {
        val clientPacket = Main.client.createClientPacket(opcode, size)
        return Main.client.preparePacket(
            clientPacket,
            Main.client.packetWriter.isaacCipher
        )
    }

    fun queueClickPacket(mouseInfo: Int, x: Int, y: Int) {
        invoke { createClickPacket(mouseInfo, x, y).send() }
    }

    fun queueClickPacket(x: Int, y: Int) {
        Main.client.mouseLastPressedMillis = System.currentTimeMillis()
        var mousePressedTime =
            Main.client.mouseLastPressedMillis - Main.client.clientMouseLastPressedMillis
        if (mousePressedTime < 0) {
            mousePressedTime = 0
        }
        if (mousePressedTime > 32767) {
            mousePressedTime = 32767
        }
        Main.client.clientMouseLastPressedMillis = Main.client.mouseLastPressedMillis
        val mouseInfo = (mousePressedTime shl 1) + 1
        queueClickPacket(mouseInfo.toInt(), x, y)
    }

    fun createClientPacket(menu: AutomatedMenu): PacketBufferNode? {
        val opcode = menu.opcode
        val client = Main.client
        val id = menu.identifier
        val param0 = menu.param0
        val param1 = menu.param1
        var selectedWidgetItemId = client.selectedSpellItemId
        var selectedWidgetSlot = client.selectedSpellChildIndex
        // Yes, keeping both in case of a future fix in naming
        val selectedWidget = client.selectedSpellWidget

        when (opcode) {
            MenuAction.ITEM_USE_ON_GAME_OBJECT, MenuAction.WIDGET_TARGET_ON_GAME_OBJECT -> return createItemWidgetOnGameObjectPacket(
                id,
                param0 + client.baseX,
                param1 + client.baseY,
                selectedWidgetSlot,
                selectedWidgetItemId,
                selectedWidget,
                false
            )
            MenuAction.GAME_OBJECT_FIRST_OPTION -> return createObjectAction1Packet(
                id,
                param0 + client.baseX,
                param1 + client.baseY,
                false
            )
            MenuAction.GAME_OBJECT_SECOND_OPTION -> return createObjectAction2Packet(
                id,
                param0 + client.baseX,
                param1 + client.baseY,
                false
            )
            MenuAction.GAME_OBJECT_THIRD_OPTION -> return createObjectAction3Packet(
                id,
                param0 + client.baseX,
                param1 + client.baseY,
                false
            )
            MenuAction.GAME_OBJECT_FOURTH_OPTION -> return createObjectAction4Packet(
                id,
                param0 + client.baseX,
                param1 + client.baseY,
                false
            )
            MenuAction.GAME_OBJECT_FIFTH_OPTION -> return createObjectAction5Packet(
                id,
                param0 + client.baseX,
                param1 + client.baseY,
                false
            )
            MenuAction.ITEM_USE_ON_NPC, MenuAction.WIDGET_TARGET_ON_NPC -> return createItemWidgetOnNPCPacket(
                id,
                selectedWidget,
                selectedWidgetItemId,
                selectedWidgetSlot,
                false)
            MenuAction.NPC_FIRST_OPTION -> return createNPCAction1Packet(id, false)
            MenuAction.NPC_SECOND_OPTION -> return createNPCAction2Packet(id, false)
            MenuAction.NPC_THIRD_OPTION -> return createNPCAction3Packet(id, false)
            MenuAction.NPC_FOURTH_OPTION -> return createNPCAction4Packet(id, false)
            MenuAction.NPC_FIFTH_OPTION -> return createNPCAction5Packet(id, false)
            MenuAction.ITEM_USE_ON_PLAYER, MenuAction.WIDGET_TARGET_ON_PLAYER -> return createItemWidgetOnPlayerPacket(
                id,
                selectedWidgetItemId,
                selectedWidgetSlot,
                selectedWidget,
                false)
            MenuAction.PLAYER_FIRST_OPTION -> return createPlayerAction1Packet(id, false)
            MenuAction.PLAYER_SECOND_OPTION -> return createPlayerAction2Packet(id, false)
            MenuAction.PLAYER_THIRD_OPTION -> return createPlayerAction3Packet(id, false)
            MenuAction.PLAYER_FOURTH_OPTION -> return createPlayerAction4Packet(id, false)
            MenuAction.PLAYER_FIFTH_OPTION -> return createPlayerAction5Packet(id, false)
            MenuAction.PLAYER_SIXTH_OPTION -> return createPlayerAction6Packet(id, false)
            MenuAction.PLAYER_SEVENTH_OPTION -> return createPlayerAction7Packet(id, false)
            MenuAction.PLAYER_EIGTH_OPTION -> return createPlayerAction8Packet(id, false)
            MenuAction.ITEM_USE_ON_GROUND_ITEM, MenuAction.WIDGET_TARGET_ON_GROUND_ITEM -> return createItemWidgetOnGroundItemPacket(
                id,
                param0 + client.baseX,
                param1 + client.baseY,
                selectedWidgetSlot,
                selectedWidgetItemId,
                selectedWidget,
                false
            )
            MenuAction.GROUND_ITEM_FIRST_OPTION -> return createGroundItemAction1Packet(
                id,
                param0 + client.baseX,
                param1 + client.baseY,
                false
            )
            MenuAction.GROUND_ITEM_SECOND_OPTION -> return createGroundItemAction2Packet(
                id,
                param0 + client.baseX,
                param1 + client.baseY,
                false
            )
            MenuAction.GROUND_ITEM_THIRD_OPTION -> return createGroundItemAction3Packet(
                id,
                param0 + client.baseX,
                param1 + client.baseY,
                false
            )
            MenuAction.GROUND_ITEM_FOURTH_OPTION -> return createGroundItemAction4Packet(
                id,
                param0 + client.baseX,
                param1 + client.baseY,
                false
            )
            MenuAction.GROUND_ITEM_FIFTH_OPTION -> return createGroundItemAction5Packet(
                id,
                param0 + client.baseX,
                param1 + client.baseY,
                false
            )
            MenuAction.WIDGET_USE_ON_ITEM, MenuAction.ITEM_USE_ON_ITEM, MenuAction.WIDGET_TARGET_ON_WIDGET -> {
                val targetParent: Widget? = Widgets.fromId(param1)
                val targetChild: Widget? = targetParent?.getChild(param0)
                var childItemId = -1
                if (targetChild != null) {
                    childItemId = targetChild.itemId
                }
                val source: Widget = client.getWidget(selectedWidget)!!
                if (source.type == WidgetType.GRAPHIC) {
                    selectedWidgetSlot = -1
                    selectedWidgetItemId = -1
                }
                return createItemWidgetOnItemWidgetPacket(
                    selectedWidget,
                    selectedWidgetSlot,
                    selectedWidgetItemId,
                    param1,
                    param0,
                    childItemId)
            }
            MenuAction.ITEM_FIRST_OPTION -> return createItemAction1Packet(
                param1,
                id,
                param0
            )
            MenuAction.ITEM_SECOND_OPTION -> return createItemAction2Packet(
                param1,
                id,
                param0
            )
            MenuAction.ITEM_THIRD_OPTION -> return createItemAction3Packet(
                param1,
                id,
                param0
            )
            MenuAction.ITEM_FOURTH_OPTION -> return createItemAction4Packet(
                param1,
                id,
                param0
            )
            MenuAction.ITEM_FIFTH_OPTION -> return createItemAction5Packet(
                param1,
                id,
                param0
            )
            MenuAction.WIDGET_FIRST_OPTION -> return createWidgetAction1Packet(
                param1,
                -1,
                param0
            )
            MenuAction.WIDGET_SECOND_OPTION -> return createWidgetAction2Packet(
                param1,
                -1,
                param0
            )
            MenuAction.WIDGET_THIRD_OPTION -> return createWidgetAction3Packet(
                param1,
                -1,
                param0
            )
            MenuAction.WIDGET_FOURTH_OPTION -> return createWidgetAction4Packet(
                param1,
                -1,
                param0
            )
            MenuAction.WIDGET_FIFTH_OPTION -> return createWidgetAction5Packet(
                param1,
                -1,
                param0
            )
            MenuAction.WIDGET_TYPE_1 -> return WidgetPackets.createType1Action(param1)
            MenuAction.WIDGET_CONTINUE -> return WidgetPackets.createContinuePacket(param1, param0)
            MenuAction.WALK -> {
                client.destinationX = param0
                client.destinationY = param1
                return createMovementPacket(
                    param0 + client.baseX,
                    param1 + client.baseY,
                    false
                )
            }
            MenuAction.CC_OP_LOW_PRIORITY,
            MenuAction.CC_OP -> {
                val widget = Widgets.fromId(param1) ?: return null
                val child = (if (param0 == -1) null else widget.getChild(param0))
                    ?: return WidgetPackets.createDefaultAction(id, param1, -1, param0)
                return WidgetPackets.createDefaultAction(id, param1, child.itemId, param0)
            }
            else -> {}
        }
        throw InteractionException("Couldn't parse packet from opcode: $opcode")
    }
}