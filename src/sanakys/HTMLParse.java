/*
 * Author:Peter Aldaron Zhang
 * Date: Ti 27 Elokuuta 2013
 * An attempt to write a basic html parser for wiktionary
 * This is basically a search and find problem on a string. 
 * for now, let's do everything linearly
 */
package sanakys;
import java.util.*;
import java.io.*;
import java.util.regex.*;

public class HTMLParse {
	private String html;
	private String deScripted;
	
	public HTMLParse(String url){
		this.html=(new UrlGet(url)).toString();
		this.deScripted="";
	}
	public String toString(){
		return html;
	}
	
	public void parse(String lang){
		//work on specified language section;
		//first grab tables out:
		Pattern p;
		Matcher m;
		String tablepattern="<table(.)+?</table>";
		String s=this.getLangSec(lang);
		p=Pattern.compile(tablepattern);
		m=p.matcher(s);
		while(m.find()){
			String temp=m.group();
			HTMLTableParse htp=new HTMLTableParse(temp);
			//replace the string inside the old string with the new string:
			s=s.substring(0, m.start())+htp.getString()+s.substring(m.end());
			//update m
			m=p.matcher(s);
		}
		//table processing complete. now let's deal with linebreak headers:
		//</hX>,</li>,</ul>,
		s=s.replaceAll("</(h[0-9]|ul|li|p)>", "\n");
		//</dd>,</dl>,</dt>
		s=s.replaceAll("</?d[d,l,t]>", " ");
		//all other tags:
		s=s.replaceAll("<(.*?)>", "");
		//remove "[edit]"s:
		s=s.replaceAll("\\[edit\\]", "");
		//fix "&#160;:
		s=s.replaceAll("&#160;", " ");
		//hardcode this:
		s=s.replaceAll("Audio(.+)(file).", "");
		System.out.println(s);
	}
	
	
	public int detectEndTag(int index,String s){
		//detects location of closing tag at given index at <
		//and returns location at "<" of </"
		String tagname="";
		int i=index+1;
		//TODO:consider space
		while(s.charAt(i)!='>'){
			tagname+=s.charAt(i);
			i++;
			if(s.charAt(i)==' ')
				break;
		}
		System.out.println("tagname="+tagname);
		for(i=index+1;i<s.length();i++){
			if(s.charAt(i)=='<' && s.substring(i+1, i+2+tagname.length()).contains(tagname)){
				return i;
			}
		}
		return -1;
		
	}
	
	public String getPlainText(){
		//or you can also use "replaceall":
		String plainText="";
		boolean insideTag=false;
		for(char ch:html.toCharArray()){
			if(ch=='<')
				insideTag=true;
			if(ch=='>'){
				insideTag=false;
				continue;
			}
			if(!insideTag)
				plainText+=ch;
		}
		
		return plainText;
	}
	public void toFile(String filename, String data){
		//writes data to filename
		try{
			PrintWriter writer = new PrintWriter(filename, "UTF-8");
			writer.println("<!DOCTYPE html>");
			writer.println("<html>");
			writer.println(data);
			writer.println("</html>");
			writer.close();
		}
		catch (Exception e){
			System.out.println(e.getMessage());
		}

	}
	
	public String getRmScript(){
		//removes script from html including the tags and returns script removed:
		//and overwrited descripted
		String scripts="", newString="";
		//ranges of script:
		ArrayList<Integer>indices=new ArrayList<Integer>();
		char array[]=html.toCharArray();
		boolean foundScript=false;
		for(int i=0;i<array.length;i++){
			if(array[i]=='<' && array[i+1]=='s' && array[i+2]=='c' && array[i+3]=='r'){
				//shall i check if foundscript is false or not?
				foundScript=true;
				indices.add(new Integer(i));
			}
			if(foundScript && array[i]=='<' && array[i+1]=='/' && array[i+2]=='s'){
				//reached an end of starting script
				//reset foundscript
				foundScript=false;
				indices.add(new Integer(i+8));
				scripts+='\n';
				i+=8;
				
			}
			if(foundScript){
				scripts+=array[i];
			}
			else{
				newString+=array[i];
			}
		}
		this.deScripted=newString;
		return scripts;
		
	}
	
	private String removeScript(){
		//removes script from html and return the result IN ONE LINE!
		//using regex
		//Update:for some reason java doesnt like the "\s" symbol
		//so for now im leaving it as is.. I might be able to use pattern+matcher instead
		String newstring=this.html.replaceAll("\\n", "");
		//System.out.println(newstring);
		this.deScripted=newstring.replaceAll("<script(.)+?/script>", "");	
		//System.out.println(this.deScripted);
		//also process all the white space:
		this.deScripted=this.deScripted.replaceAll(">(\\s)*?<", "><");
		return this.deScripted;
	}
	
	private String getLangSec(String lang){
		//use descripted
		if(this.deScripted=="")
			this.removeScript();
		String s="";
		Pattern p;
		Matcher m;
		String pattern="<span (.{1,20})?id=\""+lang+"(.+?)(<hr|<!-)";
		p=Pattern.compile(pattern);
		m=p.matcher(this.deScripted);
		while(m.find()){
			//System.out.println(m.group());
			s=m.group();
		}
		//trip the last few characters:
		s=s.substring(0,s.length()-4);
		return s;
	}
	
	public static void main(String[] args) {
		HTMLParse parser=new HTMLParse("http://en.wiktionary.org/wiki/nainen");
		System.out.println("Default Lookup language is Finnish, but you can specify language by:");
	    System.out.println( "java HTMLParse [word] [Language]");
	    System.out.println( "Note that word is case sensitive");
		System.out.println("------------------------------------------------");
		parser.parse("Finnish");
	}
}
