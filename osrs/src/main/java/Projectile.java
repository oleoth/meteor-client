import java.lang.management.GarbageCollectorMXBean;

import net.runelite.mapping.Implements;
import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;

@ObfuscatedName("bj")
@Implements("Projectile")
public final class Projectile extends Renderable {
   @ObfuscatedName("aq")
   static GarbageCollectorMXBean garbageCollector;
   @ObfuscatedName("h")
   int id;
   @ObfuscatedName("e")
   int plane;
   @ObfuscatedName("v")
   int sourceX;
   @ObfuscatedName("x")
   int sourceY;
   @ObfuscatedName("m")
   int sourceZ;
   @ObfuscatedName("q")
   int endHeight;
   @ObfuscatedName("f")
   int cycleStart;
   @ObfuscatedName("r")
   int cycleEnd;
   @ObfuscatedName("u")
   int slope;
   @ObfuscatedName("b")
   int startHeight;
   @ObfuscatedName("j")
   int targetIndex;
   @ObfuscatedName("g")
   boolean isMoving = false;
   @ObfuscatedName("i")
   double x;
   @ObfuscatedName("o")
   double y;
   @ObfuscatedName("n")
   double z;
   @ObfuscatedName("k")
   double speedX;
   @ObfuscatedName("a")
   double speedY;
   @ObfuscatedName("s")
   double speed;
   @ObfuscatedName("l")
   double speedZ;
   @ObfuscatedName("t")
   double accelerationZ;
   @ObfuscatedName("c")
   int yaw;
   @ObfuscatedName("p")
   int pitch;
   @ObfuscatedName("d")
   @ObfuscatedSignature(
      descriptor = "Lga;"
   )
   SequenceDefinition sequenceDefinition;
   @ObfuscatedName("y")
   int frame = 0;
   @ObfuscatedName("z")
   int frameCycle = 0;

   Projectile(int var1, int var2, int var3, int var4, int var5, int var6, int var7, int var8, int var9, int var10, int var11) {
      this.id = var1;
      this.plane = var2;
      this.sourceX = var3;
      this.sourceY = var4;
      this.sourceZ = var5;
      this.cycleStart = var6;
      this.cycleEnd = var7;
      this.slope = var8;
      this.startHeight = var9;
      this.targetIndex = var10;
      this.endHeight = var11;
      this.isMoving = false;
      int var12 = ClanSettings.SpotAnimationDefinition_get(this.id).sequence;
      if (var12 != -1) {
         this.sequenceDefinition = AABB.SequenceDefinition_get(var12);
      } else {
         this.sequenceDefinition = null;
      }

   }

   @ObfuscatedName("h")
   @ObfuscatedSignature(
      descriptor = "(IIIII)V",
      garbageValue = "1027863856"
   )
   final void setDestination(int var1, int var2, int var3, int var4) {
      double var5;
      if (!this.isMoving) {
         var5 = (double)(var1 - this.sourceX);
         double var7 = (double)(var2 - this.sourceY);
         double var9 = Math.sqrt(var7 * var7 + var5 * var5);
         this.x = (double)this.sourceX + var5 * (double)this.startHeight / var9;
         this.y = (double)this.sourceY + (double)this.startHeight * var7 / var9;
         this.z = (double)this.sourceZ;
      }

      var5 = (double)(this.cycleEnd + 1 - var4);
      this.speedX = ((double)var1 - this.x) / var5;
      this.speedY = ((double)var2 - this.y) / var5;
      this.speed = Math.sqrt(this.speedY * this.speedY + this.speedX * this.speedX);
      if (!this.isMoving) {
         this.speedZ = -this.speed * Math.tan((double)this.slope * 0.02454369D);
      }

      this.accelerationZ = 2.0D * ((double)var3 - this.z - var5 * this.speedZ) / (var5 * var5);
   }

