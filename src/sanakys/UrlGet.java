/*
 * Author: Peter Aldaron Zhang
 * Date: Ti 27 Elokuuta 2013
 * Contains the UrlGet Class
 * Gets URL data and returns a string of HTML code.
 */

package sanakys;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.net.URL;

public class UrlGet {
	private URL url;
	private String HTMLString;
	public UrlGet(String link){
		try{
			this.url=new URL(link);
			this.HTMLString=new String("");
		}
		catch (Exception e){}
	}
	private void getData(){
		try{
			BufferedReader in = new BufferedReader(
					new InputStreamReader(url.openStream()));
			String inputLine;
			while((inputLine=in.readLine())!=null){
				HTMLString+=inputLine;
				HTMLString+="\n";
			}
			in.close();
		}
		catch(IOException e){
			System.out.println("ERROR"+e.getMessage());
		}	
	}
	public String toString(){
		getData();
		return HTMLString;
	}
}
