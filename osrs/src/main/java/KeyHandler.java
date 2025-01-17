import java.awt.Component;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import net.runelite.mapping.Export;
import net.runelite.mapping.Implements;
import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;

@ObfuscatedName("as")
@Implements("KeyHandler")
public class KeyHandler implements KeyListener, FocusListener {
   @ObfuscatedName("e")
   Collection field74 = new ArrayList(100);
   @ObfuscatedName("v")
   Collection field75 = new ArrayList(100);
   @ObfuscatedName("x")
   @ObfuscatedSignature(
      descriptor = "[Lav;"
   )
   class30[] field73 = new class30[3];
   @ObfuscatedName("m")
   boolean[] KeyHandler_pressedKeys = new boolean[112];
   @ObfuscatedName("q")
   volatile int KeyHandler_idleCycles = 0;

   @ObfuscatedName("h")
   @ObfuscatedSignature(
      descriptor = "(Lav;II)V",
      garbageValue = "1136972282"
   )
   void method92(class30 var1, int var2) {
      this.field73[var2] = var1;
   }

   @ObfuscatedName("e")
   @ObfuscatedSignature(
      descriptor = "(I)I",
      garbageValue = "-1025402851"
   )
   int getIdleCycles() {
      return this.KeyHandler_idleCycles;
   }

   @ObfuscatedName("v")
   @ObfuscatedSignature(
      descriptor = "(Ljava/awt/Component;I)V",
      garbageValue = "-1611687696"
   )
   void method94(Component var1) {
      var1.setFocusTraversalKeysEnabled(false);
      var1.addKeyListener(this);
      var1.addFocusListener(this);
   }

   @ObfuscatedName("x")
   @ObfuscatedSignature(
      descriptor = "(Ljava/awt/Component;I)V",
      garbageValue = "-972177183"
   )
   synchronized void method91(Component var1) {
      var1.removeKeyListener(this);
      var1.removeFocusListener(this);
      synchronized(this) {
         this.field74.add(new class34(4, 0));
      }
   }

   @ObfuscatedName("m")
   @ObfuscatedSignature(
      descriptor = "(B)V",
      garbageValue = "38"
   )
   void method96() {
      ++this.KeyHandler_idleCycles;
      this.method95();
      Iterator var1 = this.field75.iterator();

      while(var1.hasNext()) {
         class34 var2 = (class34)var1.next();

         for(int var3 = 0; var3 < this.field73.length && !var2.method139(this.field73[var3]); ++var3) {
            ;
         }
      }

      this.field75.clear();
   }

   @ObfuscatedName("q")
   @ObfuscatedSignature(
      descriptor = "(I)V",
      garbageValue = "-999573246"
   )
   synchronized void method95() {
      Collection var1 = this.field75;
      this.field75 = this.field74;
      this.field74 = var1;
   }

   @ObfuscatedName("keyPressed")
   public final synchronized void keyPressed(KeyEvent var1) {
      int var2;
      label23: {
         var2 = var1.getKeyCode();
         if (var2 >= 0) {
            int var4 = class29.KeyHandler_keyCodes.length;
            if (var2 < var4) {
               var2 = class140.method754(var2);
               boolean var5 = (var2 & 128) != 0;
               if (var5) {
                  var2 = -1;
               }
               break label23;
            }
         }

         var2 = -1;
      }

      if (var2 >= 0) {
         this.KeyHandler_pressedKeys[var2] = true;
         this.field74.add(new class34(1, var2));
         this.KeyHandler_idleCycles = 0;
      }

      var1.consume();
   }

   @ObfuscatedName("keyReleased")
   public final synchronized void keyReleased(KeyEvent var1) {
      int var2;
      label16: {
         var2 = var1.getKeyCode();
         if (var2 >= 0) {
            int var4 = class29.KeyHandler_keyCodes.length;
            if (var2 < var4) {
               var2 = class140.method754(var2) & -129;
               break label16;
            }
         }

         var2 = -1;
      }

      if (var2 >= 0) {
         this.KeyHandler_pressedKeys[var2] = false;
         this.field74.add(new class34(2, var2));
      }

      var1.consume();
   }

   @ObfuscatedName("keyTyped")
   public final synchronized void keyTyped(KeyEvent var1) {
      char var2 = var1.getKeyChar();
      if (var2 != 0 && var2 != '\uffff') {
         boolean var3;
         if ((var2 <= 0 || var2 >= 128) && (var2 < 160 || var2 > 255)) {
            label47: {
               if (var2 != 0) {
                  char[] var4 = class358.cp1252AsciiExtension;

                  for(int var5 = 0; var5 < var4.length; ++var5) {
                     char var6 = var4[var5];
                     if (var6 == var2) {
                        var3 = true;
                        break label47;
                     }
                  }
               }

               var3 = false;
            }
         } else {
            var3 = true;
         }

         if (var3) {
            this.field74.add(new class34(3, var2));
         }
      }

      var1.consume();
   }