   @ObfuscatedName("e")
   @ObfuscatedSignature(
      descriptor = "(I)Lhh;",
      garbageValue = "-1279733976"
   )
   protected final Model getModel() {
      SpotAnimationDefinition var1 = ClanSettings.SpotAnimationDefinition_get(this.id);
      Model var2 = var1.getModel(this.frame);
      if (var2 == null) {
         return null;
      } else {
         var2.rotateZ(this.pitch);
         return var2;
      }
   }

   @ObfuscatedName("m")
   @ObfuscatedSignature(
      descriptor = "(II)V",
      garbageValue = "2050320762"
   )
   final void advance(int var1) {
      this.isMoving = true;
      this.x += (double)var1 * this.speedX;
      this.y += (double)var1 * this.speedY;
      this.z += (double)var1 * this.speedZ + (double)var1 * 0.5D * this.accelerationZ * (double)var1;
      this.speedZ += (double)var1 * this.accelerationZ;
      this.yaw = (int)(Math.atan2(this.speedX, this.speedY) * 325.949D) + 1024 & 2047;
      this.pitch = (int)(Math.atan2(this.speedZ, this.speed) * 325.949D) & 2047;
      if (this.sequenceDefinition != null) {
         if (!this.sequenceDefinition.isCachedModelIdSet()) {
            this.frameCycle += var1;

            while(true) {
               do {
                  do {
                     if (this.frameCycle <= this.sequenceDefinition.frameLengths[this.frame]) {
                        return;
                     }

                     this.frameCycle -= this.sequenceDefinition.frameLengths[this.frame];
                     ++this.frame;
                  } while(this.frame < this.sequenceDefinition.frameIds.length);

                  this.frame -= this.sequenceDefinition.frameCount;
               } while(this.frame >= 0 && this.frame < this.sequenceDefinition.frameIds.length);

               this.frame = 0;
            }
         } else {
            this.frame += var1;
            int var2 = this.sequenceDefinition.method1056();
            if (this.frame >= var2) {
               this.frame = var2 - this.sequenceDefinition.frameCount;
            }
         }
      }

   }

   @ObfuscatedName("m")
   @ObfuscatedSignature(
      descriptor = "(Lly;Ljava/lang/String;Ljava/lang/String;I)Lra;",
      garbageValue = "-1993204368"
   )
   public static IndexedSprite SpriteBuffer_getIndexedSpriteByName(AbstractArchive var0, String var1, String var2) {
      int var3 = var0.getGroupId(var1);
      int var4 = var0.getFileId(var3, var2);
      byte[] var7 = var0.takeFile(var3, var4);
      boolean var6;
      if (var7 == null) {
         var6 = false;
      } else {
         VarpDefinition.SpriteBuffer_decode(var7);
         var6 = true;
      }

      IndexedSprite var5;
      if (!var6) {
         var5 = null;
      } else {
         IndexedSprite var8 = new IndexedSprite();
         var8.width = GrandExchangeOfferTotalQuantityComparator.SpriteBuffer_spriteWidth;
         var8.height = class481.SpriteBuffer_spriteHeight;
         var8.xOffset = class481.SpriteBuffer_xOffsets[0];
         var8.yOffset = class414.SpriteBuffer_yOffsets[0];
         var8.subWidth = class11.SpriteBuffer_spriteWidths[0];
         var8.subHeight = StructComposition.SpriteBuffer_spriteHeights[0];
         var8.palette = WorldMapEvent.SpriteBuffer_spritePalette;
         var8.pixels = FloorDecoration.SpriteBuffer_pixels[0];
         class481.SpriteBuffer_xOffsets = null;
         class414.SpriteBuffer_yOffsets = null;
         class11.SpriteBuffer_spriteWidths = null;
         StructComposition.SpriteBuffer_spriteHeights = null;
         WorldMapEvent.SpriteBuffer_spritePalette = null;
         FloorDecoration.SpriteBuffer_pixels = null;
         var5 = var8;
      }

      return var5;
   }

   @ObfuscatedName("q")
   @ObfuscatedSignature(
      descriptor = "(IB)I",
      garbageValue = "-2"
   )
   public static int method439(int var0) {
      return Occluder.Entity_unpackID(ViewportMouse.ViewportMouse_entityTags[var0]);
   }

