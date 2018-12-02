/*
* goto "experts.shopify.com"
* get array of all the sections
* take the button of the first section
* take his name (innerHTML)

* *don't think I needed his section.
* could have used straight "waitForSelector('a.marketing-button')"
*/
const puppeteer = require('puppeteer');

(async function main() {
	try {
		const browser = await puppeteer.launch({ headless: false });
		const page = await browser.newPage();
		
		await page.goto('https://experts.shopify.com/');
		await page.waitForSelector('.section');
		
		const sections = await page.$$('.section');
		const obj = await sections[0].boundingBox();
		console.log(obj);
		console.log(obj.x);
		console.log('real shit:');
		const section = await sections[0];
		const button = await section.$('a.marketing-button');
		let button_name = await (await button.getProperty('innerHTML')).jsonValue();
		console.log(button_name);
		button_name = 'hi!';
		/*
		for (const section of sections) {
			const button = await section.$('a.marketing-button');
			button.click();
		}
		*/
		
	} catch (e) {
		console.log('our error', e);
	}
})();