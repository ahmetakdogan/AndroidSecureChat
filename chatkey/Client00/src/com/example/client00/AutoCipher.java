package com.example.client00;

public class AutoCipher {

  private static String alfabe = new String("abcçdefgðhýijklmnoöpqrsþtuüvwxyzABCÇDEFGÐHIÝJKLMNOÖPQRSÞTUÜVWXYZ .:,;+'!$&/()[]={}*?-_<0123456789");
  private static String alfabe2 = new String("Vça.U0b:1ðQWc,2dÇR+ý3e'4f!5gS$6h&Ð 7iX/8öjT(9k)üZAlÜ[Bm]Cn=ÞDo{Ep}YF;qþ*Gr?Hs-It_Ju<KvLwMxNyOÝzPÖ");
  
  
  public static String autoCipher(String planText, int key){

	  //int key = 12;

	   int planArray[] = new int[10000];
	   int key_array[] = new int[planText.length()];
	   key_array[0] = key;


	    for (int i = 0; i < planText.length(); i++) {
	          planArray[i] = alfabe.indexOf(planText.charAt(i));
	    }
	    for (int i = 1; i < planText.length() ; i++) {
	         key_array[i] = planArray[i-1];
	    }

	    String cipherText = "";
	    for (int i = 0; i < planText.length() ; i++) {
	        cipherText += alfabe.charAt((key_array[i] + planArray[i]) % 97);
	     }
		return trans(cipherText);
	  
   }
  
  public static String deAuto(String cipherText, int key){
     // int key = 12;

	  cipherText = deTrans(cipherText);
      int cipherArray[] = new int[10000];
      int key_array[] = new int[cipherText.length()];
      key_array[0] = key;

      for (int i = 0; i < cipherText.length(); i++) {
          cipherArray[i] = alfabe.indexOf(cipherText.charAt(i));
      }

      int x = 0;
      String planText = "";
      for (int i = 0; i < cipherText.length() ; i++) {
          x = cipherArray[i] - key;
          if(x < 0){
           planText += alfabe.charAt(97+x);
           key = 97+x;
          }
          else  planText += alfabe.charAt(x % 97);
          key = x % 97;
      }
	return planText;
   }
  
  public static String trans(String pText){

		String cText = "";
		int pArray[] = new int[10000];
		
		for (int i = 0; i < pText.length(); i++) {
	          pArray[i] = alfabe.indexOf(pText.charAt(i));
	    }
		
		for (int i = 0; i < pText.length(); i++) {
			cText += alfabe2.charAt(pArray[i]);
		}
	return cText;
	}
  
  public static String deTrans(String cText){
		
		String pText = "";
		
		int cArray[] = new int[10000];
		
		for (int i = 0; i < cText.length(); i++) {
	          cArray[i] = alfabe2.indexOf(cText.charAt(i));
	    }
		
		for (int i = 0; i < cText.length(); i++) {
			pText += alfabe.charAt(cArray[i]);
		}
	 return pText;
	}
  
}
