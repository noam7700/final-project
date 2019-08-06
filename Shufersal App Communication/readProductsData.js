/*
file created format:
line1) #categories
loop #catagories:
	line2.1) cat's name
	line2.2) #pds in cat
	loop #pds in cat:
		line3.1) product's id //(divProduct_...)
		line3.2) product's price
		line3.3) product's description
		line3.4) product's supplier's description
		line3.5) product's price per unit
	end
end

products images arrangement:
in folder named 'images' located in the script's folder. the products images are named by the format 'product_id.png'.
*/

//LEFT: adding scrolling while scraping each category

const puppeteer = require('puppeteer'); //clasic :)
const fs = require('fs');
const util = require('util');
const path = require('path');
const request = require('request');
const empty = require('empty-folder');

// Convert fs.readFile into Promise version of same. so we can read/write sync
const readFile = util.promisify(fs.readFile);
const writeFile = util.promisify(fs.writeFile);

(async function main() {
	try {
		
		console.log('Record video1 :P\n');
		
		const browser = await puppeteer.launch({headless: false});
		const page = await browser.newPage();
		
		//probably stupid way to delete previous data
		var outStream = await fs.createWriteStream('ProductsTextData.txt', {'flags': 'w'});
		outStream.write('');
		outStream.end();
		
		//my outFile's stream
		outStream = await fs.createWriteStream('ProductsTextData.txt', {'flags': 'a'});
		
		//emptying the folder 'images'
	    /*empty('./images', false, (o)=>{
		if(o.error) console.error(o.error);
		});*/
		console.log('Record video1.5 :P\n');
		
		await page.goto('https://www.shufersal.co.il/Pages/Catalog.aspx', {
                timeout: 3000000 //bigger timeout
            });
		await page.waitForSelector('.ciw '); //catagories
		
		console.log('Record video2 :P\n');
		
		const img_buf = [];
		const button_cats = await page.$$('.ciw ');
		const cats_num = button_cats.length;
		let pd_num;
		outStream.write(cats_num+'\n'); //line1
		
		console.log('Record video3 :P\n');
		
		let idbuf = [];
		//i starts as 1, because [0] is 'recommended' and doesn't work as others
		for (let i=1; i<cats_num; i++) {
			console.log('current cat: '+ i);
			
			console.log('Record video4 :P\n');
			
			//reload main shopping page everytime
			await page.goto('https://www.shufersal.co.il/Pages/Catalog.aspx', {
                timeout: 3000000 //bigger timeout
            });
			await page.waitForSelector('.ciw ');
			const button_cats = await page.$$('.ciw ');
			
			//goto cats[i]
			const button_cat = button_cats[i];
			const clickableImg = await button_cat.$('img'); //the img of the catagory is clickable
			const clickableImg_title = await page.evaluate(pd => pd.title, clickableImg);
			outStream.write(clickableImg_title+'\n'); //line2.1
			await clickableImg.click(); //goto current catagory
			let productsDetails;
			
			console.log('Record video5 :P\n');
			
			for(let i = 0; i<8; i++){
				//await sleep(500); // delay for scrolling data to be loaded (a second)
				await page.waitForSelector('div[id^="divProduct_"]', {timeout: 3000000}); //bigger timeout	
				productsDetails = await page.$$('div[id^="divProduct_"]', {timeout: 3000000}); //bigger timeout
				pd_num = productsDetails.length;
				const last_pd_id = await page.evaluate(el=>el.id, productsDetails[pd_num - 1]);
				console.log(last_pd_id);
				const last_selector = "#"+last_pd_id+' > #divProductBg > #divProductDetails > #divProductDetailsTexts > div.prodpricetbl > a'; // selector of the description of last pd
				await page.waitForSelector(last_selector);
				await page.hover(last_selector);
				await sleep(3 * 2500); // delay for scrolling data to be loaded (3 * 1.5 seconds)

			}				
			console.log(pd_num);
			outStream.write(pd_num+'\n'); //line2.2
			
			console.log('Record video6 :P\n');
			
			//loop over all the products			
			for(let i=0; i<pd_num; i++) {
				//probably not necessary
				const prodTextDetails = await productsDetails[i].$('div#divProductDetailsTexts');
				const prodImage = await productsDetails[i].$('div[id^="divProductImage_"]');
		
				//print all pd's text attributes
				let prodIdAttr = await page.evaluate(pd => pd.id, productsDetails[i]);
				prodIdAttr.replace(/ /g, ''); // delete all spaces(' ') <-- I dont know, dont have the courage to check if it's needed
				outStream.write(prodIdAttr+'\n'); //line3.1
				
				if(idbuf.includes(prodIdAttr)) console.log("error!! pd: " + prodIdAttr +" returns itself");
				idbuf.push(prodIdAttr);
				
				const prodPriceCss = 'span[id="spnEffectivePrice"]'; // w/o discounts
				const prodPriceStr = await prodTextDetails.$eval(prodPriceCss, el => el.innerText);
				outStream.write(prodPriceStr+'\n'); //line3.2
				
				const prodDescCss = 'a'; //the element is of the type 'a'
				const prodDescStr = await prodTextDetails.$eval(prodDescCss, el => el.innerText);
				outStream.write(prodDescStr+'\n'); //line3.3
				
				const prodBoxSupplierCss = 'div[class="ProdBoxSupplierText"]';
				const prodBoxSupplierStr = await prodTextDetails.$eval(prodBoxSupplierCss, el => el.innerText);
				outStream.write(prodBoxSupplierStr+'\n'); //line3.4
				
				const prodPricePerUnitCss = 'div[id^=divProductPricePerUnit_]';
				const prodPricePerUnitStr = await prodTextDetails.$eval(prodPricePerUnitCss, el => el.innerText);
				outStream.write(prodPricePerUnitStr+'\n'); //line3.5
				
				const prodImgCss = 'img';
				const imgSrc = await prodImage.$eval(prodImgCss, el => el.src);
				outStream.write(imgSrc+'\n'); //line3.6
				/*img_buf.push(`${prodIdAttr}.png`);
				download(imgSrc, `${prodIdAttr}.png`, function(){ // downloading image
					console.log("Image" + i + " downloaded");
				}); */  
			}
		}
		//moving all images to folder named 'images'
		/*await setTimeout(function(){
			for (let i=0; i<img_buf.length; i++) {
				const src = path.join(img_buf[i]);
				const destDir = path.join(`images`);
				
				fs.access(destDir, function(err) {
					if(err)
						fs.mkdirSync(destDir); // makes directory if doesn't exist yet
					
					fs.rename(src, path.join(destDir, img_buf[i]), function(err){
						if(err) throw err;
						console.log("image moved");
					});
				});
			}
		}, 5000); */
		
		console.log('done!');
		process.exit();

		
	} catch(e) {
		console.log('catch... :P\n');
		console.log('errors_readProductsData.txt'+e+'\n\n');
		console.log('end catch... :P\n');
	}
})();

async function download(uri, filename, callback) {
  await request.head(uri, async function(err, res, body) {
    await request(uri)
    .pipe(fs.createWriteStream(filename))
    .on("close", callback);
 });
}
function sleep(ms) {
  return new Promise(resolve => setTimeout(resolve, ms));
}

/*
file created format:
line1) #categories
loop #catagories:
	line2.1) cat's name
	line2.2) #pds in cat
	loop #pds in cat:
		line3.1) product's id //(divProduct_...)
		line3.2) product's price
		line3.3) product's description
		line3.4) product's supplier's description
		line3.5) product's price per unit
	end
end
*/