   @ObfuscatedName("hb")
   @ObfuscatedSignature(
      descriptor = "(IIIII)V",
      garbageValue = "2029958408"
   )
   static final void drawEntities(int var0, int var1, int var2, int var3) {
      ++Client.viewportDrawCount;
      GrandExchangeOfferUnitPriceComparator.method1845();
      UserComparator3.method670();
      if (Client.combatTargetPlayerIndex >= 0 && Client.players[Client.combatTargetPlayerIndex] != null) {
         WorldMapAreaData.addPlayerToScene(Client.players[Client.combatTargetPlayerIndex], false);
      }

      DevicePcmPlayerProvider.addNpcsToScene(true);
      class281.method1531();
      DevicePcmPlayerProvider.addNpcsToScene(false);
      class31.method125();

      for(GraphicsObject var4 = (GraphicsObject)Client.graphicsObjects.last(); var4 != null; var4 = (GraphicsObject)Client.graphicsObjects.previous()) {
         if (var4.plane == class383.Client_plane && !var4.isFinished) {
            if (Client.cycle >= var4.cycleStart) {
               var4.advance(Client.field408);
               if (var4.isFinished) {
                  var4.remove();
               } else {
                  ReflectionCheck.scene.drawEntity(var4.plane, var4.x, var4.y, var4.z, 60, var4, 0, -1L, false);
               }
            }
         } else {
            var4.remove();
         }
      }

      class143.setViewportShape(var0, var1, var2, var3, true);
      var0 = Client.viewportOffsetX;
      var1 = Client.viewportOffsetY;
      var2 = Client.viewportWidth;
      var3 = Client.viewportHeight;
      Rasterizer2D.Rasterizer2D_setClip(var0, var1, var0 + var2, var3 + var1);
      Rasterizer3D.Rasterizer3D_setClipFromRasterizer2D();
      int var35 = Client.camAngleX;
      if (Client.field449 / 256 > var35) {
         var35 = Client.field449 / 256;
      }

      if (Client.cameraShaking[4] && Client.cameraMoveIntensity[4] + 128 > var35) {
         var35 = Client.cameraMoveIntensity[4] + 128;
      }

      int var5 = Client.camAngleY & 2047;
      int var6 = class29.oculusOrbFocalPointX;
      int var7 = ApproximateRouteStrategy.field347;
      int var8 = class14.oculusOrbFocalPointY;
      int var9 = var35 * 3 + 600;
      int var12 = var3 - 334;
      if (var12 < 0) {
         var12 = 0;
      } else if (var12 > 100) {
         var12 = 100;
      }

      int var13 = (Client.zoomWidth - Client.zoomHeight) * var12 / 100 + Client.zoomHeight;
      int var11 = var13 * var9 / 256;
      var12 = 2048 - var35 & 2047;
      var13 = 2048 - var5 & 2047;
      int var14 = 0;
      int var15 = 0;
      int var16 = var11;
      int var17;
      int var18;
      int var19;
      if (var12 != 0) {
         var17 = Rasterizer3D.Rasterizer3D_sine[var12];
         var18 = Rasterizer3D.Rasterizer3D_cosine[var12];
         var19 = var18 * var15 - var11 * var17 >> 16;
         var16 = var17 * var15 + var18 * var11 >> 16;
         var15 = var19;
      }

      if (var13 != 0) {
         var17 = Rasterizer3D.Rasterizer3D_sine[var13];
         var18 = Rasterizer3D.Rasterizer3D_cosine[var13];
         var19 = var16 * var17 + var14 * var18 >> 16;
         var16 = var18 * var16 - var17 * var14 >> 16;
         var14 = var19;
      }

      if (Client.isCameraLocked) {
         class102.field1084 = var6 - var14;
         Frames.field2063 = var7 - var15;
         MusicPatchNode2.field2708 = var8 - var16;
         NPCComposition.field1594 = var35;
         class101.field1083 = var5;
      } else {
         class145.cameraX = var6 - var14;
         class414.cameraY = var7 - var15;
         ClanChannel.cameraZ = var8 - var16;
         class97.cameraPitch = var35;
         class128.cameraYaw = var5;
      }

      if (Client.oculusOrbState == 1 && Client.staffModLevel >= 2 && Client.cycle % 50 == 0 && (class29.oculusOrbFocalPointX >> 7 != class155.localPlayer.x >> 7 || class14.oculusOrbFocalPointY >> 7 != class155.localPlayer.y >> 7)) {
         var17 = class155.localPlayer.plane;
         var18 = (class29.oculusOrbFocalPointX >> 7) + class154.baseX;
         var19 = (class14.oculusOrbFocalPointY >> 7) + class365.baseY;
         PacketBufferNode var20 = class136.getPacketBufferNode(ClientPacket.field2504, Client.packetWriter.isaacCipher);
         var20.packetBuffer.writeShortLE(var19);
         var20.packetBuffer.writeByteA(var17);
         var20.packetBuffer.writeShortA(var18);
         var20.packetBuffer.method2383(Client.field634);
         Client.packetWriter.addNode(var20);
      }

      if (!Client.isCameraLocked) {
         var11 = class163.method865();
      } else {
         var11 = SecureRandomFuture.method444();
      }

      var12 = class145.cameraX;
      var13 = class414.cameraY;
      var14 = ClanChannel.cameraZ;
      var15 = class97.cameraPitch;
      var16 = class128.cameraYaw;

      for(var17 = 0; var17 < 5; ++var17) {
         if (Client.cameraShaking[var17]) {
            var18 = (int)(Math.random() * (double)(Client.cameraShakeIntensity[var17] * 2 + 1) - (double)Client.cameraShakeIntensity[var17] + Math.sin((double)Client.cameraShakeCycle[var17] * ((double)Client.cameraShakeSpeed[var17] / 100.0D)) * (double)Client.cameraMoveIntensity[var17]);
            if (var17 == 0) {
               class145.cameraX += var18;
            }

            if (var17 == 1) {
               class414.cameraY += var18;
            }

            if (var17 == 2) {
               ClanChannel.cameraZ += var18;
            }

            if (var17 == 3) {
               class128.cameraYaw = var18 + class128.cameraYaw & 2047;
            }

            if (var17 == 4) {
               class97.cameraPitch += var18;
               if (class97.cameraPitch < 128) {
                  class97.cameraPitch = 128;
               }

               if (class97.cameraPitch > 383) {
                  class97.cameraPitch = 383;
               }
            }
         }
      }

      var17 = MouseHandler.MouseHandler_x;
      var18 = MouseHandler.MouseHandler_y;
      if (MouseHandler.MouseHandler_lastButton != 0) {
         var17 = MouseHandler.MouseHandler_lastPressedX;
         var18 = MouseHandler.MouseHandler_lastPressedY;
      }

      if (var17 >= var0 && var17 < var0 + var2 && var18 >= var1 && var18 < var3 + var1) {
         var19 = var17 - var0;
         int var36 = var18 - var1;
         ViewportMouse.ViewportMouse_x = var19;
         ViewportMouse.ViewportMouse_y = var36;
         ViewportMouse.ViewportMouse_isInViewport = true;
         ViewportMouse.ViewportMouse_entityCount = 0;
         ViewportMouse.ViewportMouse_false0 = false;
      } else {
         class17.method56();
      }

      Client.playPcmPlayers();
      Rasterizer2D.Rasterizer2D_fillRectangle(var0, var1, var2, var3, 0);
      Client.playPcmPlayers();
      var19 = Rasterizer3D.Rasterizer3D_zoom;
      Rasterizer3D.Rasterizer3D_zoom = Client.viewportZoom;
      ReflectionCheck.scene.draw(class145.cameraX, class414.cameraY, ClanChannel.cameraZ, class97.cameraPitch, class128.cameraYaw, var11);
      Rasterizer3D.Rasterizer3D_zoom = var19;
      Client.playPcmPlayers();
      ReflectionCheck.scene.clearTempGameObjects();
      Client.overheadTextCount = 0;
      boolean var40 = false;
      int var21 = -1;
      int var22 = -1;
      int var23 = Players.Players_count;
      int[] var24 = Players.Players_indices;

      int var25;
      for(var25 = 0; var25 < var23 + Client.npcCount; ++var25) {
         Object var26;
         if (var25 < var23) {
            var26 = Client.players[var24[var25]];
            if (var24[var25] == Client.combatTargetPlayerIndex) {
               var40 = true;
               var21 = var25;
               continue;
            }

            if (var26 == class155.localPlayer) {
               var22 = var25;
               continue;
            }
         } else {
            var26 = Client.npcs[Client.npcIndices[var25 - var23]];
         }

         class128.drawActor2d((Actor)var26, var25, var0, var1, var2, var3);
      }

      if (Client.renderSelf && var22 != -1) {
         class128.drawActor2d(class155.localPlayer, var22, var0, var1, var2, var3);
      }

      if (var40) {
         class128.drawActor2d(Client.players[Client.combatTargetPlayerIndex], var21, var0, var1, var2, var3);
      }

      for(var25 = 0; var25 < Client.overheadTextCount; ++var25) {
         int var37 = Client.overheadTextXs[var25];
         int var27 = Client.overheadTextYs[var25];
         int var28 = Client.overheadTextXOffsets[var25];
         int var29 = Client.overheadTextAscents[var25];
         boolean var30 = true;

         while(var30) {
            var30 = false;

            for(int var31 = 0; var31 < var25; ++var31) {
               if (var27 + 2 > Client.overheadTextYs[var31] - Client.overheadTextAscents[var31] && var27 - var29 < Client.overheadTextYs[var31] + 2 && var37 - var28 < Client.overheadTextXOffsets[var31] + Client.overheadTextXs[var31] && var28 + var37 > Client.overheadTextXs[var31] - Client.overheadTextXOffsets[var31] && Client.overheadTextYs[var31] - Client.overheadTextAscents[var31] < var27) {
                  var27 = Client.overheadTextYs[var31] - Client.overheadTextAscents[var31];
                  var30 = true;
               }
            }
         }

         Client.viewportTempX = Client.overheadTextXs[var25];
         Client.viewportTempY = Client.overheadTextYs[var25] = var27;
         String var38 = Client.overheadText[var25];
         if (Client.chatEffects == 0) {
            int var32 = 16776960;
            if (Client.overheadTextColors[var25] < 6) {
               var32 = Client.field586[Client.overheadTextColors[var25]];
            }

            if (Client.overheadTextColors[var25] == 6) {
               var32 = Client.viewportDrawCount % 20 < 10 ? 16711680 : 16776960;
            }

            if (Client.overheadTextColors[var25] == 7) {
               var32 = Client.viewportDrawCount % 20 < 10 ? 255 : '\uffff';
            }

            if (Client.overheadTextColors[var25] == 8) {
               var32 = Client.viewportDrawCount % 20 < 10 ? '뀀' : 8454016;
            }

            int var33;
            if (Client.overheadTextColors[var25] == 9) {
               var33 = 150 - Client.overheadTextCyclesRemaining[var25];
               if (var33 < 50) {
                  var32 = var33 * 1280 + 16711680;
               } else if (var33 < 100) {
                  var32 = 16776960 - (var33 - 50) * 327680;
               } else if (var33 < 150) {
                  var32 = (var33 - 100) * 5 + '\uff00';
               }
            }

            if (Client.overheadTextColors[var25] == 10) {
               var33 = 150 - Client.overheadTextCyclesRemaining[var25];
               if (var33 < 50) {
                  var32 = var33 * 5 + 16711680;
               } else if (var33 < 100) {
                  var32 = 16711935 - (var33 - 50) * 327680;
               } else if (var33 < 150) {
                  var32 = (var33 - 100) * 327680 + 255 - (var33 - 100) * 5;
               }
            }

            if (Client.overheadTextColors[var25] == 11) {
               var33 = 150 - Client.overheadTextCyclesRemaining[var25];
               if (var33 < 50) {
                  var32 = 16777215 - var33 * 327685;
               } else if (var33 < 100) {
                  var32 = (var33 - 50) * 327685 + '\uff00';
               } else if (var33 < 150) {
                  var32 = 16777215 - (var33 - 100) * 327680;
               }
            }

            if (Client.overheadTextEffects[var25] == 0) {
               class146.fontBold12.drawCentered(var38, var0 + Client.viewportTempX, Client.viewportTempY + var1, var32, 0);
            }

            if (Client.overheadTextEffects[var25] == 1) {
               class146.fontBold12.drawCenteredWave(var38, var0 + Client.viewportTempX, Client.viewportTempY + var1, var32, 0, Client.viewportDrawCount);
            }

            if (Client.overheadTextEffects[var25] == 2) {
               class146.fontBold12.drawCenteredWave2(var38, var0 + Client.viewportTempX, Client.viewportTempY + var1, var32, 0, Client.viewportDrawCount);
            }

            if (Client.overheadTextEffects[var25] == 3) {
               class146.fontBold12.drawCenteredShake(var38, var0 + Client.viewportTempX, Client.viewportTempY + var1, var32, 0, Client.viewportDrawCount, 150 - Client.overheadTextCyclesRemaining[var25]);
            }

            if (Client.overheadTextEffects[var25] == 4) {
               var33 = (150 - Client.overheadTextCyclesRemaining[var25]) * (class146.fontBold12.stringWidth(var38) + 100) / 150;
               Rasterizer2D.Rasterizer2D_expandClip(var0 + Client.viewportTempX - 50, var1, var0 + Client.viewportTempX + 50, var3 + var1);
               class146.fontBold12.draw(var38, var0 + Client.viewportTempX + 50 - var33, Client.viewportTempY + var1, var32, 0);
               Rasterizer2D.Rasterizer2D_setClip(var0, var1, var0 + var2, var3 + var1);
            }

            if (Client.overheadTextEffects[var25] == 5) {
               var33 = 150 - Client.overheadTextCyclesRemaining[var25];
               int var34 = 0;
               if (var33 < 25) {
                  var34 = var33 - 25;
               } else if (var33 > 125) {
                  var34 = var33 - 125;
               }

               Rasterizer2D.Rasterizer2D_expandClip(var0, Client.viewportTempY + var1 - class146.fontBold12.ascent - 1, var0 + var2, Client.viewportTempY + var1 + 5);
               class146.fontBold12.drawCentered(var38, var0 + Client.viewportTempX, var34 + Client.viewportTempY + var1, var32, 0);
               Rasterizer2D.Rasterizer2D_setClip(var0, var1, var0 + var2, var3 + var1);
            }
         } else {
            class146.fontBold12.drawCentered(var38, var0 + Client.viewportTempX, Client.viewportTempY + var1, 16776960, 0);
         }
      }

      RouteStrategy.method1103(var0, var1);
      ((TextureProvider)Rasterizer3D.Rasterizer3D_textureLoader).animate(Client.field408);
      CollisionMap.method1100();
      class145.cameraX = var12;
      class414.cameraY = var13;
      ClanChannel.cameraZ = var14;
      class97.cameraPitch = var15;
      class128.cameraYaw = var16;
      if (Client.isLoading) {
         byte var39 = 0;
         var21 = var39 + NetCache.NetCache_pendingPriorityWritesCount + NetCache.NetCache_pendingPriorityResponsesCount;
         if (var21 == 0) {
            Client.isLoading = false;
         }
      }

      if (Client.isLoading) {
         Rasterizer2D.Rasterizer2D_fillRectangle(var0, var1, var2, var3, 0);
         SoundSystem.drawLoadingMessage("Loading - please wait.", false);
      }

   }
}