   public final synchronized void focusGained(FocusEvent var1) {
      this.field74.add(new class34(4, 1));
   }

   @ObfuscatedName("focusLost")
   public final synchronized void focusLost(FocusEvent var1) {
      for(int var2 = 0; var2 < 112; ++var2) {
         if (this.KeyHandler_pressedKeys[var2]) {
            this.KeyHandler_pressedKeys[var2] = false;
            this.field74.add(new class34(2, var2));
         }
      }

      this.field74.add(new class34(4, 0));
   }

   @ObfuscatedName("m")
   @ObfuscatedSignature(
      descriptor = "([BIIIIIIIII[Lgw;B)V",
      garbageValue = "-63"
   )
   static final void method97(byte[] var0, int var1, int var2, int var3, int var4, int var5, int var6, int var7, int var8, int var9, CollisionMap[] var10) {
      int var12;
      for(int var11 = 0; var11 < 8; ++var11) {
         for(var12 = 0; var12 < 8; ++var12) {
            if (var11 + var2 > 0 && var11 + var2 < 103 && var3 + var12 > 0 && var3 + var12 < 103) {
               var10[var1].flags[var11 + var2][var3 + var12] &= -16777217;
            }
         }
      }

      Buffer var24 = new Buffer(var0);

      for(var12 = 0; var12 < 4; ++var12) {
         for(int var13 = 0; var13 < 64; ++var13) {
            for(int var14 = 0; var14 < 64; ++var14) {
               if (var12 == var4 && var13 >= var5 && var13 < var5 + 8 && var14 >= var6 && var14 < var6 + 8) {
                  int var17 = var13 & 7;
                  int var18 = var14 & 7;
                  int var19 = var7 & 3;
                  int var16;
                  if (var19 == 0) {
                     var16 = var17;
                  } else if (var19 == 1) {
                     var16 = var18;
                  } else if (var19 == 2) {
                     var16 = 7 - var17;
                  } else {
                     var16 = 7 - var18;
                  }

                  int var20 = var2 + var16;
                  int var21 = var3 + class18.method60(var13 & 7, var14 & 7, var7);
                  int var22 = (var13 & 7) + var8 + var2;
                  int var23 = var3 + (var14 & 7) + var9;
                  Messages.loadTerrain(var24, var1, var20, var21, var22, var23, var7);
               } else {
                  Messages.loadTerrain(var24, 0, -1, -1, 0, 0, 0);
               }
            }
         }
      }

   }

   @ObfuscatedName("f")
   @ObfuscatedSignature(
      descriptor = "(B)V",
      garbageValue = "-107"
   )
   static void method99() {
      Iterator var0 = Messages.Messages_hashTable.iterator();

      while(var0.hasNext()) {
         Message var1 = (Message)var0.next();
         var1.clearIsFromFriend();
      }

   }

   @ObfuscatedName("ah")
   @ObfuscatedSignature(
      descriptor = "(ILbm;ZB)I",
      garbageValue = "94"
   )
   static int method98(int var0, Script var1, boolean var2) {
      if (var0 == 6754) {
         int var3 = Interpreter.Interpreter_intStack[--class87.Interpreter_intStackSize];
         NPCComposition var4 = class129.getNpcDefinition(var3);
         Interpreter.Interpreter_stringStack[++Interpreter.Interpreter_stringStackSize - 1] = var4 != null ? var4.name : "";
         return 1;
      } else if (var0 == 6764) {
         class87.Interpreter_intStackSize -= 2;
         NPCComposition var5 = class129.getNpcDefinition(class87.Interpreter_intStackSize);
         int var6 = Interpreter.Interpreter_intStack[class87.Interpreter_intStackSize + 1];
         Interpreter.Interpreter_intStack[++class87.Interpreter_intStackSize - 1] = var5.method952(var6);
         Interpreter.Interpreter_intStack[++class87.Interpreter_intStackSize - 1] = var5.method959(var6);
         return 1;
      } else {
         return 2;
      }
   }

   @ObfuscatedName("kq")
   @ObfuscatedSignature(
      descriptor = "(Lkd;I)Z",
      garbageValue = "-969832307"
   )
   static final boolean runCs1(Widget var0) {
      if (var0.cs1Comparisons == null) {
         return false;
      } else {
         for(int var1 = 0; var1 < var0.cs1Comparisons.length; ++var1) {
            int var2 = GraphicsObject.method423(var0, var1);
            int var3 = var0.cs1ComparisonValues[var1];
            if (var0.cs1Comparisons[var1] == 2) {
               if (var2 >= var3) {
                  return false;
               }
            } else if (var0.cs1Comparisons[var1] == 3) {
               if (var2 <= var3) {
                  return false;
               }
            } else if (var0.cs1Comparisons[var1] == 4) {
               if (var3 == var2) {
                  return false;
               }
            } else if (var2 != var3) {
               return false;
            }
         }

         return true;
      }
   }
}
