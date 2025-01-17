import net.runelite.mapping.Export;
import net.runelite.mapping.Implements;
import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;

@ObfuscatedName("aj")
@Implements("SoundCache")
public class SoundCache {
   @ObfuscatedName("g")
   static int field219;
   @ObfuscatedName("h")
   @ObfuscatedSignature(
      descriptor = "Lly;"
   )
   AbstractArchive soundEffectIndex;
   @ObfuscatedName("e")
   @ObfuscatedSignature(
      descriptor = "Lly;"
   )
   AbstractArchive musicSampleIndex;
   @ObfuscatedName("v")
   @ObfuscatedSignature(
      descriptor = "Lqp;"
   )
   NodeHashTable musicSamples = new NodeHashTable(256);
   @ObfuscatedName("x")
   @ObfuscatedSignature(
      descriptor = "Lqp;"
   )
   NodeHashTable rawSounds = new NodeHashTable(256);

   @ObfuscatedSignature(
      descriptor = "(Lly;Lly;)V"
   )
   public SoundCache(AbstractArchive var1, AbstractArchive var2) {
      this.soundEffectIndex = var1;
      this.musicSampleIndex = var2;
   }

   @ObfuscatedName("h")
   @ObfuscatedSignature(
      descriptor = "(II[IB)Laf;",
      garbageValue = "1"
   )
   RawSound getSoundEffect0(int var1, int var2, int[] var3) {
      int var4 = var2 ^ (var1 << 4 & '\uffff' | var1 >>> 12);
      var4 |= var1 << 16;
      long var5 = (long)var4;
      RawSound var7 = (RawSound)this.rawSounds.get(var5);
      if (var7 != null) {
         return var7;
      } else if (var3 != null && var3[0] <= 0) {
         return null;
      } else {
         SoundEffect var8 = SoundEffect.readSoundEffect(this.soundEffectIndex, var1, var2);
         if (var8 == null) {
            return null;
         } else {
            var7 = var8.toRawSound();
            this.rawSounds.put(var7, var5);
            if (var3 != null) {
               var3[0] -= var7.samples.length;
            }

            return var7;
         }
      }
   }

   @ObfuscatedName("e")
   @ObfuscatedSignature(
      descriptor = "(II[II)Laf;",
      garbageValue = "-1966397833"
   )
   RawSound getMusicSample0(int var1, int var2, int[] var3) {
      int var4 = var2 ^ (var1 << 4 & '\uffff' | var1 >>> 12);
      var4 |= var1 << 16;
      long var5 = (long)var4 ^ 4294967296L;
      RawSound var7 = (RawSound)this.rawSounds.get(var5);
      if (var7 != null) {
         return var7;
      } else if (var3 != null && var3[0] <= 0) {
         return null;
      } else {
         VorbisSample var8 = (VorbisSample)this.musicSamples.get(var5);
         if (var8 == null) {
            var8 = VorbisSample.readMusicSample(this.musicSampleIndex, var1, var2);
            if (var8 == null) {
               return null;
            }

            this.musicSamples.put(var8, var5);
         }

         var7 = var8.toRawSound(var3);
         if (var7 == null) {
            return null;
         } else {
            var8.remove();
            this.rawSounds.put(var7, var5);
            return var7;
         }
      }
   }

   @ObfuscatedName("v")
   @ObfuscatedSignature(
      descriptor = "(I[II)Laf;",
      garbageValue = "112511764"
   )
   public RawSound getSoundEffect(int var1, int[] var2) {
      if (this.soundEffectIndex.getGroupCount() == 1) {
         return this.getSoundEffect0(0, var1, var2);
      } else if (this.soundEffectIndex.getGroupFileCount(var1) == 1) {
         return this.getSoundEffect0(var1, 0, var2);
      } else {
         throw new RuntimeException();
      }
   }

   @ObfuscatedName("x")
   @ObfuscatedSignature(
      descriptor = "(I[II)Laf;",
      garbageValue = "1767210019"
   )
   public RawSound getMusicSample(int var1, int[] var2) {
      if (this.musicSampleIndex.getGroupCount() == 1) {
         return this.getMusicSample0(0, var1, var2);
      } else if (this.musicSampleIndex.getGroupFileCount(var1) == 1) {
         return this.getMusicSample0(var1, 0, var2);
      } else {
         throw new RuntimeException();
      }
   }

