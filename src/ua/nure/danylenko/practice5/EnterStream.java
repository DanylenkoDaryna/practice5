package ua.nure.danylenko.practice5;

import java.io.InputStream;

public class EnterStream extends InputStream {
    private int entries;
        @Override
        public int read()  {
           if(entries==0) {
               try {
                   Thread.sleep(2000);
               } catch (InterruptedException e) {
                   e.printStackTrace();
               }
           }
           entries=entries+1;
           int count=0;
           byte[] b=System.lineSeparator().getBytes();
           for(byte bt:b){
               count=count+(bt& 255);
            }
           return  entries==0 ? count : -1;
        }
    }
