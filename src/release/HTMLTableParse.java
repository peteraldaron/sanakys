/*
 * Parses HTML table elements
 * Input: a string that contains <table>...</table>
 * Output:a string without the tags but still (sort of) keeps table organization.
 */

public class HTMLTableParse {
	private String data;
	public HTMLTableParse(String data){
		this.data=data;
	}
	public String getString(){
		//It works! More readable than the previous method too.
		//returns the table:
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