   @ObfuscatedName("as")
   @ObfuscatedSignature(
      descriptor = "(ILbm;ZI)I",
      garbageValue = "-390039727"
   )
   static int method244(int var0, Script var1, boolean var2) {
      int var3;
      int var4;
      int var6;
      if (var0 == 3400) {
         class87.Interpreter_intStackSize -= 2;
         var3 = Interpreter.Interpreter_intStack[class87.Interpreter_intStackSize];
         var4 = Interpreter.Interpreter_intStack[class87.Interpreter_intStackSize + 1];
         EnumComposition var5 = class87.getEnum(var3);
         if (var5.outputType != 's') {
            ;
         }

         for(var6 = 0; var6 < var5.outputCount; ++var6) {
            if (var4 == var5.keys[var6]) {
               Interpreter.Interpreter_stringStack[++Interpreter.Interpreter_stringStackSize - 1] = var5.strVals[var6];
               var5 = null;
               break;
            }
         }

         if (var5 != null) {
            Interpreter.Interpreter_stringStack[++Interpreter.Interpreter_stringStackSize - 1] = var5.defaultStr;
         }

         return 1;
      } else if (var0 != 3408) {
         if (var0 == 3411) {
            var3 = Interpreter.Interpreter_intStack[--class87.Interpreter_intStackSize];
            EnumComposition var10 = class87.getEnum(var3);
            Interpreter.Interpreter_intStack[++class87.Interpreter_intStackSize - 1] = var10.size();
            return 1;
         } else {
            return 2;
         }
      } else {
         class87.Interpreter_intStackSize -= 4;
         var3 = Interpreter.Interpreter_intStack[class87.Interpreter_intStackSize];
         var4 = Interpreter.Interpreter_intStack[class87.Interpreter_intStackSize + 1];
         int var9 = Interpreter.Interpreter_intStack[class87.Interpreter_intStackSize + 2];
         var6 = Interpreter.Interpreter_intStack[class87.Interpreter_intStackSize + 3];
         EnumComposition var7 = class87.getEnum(var9);
         if (var3 == var7.inputType && var4 == var7.outputType) {
            for(int var8 = 0; var8 < var7.outputCount; ++var8) {
               if (var6 == var7.keys[var8]) {
                  if (var4 == 115) {
                     Interpreter.Interpreter_stringStack[++Interpreter.Interpreter_stringStackSize - 1] = var7.strVals[var8];
                  } else {
                     Interpreter.Interpreter_intStack[++class87.Interpreter_intStackSize - 1] = var7.intVals[var8];
                  }

                  var7 = null;
                  break;
               }
            }

            if (var7 != null) {
               if (var4 == 115) {
                  Interpreter.Interpreter_stringStack[++Interpreter.Interpreter_stringStackSize - 1] = var7.defaultStr;
               } else {
                  Interpreter.Interpreter_intStack[++class87.Interpreter_intStackSize - 1] = var7.defaultInt;
               }
            }

            return 1;
         } else {
            if (var4 == 115) {
               Interpreter.Interpreter_stringStack[++Interpreter.Interpreter_stringStackSize - 1] = "null";
            } else {
               Interpreter.Interpreter_intStack[++class87.Interpreter_intStackSize - 1] = 0;
            }

            return 1;
         }
      }
   }

   @ObfuscatedName("io")
   @ObfuscatedSignature(
      descriptor = "(B)Z",
      garbageValue = "-1"
   )
   static boolean method243() {
      return (Client.drawPlayerNames & 1) != 0;
   }

   @ObfuscatedName("mg")
   @ObfuscatedSignature(
      descriptor = "(Lkd;IIII)V",
      garbageValue = "-1447783720"
   )
   static final void drawCompass(Widget var0, int var1, int var2, int var3) {
      SpriteMask var4 = var0.getSpriteMask(false);
      if (var4 != null) {
         if (Client.minimapState < 3) {
            class201.compass.drawRotatedMaskedCenteredAround(var1, var2, var4.width, var4.height, 25, 25, Client.camAngleY, 256, var4.xStarts, var4.xWidths);
         } else {
            Rasterizer2D.Rasterizer2D_fillMaskedRectangle(var1, var2, 0, var4.xStarts, var4.xWidths);
         }

      }
   }
}
