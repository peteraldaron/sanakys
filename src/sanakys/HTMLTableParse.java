/**
 * HTMLTableParse class
 * Parses HTML table elements
 * Input: a string that contains <table>...</table>
 * Output:a string without the tags but still (sort of) keeps table organization.
 */

package sanakys;
import java.lang.StringBuffer;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

//using a stringbuffer for faster string construction. 
public class HTMLTableParse {
	public static String getFormattedTableString(String data){
		//init the stringbuffer
		StringBuffer sb;
		if(data!=null)
			sb=new StringBuffer(data);
		else
			//throw an exception since stringbuilder won't catch it
			throw new java.lang.NullPointerException();
		//end of init stringbuilder
		
		//process th & td:
		replaceStringsWith("<t[h,d](\\s?.)*?>","",sb);
		replaceStringsWith("</t[h,d]>", " ",sb);
		//process tr:
		replaceStringsWith("<tr(\\s?.)*?>","",sb);
		replaceStringsWith("</tr>", "\n",sb);
		//process the rest:
		replaceStringsWith("<(\\s?.)+?>", "",sb);
		replaceStringsWith("&#160;", " ",sb);
		return sb.toString();
	}
	private static void replaceStringsWith(String target, String replacement,StringBuffer sb){
		//starting tag
		Pattern p=Pattern.compile("target");
		Matcher m=p.matcher(sb);
		while(m.find()){
			m.appendReplacement(sb, replacement);
		}
		m.appendTail(sb);
	}
}
