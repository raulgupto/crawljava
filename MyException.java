package URLExceptions;

import java.io.IOException;  
import java.io.PrintWriter;

import org.jsoup.Jsoup;  
import org.jsoup.nodes.Document;  
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class MyException {
	
		public static void main( String[] args ) throws IOException{ 
			try{
				PrintWriter writer = new PrintWriter("productOfLetterP.txt", "UTF-8");
			writer.println("All Products of Starting letter P") ;
				Document doc = Jsoup.connect("https://www.cvedetails.com/product-list/firstchar-P/vendor_id-0/products.html").get();  
	        // get all the links of the pages of letter P
			Elements allpages = doc.select("div[id=pagingb]");
			// get the links of all the pages of the letter P
			Elements page = allpages.select("a[href]") ;
//			System.out.println(doc);   // for checking the class and id of the division

//			int i = 1 ;
			for(Element p : page)
			{
				
//				i++ ;
//				if(i == 3) // this is for testing single page
				{
					// this is use to get the link of all the pages
//					System.out.println("data : " +  p.attr("href")) ;
					System.out.println("text : " + p.text());
					
					// connecting the pages 
					Document insidepage = Jsoup.connect("https://www.cvedetails.com" + p.attr("href")).get() ;
//					System.out.println(insidepage) ;
					// now we get the products link of each page
					Elements productslink = insidepage.select("table[class=listtable]") ;
					Elements product = productslink.select("a[href]") ;
					int j = 0 ;
					int k = 4 ;
					int q = 0 ;  // for 1 test product
					for(Element pr : product)
					{
//						System.out.println("------------------------------------" + "j =   " + j + "   ---------------------------------------\n") ;
						if(j == k){
							
							// in this if i get all the product links of the page 
							q++ ; // increment test
//						System.out.println("data : " +  pr.attr("href")) ;
//						System.out.println("text : " + pr.text());
							writer.println("\n\n------" + pr.text() + "--------\n\n") ;
//							if(q == 3) // for fourth product
							{
								Document productdata = Jsoup.connect("https:" + pr.attr("href")).get() ;
//								System.out.println(productdata) ;
								// for each product we get the table 
								Elements productstats = productdata.select("table[class=stats]") ;
								Elements rows = productstats.select("tr") ;
								int size = rows.size() ;
								Element firstrow = rows.get(0) ;  // first row of the table
								Element lastrow = rows.get(size-1) ;  // last row of the table "% of all"
								Elements firstrowcolumn = firstrow.select("th") ;  // first row content
								int colsize = firstrowcolumn.size() ;
								Elements lastrowcolumn = lastrow.select("td") ;// last row content
								
								
								
								for(int y = 0 ; y < colsize-1 ; y++)
								{
									System.out.println(firstrowcolumn.get(y+1).text() + " ---->  " + lastrowcolumn.get(y).text()) ;
									String w = firstrowcolumn.get(y+1).text() + " ---->  " + lastrowcolumn.get(y).text() ;
									writer.println(w) ;
									
								}
								
								
							}
						
						k = k + 7 ; // because of every 7th row we get the product link
						}
						j++ ;
						
						// now i get all product information at j = 4 , 4+7 , 4+7+7 , and so on ;
					
						
					}
				}
			}
			writer.close() ;
	}  
	
	catch(Exception e)
	{
		e.printStackTrace() ;
	}
}
}
