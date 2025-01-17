import java.applet.Applet;
import java.net.URL;
import net.runelite.mapping.Export;
import net.runelite.mapping.Implements;
import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;
import netscape.javascript.JSObject;

@ObfuscatedName("cu")
@Implements("HealthBarUpdate")
public class HealthBarUpdate extends Node {
   @ObfuscatedName("iz")
   static int selectedItemId;
   @ObfuscatedName("h")
   int cycle;
   @ObfuscatedName("e")
   int health;
   @ObfuscatedName("v")
   int health2;
   @ObfuscatedName("x")
   int cycleOffset;

   HealthBarUpdate(int var1, int var2, int var3, int var4) {
      this.cycle = var1;
      this.health = var2;
      this.health2 = var3;
      this.cycleOffset = var4;
   }

   @ObfuscatedName("h")
   @ObfuscatedSignature(
      descriptor = "(IIIII)V",
      garbageValue = "839931958"
   )
   void set(int var1, int var2, int var3, int var4) {
      this.cycle = var1;
      this.health = var2;
      this.health2 = var3;
      this.cycleOffset = var4;
   }

   @ObfuscatedName("e")
   @ObfuscatedSignature(
      descriptor = "(IB)Lqm;",
      garbageValue = "0"
   )
   public static DbRowType getDbRowType(int var0) {
      DbRowType var1 = (DbRowType)DbRowType.DBRowType_cache.get((long)var0);
      if (var1 != null) {
         return var1;
      } else {
         byte[] var2 = DbRowType.field3979.takeFile(38, var0);
         var1 = new DbRowType();
         if (var2 != null) {
            var1.method2318(new Buffer(var2));
         }

         var1.method2319();
         DbRowType.DBRowType_cache.put(var1, (long)var0);
         return var1;
      }
   }

   @ObfuscatedName("v")
   @ObfuscatedSignature(
      descriptor = "(Ljava/lang/String;ILjava/lang/String;I)Z",
      garbageValue = "-1242120679"
   )
   static boolean method517(String var0, int var1, String var2) {
      if (var1 == 0) {
         try {
            if (!class32.field87.startsWith("win")) {
               throw new Exception();
            } else if (!var0.startsWith("http://") && !var0.startsWith("https://")) {
               throw new Exception();
            } else {
               String var13 = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789?&=,.%+-_#:/*";

               for(int var4 = 0; var4 < var0.length(); ++var4) {
                  if (var13.indexOf(var0.charAt(var4)) == -1) {
                     throw new Exception();
                  }
               }

               Runtime.getRuntime().exec("cmd /c start \"j\" \"" + var0 + "\"");
               return true;
            }
         } catch (Throwable var8) {
            return false;
         }
      } else if (var1 == 1) {
         try {
            Applet var7 = class32.field86;
            Object[] var5 = new Object[]{(new URL(class32.field86.getCodeBase(), var0)).toString()};
            Object var3 = null;
            return var3 != null;
         } catch (Throwable var9) {
            return false;
         }
      } else if (var1 == 2) {
         try {
            class32.field86.getAppletContext().showDocument(new URL(class32.field86.getCodeBase(), var0), "_blank");
            return true;
         } catch (Exception var10) {
            return false;
         }
      } else if (var1 == 3) {
         try {
            class27.method102(class32.field86, "loggedout");
         } catch (Throwable var12) {
            ;
         }

         try {
            class32.field86.getAppletContext().showDocument(new URL(class32.field86.getCodeBase(), var0), "_top");
            return true;
         } catch (Exception var11) {
            return false;
         }
      } else {
         throw new IllegalArgumentException();
      }
   }

   @ObfuscatedName("bh")
   @ObfuscatedSignature(
      descriptor = "(ILbm;ZB)I",
      garbageValue = "59"
   )
   static int method520(int var0, Script var1, boolean var2) {
      return 2;
   }
}
