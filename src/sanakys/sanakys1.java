package sanakys;
import com.sun.*;
import java.util.*;
import java.lang.*;
public class sanakys1 {

	public static void main(String[] args) {
      Scanner sc=new Scanner(System.in);
      System.out.println("+++++++++++++++++++++++++++++++");
      System.out.print(">>>");
      while(sc.hasNextLine()){
		    HTMLParse parser=new HTMLParse("http://en.m.wiktionary.org/wiki/"+sc.nextLine());
        try{
			  parser.parse("Finnish");
        }
        catch (java.lang.StringIndexOutOfBoundsException e){
          System.out.println("word doesn't exist");
        }
        System.out.println("+++++++++++++++++++++++++++++++");
        System.out.print(">>>");
      }

	}

}
