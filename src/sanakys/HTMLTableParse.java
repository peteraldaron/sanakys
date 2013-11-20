/**
 * HTMLTableParse class
 * Parses HTML table elements
 * Input: a string that contains <table>...</table>
 * Output:a string without the tags but still (sort of) keeps table organization.
 */

package sanakys;
import java.lang.StringBuilder;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

//using a StringBuilder for faster string construction. 
public class HTMLTableParse {
	public static String getFormattedTableString(String data){
		//init the stringbuilder
		StringBuilder sb;
		if(data!=null)
			sb=new StringBuilder(data);
		else
			//throw an exception since stringbuilder won't catch it
			throw new java.lang.NullPointerException();
		//end of init stringbuilder
		
		String localdata=this.data;
		//process th & td:
		localdata=localdata.replaceAll("<t[h,d](\\s?.)*?>", "");
		localdata=localdata.replaceAll("</t[h,d]>", " ");
		//process tr:
		localdata=localdata.replaceAll("<tr(\\s?.)*?>", "");
		localdata=localdata.replaceAll("</tr>","\n");
		//process the rest:
		localdata=localdata.replaceAll("<(\\s?.)+?>", "");
		localdata=localdata.replaceAll("&#160;", " ");
		return localdata;
	}
}
