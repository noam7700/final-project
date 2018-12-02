const puppeteer = require('puppeteer');

(async function main() {
	try {
		const browser = await puppeteer.launch({ headless: true });
		const page = await browser.newPage();
		
		//first calculate number of sections
		await page.goto('https://experts.shopify.com/');
		await page.waitForSelector('.section');
		const sections = await page.$$('.section');
		let len = sections.length;
		
		for (let i = 0; i < len; i++) {
			
			//go take the sections every time...
			await page.goto('https://experts.shopify.com/');
			await page.waitForSelector('.section');
			const sections = await page.$$('.section');
			
			const section = sections[i];
			const button = await section.$('a.marketing-button');
			const buttonName = await page.evaluate(button => button.innerText, button);
			console.log('\n\n');
			console.log(buttonName); //section name
			button.click();
			
			await page.waitForSelector('#ExpertsResults'); //'#' for element's id
			const lis = await page.$$('#ExpertsResults > li');
			
			// loop over each li of the ExpertResults ul
			for (const li of lis) {
				const name = await li.$eval('h2', (h2) => {
					return h2.innerText;
				});
				console.log('name:', name);
			}
		}
		
		console.log('\n\ndone');
		await browser.close();
	} catch (e) {
		console.log('our error', e);
	}
})();