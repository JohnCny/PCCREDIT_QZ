/*    */ package com.cardpay.pccredit.QZBankInterface.service;
/*    */ 
/*    */ import java.io.PrintStream;
/*    */ import java.security.MessageDigest;
/*    */ 
/*    */ public class MD5Util
/*    */ {
/*    */   public static final String encode(String s)
/*    */   {
/* 12 */     char[] hexDigits = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };
/*    */     try {
/* 14 */       byte[] btInput = s.getBytes();
/*    */ 
/* 16 */       MessageDigest mdInst = MessageDigest.getInstance("MD5");
/*    */ 
/* 18 */       mdInst.update(btInput);
/*    */ 
/* 20 */       byte[] md = mdInst.digest();
/*    */ 
/* 22 */       int j = md.length;
/* 23 */       char[] str = new char[j * 2];
/* 24 */       int k = 0;
/* 25 */       for (int i = 0; i < j; i++) {
/* 26 */         byte byte0 = md[i];
/* 27 */         str[(k++)] = hexDigits[(byte0 >>> 4 & 0xF)];
/* 28 */         str[(k++)] = hexDigits[(byte0 & 0xF)];
/*    */       }
/* 30 */       return new String(str);
/*    */     } catch (Exception e) {
/* 32 */       e.printStackTrace();
/* 33 */     }return null;
/*    */   }
/*    */ 
/*    */   public static void main(String[] args)
/*    */   {
/* 38 */     System.out.print(encode("00-24-D6-9A-67-62"));
/*    */   }
/*    */ }

/* Location:           D:\eclipse_xiakai\workspace\qzbank_ydxx\WebContent\WEB-INF\lib\powerweb20151126.jar
 * Qualified Name:     com.pactera.powerweb.util.MD5Util
 * JD-Core Version:    0.6.2
 */