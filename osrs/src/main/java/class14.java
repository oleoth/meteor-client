import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import net.runelite.mapping.Export;
import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;

@ObfuscatedName("n")
public class class14 {
   @ObfuscatedName("hc")
   @ObfuscatedSignature(
      descriptor = "Lou;"
   )
   static AbstractSocket field42;
   @ObfuscatedName("kn")
   static int oculusOrbFocalPointY;
   @ObfuscatedName("h")
   final int field40;
   @ObfuscatedName("e")
   final String field44;
   @ObfuscatedName("v")
   final ThreadFactory field41;
   @ObfuscatedName("x")
   final ThreadPoolExecutor field45;

   public class14(String var1, int var2, int var3) {
      this.field44 = var1;
      this.field40 = var2;
      this.field41 = new class16(this);
      this.field45 = this.method46(var3);
   }

   @ObfuscatedName("h")
   @ObfuscatedSignature(
      descriptor = "(II)Ljava/util/concurrent/ThreadPoolExecutor;",
      garbageValue = "496995616"
   )
   final ThreadPoolExecutor method46(int var1) {
      return new ThreadPoolExecutor(var1, var1, 0L, TimeUnit.MILLISECONDS, new ArrayBlockingQueue(this.field40), this.field41);
   }

   @ObfuscatedName("e")
   @ObfuscatedSignature(
      descriptor = "(Lj;B)Lt;",
      garbageValue = "-99"
   )
   public class19 method47(class10 var1) {
      if (this.field45.getQueue().remainingCapacity() <= 0) {
         System.err.println("REST thread pool queue is empty\r\nThread pool size " + this.field45.getCorePoolSize() + " Queue capacity " + this.field40);
         return new class19("Queue full");
      } else {
         class19 var2 = new class19(this.field45.submit(new class20(this, var1)));
         return var2;
      }
   }

   @ObfuscatedName("v")
   @ObfuscatedSignature(
      descriptor = "(I)V",
      garbageValue = "1559640620"
   )
   public final void method50() {
      try {
         this.field45.shutdown();
      } catch (Exception var2) {
         System.err.println("Error shutting down RestRequestService\r\n" + var2);
      }

   }

   @ObfuscatedName("x")
   @ObfuscatedSignature(
      descriptor = "([BIIII[Lgw;I)V",
      garbageValue = "2031044103"
   )
   static final void method48(byte[] var0, int var1, int var2, int var3, int var4, CollisionMap[] var5) {
      int var7;
      int var8;
      for(int var6 = 0; var6 < 4; ++var6) {
         for(var7 = 0; var7 < 64; ++var7) {
            for(var8 = 0; var8 < 64; ++var8) {
               if (var7 + var1 > 0 && var7 + var1 < 103 && var8 + var2 > 0 && var8 + var2 < 103) {
                  var5[var6].flags[var7 + var1][var2 + var8] &= -16777217;
               }
            }
         }
      }

      Buffer var12 = new Buffer(var0);

      for(var7 = 0; var7 < 4; ++var7) {
         for(var8 = 0; var8 < 64; ++var8) {
            for(int var9 = 0; var9 < 64; ++var9) {
               int var10 = var8 + var1;
               int var11 = var9 + var2;
               Messages.loadTerrain(var12, var7, var10, var11, var3 + var10, var11 + var4, 0);
            }
         }
      }

   }

   @ObfuscatedName("t")
   @ObfuscatedSignature(
      descriptor = "(ILbm;ZB)I",
      garbageValue = "39"
   )
   static int method51(int var0, Script var1, boolean var2) {
      Widget var3 = ObjTypeCustomisation.getWidget(Interpreter.Interpreter_intStack[--class87.Interpreter_intStackSize]);
      if (var0 == 2500) {
         Interpreter.Interpreter_intStack[++class87.Interpreter_intStackSize - 1] = var3.x;
         return 1;
      } else if (var0 == 2501) {
         Interpreter.Interpreter_intStack[++class87.Interpreter_intStackSize - 1] = var3.y;
         return 1;
      } else if (var0 == 2502) {
         Interpreter.Interpreter_intStack[++class87.Interpreter_intStackSize - 1] = var3.width;
         return 1;
      } else if (var0 == 2503) {
         Interpreter.Interpreter_intStack[++class87.Interpreter_intStackSize - 1] = var3.height;
         return 1;
      } else if (var0 == 2504) {
         Interpreter.Interpreter_intStack[++class87.Interpreter_intStackSize - 1] = var3.isHidden ? 1 : 0;
         return 1;
      } else if (var0 == 2505) {
         Interpreter.Interpreter_intStack[++class87.Interpreter_intStackSize - 1] = var3.parentId;
         return 1;
      } else {
         return 2;
      }
   }

   @ObfuscatedName("lq")
   @ObfuscatedSignature(
      descriptor = "(II)V",
      garbageValue = "1631694430"
   )
   static final void method49(int var0) {
      var0 = Math.max(Math.min(var0, 100), 0);
      var0 = 100 - var0;
      float var1 = (float)var0 / 200.0F + 0.5F;
      FileSystem.method879((double)var1);
   }
}
