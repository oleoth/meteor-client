import java.util.Collection;
import java.util.Iterator;
import net.runelite.mapping.Export;
import net.runelite.mapping.Implements;
import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;

@ObfuscatedName("mv")
@Implements("IterableNodeDeque")
public class IterableNodeDeque implements Iterable, Collection {
   @ObfuscatedName("h")
   @ObfuscatedSignature(
      descriptor = "Lpl;"
   )
   Node sentinel = new Node();
   @ObfuscatedName("e")
   @ObfuscatedSignature(
      descriptor = "Lpl;"
   )
   Node field3553;

   public IterableNodeDeque() {
      this.sentinel.previous = this.sentinel;
      this.sentinel.next = this.sentinel;
   }

   @ObfuscatedName("h")
   public void rsClear() {
      while(this.sentinel.previous != this.sentinel) {
         this.sentinel.previous.remove();
      }

   }

   @ObfuscatedName("e")
   @ObfuscatedSignature(
      descriptor = "(Lpl;)V"
   )
   public void addFirst(Node var1) {
      if (var1.next != null) {
         var1.remove();
      }

      var1.next = this.sentinel.next;
      var1.previous = this.sentinel;
      var1.next.previous = var1;
      var1.previous.next = var1;
   }

   @ObfuscatedName("v")
   @ObfuscatedSignature(
      descriptor = "(Lpl;)V"
   )
   public void addLast(Node var1) {
      if (var1.next != null) {
         var1.remove();
      }

      var1.next = this.sentinel;
      var1.previous = this.sentinel.previous;
      var1.next.previous = var1;
      var1.previous.next = var1;
   }

   @ObfuscatedName("m")
   @ObfuscatedSignature(
      descriptor = "()Lpl;"
   )
   public Node last() {
      return this.method1888((Node)null);
   }

   @ObfuscatedName("q")
   @ObfuscatedSignature(
      descriptor = "(Lpl;)Lpl;"
   )
   Node method1888(Node var1) {
      Node var2;
      if (var1 == null) {
         var2 = this.sentinel.previous;
      } else {
         var2 = var1;
      }

      if (var2 == this.sentinel) {
         this.field3553 = null;
         return null;
      } else {
         this.field3553 = var2.previous;
         return var2;
      }
   }

   @ObfuscatedName("f")
   @ObfuscatedSignature(
      descriptor = "()Lpl;"
   )
   public Node previous() {
      Node var1 = this.field3553;
      if (var1 == this.sentinel) {
         this.field3553 = null;
         return null;
      } else {
         this.field3553 = var1.previous;
         return var1;
      }
   }

   @ObfuscatedName("r")
   int method1890() {
      int var1 = 0;

      for(Node var2 = this.sentinel.previous; var2 != this.sentinel; var2 = var2.previous) {
         ++var1;
      }

      return var1;
   }

   @ObfuscatedName("u")
   public boolean method1891() {
      return this.sentinel.previous == this.sentinel;
   }

   @ObfuscatedName("b")
   @ObfuscatedSignature(
      descriptor = "()[Lpl;"
   )
   Node[] method1892() {
      Node[] var1 = new Node[this.method1890()];
      int var2 = 0;

      for(Node var3 = this.sentinel.previous; var3 != this.sentinel; var3 = var3.previous) {
         var1[var2++] = var3;
      }

      return var1;
   }

   @ObfuscatedName("j")
   @ObfuscatedSignature(
      descriptor = "(Lpl;)Z"
   )
   boolean method1894(Node var1) {
      this.addFirst(var1);
      return true;
   }

   @ObfuscatedName("size")
   public int size() {
      return this.method1890();
   }

   public boolean contains(Object var1) {
      throw new RuntimeException();
   }

   @ObfuscatedName("iterator")
   public Iterator iterator() {
      return new IterableNodeDequeDescendingIterator(this);
   }

   @ObfuscatedName("isEmpty")
   public boolean isEmpty() {
      return this.method1891();
   }

   @ObfuscatedName("toArray")
   public Object[] toArray() {
      return this.method1892();
   }

   @ObfuscatedName("toArray")
   public Object[] toArray(Object[] var1) {
      int var2 = 0;

      for(Node var3 = this.sentinel.previous; var3 != this.sentinel; var3 = var3.previous) {
         var1[var2++] = var3;
      }

      return var1;
   }

   public boolean remove(Object var1) {
      throw new RuntimeException();
   }

   public boolean containsAll(Collection var1) {
      throw new RuntimeException();
   }

   public boolean addAll(Collection var1) {
      throw new RuntimeException();
   }

   public boolean removeAll(Collection var1) {
      throw new RuntimeException();
   }

   public boolean retainAll(Collection var1) {
      throw new RuntimeException();
   }

   @ObfuscatedName("add")
   public boolean add(Object var1) {
      return this.method1894((Node)var1);
   }

   @ObfuscatedName("equals")
   public boolean equals(Object var1) {
      return super.equals(var1);
   }

   @ObfuscatedName("hashCode")
   public int hashCode() {
      return super.hashCode();
   }

   @ObfuscatedName("clear")
   public void clear() {
      this.rsClear();
   }

   @ObfuscatedName("x")
   @ObfuscatedSignature(
      descriptor = "(Lpl;Lpl;)V"
   )
   public static void IterableNodeDeque_addBefore(Node var0, Node var1) {
      if (var0.next != null) {
         var0.remove();
      }

      var0.next = var1;
      var0.previous = var1.previous;
      var0.next.previous = var0;
      var0.previous.next = var0;
   }
}
