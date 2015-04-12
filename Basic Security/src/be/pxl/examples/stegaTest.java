package be.pxl.examples;

import java.io.*;

public class stegaTest
{  public static void main(String[] args)
   {  new stegaTest(); }

   public stegaTest()
   {  
      decrypt(7);

      System.out.println();
   }  
        
   public void decrypt(int max)
   {      
      try
      {
         RandomAccessFile data = new RandomAccessFile("face.bmp","rw");
         long size = data.length();

         int num = 0;       // adds up the values of the LSB bits
         int power = 128;   // powers of 2 for bit values
         int bits = 0;      // counts bits, stop at 8
         int count = 0;     // counts characters, stopping at max
         
         for (int x=54; x < size; x++)
         {
            data.seek(x);
            byte b = data.readByte();

            b = (byte)(b & 1);         // mask the LSB bit
            
            num = num + b * power;     // add up bit values
            
            bits = bits + 1;           // counting bits up to 8
            
            power = power / 2;         // next power of 2
            
            if (bits % 8 == 0 )        // at end of each byte
            {  char c = (char)num;        // CAST to char
               System.out.print(c);     // print the char
               power = 128;               // start over for next byte
               num = 0;                   // restart total for next byte
               count = count + 1;         // counting chars
               if (count >= max)
               {  return;  }              // stop when max chars printed
            }   
         }
         data.close();
      }
      catch (IOException ex)
      { }
   }
}

/****

The face.bmp file (above) contains a secret message, recorded with 
simple LSB Steganography.  The program above will print the message.

(1) Copy the picture and use a HEX EDITOR to READ the secret
    message by writing down the LSB bits.  Whenever a byte is ODD,
    the LSB is 1.  Whenever a byte is EVEN, the LSB is 0.
    The message is 7 bytes (56 bits) long.  After writing down
    the bits, you will need to add up each byte to get the ASCII code,
    then convert it to a letter.  Remember that the ASCII code 
    for 'A' is 65, and the ASCII code for 'a' is 97.

(2) AFTER decoding by hand, use the program above to decrypt the 
    message automatically.  Check that they match correctly.

(3) Now think of a 4-letter word (your choice, anything you like).
    Write the ASCII codes for the letters, convert each to binary
    bits, and then use the hex editor to type the message into
    in the .bmp file and save it.

(4) Now use the DECRYPT program to decode your message -
    hopefully it comes out correctly.
    
(5) Now you probably noticed that translating characters into binary
    and back by hand is pretty annoying.  The program above takes care
    of changing bits into characters.  We need a nice method that can
    change a char into bits.  
    
    Do you remember the easy way to change an integer into binary bits?
    Divide by 2 - write the remainder (either 0 or 1) and then repeat.
    When you are finished, read the remainders BACKWARD.  
    
    Write a method that accepts an int as a parameter and returns
    a STRING containing 1's and 0's, in the correct order, representing
    the binary equivalent of the int parameter.  HINT - calculate the
    remainder first by using %, and THEN divide the number by 2.

(6) Explain the purpose of each of the following commands
    ( they do things with single bits ).  Assume that B is a byte.
    
    byte P = B & 255;
    
    byte Q = B | 255;
    
    byte R = B ^ 255;
    
    byte S = B | 1;
    
    byte T = B & 1;
    
    You can check your answers by using a simple test program
    and printing the results.
    
****/
