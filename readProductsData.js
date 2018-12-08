const puppeteer = require('puppeteer'); //clasic :)
const fs = require('fs');
const util = require('util');

// Convert fs.readFile into Promise version of same. so we can read/write sync
const readFile = util.promisify(fs.readFile);
const writeFile = util.promisify(fs.writeFile);

(async function main() {
	try {
		const browser = await puppeteer.launch({headless: true});
		const page = await browser.newPage();
		
		//probably stupid way to delete previous data
		var outStream = await fs.createWriteStream('ProductsTextData.txt', {'flags': 'w'});
		outStream.write('');
		outStream.end();
		
		//my outFile's stream
		outStream = await fs.createWriteStream('ProductsTextData.txt', {'flags': 'a'});
		
		
		await page.goto('https://www.shufersal.co.il/Pages/Catalog.aspx');
		await page.waitForSelector('.ciw ');
		
		
		
		const button_cats = await page.$$('.ciw ');
		const cats_num = button_cats.length;
		outStream.write('number of cats: '+cats_num+'\n');
		//i starts as 1, because [0] is 'recommended'
		for (let i=1; i<cats_num; i++) {
			console.log('current cat: '+i);
			//reload main shopping page everytime
			await page.goto('https://www.shufersal.co.il/Pages/Catalog.aspx');
			await page.waitForSelector('.ciw ');
			const button_cats = await page.$$('.ciw ');
			
			//goto cats[i]
			const button_cat = button_cats[i];
			const clickable = await button_cat.$('img');
			clickable.click();
			await page.waitForSelector('div[id^="divProduct_"]');
			
			const productsDetails = await page.$$('div[id^="divProduct_"]');
			const pd_num = productsDetails.length;
			//loop over all the products
			for(let i=0; i<pd_num; i++) {
				//probably not necessary
				const prodTextDetails = await productsDetails[i].$('div#divProductDetailsTexts');
				
				let prodIdAttr = await page.evaluate(pd => pd.id, productsDetails[i]);
				prodIdAttr.replace(/ /g, '');
				outStream.write('\n'+prodIdAttr+'\n');
				
				const prodPriceCss = 'span[id="spnEffectivePrice"]';
				const prodPriceStr = await prodTextDetails.$eval(prodPriceCss, el => el.innerText);
				outStream.write('price: '+prodPriceStr+'\n');
				
				const prodDescCss = 'a';
				const prodDescStr = await prodTextDetails.$eval(prodDescCss, el => el.innerText);
				outStream.write('desc: '+prodDescStr+'\n');
				
				const prodBoxSupplierCss = 'div[class="ProdBoxSupplierText"]';
				const prodBoxSupplierStr = await prodTextDetails.$eval(prodBoxSupplierCss, el => el.innerText);
				outStream.write('boxSupplier: '+prodBoxSupplierStr+'\n');
				
				const prodPricePerUnitCss = 'div[id^=divProductPricePerUnit_]';
				const prodPricePerUnitStr = await prodTextDetails.$eval(prodPricePerUnitCss, el => el.innerText);
				outStream.write('pricePerUnit: '+prodPricePerUnitStr+'\n');
			}
		}
		
		
		console.log('done!');
		
		
	} catch(e) {
		writeFile('errors_readProductsData.txt'+e+'\n\n');
	}
})();