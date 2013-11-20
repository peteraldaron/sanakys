
package sanakys;
public class sanakys {

	public static void main(String[] args) {
		if(args.length==0){
			System.out.println("Default Lookup language is Finnish, but you can specify language by:");
		    System.out.println( "java sanakys [word] [Language]");
		    System.out.println( "Note that word is case sensitive");
		    return;
		}
		System.out.println("------------------------------------------------");
		HTMLParse parser=new HTMLParse("http://en.wiktionary.org/wiki/"+args[0]);
		if(args.length==1)
			parser.parse("Finnish");
		else 
			parser.parse(args[1]);

	}

}
