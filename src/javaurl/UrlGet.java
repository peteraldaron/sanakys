/*
 * Author: Peter Aldaron Zhang
 * Date: Ti 27 Elokuuta 2013
 * Contains the UrlGet Class
 * Gets URL data and returns a string of HTML code.
 */

package javaurl;
import java.io.*;
import java.net.*;

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
	
	public static void main(String[]args) throws Exception{
		UrlGet instr=new UrlGet("http://en.wiktionary.org/wiki/minä");
		System.out.println(instr.toString());
	}
}
