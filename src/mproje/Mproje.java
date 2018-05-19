package mproje;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Mproje {
    //Static tanımladık çünkü programın her yerinde kullanabileceğimiz değişken ve diziler.
    public static String al;
    public static boolean bulundu;
    public static int [] hashdizi= new int[211];
    public static String [] hashkelime= new String[211];
   
    public static int asciiconvert(String satir)
    {
        int kelimeTop = 0;
        char[] dizi=new char[satir.length()];//Gelen Stringin boyunda karakter dizisi oluşturduk.
        dizi=satir.toCharArray();//Gelen Stringin karakterleri karakter dizisine atıldı.
        for(int i=0; i<dizi.length; i++)
        {
            int harfAscii =(int)dizi[i] ;
            kelimeTop += harfAscii*((i+1)^2) ;//Ascii bulma fonksiyonu           
        }
        return kelimeTop;
    }

    public static void asciiListe(int kelimeTop, String kelime)//hashtable'a yerleştirme fonksiyonu
    {
        for (int j = 0; j < hashdizi.length; j++) {
            int mod = kelimeTop%211 ;
            if(hashdizi[mod] == 0)
            {
                hashdizi[mod] = kelimeTop ;
                hashkelime[mod] = kelime;
                break; 
            }
            else
            {
                int kalan  = (mod+(j*j))%211 ;
                if(hashdizi[kalan] == 0)
                {
                    hashdizi[kalan] = kelimeTop ;
                    hashkelime[kalan] = kelime;
                    break;
                }
            }
        }
    }
    
    public static boolean arama(String aranan)//Normal arama fonksiyonu
    {
        int asciideger=asciiconvert(aranan);  
        int mod=asciideger%211;
            if(hashdizi[mod]==0)
            {
                System.out.println(aranan+" kelimesi bulunamadı.. ");
                bulundu=false;
            }
            else if(hashkelime[mod].equals(aranan))//aranan kelime ile hashkelime[mod]birebir mi diye karşılaştırır. 
            {
                System.out.println(mod+". sırada "+aranan+" kelimesi bulundu..");
                bulundu=true;
            }
            else{
                for(int i=0; i<aranan.length(); i++)
                {
                    int kalan  = (mod+(i*i))%211 ;
                    if(hashdizi[kalan]!=0)
                    {
                        if(hashkelime[kalan].equals(aranan))//aranan kelime ile hashkelime[kalan]birebir mi diye karşılaştırır.
                        {
                        System.out.println(mod+". sırada "+aranan+" kelimesi bulundu..");
                        bulundu=true;
                        break;
                        }
                        else{
                            System.out.println(aranan+" kelimesi bulunamadı.. ");
                            bulundu=false;
                            break;
                        }
                    }
                    else
                    {
                        System.out.println(aranan+" kelimesi bulunamadı.. ");
                        bulundu=false;
                        break;
                    }
                }    
            }
            return bulundu;
    }    
    /*Mantığı aranan kelimenin bir karakter dizinde tutulması ve for ile bu kelimenin önce 0. indisi
    daha sonra 1... gibi karakterlerinin silinip aranmasını sağlar.*/
    public static void harfEksikArama(String aranan){//Harfleri teker teker azaltıp arama yapan fonksiyon.
        char[] dizi=new char[aranan.length()];
        int a=0;
        System.out.println("-----------------------------------");
        System.out.println("Harf eksiltme araması");
        System.out.println("-----------------------------------");
        for (int i = 0; i < aranan.length(); i++) 
        {
            for(int j=0;j<aranan.length();j++)
            {
                if(i!=j)
                {
                    dizi[a]=aranan.charAt(j);//Stringin karakterlerini bir önceki karaktere atıyor böylece eksiltme işlemi yapar
                    a++;
                }
            }
            a=0;
            String search = new String(dizi);
            int ascii=asciiconvert(search);
            int mod=ascii%211;
            
            for (int k = 0; k < dizi.length; k++) 
            {
                if(hashdizi[mod]==0)
                {
                    System.out.println(search+" kelimesi bulunamadı.. ");
                    break;
                }
                else if(hashkelime[mod].equals(search)){
                        System.out.println(mod+". sırada "+search+" kelimesi bulundu..");
                        break;
                }else 
                    mod = mod+(k*k)%211;
                    if(ascii==hashdizi[mod]){
                        System.out.println(mod+". sırada "+search+" kelimesi bulundu..");
                        break;
                    }
                    else if(hashkelime[mod].equals(search)){
                        System.out.println(mod+". sırada "+search+" kelimesi bulundu..");
                        break;
                    }else{
                        System.out.println(search+" kelimesi bulunamadı.. ");
                        break;
                    } 
                }
            }
        }
   
    
    public static void yerDegisAra(String aranan){//Komşu harflerin yer değiştirme yaptığı fonksiyon
        
        char[] dizi=new char[aranan.length()];
        char[] dizi2=new char[aranan.length()];
        dizi=aranan.toCharArray();
        dizi2=aranan.toCharArray();
        System.out.println("-----------------------------------");
        System.out.println("Harf yer değiştirme araması");
        System.out.println("-----------------------------------");
        for(int i=0;i<dizi.length-1;i++)
        {
                dizi[i] = dizi2[i+1];//dizinin elemanları yer değiştirir.
                dizi[i+1] = dizi2[i];
                aranan = String.valueOf(dizi);
                arama(aranan);
                dizi[i] = dizi2[i];//tekrar eski haline gelir.
                dizi[i+1] = dizi2[i+1];      
        }
    }
    public static void main(String[] args)
    {
        String path = "kelime.txt";
        File f= new File(path);//okuma işlemi başlar.
        try
        {
            Scanner okuma=new Scanner(f);
            while(okuma.hasNextLine())
            {
                al=okuma.nextLine();//okunan satırı al stringinde tutar
                asciiListe(asciiconvert(al), al);//asciidegerlerinin toplamı ve kelimeyi listeye yerleştirir.
            }
        } 
        catch(FileNotFoundException e)
        {
            System.err.println("Doysa okunamadı dizini kontrol ediniz.");
        }
        for(int i=0;i<hashdizi.length;i++)
        {
            System.out.println("["+i+"]      "+hashdizi[i]+"    "+hashkelime[i]);//Ekrana yazar hash table        
        }
        String aranan;
        Scanner s=new Scanner(System.in);//klavyeden değer almak için.
        for(int i=0;i<25;i++)//Rastgele 25 dedim çoğalabilir.
        {
            System.out.println("Aranacak kelimeyi giriniz :");
            System.out.println("-----Çıkış için: exit-----");
            aranan=s.next();
            switch(aranan)
            {
                case "EXIT"://Program EXIT yada exit yazıldığında sonlanır.
                case "exit":
                    System.exit(0);
                    break;
                default:
                arama(aranan);
                if(bulundu==false)
                {
                    harfEksikArama(aranan);
                    yerDegisAra(aranan);
                }
            }
        }
   
    }
}


